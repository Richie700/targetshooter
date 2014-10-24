package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class Menu extends GameItem {
	
	private Paint paint;
	private Game game;
	
	public Menu(DrawingSurface drawingSurface){
		super(drawingSurface);
		
		game = drawingSurface.getGame();
		
		paint = new Paint();
		
		paint.setTextSize(20);
		paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
		
		paint.setColor(0xff00ff00);
		paint.setAntiAlias(true);
	}
	
	
	public void drawSelf(Canvas canvas){
		
			paint.setColor(0xaa000000);
			canvas.drawPaint(paint);
			
			paint.setTextSize(30);
			paint.setColor(0xff000099);
			
			String message = "Menu";
			
			float textWidth = paint.measureText(message);
			
			float startX = (surfaceWidth / 2) - (textWidth / 2);
			float startY = surfaceHeight / 2;
			
			canvas.drawText(message,startX,startY,paint);
		
	}
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		
	}
	
	public void handleTouchEvent(SimpleMotionEvent event,DrawingSurface drawingSurface){
		
		float x = event.getXCoord();
		float y = event.getYCoord();
		
		if(event.isDown()){
			 
			 if(y > surfaceHeight / 2){
			 		setReadyToBeRemoved(true);
			 	game.unPause();
			 }
			}
	}
}