package com.tanqbay.targetshooter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;
import android.util.Log;

public class Gun extends Item {
	
	
	private float[] Pivot;
	private double pivotRadius;
	private double length;
	private double handleLength;
	private double angle;
	
	private LaserBank laserBank;
	
	private Paint paint;
	private Bitmap gun;
	
	public Gun(DrawingSurface drawingSurface){
		super(drawingSurface);
		
		Pivot = new float[]{drawingSurface.getWidth() / 2, (float) (drawingSurface.getHeight() * (3.0/4.0))};
		length = drawingSurface.getHeight() / 5.0;
		handleLength = (2.0 * length) / 5.0;
		pivotRadius = drawingSurface.getWidth() / 20.0;
		angle = (float) 0.1;
		
		laserBank = new LaserBank(200,0.500);
		
		paint = new Paint();
		paint.setStrokeWidth(7);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setAntiAlias(true);
		
		gun = BitmapFactory.decodeResource(drawingSurface.getContext().getResources(),R.drawable.gun1);
	}
	
	public void drawSelf(Canvas canvas){ 
		
		double gunLength = length - handleLength;
		
		double startX = (FloatMath.sin((float) -angle) * handleLength);
		double startY = (FloatMath.cos((float) -angle) * handleLength);
		double endX = (FloatMath.sin((float) angle) * gunLength);
		double endY = (FloatMath.cos((float) angle) * gunLength);
		
		RectF rect = new RectF((float) -(length / 4),0,(float) (length / 4),(float) length);
		
		Rect sourceRect = new Rect(0,0,gun.getWidth(),gun.getHeight());
		
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		//canvas.rotate((float) ((angle * (180/Math.PI)) + 180),Position[0],Position[1]);
		
		canvas.translate((float) getBarrelPosition()[0],(float) getBarrelPosition()[1]);
		canvas.rotate((float) (angle * (180/Math.PI)));
		
		
		canvas.drawBitmap(gun,sourceRect,rect,new Paint());
		
		canvas.restore();
		
	}
	
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		
		if(!drawingSurface.getPaused()){
		   laserBank.updateTime(timeDifference);
		}
		
	}
	
	public void handleTouchEvent(SimpleMotionEvent event,DrawingSurface drawingSurface){
		
		float y = event.getYCoord();
		float x = event.getXCoord();
		
		if(!drawingSurface.getPaused()){
			if(y > drawingSurface.getHeight() / 6.0 
				&& event.isDown()){
			   laserBank.fire(drawingSurface,getBarrelPosition(),-angle);
			}
			
			if(event.isMove()){
				float middle = (float) drawingSurface.getWidth() / 2;
				
				
				if(y > drawingSurface.getHeight() / 2.0 
					&& x < drawingSurface.getWidth() * (4.0/5.0) 
					&& x > drawingSurface.getWidth() * (1.0/5.0)){
					
					double opp = middle - x;
					double adj = getPivot()[1] - y;
					
					
					if(y >= getPivot()[1]){
						float angle = (float) Math.atan(opp/adj);
						
						setAngle(-angle);
					}else{
						float angle = (float) Math.atan(opp/adj);
						
						setAngle((float) (-angle - Math.PI));
					}
					
				}
			}
		}
	}
	
	
	public double[] getBarrelPosition(){
		
		double gunLength = length - handleLength;
		
		double barrelX = (gunLength * FloatMath.sin((float) angle)) + Pivot[0];
		double barrelY = Pivot[1] - (gunLength * FloatMath.cos((float) angle));
		
		return new double[]{barrelX,barrelY};
	}
	
	public void setAngle(float newAngle){
		angle = newAngle;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public double getLength(){
		return length;
	}
	
	public double getHandleLength(){
		return handleLength;
	}
	
	public float[] getPivot(){
		return Pivot;
	}
	
	public int getType(){
		return GUN_TYPE;
	}
	
	public float[] getPosition(){
		return Pivot;
	}
	
	public float getRadius(){
		return (float) length;
	}
	
	public boolean checkForCollision(){
		return true;
	}

}