package controllers;

import java.util.ArrayList;
import java.util.List;

import models.UserModel;
import play.Logger;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import at.ac.tuwien.big.we14.lab2.api.Choice;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.QuizGame;
import at.ac.tuwien.big.we14.lab2.api.Round;
import at.ac.tuwien.big.we14.lab2.api.User;
import at.ac.tuwien.big.we14.lab2.api.impl.PlayQuizFactory;

public class FlowController extends Controller {

	@Security.Authenticated(Secured.class)
	public static Result index() {
		return ok(index.render());
	}

	@Security.Authenticated(Secured.class)
	@Transactional
	public static Result quiz() {
		Logger.info("StartQuiz: "+request().username());

		UserModel user = UserModel.findUserByName(request().username());


		QuizFactory factory = new PlayQuizFactory(
				Messages.get("jsonFilePath"),
				user);
		QuizGame game = factory.createQuizGame();
		game.startNewRound();

		Cache.set(request().username(), game);

		// Render quiz with first question*/
		return ok(views.html.quiz.render(game.getPlayers(), game.getCurrentRound()));
	}

	@Security.Authenticated(Secured.class)
	public static Result nextQuestion() {

		QuizGame game = (QuizGame) Cache.get(request().username());
		User user = game.getPlayers().get(0); 
		DynamicForm form = Form.form().bindFromRequest();

		// Retrieve Question Answer
		Round currentRound =  game.getCurrentRound();
		Question currentQuestion = currentRound.getCurrentQuestion(user);
		List<Choice> allChoices = currentQuestion.getAllChoices();
		List<Choice> userChoices = new ArrayList<Choice>();

		// Evaluate answers
		for(int i = 0; i < currentQuestion.getAllChoices().size(); i++){
			String playerAnswer = form.get("option"+(i+1));
			int choiceId = Integer.valueOf(form.get("option"+(i+1)+"Id"));

			if( "on".equals(playerAnswer) ){
				for ( Choice choice : allChoices ){
					if( choice.getId() == choiceId ){
						userChoices.add(choice);
					}
				}
			}
		}

		long timeLeft = Long.valueOf(form.get("timeleftvalue"));
		game.answerCurrentQuestion(user, userChoices, timeLeft);

		Cache.set(request().username(), game);
		
		if (game.isGameOver()) {
			return ok(views.html.quizover.render(game));
		}


		if (game.isRoundOver()) { 
			return ok(views.html.roundover.render(game));
		}


		// Render quiz with next question or roundOver.
		return ok(views.html.quiz.render(game.getPlayers(), currentRound));
	}

	@Security.Authenticated(Secured.class)
	public static Result newRound() {
		
		QuizGame game = (QuizGame) Cache.get(request().username());
		
		game.startNewRound();
		
		Cache.set(request().username(), game);
		
		// Render quiz with first question*/
		return ok(views.html.quiz.render(game.getPlayers(), game.getCurrentRound()));
	}

}
