package org.logme.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.logme.client.SnapshotCollection;

public class LogmeServer {
	
	protected ArrayList<SnapshotCollection> collections;
	protected static int PORT = 8383;

	public static void main(String[] args) {
		new LogmeServer();
	}
	
	public LogmeServer(){
		listen();
	}
	
	public void listen(){
		System.out.println("LISTENING ON PORT " + PORT);
		try {
			collections = new ArrayList<SnapshotCollection>();
			ServerSocket ss = new ServerSocket(PORT);
			while (true) {
				Socket s = ss.accept();
				InputStream is = s.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				collections.add((SnapshotCollection) ois.readObject());
				System.out.println(" I GOT THE INPUTS! ");
				System.out.println(collections.get(collections.size()-1));
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
