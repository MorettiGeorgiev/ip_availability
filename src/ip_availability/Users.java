package ip_availability;
import java.util.HashMap;
import java.util.Map;

public class Users {
	public static Map<String, User> currentlyLoggedUsers = new HashMap<String, User>();
	public static Map<String, User> usersToLogin = new HashMap<String, User>();
	
	public static String getCurrentlyLoggedUsers(){
		String users = "ok:";		
		for (String username : currentlyLoggedUsers.keySet()) {
		   users += username+":";
		}
		return users;
	}
	
	public static String getAbsentUsers(){
		String users = "ok:";
		for (User user : usersToLogin.values()) {
		   if(!user.getLoggedIn()) users += user.getUsername()+":";
		}
		return users;
	}
	
}
