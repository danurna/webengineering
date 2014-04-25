package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.Player;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.Quiz;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.Round;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;
import at.ac.tuwien.big.we14.lab2.api.impl.SimplePlayer;
import at.ac.tuwien.big.we14.lab2.api.impl.SimpleQuiz;
import at.ac.tuwien.big.we14.lab2.api.impl.SimpleRound;

@WebServlet(name = "BigQuiz", urlPatterns = { "/BigQuizServlet" })
public class BigQuizServlet extends HttpServlet {
    private static final long serialVersionUID = -2708561549069343716L;
    private final static int maxRounds = 5;
    private final static int maxQuestions = 3;
    private final static int maxPlayers = 2;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String actionParam = request.getParameter("action");
        if (actionParam == null) {
            return;
        }

        switch (actionParam) {
        case "start":
            startNewQuiz(request, response);
            break;

        case "nextQuestion":
            showNextQuestion(request, response);
            break;

        case "nextRound":
            showNextRound(request, response);
            break;

        default:
            return;
        }

    }

    private void startNewQuiz(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/question.jsp");

        Quiz quiz = new SimpleQuiz();
        Player test = new SimplePlayer(), computer = new SimplePlayer();
        List<Player> players = new ArrayList<Player>(maxPlayers);

        List<Round> rounds = new ArrayList<Round>(maxRounds);


        test.setName("Test");
        players.add(test);

        computer.setName("Computer");
        players.add(computer);

        quiz.setPlayers(players);

        List<Category> categories = ServletQuizFactory.init(context).
                createQuestionDataProvider().loadCategoryData();

        categories = categories.subList(0, maxRounds);
        for (Category category : categories) {
            Round round = new SimpleRound();
            round.setCategory(category);
            List<Question> questions = category.getQuestions();
            questions = questions.subList(0, maxQuestions);
            round.setQuestions(questions);
            rounds.add(round);
        }


        quiz.setRounds(rounds);


        session.setAttribute("quiz", quiz);
        dispatcher.forward(request, response);
    }

    private void showNextQuestion(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            // No session, so don't show next question.
            showStartPage(request, response);
            return;
        }

        ServletContext context = getServletContext();
        Quiz activeQuiz = (Quiz)session.getAttribute("quiz");

        // Retrieve Question Answer
        Round currentRound =  activeQuiz.getRounds().get(activeQuiz.getCurrentRoundNumber());
        Question currentQuestion = currentRound.getQuestions().get(currentRound.getCurrentQuestionNumber());
        Player user = activeQuiz.getPlayer(0);
        Player npc = activeQuiz.getPlayer(1);
        List<Choice> correctChoices = currentQuestion.getCorrectChoices();
        
        System.out.println(user.getRoundAnswers().length);

        // Evaluate answers
        boolean userAnswerCorrect = true;
        for(int i = 0; i < currentQuestion.getAllChoices().size(); i++){
            String playerAnswer = request.getParameter("option"+(i+1));
            int choiceId = Integer.valueOf(request.getParameter("option"+(i+1)+"id"));
            
            boolean answerShouldBeCorrect = false;
            for ( Choice choice : correctChoices ){
                if( choice.getId() == choiceId ){
                    answerShouldBeCorrect = true;
                }
            }
            
            if( "on".equals(playerAnswer) ){
                userAnswerCorrect = userAnswerCorrect && answerShouldBeCorrect;
            }else{
                userAnswerCorrect = userAnswerCorrect && !answerShouldBeCorrect;
            }
        }
        
        System.out.println(userAnswerCorrect ? "True" : "False");
        user.setRoundAnswer(currentRound.getCurrentQuestionNumber(), userAnswerCorrect);
        user.setRoundAnswerTime(currentRound.getCurrentQuestionNumber(), (int)currentQuestion.getMaxTime() - Integer.valueOf(request.getParameter("timeleftvalue")));
        npc.setRoundAnswer(currentRound.getCurrentQuestionNumber(), new Random().nextBoolean());

        activeQuiz.setPlayer(0, user);
        activeQuiz.setPlayer(1, npc);
        currentRound.increaseQuestionNumber();
        
        if( currentRound.getCurrentQuestionNumber() == maxQuestions){
            // Forward to round end page
            session.setAttribute("quiz", activeQuiz);
            RequestDispatcher dispatcher = context.getRequestDispatcher("/roundcomplete.jsp");
            dispatcher.forward(request, response);
            return;
        }

        activeQuiz.setRound(activeQuiz.getCurrentRoundNumber(), currentRound);
        session.setAttribute("quiz", activeQuiz);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/question.jsp");
        dispatcher.forward(request, response);
    }

    private void showNextRound(HttpServletRequest request,
            HttpServletResponse response) {

    }

    private void showStartPage(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/start.jsp");
        dispatcher.forward(request, response);
    }

}