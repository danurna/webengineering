package controllers;

import models.LoginModel;
import play.*;
import play.api.templates.Html;
import play.mvc.*;
import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;
import play.cache.Cache;

public class FlowController extends Controller {
	
	@Security.Authenticated(Secured.class)
    public static Result index() {
		LoginModel login = (LoginModel) Cache.get("userkey");
		Logger.info("username  is: " + login.username);
		Logger.info("password  is: " + login.password);
		
    	// Render index
        return ok(index.render());
    }
    
	@Security.Authenticated(Secured.class)
    public static Result quiz() {

    	// Render quiz with first question
        return ok(quiz.render(null, null));
    }
	
	@Security.Authenticated(Secured.class)
    public static Result nextQuestion() {
    	// Render quiz with next question or roundOver.
        return ok(index.render());
    }
	
	/* 
	 * ###############################################################
	 *  Bellow here, methods that are redirected from the above ones.
	 *  Not called directly via routes. (Maybe they have to be defined 
	 *  inside routes to use redirect?)
	 * ###############################################################
	 */
	
	@Security.Authenticated(Secured.class)
    public static Result roundOver() {
    	// Render round over or quizover?
        return null;//ok(roundover.render());
    }
	
	@Security.Authenticated(Secured.class)
    public static Result quizOver() {
    	// Render quiz over
        return null;//ok(quizover.render());
    }
}
