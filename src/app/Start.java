package app;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import bll.SerialLED;
import bll.animation.Animation;
import bll.animation.Plates;
import bll.animation.Smiley;
import dal.ArduinoConnector;

public class Start {

	private boolean running = true;

	private long tickrate = 500;
	private Date lastTicked;

	private SerialLED[][][] map;
	public static final int cubeSize = 8;

	private ArrayList<Animation> animationen = new ArrayList<Animation>();
	private int currentAnimation = 1;

	public Start() {
		ArduinoConnector a = new ArduinoConnector();
		a.openCon();
		animationen.add(new Plates());
		animationen.add(new Smiley());

		map = new SerialLED[8][8][8];
		for (int x = 0; x < cubeSize; x++) {
			for (int y = 0; y < cubeSize; y++) {
				for (int z = 0; z < cubeSize; z++) {
					map[x][y][z] = new SerialLED(new Color(0, 0, 0));
				}
			}
		}
		lastTicked = new Date();
		while (running) {
			if (lastTicked.getTime() + tickrate < System.currentTimeMillis()) {
				lastTicked = new Date();
				clearMap();
				animationen.get(currentAnimation).tick(map);
				String output = ledsToString();
				for (int i = 0; i < output.length(); i++) {
					System.out.println(i + " -> " + (int)output.charAt(i));
				}
				// a.sendMessage(ledsToString());
				running = false;
			}

		}
		a.closeCon();
	}

	private String ledsToString() {
		String output = "";
		int counter;
		for (int z = 0; z < Start.cubeSize; z++) {
			counter = 0;
			output = output + "" + (char) z;
			for (int x = 0; x < Start.cubeSize; x++) {
				for (int y = 0; y < Start.cubeSize; y++) {
					Color c = map[x][y][z].getC();
					output = output + "" + (char) counter;
					output = output + "" + (char) c.getRed();
					output = output + "" + (char) c.getGreen();
					output = output + "" + (char) c.getBlue();
					counter++;
				}
			}
			output = output + "" + (char) 1000; // trennzeichen
		}
		return output;
	}

	private void clearMap() {
		for (int x = 0; x < cubeSize; x++) {
			for (int y = 0; y < cubeSize; y++) {
				for (int z = 0; z < cubeSize; z++) {
					map[x][y][z].setC(new Color(0, 0, 0));
				}
			}
		}
	}

	public static void main(String[] args) {
		new Start();
	}

}
