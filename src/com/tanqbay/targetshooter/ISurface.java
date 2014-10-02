package com.tanqbay.targetshooter;

import android.graphics.Canvas;

public interface ISurface {
	void onInitalize();
	void onRedraw(Canvas canvas);
	void onUpdate(double gameTime);
}