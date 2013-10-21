package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import databean.User;

public class Controller extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException{
		// Init model, which means getting the connection with db
		Model model = new Model(getServletConfig());
		
		Action.add(new LoginAction(model));
		Action.add(new LogoutAction(model));
		Action.add(new SearchAjaxAction(model));
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = performTheAction(request);
		sendToNextPage(nextPage, request, response);
	}


	private String performTheAction(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String servletPath = request.getServletPath();
		User user = (User)session.getAttribute("user");
		String action = getActionName(servletPath);
		
		if (user == null) {
			return Action.perform("login.do", request);
		}
		
		return Action.perform(action, request);
	}

	/*
     * If nextPage is null, send back 404
     * If nextPage ends with ".do", redirect to this page.
     * If nextPage ends with ".jsp", dispatch (forward) to the page (the view)
     *    This is the common case
     */
	private void sendToNextPage(String nextPage, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getServletPath());
			return;
		}
		
		// Treat .do and .jsp in different ways
		// .do should send another request (which can be done in redirect)
		// .jsp just forward to display
		
		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);  // Update URL with the nextPage.do and ask for another new request
			return;
		}
		
		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
			requestDispatcher.forward(request, response);
			return;
		}
		
		throw new ServletException(Controller.class.getName() + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
	}
	
	// Get the action name which is appended after the url, like "login.do"
	private String getActionName(String servletPath) {
		int lastSlash = servletPath.lastIndexOf("/");
		return servletPath.substring(lastSlash+1);
	}
}
