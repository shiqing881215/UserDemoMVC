package model;

//import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean.User;

public class UserDAO extends GenericDAO<User>{

	public UserDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(User.class, tableName, pool);
	}

	public User[] lookupStartsWith(String searchEntry) throws RollbackException {
		User[] list = match(MatchArg.startsWith("userName", searchEntry));
//		Arrays.sort(list);
		return list;
	}
}
