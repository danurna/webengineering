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
		System.out.println("redirect to start!");
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
		
		List<Category> categories = randomSample(ServletQuizFactory.init(context).
				createQuestionDataProvider().loadCategoryData(), maxRounds);
		
		for (Category category: categories) {
			Round round = new SimpleRound();
			
			round.setCategory(category.getName());
			round.setQuestions(randomSample(category.getQuestions(), maxQuestions));
			
			rounds.add(round);
		}
		
		
		quiz.setRounds(rounds);
		
		
		session.setAttribute("quiz", quiz);
		dispatcher.forward(request, response);
	}

	private void showNextQuestion(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
	}

	private void showNextRound(HttpServletRequest request,
			HttpServletResponse response) {

	}

}