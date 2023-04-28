package hotelserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

// This code connects to the HotelServer, which is running on the local host at port 1181.
// It creates a socket, connects to the server, creates an input stream and an output stream,
// and then sends a message to the server.
public class HotelClient 
{
	private static DataInputStream i;
	private static DataOutputStream o;
	private static Scanner input=new Scanner(System.in);
	private static boolean firstTime;
	
	/**
	 * This method opens a socket that connects to the localhost on port 1181
	 * When the connection is established, welcome message is read from the server and printed.
	 * After that user's name is taken as input and a USER command is sent to login the user.
	 * Aftwr logging in, the user can choose the options from the displayed menu.
	 * @param args Command line arguments
	 */
	public static void main(String[] args)
	{
		System.out.println("Connecting to the server . . .");
		
		try(Socket s=new Socket("localhost",HotelProtocol.PORT))
		{
			i=new DataInputStream(s.getInputStream());
			o=new DataOutputStream(s.getOutputStream());
			
			System.out.println("Connected to the server . . .");
			
			System.out.println("Server : "+i.readUTF());
			
			firstTime=true;
			executeCommand(4);
			
			int in=0;
			while(in !=5)
			{
				printMenu();
				try 
				{
					in=input.nextInt();
					if(!(in>=1 && in<=5))
						throw new InputMismatchException();
					else
						executeCommand(in);
					
				}
				catch(InputMismatchException e) 
				{
					System.out.println("Wrong Input");
					System.out.println("Server Disconnecting");
					return;
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Disconnected . . .");
		}
		finally {input.close();}
		
	}
	
	/**
	 * 
	 * @param in the option number
	 * @throws IOException IOException if input is not valid so that client can disconnect from server.
	 */
	private static void executeCommand(int in) throws IOException
	{
		switch(in)
		{
			//Reserving a room
			case 1:
				System.out.println("Enter first and last date");
				int first=input.nextInt();
				int last=input.nextInt();
				o.writeUTF(HotelProtocol.COMMAND_RESERVE);
				o.writeInt(first);
				o.writeInt(last);
				o.flush();
				System.out.println("Server : "+i.readUTF());
				break;
				
			//Cancelling reservation
			case 2:
				o.writeUTF(HotelProtocol.COMMAND_CANCEL);
				o.flush();
				System.out.println("Server : "+i.readUTF());
				break;
				
			//Checking availability
			case 3:
				o.writeUTF(HotelProtocol.COMMAND_AVAIL);
				o.flush();
				System.out.println("Server : "+i.readUTF());
				break;
				
			//logging in the user
			case 4:
				if((!firstTime) && input.hasNextLine())//clearing the input stream
				{
					input.nextLine();
				}
					
				System.out.println("Enter Your Name");
				String name=input.nextLine();
				o.writeUTF("USER");
				o.writeUTF(name);
				o.flush();
				System.out.println("Server : "+i.readUTF());
				firstTime=false;
				break;
				
			//Quitting the server	
			case 5:
				o.writeUTF(HotelProtocol.COMMAND_QUIT);
				o.flush();
				System.out.println("Server : "+i.readUTF());
				break;
			
			//Any other input disconnects the server.
			default:
				throw new IOException();
		}
	}
	
	/**
	 * Printing the menu
	 */
	private static void printMenu()
	{
		System.out.println("Choose one of the following commands, else server will disconnect");
		System.out.println("Enter number");
		System.out.println("1. RESERVE ROOM");
		System.out.println("2. CANCEL RESERVATION");
		System.out.println("3. CHECK AVAILABILITY");
		System.out.println("4. CHANGE USER");
		System.out.println("5. QUIT");
	}
}
