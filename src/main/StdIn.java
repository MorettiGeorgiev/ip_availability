package main;

public class StdIn {
	private String cmdLine;
	private String[] cmdLineParts;
	private String username;
	private String command;
	
	public StdIn(String stdIn){
		this.cmdLine = stdIn;
		this.cmdLineParts = this.cmdLine.split(":");
		try{
			this.username = this.cmdLineParts[0];
			this.command = this.cmdLineParts[1];
			this.executeCommand();
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Ooops! Please enter 2 or more arguments.");
		}	
	}

	public boolean isLoogedIn(){
		try{
			Users.currentlyLoggedUsers.get(this.username).getLoggedIn();
			return true;
		}
		catch(NullPointerException e){
			return false;
		}
	}

	//Commands StdIn
	public void login(){
		User user = Users.currentlyLoggedUsers.get(this.username);
		if(user == null){
			user = Users.usersToLogin.get(this.username);
			if(user != null) user.login();
			else{
				user = new User(this.username);
				user.login();
			}
			System.out.println("ok");
		}else System.out.println("Ooops! You are already logged in!");
	}
	
	public void logout(){
			User.logout(this.username);
			System.out.println("ok");
	}
	
//	public void info(){
//		try{
//			if(this.isLoogedIn()) Users.usersToLogin.get(this.cmdLineParts[2]).printInfo();
//			else System.out.println("Ooops! You are not logged in!");
//		}catch(NullPointerException e){
//			System.out.println("Ooops! No such user!");
//		}
//	}
//	
//	public void listavailable(){
//		if(this.isLoogedIn()) Users.printCurrentlyLoggedUsers();
//		else System.out.println("Ooops! You are not logged in!");
//	}
	
	public void executeCommand(){
		if(this.command.equals("login")) this.login();
		else if(this.command.equals("logout")) this.logout();
//		else if(this.command.equals("info")) this.info();
//		else if(this.command.equals("listavailable")) this.listavailable();
		else System.out.println("Ooops! Unknow command!");
	}
}
