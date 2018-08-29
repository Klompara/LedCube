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
				for (int z = 0; z < cubeSize; z++) {
					a.sendMessage(ledsToString(z));
				}
				running = false;
			}

		}
		a.closeCon();
	}

	private String ledsToString(int ebene) {
		String output = "";
		for (int x = 0; x < cubeSize; x++) {
			for (int y = 0; y < cubeSize; y++) {
				// Color c = map[x][y][ebene].getC();
				output = output + x + ";" + y + ";" + ebene + ";";
			}
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
