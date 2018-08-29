package bll.animation;

import java.awt.Color;

import app.Start;
import bll.SerialLED;

public class Smiley extends Animation {

	private Color b = new Color(0, 0, 0);
	private Color g = new Color(0, 255, 0);
	private int counter = -1, i = 1;

	private Color[][] face = { { b, b, g, g, g, g, b, b }, { b, g, b, b, b, b, g, b }, { g, b, g, b, b, g, b, g },
			{ g, b, b, b, b, b, b, g }, { g, b, g, b, b, g, b, g }, { g, b, b, g, g, b, b, g },
			{ b, g, b, b, b, b, g, b }, { b, b, g, g, g, g, b, b } };

	public void tick(SerialLED[][][] leds) {
		counter += i;
		if((counter == 7 && i > 0) || (counter == 0 && i < 0))
			i*=-1;
		for (int y = 0; y < Start.cubeSize; y++) {
			for (int z = 0; z < Start.cubeSize; z++) {
				leds[counter][y][z].setC(face[y][z]);
			}
		}
	}

}
