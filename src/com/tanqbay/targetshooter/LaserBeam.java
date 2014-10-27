package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.FloatMath;

public class LaserBeam extends GameItem {
	
	private double[] start;
	private double[] currentStart;
	private double[] currentEnd;
	private double angle;
	private float actualLength = 40;
	private double length = 40;
	private float actualWidth = 10;
	private double width = 10;
	private double speed;
	private double range;
	private boolean hit = false;
	private Paint paint;
	
	public LaserBeam(double[] start, double speed, double angle, DrawingSurface drawingSurface){
		super(drawingSurface);
		
		collisionDamageDealt = 5;
		collisionDamage = 2;
		
		this.start = start;
		this.currentStart = start;
		this.currentEnd = start;
		this.angle = angle;
		this.range = drawingSurface.getHeight() * 1.1;
		
		this.speed = speed;
		
		paint = new Paint();
		paint.setColor(0xffff0000);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setAntiAlias(true);
	}
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		if(reachedLimit()){
		   setReadyToBeRemoved(true);
		}
			
			length =  actualLength * ((currentStart[1])/start[1]);
			width =  actualWidth * ((currentStart[1])/start[1]);
			
			double XChange = -1 * (FloatMath.sin((float) angle) * speed * timeDifference);
			double YChange = -1 * (FloatMath.cos((float) angle) * speed * timeDifference);
			
			currentEnd = new double[]{(currentEnd[0] + XChange),(currentEnd[1] + YChange)};
			
			if(distance(currentStart[0],currentStart[1],currentEnd[0],currentEnd[1]) > length){
				currentStart = new double[]{(currentStart[0] + XChange),(currentStart[1] + YChange)};
			}
		
	}
	
	public void drawSelf(Canvas canvas){
		if(!hitTarget() && !reachedLimit()){
			
			paint.setStrokeWidth((float) width);
			
			canvas.drawLine((float) currentStart[0],(float) currentStart[1],(float) currentEnd[0],(float) currentEnd[1],paint);
		}
	}
	
	public boolean checkForCollision(){
		return true;
	}
	
	public boolean reachedLimit(){
		return distance(start[0],start[1],currentEnd[0],currentEnd[1]) > range;
	}
	
	public boolean hitTarget(){
		return hit;
	}
	
	public void setHit(){
		hit = true;
	}
	
	public boolean getHit(){
		return hit;
	}
	
	public double[] getEnd(){
		return currentEnd;
	}
	
	public float[] getPosition(){
		return new float[]{(float) currentEnd[0],(float) currentEnd[1]};
	}
	
	public double getAngle(){
		return angle;
	}
	
	public int getType(){
		return WEAPONFIRE_TYPE;
	}
}