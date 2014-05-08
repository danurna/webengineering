package controllers;

import play.*;
import play.api.templates.Html;
import play.mvc.*;
import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;

public class AuthenticationController extends Controller {
	
    public static Result index() {
    	// Wenn erstes mal aufgerufen --> render authentication.html
    	// Ansonsten (eingeloggt) --> redirect quiz.html
    	
        return ok(index.render("Your application is ready."));
    }
    
    public static Result login() {
    	return null;
    }
    
    public static Result register() {
    	return null;
    }
}
