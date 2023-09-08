/**
 * Mostafaa Abdelaziz B00875982
 */
//This is a support class that extends Thread, runs the client thread
//and sends and receives messages
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private final Socket client;
    private BufferedReader in;
    private PrintWriter out;
    //Create a userName variable to accept the input userName from the user
    private String userName;
    //Create an array list to store the clients
    public static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    public ClientHandler(Socket socket) throws IOException{
        client = socket;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    public void run() throws NullPointerException{
        try{
            //Read the userName from the client and store it in the userName variable
            this.userName= in.readLine();
            clients.add(this);
            System.out.println(userName + " has joined");
            broadcast(userName + " has joined");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        try{
            //Store the messages of all clients and broadcast them to all clients
            String received;
            do{
                received = in.readLine();
                //if the recieved is not equal to null it prints it out. This also prevents the server from returning
                //null to the clients when one of the users leaves the chat.
                if(received !=null) {
                    System.out.println(received);
                }
                //Broadcast the message to all clients
                broadcast(received);
            } while (!received.equals("BYE"));
        }

        catch(NullPointerException | IOException e){
            e.getMessage();
        }
        finally{
            try{
                if(client!=null){
                    System.out.println("Closing connection...");
                    client.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }//end run

    //This method broadcasts the message to all clients by also making sure that the current client does not recieve the
    //message that they sent and that other users recieve it.
    public void broadcast(String message){
        for(ClientHandler client : clients){
            try{
                if(!client.userName.equals(userName)){
                    //If the message does not equal null it prints it out
                    if(!message.equals("null")){
                        client.out.println(message);
                    }
                }
            }
            catch(NullPointerException e) {
                e.getMessage();
            }
        }

    }
}//end ClientHandler class


