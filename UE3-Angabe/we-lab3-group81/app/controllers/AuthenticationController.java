package controllers;

import models.LoginModel;
import models.UserModel;
import models.UserRegisterModel;
import play.Logger;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
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

	@Transactional
	public static Result loginSubmit() {
		Form<LoginModel> loginForm = Form.form(LoginModel.class).bindFromRequest();

		if( loginForm.hasErrors() ){
			return badRequest(authentication.render(loginForm));
		} 	
		
		session("username", loginForm.get().username);
		return redirect(routes.FlowController.index());
	}

	public static Result logout() {
		session().clear();
		return redirect(routes.FlowController.index());
	}
	
	public static Result register() {
		Form<UserRegisterModel> registerForm = Form.form(UserRegisterModel.class);
		return ok(registration.render(registerForm));
	}

	@Transactional
	public static Result registerSubmit() {
		Form<UserRegisterModel> registerForm = Form.form(UserRegisterModel.class).bindFromRequest();

		if( registerForm.hasErrors() ){
			return badRequest(registration.render(registerForm));
		} 
		
		// Get data
		UserRegisterModel data = registerForm.get();
		UserModel user = new UserModel();
		user.setName(data.username);
		try {
			user.setPassword(PasswordHash.createHash(data.password));
		} catch (Exception e) {
			return redirect(routes.AuthenticationController.authentication());
		}
		user.setBirthdate(data.birthdate);
		user.setGender(data.gender);
		user.setFirstname(data.firstname);
		user.setLastname(data.lastname);
		JPA.em().persist(user);
		
		session("username", registerForm.get().username);
		return redirect(routes.FlowController.index());
	}
}
