package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server {
	private final int port;
	private boolean running;
	
	private final List<ClientHandler> clients =
		Collections.synchronizedList(
			new LinkedList<ClientHandler>());
	private ServerSocket serverSocket;

	public Server(int port) {
		this.port = port;
	}

	public void startServer() throws IOException {
		final ServerSocket localServerSocket = createServerSocket();

		while(isRunning()) {
			Socket socket;
			try {
				socket = localServerSocket.accept();
			} catch (SocketException e) {
				if (!localServerSocket.isClosed()) {
					throw e;
				}
				break;
			}

			ClientHandler client = new ClientHandler(this, socket);
			clients.add(client);
			new Thread(client).start();
			System.out.println("Server is running..");
		}
	}

	private synchronized ServerSocket createServerSocket() throws IOException {
		if (running) {
			throw new IllegalStateException("Already running");
		}

		running = true;
		serverSocket = new ServerSocket(port);
		return serverSocket;
	}
	
	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized void stopServer() throws IOException {
		if (!running) {
			throw new IllegalStateException("Not running");
		}

		running = false;
		
		serverSocket.close();
		serverSocket = null;
		
		for (ClientHandler next : clients) {
			next.stopClient();
		}
	}

	public void onClientStopped(ClientHandler clientHandler) {
		clients.remove(clientHandler);
	}
}