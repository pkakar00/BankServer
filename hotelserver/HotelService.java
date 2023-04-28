package hotelserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class makes serves the client.
 *
 */
public class HotelService implements Runnable 
{
	private Hotel hotel;
	private Socket s;
	private DataInputStream i;
	private DataOutputStream o;
	private String user;
	
	HotelService(Hotel hotel, Socket socket)
	{
		this.hotel=hotel;
		this.s=socket;
	}
	
	/**
	 * This method serves the client. If the first command received is not USER command, server exits.
	 * After getting the user's name, user's requests are executed until user quits 
	 */
	@Override
	public void run() 
	{
		try(Socket copySocket=this.s)
		{
			i=new DataInputStream(copySocket.getInputStream());
			
			o=new DataOutputStream(copySocket.getOutputStream());
			
			String input=i.readUTF();
			
			//Checking if the first command is USER command or not
			if(!(input.equalsIgnoreCase(HotelProtocol.COMMAND_USER)))
			{
				o.writeUTF("Invalid command: Closing Connection");
				o.flush();
				return;
			}
			else
			{
				this.user=i.readUTF();
				
				o.writeUTF("Hello, "+this.user);//Hello message sent to the user.
				o.flush();
				
				input=i.readUTF();
				
				//Executing the requests until user quits.
				while(!(input.equalsIgnoreCase(HotelProtocol.COMMAND_QUIT)))
				{
					if(input.equalsIgnoreCase(HotelProtocol.COMMAND_AVAIL))
					{
						o.writeUTF(hotel.reservationInformation());
					}
					else if(input.equalsIgnoreCase(HotelProtocol.COMMAND_CANCEL))
					{
						boolean result=hotel.cancelReservation(this.user);
						if(result)
							o.writeUTF("Reservations successfully canceled for "+this.user);
						else
							o.writeUTF("Reservations not canceled for "+this.user+", no current reservation.");	
					}
					else if(input.equalsIgnoreCase(HotelProtocol.COMMAND_RESERVE))
					{
						int first=i.readInt();
						int last=i.readInt();
						
						boolean result=this.hotel.requestReservation(this.user, first, last);
						
						if(result)
							o.writeUTF("Reservation made: "+this.user+" from "+first+" through "+last);
						else
							o.writeUTF("Reservation unsuccessful: "+this.user+" from "+first+" through "+last);
					}
					else if(input.equalsIgnoreCase(HotelProtocol.COMMAND_USER))
					{
						this.user=i.readUTF();
						o.writeUTF("Hello,  "+this.user);
						o.flush();
					}
					else
					{
						o.writeUTF("Invalid command: Closing Connection");
						return;
					}
					o.flush();
					input=i.readUTF();
					System.out.println(input);
				}
				
				o.writeUTF("Closing Connection");
				o.flush();
				return;	
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Client Disconnected");
			
		}
	}

}
