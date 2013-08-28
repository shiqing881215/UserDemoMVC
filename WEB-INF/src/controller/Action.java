package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class Action {
	public abstract String getActionName();
	// Return the name of the jsp used to render the output
	public abstract String perform(HttpServletRequest request);
	// Map used to match an action name with the real action
	/*
	 * login.do    --- loginAction
	 * logout.do   --- logoutAction
	 */
	private static Map<String, Action> map = new HashMap<String, Action>();
	
	// Add an action instance to the map
	public static void add(Action a) {
		synchronized(map) {
			if (map.get(a.getActionName()) != null) {
				throw new AssertionError("Two actions with the same name (" + a.getActionName() + "): " + a.getClass().getName() + " and " + map.get(a.getActionName()).getClass().getName());
			}
			map.put(a.getActionName(), a);
		}
	}
	
	// Match the action name then perform the corresponding action
	public static String perform(String actionName, HttpServletRequest request) {
		Action a;
		synchronized (map) {
			a = map.get(actionName);
		}
		
		if (a == null) return null;
		return a.perform(request);
	}
}
