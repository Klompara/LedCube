package bll.animation;

import bll.SerialLED;

public abstract class Animation {

	public Animation() {

	}

	public abstract void tick(SerialLED[][][] leds);

}
