package com.tanqbay.targetshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;


public class Bomb extends Target {
	
	private Paint paint;
	private Bitmap bomb;
	
	public Bomb(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		collisionDamage = 10;
		collisionDamageDealt = 10;
		
		radius = 20;
		
		paint = new Paint();
		
		paint.setColor(0xff00ff00);
		paint.setAntiAlias(true);
		
		bomb = BitmapFactory.decodeResource(drawingSurface.getContext().getResources(),R.drawable.bomb1);
		
	}
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		
		Velocity[1] = 75;
		
		super.update(timeDifference,drawingSurface);
		
	}
	
	public void drawSelf(Canvas canvas){
		if(!hit){
			
			RectF rect = new RectF(-radius,-radius,radius,radius);
			
			Rect sourceRect = new Rect(0,0,bomb.getWidth(),bomb.getHeight());
			
			canvas.save(Canvas.MATRIX_SAVE_FLAG);
			canvas.translate(Position[0],Position[1]);
			
			
			canvas.drawBitmap(bomb,sourceRect,rect,new Paint());
			
			canvas.restore();
			
			//canvas.drawCircle(Position[0],Position[1],radius,paint);
		}
	}
	
	public void setPosition(float[] newPosition){
		Position = newPosition;
	}
	
	public void setDestination(float[] newDestination){
		Destination = newDestination;
	}
}