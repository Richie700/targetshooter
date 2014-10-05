package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class DisplayContainer extends Item {

	private Score score;
	private PauseButton pauseButton;
	private Paint paint;
	private Rect backgroundRect;
	
	public DisplayContainer(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		drawOrder = 100;
		
		score = new Score(drawingSurface);
		//drawingSurface.getItems().add(score);
		
		pauseButton = new PauseButton(drawingSurface);
		//drawingSurface.getItems().add(pauseButton);
		
		paint = new Paint();
		paint.setColor(0xffcccccc);
		
		backgroundRect = new Rect(0,0,drawingSurface.getWidth(),drawingSurface.getHeight() / 12);
		
	}
	
	public void drawSelf(Canvas canvas){
		canvas.drawRect(backgroundRect,paint);
		score.drawSelf(canvas);
		pauseButton.drawSelf(canvas);
	}
	
	public PauseButton getPauseButton(){
		return pauseButton;
	}
	
	public Score getScore(){
		return score;
	}
}