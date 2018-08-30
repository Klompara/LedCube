package dal;

import javax.swing.JOptionPane;

import arduino.Arduino;

public class ArduinoConnector {

	private static ArduinoConnector instance;
	private Arduino a;
	private Thread readingThread;

	public ArduinoConnector() {
		a = new Arduino(JOptionPane.showInputDialog("Port Description (String):"), 115200);
	}

	public static ArduinoConnector getInstance() {
		if (instance == null) {
			instance = new ArduinoConnector();
		}
		return instance;
	}

	public void startReceivingMessages() {
		Runnable r = new Runnable() {
			public void run() {
				System.out.println("start reading");
				while (true) {
					System.out.println("Reading: " + (int) a.serialRead(1).charAt(0));
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	public void stopReceivingMessages() {
		try {
			readingThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		Runnable r = new Runnable() {
			public void run() {
				a.serialWrite(msg);
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	public void openCon() {
		a.openConnection();
	}

	public void closeCon() {
		a.closeConnection();
	}

}
