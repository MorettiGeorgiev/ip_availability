package main;
import java.time.LocalDateTime;


public class User {
	
	private String username;
	private boolean loggedIn = false;
	private int loggedInNum = 0;
	private static String interval="";

	public User(String username) {
		this.username = username;
	}
	
	public User(String username, boolean loggedIn, int loggedInNum) {
		this.username = username;
		this.loggedIn = loggedIn;
		this.loggedInNum = loggedInNum;
	}
	
	public String getUsername(){
		return username;
	}
	
	public boolean getLoggedIn(){
		return loggedIn;
	}

	public String getInfo(){
		String info = username+":"+loggedIn+":"+loggedInNum;
		if(!loggedIn) info += interval;
		return info;
	}
	
	public void login(){
			loggedIn = true;
			loggedInNum++;
			Users.currentlyLoggedUsers.put(username , new User(username, loggedIn, loggedInNum));
			Users.usersToLogin.put(this.username , new User(username, loggedIn, loggedInNum));
			interval+=LocalDateTime.now()+":";
		}
	
	public static void logout(String username) throws NullPointerException{
			Users.usersToLogin.get(username).loggedIn = false;
			Users.currentlyLoggedUsers.get(username).loggedIn = false;
			Users.currentlyLoggedUsers.remove(username);
			interval+=LocalDateTime.now()+":";	
	}
}
