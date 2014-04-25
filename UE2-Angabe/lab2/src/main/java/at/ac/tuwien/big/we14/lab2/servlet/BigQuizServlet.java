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

    private List<Category> quizData;
    
	protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
		actionHandler(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
		actionHandler(request, response);
	}
    
	private void actionHandler(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {
		String actionParam = request.getParameter("action");
		if (actionParam == null) {
			redirectToStart(response);
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
                redirectToStart(response);
                return;
		}
	}
    
	private void redirectToStart(HttpServletResponse response) throws IOException {
		response.sendRedirect("start.jsp");
	}
	
	// Source: http://eyalsch.wordpress.com/2010/04/01/random-sample/
	private static <T> List<T> randomSample(List<T> items, int m){
	    Random rnd = new Random();
		for(int i=0;i<m;i++){
	        int pos = i + rnd.nextInt(items.size() - i);
	        T tmp = items.get(pos);
	        items.set(pos, items.get(i));
	        items.set(i, tmp);
	    }
	    return items.subList(0, m);
	}
	
	private List<Category> getQuizData() {
		if(quizData == null) {
			ServletContext servletContext = getServletContext();
			QuizFactory factory = ServletQuizFactory.init(servletContext);
			QuestionDataProvider provider = factory.createQuestionDataProvider();
			quizData = provider.loadCategoryData();
		}
		return quizData;
	}

   	
	private void startNewQuiz(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
		
		List<Player> players = new ArrayList<Player>(maxPlayers);
		
		Player test = new SimplePlayer();
		test.setName("Test");
		players.add(test);
        
		Player computer = new SimplePlayer();
		computer.setName("Computer");
		players.add(computer);
		
		
		List<Round> rounds = new ArrayList<Round>(maxRounds);
		for (Category category: getQuizData()) {
			Round round = new SimpleRound();
			
			round.setCategory(category.getName());
			round.setQuestions(randomSample(category.getQuestions(), maxQuestions));
			
			rounds.add(round);
		}
		
		
		Quiz quiz = new SimpleQuiz();
        
		quiz.setPlayers(players);
		quiz.setRounds(rounds);
		
		setSessionQuiz(quiz, request);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/question.jsp");
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

	private void setSessionQuiz(Quiz quiz, HttpServletRequest request) {
		//TODO herausfinden ob das mit der Session auch geht
		
		request.getSession().setAttribute("quiz", quiz);
	}
	
	private Quiz getSessionQuiz(HttpServletRequest request) {
		//TODO herausfinden ob das mit der Session auch geht
		
		return (Quiz) request.getSession().getAttribute("quiz");
	}
	
}