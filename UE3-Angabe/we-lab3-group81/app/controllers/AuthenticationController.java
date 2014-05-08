package controllers;

import play.*;
import play.api.templates.Html;
import play.data.DynamicForm;
import play.data.Form;
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
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
    	//TODO: read information from form
        Logger.info("Username is: " + dynamicForm.get("username"));
        Logger.info("Passwort is: " + dynamicForm.get("password"));
    	return ok(index.render("Logged in"));
    }
    
    public static Result register() {
    	return null;
    }
}
