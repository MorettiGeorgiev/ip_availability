package main;
import java.io.IOException;
import java.util.Scanner;

import server.Server;


public class Main {

	public static void main(String[] args) throws IOException{
		Server server = new Server(81);
		server.startServer();
	}
	
}
