package controllers;

import models.LoginModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.authentication;
import views.html.registration;

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
		} else {
			
			/* CACHE EXAMPLE
			LoginModel login  = new LoginModel();
			login.username = "gerry";
			login.password = "gerry";
			Cache.set("userkey", login);
			//*/
			
			session("username", loginForm.get().username);
			return redirect(routes.FlowController.index());
		}
	}

	public static Result register() {
		return ok(registration.render());
	}

	public static Result registerSubmit() {
		return null;   	
	}
}
