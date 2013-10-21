package controller;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.User;
import formbean.SearchForm;

public class SearchAjaxAction extends Action {
	private FormBeanFactory<SearchForm> formBeanFactory = FormBeanFactory.getInstance(SearchForm.class);
	
	private UserDAO userDAO;
	
	public SearchAjaxAction(Model model) {
		userDAO = model.getUserDAO();
	}

	@Override
	public String getActionName() {
		return "search-ajax.do";
	}

	@Override
	// Search all username starts with the input from search form
	public String perform(HttpServletRequest request) {
		try {
			SearchForm searchForm = formBeanFactory.create(request);
			User[] list = userDAO.lookupStartsWith(searchForm.getUsername());
			
			// Set the namelist attribute in the request and then return to search-ajax jsp file
			request.setAttribute("namelist", list);
			
			return "search-ajax.jsp";
		} catch (RollbackException e) {
			return "welcome.jsp";
		} catch (FormBeanException e) {
			return "welcome.jsp";
		}
	}

}
