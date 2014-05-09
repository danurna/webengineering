package models;

import play.data.validation.Constraints.Required;

public class UserModel {
	@Required
	public String username;
	@Required
	public String password;
}
