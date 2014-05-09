package models;

import play.data.validation.Constraints.Required;

public class LoginModel {
	
	@Required
	public String username;
	@Required
	public String password;
	
	public String validate() {
        if (authenticate(username, password) == null) {
            return "Invalid email or password";
        }
        // If passes, return null.
        return null;
    }

	private Object authenticate(String username, String password) {
		return null;
	}
	
}
