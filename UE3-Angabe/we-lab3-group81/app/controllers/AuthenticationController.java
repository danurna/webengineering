package controllers;

import models.LoginModel;
import models.UserRegisterModel;
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
			
			session("username", loginForm.get().username);
			return redirect(routes.FlowController.index());
		}
	}

	public static Result logout() {
		session().clear();
		return redirect(routes.FlowController.index());
	}
	
	public static Result register() {
		Form<UserRegisterModel> registerForm = Form.form(UserRegisterModel.class);
		return ok(registration.render(registerForm));
	}

	public static Result registerSubmit() {
		Form<UserRegisterModel> registerForm = Form.form(UserRegisterModel.class).bindFromRequest();

		if( registerForm.hasErrors() ){
			return badRequest(registration.render(registerForm));
		} else {	
			session("username", registerForm.get().username);
			return redirect(routes.FlowController.index());
		}	
	}
}
