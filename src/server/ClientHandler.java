package server;
import ip_availability.User;
import ip_availability.Users;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
	private static final String COMMAND_STOP_SERVER = "shutdown";
	private Socket socket;
	private Server server;
	private PrintStream out;
	private Scanner scanner;
	private String username = "";
	private String command = "";
	private String inputLine;
	private String[] inputLineParts;

	public ClientHandler(Server server, Socket socket) {
 		this.socket = socket;
		this.server = server;
 	}
	
	public void stopClient() throws IOException {
		socket.close();
	}
	
	private void splitLine(String line){
		inputLine = line;
		inputLineParts = inputLine.split(":");
		try{
			username = this.inputLineParts[0];
			command = this.inputLineParts[1];
		}
		catch(ArrayIndexOutOfBoundsException e){
			out.println("Ooops! Please enter 2 or more arguments.");
		}	
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
		User user = Users.currentlyLoggedUsers.get(this.username);
		if(user == null){
			user = Users.usersToLogin.get(this.username);
			if(user != null) user.login();
			else{
				user = new User(this.username);
				user.login();
			}
			out.println("ok");
		}else out.println("Ooops! You are already logged in!");
	}
	
	public void logout(){
		User.logout(username);
		out.println("ok");
	}
	
	public void info(){
		try{
			if(isLoogedIn()) out.print(Users.usersToLogin.get(this.inputLineParts[2]).getInfo());
			else out.println("Ooops! You are not logged in!");
		}catch(NullPointerException e){
			out.println("Ooops! No such user!");
		}
	}
	
	public void listavailable(){
		if(isLoogedIn()) out.println(Users.getCurrentlyLoggedUsers());
		else out.println("error:notlogged");
	}
	
	public void listabsent(){
		if(isLoogedIn()) out.println(Users.getAbsentUsers());
		else out.println("error:notlogged");
	}
	public void run() {
		try {
			out = new PrintStream(socket.getOutputStream());
			scanner = new Scanner(socket.getInputStream());
			out.println("Conected to: " + this.socket.getLocalSocketAddress());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine(); 
				splitLine(line);
				if(command.equals("login")) this.login();
				else if(this.command.equals("logout")){
					try{
						this.logout();
					}catch(NullPointerException e){
						out.println("Ooops! You are not logged in.");
					}
				}
				else if(command.equals("info")) this.info();
				else if(command.equals("listavailable")) this.listavailable();
				else if(command.equals("listabsent")) this.listabsent();
				else if (COMMAND_STOP_SERVER.equals(command)) {
					server.stopServer();
					break;
				}
				else out.println("Ooops! Unknow command!");
			}
			scanner.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
