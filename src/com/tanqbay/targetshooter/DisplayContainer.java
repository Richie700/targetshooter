package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class DisplayContainer extends Item {

	
	private Paint paint;
	private Rect backgroundRect;
	
	public DisplayContainer(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		drawOrder = 100;
		
		
		paint = new Paint();
		paint.setColor(0xffcccccc);
		
		backgroundRect = new Rect(0,0,drawingSurface.getWidth(),drawingSurface.getHeight() / 12);
		
	}
	
	public void drawSelf(Canvas canvas){
		canvas.drawRect(backgroundRect,paint);
	}
	
	
}