package bll.animation;

import java.awt.Color;

import app.Start;
import bll.SerialLED;

public class Rainbow extends Animation {

	public void tick(SerialLED[][][] leds) {
		for (int x = 0; x < Start.cubeSize; x++) {
			for (int y = 0; y < Start.cubeSize; y++) {
				for (int z = 0; z < Start.cubeSize; z++) {
					int r = toRGB((float) x / (float) 7 * (float) 255);
					int g = toRGB((float) y / (float) 7 * (float) 255);
					int b = toRGB((float) z / (float) 7 * (float) 255);
					leds[x][y][z].setC(new Color(r, g, b));
				}
			}
		}
	}
	
	private int toRGB(float i) {
		if(i > 255)
			i = 255;
		if(i < 0)
			i = 0;
		return (int)i;
	}

}
