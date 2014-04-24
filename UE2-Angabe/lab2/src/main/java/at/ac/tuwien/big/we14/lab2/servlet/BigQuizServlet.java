package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Game;
import at.ac.tuwien.big.we14.lab2.api.Player;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;
import at.ac.tuwien.big.we14.lab2.api.impl.SimpleGame;
import at.ac.tuwien.big.we14.lab2.api.impl.SimplePlayer;

@WebServlet(name = "BigQuiz", urlPatterns = { "/BigQuizServlet" })
public class BigQuizServlet extends HttpServlet {
	private static final long serialVersionUID = -2708561549069343716L;
	final static int maxRounds = 5;
	final static int maxQuestions = 3;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String actionParam = request.getParameter("action");
		if (actionParam == null) {
			return;
		}

		switch (actionParam) {
		case "start":
			startNewGame(request, response);
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

	private void printList() {
		ServletContext servletContext = getServletContext();
		QuizFactory factory = ServletQuizFactory.init(servletContext);
		QuestionDataProvider provider = factory.createQuestionDataProvider();
		List<Category> categories = provider.loadCategoryData();
		for (Iterator<Category> it = categories.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}

	private void startNewGame(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/question.jsp");
		
		Player test = new SimplePlayer();
		test.setName("Test");
		Player computer = new SimplePlayer();
		computer.setName("Computer");
		List<Player> players = new ArrayList<Player>();
		players.add(test);
		players.add(computer);
		
		
		Game game = new SimpleGame();
		game.setPlayers(players);
		
		
		
		
		dispatcher.forward(request, response);
	}

	private void showNextQuestion(HttpServletRequest request,
			HttpServletResponse response) {

	}

	private void showNextRound(HttpServletRequest request,
			HttpServletResponse response) {

	}

}