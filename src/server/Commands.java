package server;

import java.io.PrintStream;

import main.User;
import main.Users;

public class Commands {

	private String username = "";
	private String command = "";
	private PrintStream out;
	private String[] inputLineParts;

	public Commands() {
		
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCommand(){
		return command;
	}
	
	public void setCommand(String command){
		this.command = command;
	}
	
	public void setOut(PrintStream out) {
		this.out = out;
	}
	
	public String[] getInputLineParts() {
		return inputLineParts;
	}

	public void setInputLineParts(String[] inputLineParts) {
		this.inputLineParts = inputLineParts;
	}

	public boolean isLoogedIn(){
		try{
			Users.currentlyLoggedUsers.get(username).getLoggedIn();
			return true;
		}
		catch(NullPointerException e){
			return false;
		}
	}
	
	public void login(){
		User user = Users.currentlyLoggedUsers.get(username);
		if(user == null){
			user = Users.usersToLogin.get(username);
			if(user != null) user.login();
			else{
				user = new User(username);
				user.login();
			}
			out.println("ok");
		}else out.println("error: already logged");
	}
	
	public void logout(){
		try{
			User.logout(username);
			out.println("ok");
		}catch(NullPointerException e){
			out.println("error: notlogged");
		}
	}
	
	public void info(){
		try{
			if(isLoogedIn()) out.print(Users.usersToLogin.get(this.inputLineParts[2]).getInfo());
			else out.println("error: notlogged");
		}catch(NullPointerException e){
			out.println("error:nouser");
		}catch(ArrayIndexOutOfBoundsException e){
			out.println("error:no second user");
		};
	}
	
	public void listavailable(){
		if(isLoogedIn()) out.println(Users.getCurrentlyLoggedUsers());
		else out.println("error:notlogged");
	}
	
	public void listabsent(){
		if(isLoogedIn()) out.println(Users.getAbsentUsers());
		else out.println("error:notlogged");
	}

}
