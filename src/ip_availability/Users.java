package ip_availability;
import java.util.HashMap;
import java.util.Map;

public class Users {
	public static Map<String, User> currentlyLoggedUsers = new HashMap<String, User>();
	public static Map<String, User> usersToLogin = new HashMap<String, User>();
	
	public static void printCurrentlyLoggedUsers(){
		System.out.print("ok:");
		for (String username : currentlyLoggedUsers.keySet()) {
		    System.out.print(username+":");
		}
		System.out.println();
	}
	
}
