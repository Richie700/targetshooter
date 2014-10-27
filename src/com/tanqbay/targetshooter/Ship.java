package com.tanqbay.targetshooter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/*
 * Ship Ideas:
 * Bomber - stops and moves back and forth avoiding lasers and dropping bombs
 * Kamikaze flies into shield
 * 
 */

public class Ship extends SmartTarget {
	
	protected Bitmap shipImage;
	
	
	public Ship(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		collisionDamage = 10;
 collisionDamageDealt = 10;
		
		Velocity = new float[]{0,0};
		Acceleration = new float[]{0,0};
		
		shipImage = BitmapFactory.decodeResource(drawingSurface.getContext().getResources(),R.drawable.ship1);
		
	}
	
	public void drawSelf(Canvas canvas){
		if(!hit){
			
			RectF rect = new RectF(-radius,-radius,radius,radius);
			
			Rect sourceRect = new Rect(0,0,shipImage.getWidth(),shipImage.getHeight());
			
			canvas.save(Canvas.MATRIX_SAVE_FLAG);
			
			canvas.translate(Position[0],Position[1]);
			canvas.rotate((float) (-1 * rotation * (180/Math.PI)));
			
			
			canvas.drawBitmap(shipImage,sourceRect,rect,new Paint());
			
			canvas.restore(); 
			 
		}
	}
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		super.update(timeDifference,drawingSurface);
		
	}
	
}