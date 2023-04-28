package hotelserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class HotelServer 
{	
	/**
	 * This method creates a server at port 1181, and when a client is connected, a thread is created for that client
	 * to serve it and the server waits for another client.
	 * @param Args
	 */
	public static void main(String[] Args)
	{
		Hotel hotel=new Hotel();
		try(ServerSocket server=new ServerSocket(HotelProtocol.PORT))//making a server at port 1181
		{
			System.out.println("Waiting for client to connect . . .");
			while(true)
			{
				try
				{
					Socket s=server.accept();//accepting the client's request if any.
					System.out.println(" Client is now Connected . . .");
			
					DataOutputStream o=new DataOutputStream(s.getOutputStream());
					
					o.writeUTF("Welcome to Hotel Server :-)");//Sending a welcome message to client.
					o.flush();
					
					System.out.println("Creating a new Thread for this request . . .");
					HotelService service=new HotelService(hotel, s);
					
					Thread serviceThread=new Thread(service);
					
					serviceThread.start();//starting a new thread for the client.
					
					System.out.println("Client is being Served, Waiting for next client");
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		catch(IOException e)
		{
			System.out.print("Server is Down");
		}
	}
}
