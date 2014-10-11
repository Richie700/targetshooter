package com.tanqbay.targetshooter;

public class WaveComlete extends Item{
	
	private int waveNumber;
	private int frameCount = 0;
	
	public WaveComplete(DrawingSurface drawingSurface,int waveNumber) {
		super(drawingSurface);
		
		this.waveNumber = waveNumber;
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
