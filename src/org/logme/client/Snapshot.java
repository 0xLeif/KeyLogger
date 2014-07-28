package org.logme.client;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Snapshot implements Serializable {

	private static final long serialVersionUID = 7239630120766283254L;
	protected ArrayList<String> progs;
	protected String userInput;
	protected String dateTime;
	protected String hostName;

	public Snapshot(String userInput, ArrayList<String> progs) {
		this.userInput = userInput;
		this.progs = progs;
		hostName = "";
		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostName = addr.getHostName();
		} catch (UnknownHostException ex) {
			System.out.println("Hostname can not be resolved");
		}
		getTime();
	}

	protected void getTime() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		dateTime = sdf.format(cal.getTime());
	}

	@Override
	public String toString() {
		String s = "";
		s += "HOST: " + hostName + "\nTIME: " + dateTime + "\nINPUT: " + userInput + "\nPROGS\n";
		for (String s1 : progs) {
			if(s1.contains("Google") || s1.contains("\\") || s1.contains("/") || s1.contains("Internet"))
			s += "\t" + s1 + "\n";
		}
		return s;
	}
}
