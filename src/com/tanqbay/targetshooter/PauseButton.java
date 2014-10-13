package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class PauseButton extends Item {
	
	private Paint paint;
	private boolean Paused = false;
	private double timePaused;
	private double pauseLength = 0;
	
	public PauseButton(DrawingSurface drawingSurface){
		super(drawingSurface);
		
		paint = new Paint();
		
		paint.setTextSize(20);
		paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
		
		paint.setColor(0xff00ff00);
		paint.setAntiAlias(true);
	}
	
	
	public void drawSelf(Canvas canvas){
		String pauseMessage = "";
		
		
		pauseMessage = "Pause";
		
		paint.setTextSize(20);
		paint.setColor(0xff000099);
		
		float textWidth = paint.measureText(pauseMessage);
		
		float startX = surfaceWidth - textWidth;
		
		canvas.drawText(pauseMessage,startX,30,paint);
		
		/*if(Paused){
			paint.setColor(0xaa000000);
			canvas.drawPaint(paint);
			
			paint.setTextSize(30);
			paint.setColor(0xff000099);
			
			String message = "Paused";
			
			textWidth = paint.measureText(message);
			
			startX = (surfaceWidth / 2) - (textWidth / 2);
			float startY = surfaceHeight / 2;
			
			canvas.drawText(message,startX,startY,paint);
		}*/
	}
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		if(Paused){
			pauseLength = pauseLength + timeDifference;
		}else{
			pauseLength = 0;
		}
	}
	
	public void handleTouchEvent(SimpleMotionEvent event,DrawingSurface drawingSurface){
		
		float XCoord = event.getXCoord();
		float YCoord = event.getYCoord();
		
		if(event.isDown()){
			if(!Paused){
				if(XCoord > surfaceWidth - (surfaceWidth / 5) && YCoord < surfaceHeight / 10){
					togglePause();
					
					Menu menu = new Menu(drawingSurface);
					
					drawingSurface.getItems().add(menu);
					
				}
		}
	}
}
	
	public void togglePause(){
		if(Paused){
			UnPauseGame();
		}else{
			PauseGame();
		}
	}
	
	public void PauseGame(){
		Paused = true;
		//timePaused = System.currentTimeMillis();
	}
	
	public void UnPauseGame(){
		Paused = false;
		//pauseLength = System.currentTimeMillis() - timePaused;
	}
	
	public boolean getPaused(){
		return Paused;
	}
	
	public double getPauseLength(){
		return pauseLength;
	}
}