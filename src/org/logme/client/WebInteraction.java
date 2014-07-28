package org.logme.client;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WebInteraction {
	
	protected static int PORT = 8000;
	
	public void sendObject(Object o){
		try{
			Socket s = new Socket("localhost", PORT);
			OutputStream os = s.getOutputStream();
			ObjectOutputStream objout = new ObjectOutputStream(os);
			System.out.println("SENDING" + o.toString());
			objout.writeObject(o);
			objout.close();
			os.close();
			s.close();
		}catch (Exception e){
			System.err.println("Error writing to socket! or something like that w/e");
			e.printStackTrace();
		}
	}
	
}
