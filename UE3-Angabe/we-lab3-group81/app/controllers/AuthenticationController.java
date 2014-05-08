package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class AuthenticationController extends Controller {

	@play.db.jpa.Transactional
    public static Result start() {
        return ok(index.render("Your new aasdasdpplication is ready."));
    }

}
