package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

/**
 * This kind of form bean class is more like make the web page javable
 * With this class, we can get the user input on login page using methods here
 * @author shiqing
 *
 */
public class LoginForm extends FormBean{
	private String userName;
	private String password;
	private String action;
	
//	public LoginForm(HttpServletRequest request) {
//		this.userName = (String)request.getParameter("userName");
//		this.password = (String)request.getParameter("password");
//		this.action = (String)request.getParameter("action");
//	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName.trim();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password.trim();
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	// Login form has error (which is defined in the login form class)
	// This kind of error more like plain text check (which can be also done by Javascript)
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (userName == null || userName.length() == 0) errors.add("userName is required.");
		if (password == null || password.length() == 0) errors.add("password is required.");
		if (action == null) errors.add("button is required.");
		if (errors.size() > 0) return errors;
		
		if (!action.equals("Login") && !action.equals("Register")) {
			errors.add("Invalid button");
		}
		if (userName.matches(".*[<>\"].*")) {
			errors.add("Username cannot contain angel bracket or quote");
		}
		return errors;
	}
	
//	public boolean isPresent() {
//		return userName != null || password != null || action != null;
//	}
}
