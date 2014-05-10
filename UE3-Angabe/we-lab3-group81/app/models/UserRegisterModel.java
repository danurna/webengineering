package models;

import java.util.Date;

import play.Logger;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.data.format.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegisterModel {
	
	public String firstname;
	public String lastname;
	
	@Formats.DateTime(pattern="dd.MM.yyyy")
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
		 // String to be scanned to find the pattern.
	      //String pattern = "(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";

	      Logger.info(birthdate.toString());
	      
	      // Validate birthdate
	      if( birthdate.after(new Date()) ){
	    	  return "Birthdate can't be in the future";
	      } 
	      
	      // Validate username uniquness
	      if( !uniqueUsername(username) ){
	    	  return "Username already exists!";
	      }
	      
	      return null;
    }

	private boolean uniqueUsername(String username) {
		//TODO: Implement
		return true;
	}
	
}
