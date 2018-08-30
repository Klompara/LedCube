package bll.animation;

import java.awt.Color;

import bll.SerialLED;

public class Helix extends Animation {
	private Color b = new Color(0, 0, 0);
	private Color g = new Color(0, 255, 0);
	int counter = 0, i = 1;

	private int[][] circle = { { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 }, { 1, 1 }, { 1, 6 }, { 2, 0 }, { 2, 7 }, { 3, 0 },
			{ 3, 7 }, { 4, 0 }, { 4, 7 }, { 5, 0 }, { 5, 7 }, { 6, 1 }, { 6, 6 }, { 7, 2 }, { 7, 3 }, { 7, 4 },
			{ 7, 5 } };

	public void tick(SerialLED[][][] leds) {
		counter += i;
		for (int i = 0; i < circle.length; i++) {
			leds[circle[i][0]][circle[i][1]][i / 3].setC(new Color(255, 0, 0));
		}
	}
}