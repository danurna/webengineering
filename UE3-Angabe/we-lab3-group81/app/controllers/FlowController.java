package controllers;

import models.QuizMapper;
import models.UserModel;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.QuizGame;
import at.ac.tuwien.big.we14.lab2.api.impl.PlayQuizFactory;

public class FlowController extends Controller {

	@Security.Authenticated(Secured.class)
	public static Result index() {
		if (session().get("username") == null) {
			return redirect(routes.AuthenticationController.authentication());
		} else {
			// Render index

			return ok(index.render());
		}
		/*
		 * CACHE EXAMPLE LoginModel login = (LoginModel) Cache.get("userkey");
		 * if(login != null) { Logger.info("username  is: " + login.username);
		 * Logger.info("password  is: " + login.password); } else {
		 * Logger.info("login is null"); } //
		 */

	}

	@Security.Authenticated(Secured.class)
	@Transactional
	public static Result quiz() {
		Logger.info("StartQuiz: "+request().username());
		
		UserModel user = UserModel.findUserByName(request().username());
		QuizFactory factory = new PlayQuizFactory(
				Messages.get("jsonFilePath"),
				user);
		QuizMapper quizMapper = new QuizMapper();
		quizMapper.setQuiz(factory.createQuizGame());
		quizMapper.setUser(user);
		QuizGame quiz = quizMapper.getQuiz();
		
		JPA.em().persist(quizMapper);
		
		// Render quiz with first question
		return ok(quiz.render(quiz.getPlayers(), quiz.getCurrentRound()));
	}

	@Security.Authenticated(Secured.class)
	public static Result nextQuestion() {
		// Render quiz with next question or roundOver.
		return ok(index.render());
	}

	/*
	 * ############################################################### Bellow
	 * here, methods that are redirected from the above ones. Not called
	 * directly via routes. (Maybe they have to be defined inside routes to use
	 * redirect?)
	 * ###############################################################
	 */

	@Security.Authenticated(Secured.class)
	public static Result roundOver() {
		// Render round over or quizover?
		return null;// ok(roundover.render());
	}

	@Security.Authenticated(Secured.class)
	public static Result quizOver() {
		// Render quiz over
		return null;// ok(quizover.render());
	}
}
