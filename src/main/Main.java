package main;
import java.io.IOException;
import server.Server;


public class Main {

	public static void main(String[] args) throws IOException{
		Server server = new Server(81);
		server.startServer();
	}
	
}
