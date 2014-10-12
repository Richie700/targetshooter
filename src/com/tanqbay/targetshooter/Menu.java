package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class Menu extends Item {
	
	private Paint paint;
	
	public Menu(DrawingSurface drawingSurface){
		super(drawingSurface);
		
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
	
	public void handleTouchEvent(MotionEvent event,DrawingSurface drawingSurface){
		int Index = event.getActionIndex();
		
		float XCoord = event.getX(Index);
		float YCoord = event.getY(Index);
		
		if(event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
			 
			 if(XCoord > surfaceWidth / 2){
			 		setReadyToBeRemoved(true);
			 	drawingSurface.getPauseButton().UnPauseGame();
			 }
			}
	}
}