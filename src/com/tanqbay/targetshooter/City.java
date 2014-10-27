package com.tanqbay.targetshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class City extends GameItem {
	
	private Paint paint;
	private double shieldPosition;
	private double planetPosition;
	//private double shieldStrength = 100;
	//private double shieldRegeneration = 0.5;
	private Bitmap planetImage;
	private double planetRadius;
	
	
	public City(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		life = 100;
		maxLife = 100;
		regeneration = 5;
		collisionDamageDealt = 1000;
		
		paint = new Paint();
		//paint.setStrokeWidth(7);
		//paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setAntiAlias(true);
		paint.setColor(0xaaddddff);
		
		shieldPosition = surfaceHeight * (7.0/8.0);
		
		planetRadius = surfaceWidth * 2.0;
		
		planetPosition = shieldPosition + surfaceHeight * (1.0/12.0);
		
		planetImage = BitmapFactory.decodeResource(drawingSurface.getContext().getResources(),R.drawable.planet);
	}
	
	
	public void drawSelf(Canvas canvas){ 
		
		RectF rect = new RectF((float) ((surfaceWidth / 2.0) - planetRadius),(float) -planetRadius,(float) ((surfaceWidth / 2.0) + planetRadius),(float) planetRadius);
		
		Rect sourceRect = new Rect(0,0,planetImage.getWidth(),planetImage.getHeight());
		
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.translate(0,surfaceHeight + (float) (surfaceWidth * 1.85));
		
		
		canvas.drawBitmap(planetImage,sourceRect,rect,new Paint());
		
		canvas.restore();
		
		
		RectF shieldBounds = new RectF((float) -(surfaceWidth * (1.0/4.0)),
										(float) (shieldPosition),
										(float) (surfaceWidth * (5.0/4.0)),
										(float) (surfaceHeight * (9.0/8.0)));
		
		paint.setColor(0xaaddddff);
		
		int shieldAlpha = (int) (0xaa * (life / 100));
		
		paint.setAlpha(shieldAlpha);
		
		canvas.drawArc(shieldBounds,180,180,true,paint);
		
	}
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
			super.update(timeDifference,drawingSurface);
			
	}
	
	public float getRadius(){
		return (float) planetRadius;
	}
	
	public float[] getPosition(){
		return new float[]{(float) surfaceWidth / 2,(float) (planetPosition + planetRadius)};
	}
	
	public double getShieldPosition(){
		return shieldPosition;
	}
	
	public double getShieldStrength(){
		return life;
	}
	
	public boolean checkForCollision(){
		return true;
	}
}