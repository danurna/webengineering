package models;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import controllers.PasswordHash;
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
