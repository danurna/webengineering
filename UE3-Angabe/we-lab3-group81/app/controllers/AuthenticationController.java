package controllers;

import play.*;
import play.api.templates.Html;
import play.mvc.*;
import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;

public class AuthenticationController extends Controller {
	
    public static Result authentication() {
    	// Render authentication
        return ok(authentication.render());
    }
    
    public static Result login() {
    	return ok(index.render("Logged in"));
    }
    
    public static Result register() {
    	return null;
    }
}
