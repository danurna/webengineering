package controllers;

import models.User;
import play.*;
import play.api.templates.Html;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;

public class AuthenticationController extends Controller {
	
    public static Result authentication() {
    	// Render authentication
    	User test;
    	Form<User> userForm = Form.form(User.class);
        return ok(authentication.render(userForm));
    }
    
    public static Result loginSubmit() {
    	Form<User> userForm = Form.form(User.class).bindFromRequest();
    	User user = userForm.get();
    	//TODO: read information from form
        Logger.info("Username is: " + user.username);
        Logger.info("Passwort is: " + user.password);
    	return ok(index.render("Logged in"));
    }
    
    public static Result register() {
    	return ok(registration.render());
    }
    
    public static Result registerSubmit() {
    	return null;
    }
}
