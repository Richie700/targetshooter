package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class WaveComplete extends Item{
	
	private Paint paint;
	private int waveNumber;
	private int frameCount = 0;
	
	public WaveComplete(DrawingSurface drawingSurface,int waveNumber) {
		super(drawingSurface);
		
		this.waveNumber = waveNumber;
		
		paint = new Paint();
		
		paint.setTextSize(30);
		paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
		
		paint.setColor(0xff00ff00);
		paint.setAntiAlias(true);
		
	}
	
	public void drawSelf(Canvas canvas){
		
		paint.setColor(0xff00ff00);
		
		String message = "Wave" + String.valueOf(waveNumber) + " Complete";
		
		float textWidth = paint.measureText(message);
		
		float startX = (surfaceWidth / 2) - (textWidth / 2);
		float startY = surfaceHeight / 2;
		
		canvas.drawText(message,startX,startY,paint);
	}
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		frameCount++;
		
		if(frameCount > 100){
			setReadyToBeRemoved(true);
		}
	
	}
	
}
