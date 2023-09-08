/**
 * Mostafaa Abdelaziz B00875982
 */
import java.io.*;
import java.net.*;


public class ChatClient{
	private static final int PORT = 36000;
	private static Socket link;
	private static BufferedReader in;
	private static PrintWriter out;
	private static BufferedReader kbd;
	private static String userName;

	public static void main(String[] args) throws Exception{
		try{
			link = new Socket("127.0.0.1", PORT);
			in = new BufferedReader(new InputStreamReader(link.getInputStream()));
			out = new PrintWriter(link.getOutputStream(), true);
			kbd = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Connected to the chat server" + "\n");
			String message;
			//This asks the user to input their name and then it's stored in the message variable and sent to the server
			System.out.print("Enter name: ");
			message = kbd.readLine();
			userName = message;
			out.println(userName);

			do{
				/*This checks if there are any messages firstly before sending anything to the other clients in the
				chat room. if there is, it'll make sure to print it out before sending anything. After that, it'll
				ask the user to input a message and then it'll send it to the server and to the other clients.*/
				listen();
				send();
				message = kbd.readLine();
				out.println("Message from " + userName + ": " + message);
			}while (!message.equals("BYE"));

		}
		catch(UnknownHostException e){System.exit(1);}
		catch(IOException e){System.exit(1);}

		finally{
			try{
				if (link!=null){
					send();
					System.out.print("Connection shut down");
					try{
						link.close();
					}
					catch (SocketException e){
						System.out.println("Socket closed");
					}
				}
			}
			catch(IOException e){System.exit(1);}
		}
	}//end main

	/**
	 * Cite:https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html
	 * This method is used to listen for any messages from the server and then print them out to the users found within
	 * the chat room. This was very much inspired by the code found in the link above and I have modified it to fit my needs
	 * for this assignment. It basically manages threads and makes sure everything is up to date and send out to the
	 * other clients in the chat room. If there are no messages, it'll just wait for the user to send a message. This was
	 * needed because it was not updating the chat room unless one of the clients sent a message. Which is very inefficient.
	 */
	public static void listen() throws IOException{
		Thread Thread =new Thread() {
			public void run() {
				try {
					String response;
					do {
						//This stores the responses in the response variable and then prints it out to the user. after
						//that,it'll print out the responses and then start the thread at the end.
						response = in.readLine();
						System.out.println("\n"+response);
						send();
					} while (!response.equals("BYE"));
				} catch (IOException e) {
				}
			}
		};
		Thread.start();
	}

	/**
	 * This method is used to display this piece of line which is repeated throughout the program. It's used to guide the
	 * user and make sure that they have the option to type in a message and send it to the other clients in the chat room.
	 */
	public static void send(){
		System.out.print("Enter message (BYE to exit): ");
	}
}//end class MultiEchoClient

	
	
