package app;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import bll.SerialLED;
import bll.animation.Animation;
import bll.animation.Helix;
import bll.animation.Plates;
import bll.animation.Rainbow;
import bll.animation.SineWave;
import bll.animation.Smiley;
import dal.ArduinoConnector;
import gui.Camera;

public class Start {

	private boolean running = true;

	private long tickrate = 100;
	private Date lastTicked;

	private SerialLED[][][] map;
	public static final int cubeSize = 8;

	private ArrayList<Animation> animationen = new ArrayList<Animation>();
	private int currentAnimation = 2;

	public Start() {
		ArduinoConnector a = ArduinoConnector.getInstance();
		Camera.startCamera(this);
		a.openCon();
		// a.startReceivingMessages();
		animationen.add(new Plates());
		animationen.add(new Smiley());
		animationen.add(new Rainbow());
		animationen.add(new SineWave());
		animationen.add(new Helix());

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
				a.sendMessage(ledsToString());
			}
		}
		a.stopReceivingMessages();
		a.closeCon();
	}

	private String ledsToString() {
		String output = "";
		int counter;
		for (int z = 0; z < Start.cubeSize; z++) {
			counter = 0;
			output = addCharToString(output, 254); // anfang
			output = addCharToString(output, z); // nach anfang 1x ebene
			for (int x = 0; x < Start.cubeSize; x++) {
				for (int y = 0; y < Start.cubeSize; y++) {
					Color c = map[x][y][z].getC();
					output = addCharToString(output, new int[] { counter, trimColor(c.getRed()),
							trimColor(c.getGreen()), trimColor(c.getBlue()) }); // (id
																				// (0-63)
																				// +
																				// rgb
																				// (0-253))
																				// *
																				// 64
																				// mal
					counter++;
				}
			}
			output = addCharToString(output, 255); // ende
		}
		return output; // [254 ebene [(id rgb)*64] 255] * 8
	}

	private int trimColor(int c) {
		if (c >= 254)
			c = 253;
		return c;
	}

	private String addCharToString(String o, int c) {
		o = o + "" + (char) +c;
		return o;
	}

	private String addCharToString(String o, int[] c) {
		for (int i : c)
			o = addCharToString(o, i);
		return o;
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

	public Color[][][] getmap() {
		Color[][][] c = new Color[cubeSize][cubeSize][cubeSize];
		if (map == null)
			return null;
		for (int x = 0; x < cubeSize; x++) {
			for (int y = 0; y < cubeSize; y++) {
				for (int z = 0; z < cubeSize; z++) {
					c[x][y][z] = map[x][y][z].getC();
				}
			}
		}
		return c;
	}

	public static void main(String[] args) {
		new Start();
	}

}
