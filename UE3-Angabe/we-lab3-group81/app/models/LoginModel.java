package models;


import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class LoginModel {
	
	@Required
	@MinLength(4)
	@MaxLength(8)
	public String username;
	
	@Required
	@MinLength(4)
	@MaxLength(8)
	public String password;
	
	public String validate() {
		//FOR DEV
		return null;
		
		/*
        if (authenticate(username, password) == null) {
            return "Invalid email or password";
        }
        // If passes, return null.
        return null;
        */
    }

	private Object authenticate(String username, String password) {
		return null;
	}
	
}
