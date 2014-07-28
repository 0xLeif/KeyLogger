package org.logme.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LogFileIO {
	
	
	public void writeFile(SnapshotCollection sc){
        try{
    		FileOutputStream fos = new FileOutputStream(new File("data.ser"));
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sc);
            oos.close();
            System.out.println("Wrote to file");
        }catch (Exception e){
            e.printStackTrace();
        }
	}
	
	
	
}
