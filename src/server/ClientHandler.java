package server;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
	private Socket socket;
	private Server server;
	private PrintStream out;
	private Scanner scanner;
	private Commands commands = new Commands();
	
	public ClientHandler(Server server, Socket socket) {
 		this.socket = socket;
		this.server = server;
 	}
	
	public void stopClient() throws IOException {
		socket.close();
	}
	
	private void splitLine(String line){
		String inputLine = line;
		commands.setInputLineParts(inputLine.split(":"));
		String[] lineParts = commands.getInputLineParts();
		try{
			commands.setUsername(lineParts[0]);
			commands.setCommand(lineParts[1]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			out.println("error: 2 or more arguments");
		}	
	}
	
	public void run() {
		try {
			out = new PrintStream(socket.getOutputStream());
			commands.setOut(out);
			scanner = new Scanner(socket.getInputStream());
			out.println("Conected to: " + this.socket.getLocalSocketAddress());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine(); 
				splitLine(line);
				if(commands.getCommand().equals("login")) commands.login();
				else if(commands.getCommand().equals("logout")) commands.logout();
				else if(commands.getCommand().equals("info")) commands.info();
				else if(commands.getCommand().equals("listavailable")) commands.listavailable();
				else if(commands.getCommand().equals("listabsent")) commands.listabsent();
				else if(commands.getCommand().equals("shutdown")) {
					server.stopServer();
					break;
				}
				else out.println("error: unknown command!");
			}
			scanner.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
