package bll.animation;

import java.awt.Color;

import app.Start;
import bll.SerialLED;

public class Plates extends Animation {

	private int xcounter = -1, ycounter = -1, zcounter = -1;
	private int count = 1;

	public void tick(SerialLED[][][] leds) {
		xcounter += count;
		ycounter += count;
		zcounter += count;

		if (xcounter == Start.cubeSize - 1 || xcounter == 0)
			count *= -1;

		for (int x = 0; x < Start.cubeSize; x++) {
			for (int y = 0; y < Start.cubeSize; y++) {
				for (int z = 0; z < Start.cubeSize; z++) {
					int curR = leds[x][y][z].getC().getRed();
					int curG = leds[x][y][z].getC().getGreen();
					int curB = leds[x][y][z].getC().getBlue();

					if (x == xcounter) {
						curR = 255;
					} else if (y == ycounter) {
						curG = 255;
					} else if (z == zcounter) {
						curB = 255;
					}

					leds[x][y][z].setC(new Color(curR, curG, curB));
				}
			}
		}
	}
}
