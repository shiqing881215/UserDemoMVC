package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;


public class LogoutAction extends Action{
	
	public LogoutAction(Model model){}

	@Override
	public String getActionName() {
		return "logout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("user", null);
		return "login.jsp";
	};
}
