package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class PauseButton extends Item {
	
	private Paint paint;
	private Game game;
	
	public PauseButton(DrawingSurface drawingSurface){
		super(drawingSurface);
		
		game = drawingSurface.getGame();
		
		drawOrder = 100;
		
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
		
	}
	
	public void handleTouchEvent(SimpleMotionEvent event,DrawingSurface drawingSurface){
		
		float x = event.getXCoord();
		float y = event.getYCoord();
		
		if(event.isDown()){
			//if(!Paused){
				if(x > surfaceWidth - (surfaceWidth / 5) && y < surfaceHeight / 10){
					game.pause();
					
					Menu menu = new Menu(drawingSurface);
					
					drawingSurface.getItems().add(menu);
					
				}
		//}
	}
}
	
}