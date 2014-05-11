package models;

import java.util.Date;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;

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
	    	  return Messages.get("error.futuredate"); 
	      }
	      
	      // Validate username uniquness
	      if( !uniqueUsername(username) ){
	    	  return Messages.get("error.userexists");
	      }
	      
	      return null;
    }

	private boolean uniqueUsername(String username) {
		return (UserModel.findUserByName(username) == null);
	}
	
}
