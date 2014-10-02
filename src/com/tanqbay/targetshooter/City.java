package com.tanqbay.targetshooter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class City extends Item {
	
	private Paint paint;
	private double shieldPosition;
	private double planetPosition;
	private double shieldStrength = 100;
	private double shieldRegeneration = 0.5;
	private Bitmap planetImage;
	
	public City(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		paint = new Paint();
		//paint.setStrokeWidth(7);
		//paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setAntiAlias(true);
		paint.setColor(0xaaddddff);
		
		shieldPosition = drawingSurface.getHeight() * (7.0/8.0);
		
		planetPosition = shieldPosition + drawingSurface.getHeight() * (1.0/12.0);
		
		planetImage = BitmapFactory.decodeResource(drawingSurface.getContext().getResources(),R.drawable.planet);
	}
	
	
	public void drawSelf(Canvas canvas){ 
		
		/*RectF planetBounds = new RectF((float) -(drawingSurface.getWidth() * (1.0/4.0)),
				(float) (planetPosition),
				(float) (drawingSurface.getWidth() * (5.0/4.0)),
				(float) (drawingSurface.getHeight() * (9.0/8.0)));*/
		
		//paint.setColor(0xff00bb55);
		//canvas.drawArc(planetBounds,180,180,true,paint);
		
		double planetRadius = drawingSurface.getWidth() * 2.0;
		
		RectF rect = new RectF((float) ((drawingSurface.getWidth() / 2.0) - planetRadius),(float) -planetRadius,(float) ((drawingSurface.getWidth() / 2.0) + planetRadius),(float) planetRadius);
		
		Rect sourceRect = new Rect(0,0,planetImage.getWidth(),planetImage.getHeight());
		
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.translate(0,drawingSurface.getHeight() + (float) (drawingSurface.getWidth() * 1.85));
		
		
		canvas.drawBitmap(planetImage,sourceRect,rect,new Paint());
		
		canvas.restore();
		
		
		RectF shieldBounds = new RectF((float) -(drawingSurface.getWidth() * (1.0/4.0)),
										(float) (shieldPosition),
										(float) (drawingSurface.getWidth() * (5.0/4.0)),
										(float) (drawingSurface.getHeight() * (9.0/8.0)));
		
		paint.setColor(0xaaddddff);
		
		int shieldAlpha = (int) (0xaa * (shieldStrength / 100));
		
		paint.setAlpha(shieldAlpha);
		
		canvas.drawArc(shieldBounds,180,180,true,paint);
		
	}
	
	public void update(double timeDifference){
		if(!drawingSurface.getPauseButton().getPaused()){
			shieldStrength += shieldRegeneration * timeDifference;
			
			if(shieldStrength > 100){
				shieldStrength = 100;
			}
		}
	}
	
	public double getShieldPosition(){
		return shieldPosition;
	}
	
	public void reduceShield(double Amount){
		shieldStrength -= Amount;
	}
	
	public double getShieldStrength(){
		return shieldStrength;
	}
}
