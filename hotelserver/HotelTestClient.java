package hotelserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HotelTestClient {
	
	public static void main(String[] args) 
	{
	      final int PORT = 1181;
	      try (Socket s = new Socket("localhost", PORT)) 
	      {
		      InputStream instream = s.getInputStream();
		      OutputStream outstream = s.getOutputStream();
		
		      DataInputStream in = new DataInputStream(instream);
		      DataOutputStream out = new DataOutputStream(outstream);
		      
		      String response = in.readUTF();
		      System.out.println("Receiving: " + response);

		      out.writeUTF("USER");
		      out.writeUTF("Jill");
		      response = in.readUTF();
		      System.out.println("Receiving: " + response);
		      
    		  out.writeUTF("RESERVE");
    		  out.writeInt(3);
    		  out.writeInt(5);
    		  out.flush();    
	    	  System.out.println("\nReceiving: " + in.readUTF());			      
		      
    		  out.writeUTF("RESERVE");
    		  out.writeInt(10);
    		  out.writeInt(12);
    		  out.flush();    
	    	  System.out.println("\nReceiving: " + in.readUTF());		    	  

    		  out.writeUTF("USER");
    		  out.writeUTF("Jack");
    		  out.flush();  
	    	  System.out.println("\nReceiving: " + in.readUTF());	
	    	  
    		  out.writeUTF("CANCEL");
    		  out.flush();	
	    	  System.out.println("\nReceiving: " + in.readUTF());	
	    	  
    		  out.writeUTF("RESERVE");
    		  out.writeInt(10);
    		  out.writeInt(12);
    		  out.flush();    
	    	  System.out.println("\nReceiving: " + in.readUTF());	
	    	  
    		  out.writeUTF("CANCEL");
    		  out.flush();	
	    	  System.out.println("\nReceiving: " + in.readUTF());	
	    	  	    	  
	    	  
    		  out.writeUTF("AVAIL");
    		  out.flush();		    	  
	    	  System.out.println("\nReceiving: " + in.readUTF());	
	    	  
    		  out.writeUTF("QUIT");
    		  out.flush();	
	    	  System.out.println("\nReceiving: " + in.readUTF());	

		      
	      } catch(IOException e) {
	    	  System.out.println(e.getStackTrace());
	      }
	}

}
