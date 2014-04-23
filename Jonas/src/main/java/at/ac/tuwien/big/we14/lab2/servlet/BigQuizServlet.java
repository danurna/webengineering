package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;
import at.ac.tuwien.big.we14.lab2.model.Game;
import at.ac.tuwien.big.we14.lab2.model.Player;
import at.ac.tuwien.big.we14.lab2.model.Round;

public class BigQuizServlet extends HttpServlet {

	private static final int NUM_ROUNDS = 5;
	private static final int NUM_QUESTIONS_PER_ROUND = 3;

	private static final long serialVersionUID = -5535359999476767368L;
	private static final Map<String, BigQuizServletAction> actions = new HashMap<String, BigQuizServletAction>();

	private final Random random = new Random();

	static {
		actions.put("start", new BigQuizServletAction() {
			@Override
			public void run(BigQuizServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				servlet.start(req, resp);
			}
		});
		actions.put("play", new BigQuizServletAction() {
			@Override
			public void run(BigQuizServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				servlet.play(req, resp);
			}
		});
		actions.put("nextRound", new BigQuizServletAction() {
			@Override
			public void run(BigQuizServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				servlet.nextRound(req, resp);
			}
		});
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest(req, resp);
	}

	private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String actionKey = req.getParameter("action");
		if (actionKey != null) {
			BigQuizServletAction action = actions.get(actionKey);
			if (action != null) {
				action.run(this, req, resp);
			}
		}
	}

	private void start(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();

		Game game = new Game();

		Player player1 = new Player();
		player1.setName("Max Muster");
		game.setPlayer1(player1);

		Player player2 = new Player();
		player2.setName("Computer");
		game.setPlayer2(player2);

		List<Round> rounds = new ArrayList<Round>(NUM_ROUNDS);

		QuizFactory factory = ServletQuizFactory.init(servletContext);
		QuestionDataProvider provider = factory.createQuestionDataProvider();
		List<Category> categories = provider.loadCategoryData();
		Collections.shuffle(categories, random);
		categories = categories.subList(0, NUM_ROUNDS);
		for (Category category : categories) {
			Round round = new Round();
			round.setCategory(category);
			List<Question> questions = category.getQuestions();
			Collections.shuffle(questions, random);
			questions = questions.subList(0, NUM_QUESTIONS_PER_ROUND);
			round.setQuestions(questions);
			rounds.add(round);
		}
		game.setRounds(rounds);

		HttpSession session = req.getSession(true);
		session.setAttribute("game", game);

		RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/question.jsp");
		dispatcher.forward(req, resp);
	}

	private void play(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		HttpSession session = req.getSession(false);
		if (session == null) {
			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/start.jsp");
			dispatcher.forward(req, resp);
			return;
		}
		Game game = (Game) session.getAttribute("game");
		
		/////////////////////////////////////////////////////////
		// EVALUATE SCORE: 
		boolean won = true;
	    String[] interests = req.getParameterValues("option");
	    if (interests!=null && interests.length == game.getCurrentRound().getCurrentQuestion().getCorrectChoices().size()) {
	    	for (Choice choice : game.getCurrentRound().getCurrentQuestion().getCorrectChoices()) {
	    		Integer id = choice.getId();
	    		won=false;
	    		for (String answer : interests) {
	    			if (answer != null && answer.equals(id.toString())) {
	    				won=true;
	    				break;
	    			}
	    		}
	    		if (won==false) {
	    			break;
	    		}
	    	}
	    } else if (game.getCurrentRound().getCurrentQuestion().getCorrectChoices().size() == 0) {
	    	won=true;
	    } else {
	    	won=false;
	    }
	    game.getCurrentRound().getScorePlayer1().put(game.getCurrentRound().getCurrentQuestionIndex(), (won?1:0)); 
	    
	    
	    /////////////////////////////////////////////////////////
	    // "KI":
	    Random random = new Random(System.currentTimeMillis());
	    boolean scoreKi = random.nextBoolean();
	    game.getCurrentRound().getScorePlayer2().put(game.getCurrentRound().getCurrentQuestionIndex(), (scoreKi?1:0)); 

	    
	    /////////////////////////////////////////////////////////
	    //PAGEFLOW:
		int nextQuestionIndex = game.getCurrentRound().getCurrentQuestionIndex() + 1;
		if (nextQuestionIndex >= NUM_QUESTIONS_PER_ROUND) {
			//EVALUATE WHO WON THE CURRENT ROUND:
			int scorePlayer1=0, scorePlayer2=0;
			for (Integer score : game.getCurrentRound().getScorePlayer1().values()) {
				scorePlayer1+=score;
			}
			for (Integer score : game.getCurrentRound().getScorePlayer2().values()) {
				scorePlayer2+=score;
			}
			if (scorePlayer1==scorePlayer2) {
				game.getCurrentRound().setWinner(0);
			} else {
				if (scorePlayer1>scorePlayer2) {
					game.getCurrentRound().setWinner(1);
					game.setGamesWonPlayer1(game.getGamesWonPlayer1()+1);
				} else {
					game.getCurrentRound().setWinner(2);
					game.setGamesWonPlayer2(game.getGamesWonPlayer2()+1);
				}
			}
			
			int nextRoundIndex = game.getCurrentRoundIndex() + 1;
			if (nextRoundIndex >= NUM_ROUNDS) {
				// game complete
				RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/finish.jsp");
				dispatcher.forward(req, resp);
				return;
			}
			// round complete
			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/roundcomplete.jsp");
			dispatcher.forward(req, resp);
			return;
		}
		// next question
		game.getCurrentRound().setCurrentQuestionIndex(nextQuestionIndex);
		session.setAttribute("game", game);
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/question.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void nextRound(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		HttpSession session = req.getSession(false);
		if (session == null) {
			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/start.jsp");
			dispatcher.forward(req, resp);
			return;
		}
		Game game = (Game) session.getAttribute("game");
		int nextRoundIndex = game.getCurrentRoundIndex() + 1;
		game.setCurrentRoundIndex(nextRoundIndex);
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/question.jsp");
		dispatcher.forward(req, resp);
	}
}