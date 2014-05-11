package models;


import play.data.validation.Constraints.Required;
import controllers.PasswordHash;

public class LoginModel {
	
	@Required
	public String username;
	
	@Required
	public String password;
	
	public String validate() {

        if ( !authenticate(username, password) ) {
            return "Invalid email or password";
        }
        
        // If passes, return null.
        return null;
    }

	private boolean authenticate(String username, String password) {
		UserModel user = UserModel.findUserByName(username);
		
		if( user == null){
			return false;
		}
		
		try {
			return PasswordHash.validatePassword( password, user.getPassword() );
		} catch (Exception e) {
			return false;
		}
	}
	
}
