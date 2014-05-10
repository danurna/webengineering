package models;

import java.util.Date;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.data.format.*;

public class UserRegisterModel {	
	public String firstname;
	public String lastname;
	
	// Uses application wide language specific date formats
	public Date birthdate;
	public String gender;
	
	@Required
	@MinLength(4)
	@MaxLength(8)
	public String username;
	
	@Required
	@MinLength(4)
	@MaxLength(8)
	public String password;
	
	public String validate() {  
	      // Validate birthdate
	      if( birthdate != null && birthdate.after(new Date()) ){
	    	  return "Birthdate can't be in the future";
	      }
	      
	      // Validate username uniquness
	      if( !uniqueUsername(username) ){
	    	  return "Username already exists!";
	      }
	      
	      return null;
    }

	private boolean uniqueUsername(String username) {
		return (UserModel.findUserByName(username) == null);
	}
	
}
