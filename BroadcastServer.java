/**
 * Mostafaa Abdelaziz B00875982
 */

import java.io.*;
import java.net.*;
public class BroadcastServer {
	private static ServerSocket serverSock;
	private static final int PORT = 36000;
	public static void main(String[] args) throws IOException
	{
		try{
			serverSock=new ServerSocket(PORT);
		}
		catch (IOException e)
		{
			System.out.println("Can't listen on " + PORT);

			System.exit(1);
		}
		do
		{
			Socket client = null;
			System.out.println("Listening for connection...");
			try{
				client = serverSock.accept();
				System.out.println("New client accepted");
				ClientHandler handler = new ClientHandler(client);
				Thread thread = new Thread(handler);
				thread.start();
			}
			catch (IOException e)
			{
				System.out.println("Accept failed");
				System.exit(1);
			}
			System.out.println("Connection successful");
			System.out.println("Listening for input ...");
		}while(true);
	}
}
