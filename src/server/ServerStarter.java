package server;

import java.io.IOException;

public class ServerStarter {
	
	private static final int SERVER_PORT = 81;
	public ServerStarter() throws IOException{
		Server server = new Server(SERVER_PORT);
		server.startServer();
	}

}
