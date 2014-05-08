package controllers;

import play.mvc.*;
import play.mvc.Http.*;


public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
    	// used to get the username of the current logged in user.
    	// if null, onUnauthorized is called.
    	
    	// TODO: where to save if user is logged in. 
    	// Inside session enough?
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.AuthenticationController.authentication());
    }
}