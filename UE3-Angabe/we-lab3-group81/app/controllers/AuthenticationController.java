package controllers;

import models.LoginModel;
import play.*;
import play.api.templates.Html;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;
import play.i18n.Lang;

public class AuthenticationController extends Controller {
	
    public static Result authentication() {
    	// Render authentication
    	Form<LoginModel> loginForm = Form.form(LoginModel.class);
        return ok(authentication.render(loginForm));
    }
    
    public static Result loginSubmit() {
    	Form<LoginModel> loginForm = Form.form(LoginModel.class).bindFromRequest();
    	
    	if( loginForm.hasErrors() ){
    		return badRequest(authentication.render(loginForm));
    	}

    	LoginModel userModel = loginForm.get();
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
