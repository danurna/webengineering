package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;

public class Application extends Controller {

	@play.db.jpa.Transactional
    public static Result index() {
    	// Show Authentication.html
		
		// user must implement Interface User
		SimpleUser user = new SimpleUser();
		user.setName("Ronald");
		String jsonFilePath = "conf/data.de.json";
		QuizFactory factory = new PlayQuizFactory(jsonFilePath, user); 
		QuizGame game = factory.createQuizGame(); 
		game.startNewRound(); // start new game/round 
		game.getPlayers().get(0); // get human player
		/*
		Round round = game.getCurrentRound();// current round
		round.getAnswer(questionNr, user); // answer of question nr <questionNr> of <user> 
		Question question = round.getCurrentQuestion(user); // current question of user 
		question.getAllChoices(); // all possible choices for a question 
		round.answerCurrentQuestion(user, choices, time); // user with chosen answers and time left (also automatically answers the question for the computer opponent) 
		round.getRoundWinner(); // winner of round or null if no winner exists yet 
		game.isRoundOver(); // check if round is over
		game.getWonRounds(user); // number of rounds won by the given user game.isGameOver(); // check if game is over
		game.getWinner(); // winner of round or null if no winner exists yet
		*/
		
		
        return ok(index.render("Your application is ready."));
    }

}
