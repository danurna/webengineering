package forms;

import models.UserModel;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Messages;

public class LoginForm {
	@Required
	@MinLength(4)
	@MaxLength(10)
	public String username;
	@Required
	public String password;
	
	public String validate() {
		if(UserModel.authenticate(username, password) == null) {
            return Messages.get("error.login");
        }
		return null;
	}
}
