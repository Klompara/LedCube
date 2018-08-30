package bll.animation;

import java.awt.Color;

import app.Start;
import bll.SerialLED;

public class SineWave extends Animation{
	
	private int xcounter = -1, ycounter = -1, zcounter = -1;
	private float counter = 0, i = 0.2f;
	@Override
	public void tick(SerialLED[][][] leds) {
		
		counter += i;
		
		for (int x = 0; x < Start.cubeSize; x++) {
			int y = (int) ((int)3*Math.sin(2*x-counter)) + 3;
			leds[x][y][5].setC(new Color(255,0,0));
		}
	}

}
