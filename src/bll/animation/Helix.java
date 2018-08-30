package bll.animation;

import java.awt.Color;

import bll.SerialLED;

public class Helix extends Animation {
	int counter = 0, i = 1;

	private int[][] circle = { { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 }, { 1, 6 }, { 2, 7 }, { 3, 7 }, { 4, 7 }, { 5, 7 },
			{ 6, 6 }, { 7, 5 }, { 7, 4 }, { 7, 3 }, { 7, 2 }, { 6, 1 }, { 5, 0 }, { 4, 0 }, { 3, 0 }, { 2, 0 },
			{ 1, 1 } };

	public void tick(SerialLED[][][] leds) {
		counter += i;
		for (int e = 0; e < 3; e++) {
			for (int i = 0; i < circle.length; i++) {
				leds[circle[i][0]][circle[i][1]][i / 4 + e * 4].setC(new Color(255, 0, 0));
			}
		}

	}
}