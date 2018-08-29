package app;

import javax.swing.JOptionPane;

import arduino.Arduino;

public class SendString {

	public static void main(String[] args) {
		Arduino a = new Arduino(JOptionPane.showInputDialog("Port Description (String):"), 115200);
		a.openConnection();
		
		for(int i = 0; i < 10; i++) {
			a.serialWrite(JOptionPane.showInputDialog("Sendende Nachricht"));
			System.out.println(a.serialRead());
		}
		
		a.closeConnection();
	}

}
