package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private UserDAO userDAO;
	
	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			// Using root user here
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL,"root","");
			
			userDAO = new UserDAO("user", pool);
		} catch (DAOException e) { 
			throw new ServletException(e);
		}
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
}
