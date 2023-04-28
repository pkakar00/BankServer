package hotelserver;

public interface HotelProtocol 
{
	// This is the port number that the client will connect to.
	final static int PORT=1181;

	// The following are the commands that the client can send to the server.
	// These commands are used by the client to communicate with the server.

	// COMMAND_QUIT is used to quit the connection between the client and the server.
	final static String COMMAND_QUIT="QUIT";
	// COMMAND_USER is used to identify the user to the server.
	final static String COMMAND_USER="USER";
	// COMMAND_CANCEL is used to cancel a reservation that the user has made.
	final static String COMMAND_CANCEL="CANCEL";
	// COMMAND_AVAIL is used to view the list of available rooms.
	final static String COMMAND_AVAIL="AVAIL";
	// COMMAND_RESERVE is used to reserve a room.
	final static String COMMAND_RESERVE="RESERVE";
}
