package dal;

import javax.swing.JOptionPane;

import arduino.Arduino;

public class ArduinoConnector {

	private static ArduinoConnector instance;
	private Arduino a;

	public ArduinoConnector() {
		a = new Arduino(JOptionPane.showInputDialog("Port Description (String):"), 115200);
	}

	public ArduinoConnector getInstance() {
		if (instance == null) {
			instance = new ArduinoConnector();
		}
		return instance;
	}

	public void sendMessage(String msg) {
		a.serialWrite(msg);
	}

	public void openCon() {
		a.openConnection();
	}

	public void closeCon() {
		a.closeConnection();
	}

}
