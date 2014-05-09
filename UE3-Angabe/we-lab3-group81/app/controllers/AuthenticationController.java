package controllers;

import models.UserModel;
import play.*;
import play.api.templates.Html;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;

public class AuthenticationController extends Controller {
	
    public static Result authentication() {
    	// Render authentication
    	Form<UserModel> userForm = Form.form(UserModel.class);
        return ok(authentication.render(userForm));
    }
    
    public static Result loginSubmit() {
    	Form<UserModel> userForm = Form.form(UserModel.class).bindFromRequest();
    	UserModel userModel = userForm.get();
    	//TODO: read information from form
        Logger.info("Username is: " + userModel.username);
        Logger.info("Passwort is: " + userModel.password);
    	return ok(index.render("Logged in"));
    }
    
    public static Result register() {
    	return ok(registration.render());
    }
    
    public static Result registerSubmit() {
    	return null;   	
    }
}
