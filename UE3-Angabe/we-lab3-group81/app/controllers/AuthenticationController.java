package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.http.ParseException;
import org.jboss.logging.annotations.Message;

import models.LoginModel;
import models.UserModel;
import models.UserRegisterModel;
import play.Logger;
import play.data.Form;
import play.data.format.Formatters;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.i18n.Messages;
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
		
		Formatters.register(Date.class, new Formatters.SimpleFormatter<Date>(){
			  
			private String pattern = "(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";
			
			@Override
			public Date parse(String arg0, Locale arg1) throws java.text.ParseException{
				Logger.info("String " + arg0);
				Logger.info("Locale " + arg1);
				
				Date date;
				try {
					date = new SimpleDateFormat( Messages.get("form.dateformat"), arg1).parse(arg0);
				} catch (java.text.ParseException e) {
					// As some browsers print 01/01/2000 but send 2000-01-01, we defined this fallback case.
					date = new SimpleDateFormat( Messages.get("form.fallbackformat"), arg1).parse(arg0); 
				}
				
				Logger.info("date " + date);
				return date;
			}

			@Override
			public String print(Date arg0, Locale arg1) {
				// TODO Auto-generated method stub
				return arg0.toString()
			}
		  });
		  
		
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
