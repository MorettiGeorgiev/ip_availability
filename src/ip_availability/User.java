package ip_availability;

public class User {
	
	private String username;
	private boolean loggedIn = false;
	private int loggedInNum = 0;
	
	public User(String username) {
		this.username = username;
	}
	
	public User(String username, boolean loggedIn, int loggedInNum) {
		this.username = username;
		this.loggedIn = loggedIn;
		this.loggedInNum = loggedInNum;
	}
	
	public boolean getLoggedIn(){
		return this.loggedIn;
	}

	public void printInfo(){
		System.out.println(this.username+":"+this.loggedIn+":"+this.loggedInNum);
	}
	
	public void login(){
			this.loggedIn = true;
			this.loggedInNum++;
			Users.currentlyLoggedUsers.put(this.username , new User(this.username, this.loggedIn, this.loggedInNum));
			Users.usersToLogin.put(this.username , new User(this.username, this.loggedIn, this.loggedInNum));
		}
	
	public static void logout(String username){
			try{
			Users.usersToLogin.get(username).loggedIn = false;
			Users.currentlyLoggedUsers.get(username).loggedIn = false;
			Users.currentlyLoggedUsers.remove(username);
			}
			catch(NullPointerException e){
				System.out.println("Ooops! You are not logged in.");
			}
			
	}
}
