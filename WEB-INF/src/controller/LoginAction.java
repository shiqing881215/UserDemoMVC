package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.User;
import formbean.LoginForm;

public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	private UserDAO userDAO;
	
	// Init userDAO from model
	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
	}
	
	@Override
	public String getActionName() {
		return "login.do";
	}
	
	@Override
	// Return the name of the jsp used to render the output
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		// User already login
		if (session.getAttribute("user") != null) {
			return "welcome.jsp";  // return to welcome.do jsp
		}
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);  // set errors attribute for following page usage
		
		try {
			LoginForm loginForm = formBeanFactory.create(request);
			request.setAttribute("form", loginForm);
			
			// First time the login form is initialized
			if (!loginForm.isPresent()) {
				return "login.jsp";
			}
			
			errors.addAll(loginForm.getValidationErrors());
			if (errors.size() > 0) {
				return "login.jsp";
			}
			
			// Register button
			if (loginForm.getAction().equals("Register")) {
				User user = new User();
				user.setUserName(loginForm.getUserName());
				user.setPassword(loginForm.getPassword());
				userDAO.create(user);
				session.setAttribute("user", user);
				return "welcome.jsp";
			}
			
			// Login button
			User user = userDAO.read(loginForm.getUserName());
			
			if (user == null) {
				errors.add("User not found.");
				return "login.jsp";
			} else if (!user.getPassword().equals(loginForm.getPassword())) {
				errors.add("Invalid password.");
				return "login.jsp";
			} else {
				session.setAttribute("user", user);
				return "welcome.jsp";
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "login.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "login.jsp";
		}
	}
}
