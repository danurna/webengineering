package controllers;

import play.*;
import play.mvc.*;

import views.html.*;
import at.ac.tuwien.big.we14.lab2.api.*;

public class Application extends Controller {

	@play.db.jpa.Transactional
    public static Result index() {
    	// Show Authentication.html
        return ok(index.render("Your application is ready."));
    }

}
