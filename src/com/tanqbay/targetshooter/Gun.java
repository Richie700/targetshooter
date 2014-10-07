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
import android.view.MotionEvent;

public class Gun extends Item {
	
	
	private float[] Pivot;
	private double pivotRadius;
	private double length;
	private double handleLength;
	private double angle;
	
	private ArrayList<LaserBeam> lasers = new ArrayList<LaserBeam>();
	private float laserSpeed;
	private double rateOfFire = 0.500;
	private double lastShot;
	private double timeSinceLastShot = 0;
	//private float surfaceHeight;
	private Paint paint;
	private Bitmap gun;
	
	public Gun(DrawingSurface drawingSurface){
		super();
		
		Pivot = new float[]{drawingSurface.getWidth() / 2, (float) (drawingSurface.getHeight() * (3.0/4.0))};
		length = drawingSurface.getHeight() / 5.0;
		handleLength = (2.0 * length) / 5.0;
		pivotRadius = drawingSurface.getWidth() / 20.0;
		angle = (float) 0.1;
		
		laserSpeed = 200;
		
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
		
		/*for(int i = 0;i < lasers.size();i++){
			lasers.get(i).drawSelf(canvas);
		}*/
	}
	
	/*public void drawSelf2(Canvas canvas){ 
		
		double gunLength = length - handleLength;
		
		double startX = (FloatMath.sin((float) -angle) * handleLength) + Pivot[0];
		double startY = (FloatMath.cos((float) -angle) * handleLength) + Pivot[1];
		double endX = (FloatMath.sin((float) angle) * gunLength) + Pivot[0];
		double endY = Pivot[1] - (FloatMath.cos((float) angle) * gunLength);
		
		paint.setColor(0xff000099);
		canvas.drawLine((float) startX,(float) startY,(float) endX,(float) endY,paint);
		
		paint.setColor(0x88aaaaaa);
		
		
		RectF shieldBounds = new RectF((float) (getPivot()[0] - pivotRadius),
										(float) (getPivot()[1] - pivotRadius),
										(float) (getPivot()[0] + pivotRadius),
										(float) (getPivot()[1] + pivotRadius));

		canvas.drawArc(shieldBounds,(float) (angle * (180 / Math.PI)),-180,true,paint);
		
		for(int i = 0;i < lasers.size();i++){
			lasers.get(i).drawSelf(canvas);
		}
	}*/
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		
		//Log.i("Lasers",String.valueOf(lasers.size()));
		
		if(!drawingSurface.getPaused()){
			timeSinceLastShot += timeDifference;
		}
		
		for(int i = lasers.size() - 1;i >= 0;i--){
			try{
				//lasers.get(i).update(timeDifference);
				if(lasers.get(i).getHit() || lasers.get(i).reachedLimit()){
					lasers.remove(i);
				}
			}catch(IndexOutOfBoundsException e){
				
			}
		}
	}
	
	public void handleTouchEvent(MotionEvent event,DrawingSurface drawingSurface){
		int Index = event.getActionIndex();
		
		float XCoord = event.getX(Index);
		float YCoord = event.getY(Index);
		
		if(!drawingSurface.getPaused()){
			if(YCoord > drawingSurface.getHeight() / 6.0 
				//&& (XCoord > drawingSurface.getWidth() * (4.0/5.0) || XCoord < drawingSurface.getWidth() * (1.0/5.0))
				&& (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)){
				fire(drawingSurface);
			}
			
			if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
				float middle = (float) drawingSurface.getWidth() / 2;
				
				
				//if(YCoord > drawingSurface.getHeight() - (drawingSurface.getHeight() - getPivot()[1])){
				if(YCoord > drawingSurface.getHeight() / 2.0 && XCoord < drawingSurface.getWidth() * (4.0/5.0) && XCoord > drawingSurface.getWidth() * (1.0/5.0)){
					
					double opp = middle - XCoord;
					double adj = getPivot()[1] -YCoord;
					
					
					if(YCoord >= getPivot()[1]){
						float angle = (float) Math.atan(opp/adj);
						
						setAngle(-angle);
					}else{
						float angle = (float) Math.atan(opp/adj);
						
						setAngle((float) (-angle - Math.PI));
					}
					
					/*double opp = middle - XCoord;
					
					
					if(opp > 0){
						opp = Math.min(opp,getHandleLength());
					}else{
						opp = Math.max(opp,-getHandleLength());
					}
					
					if(YCoord >= getPivot()[1]){
						float angle = (float) Math.asin(opp/getHandleLength());
						
						setAngle(angle);
					}else{
						float angle = (float) Math.asin(opp/getHandleLength());
						
						setAngle((float) (Math.PI - angle));
					}*/
					
				}
			}
		}
	}
	
	public void fire(DrawingSurface drawingSurface){
		if(timeSinceLastShot >= rateOfFire){
			LaserBeam laser = new LaserBeam(getBarrelPosition(),laserSpeed,-angle,drawingSurface);
			lasers.add(laser);
			drawingSurface.getItems().add(laser);
			lastShot = System.currentTimeMillis();
			timeSinceLastShot = 0;
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
	
	public ArrayList<LaserBeam> getLasers(){
		return lasers;
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
	
	public boolean checkForHit(ArrayList<Item> items){
		
		boolean hitFound = false;
		
		LaserBeam laser;
		Target target;
		int targetHit = -1;
		int laserHit = -1;
		
		for(int i = 0;i < lasers.size();i++){
			try{
				laser = lasers.get(i);
				for(int j = 0;j < items.size();j++){
					try{
						if(items.get(j).getType() == Item.TARGET_TYPE){
							target = (Target) items.get(j);
							if(!target.getHit() && !laser.getHit() && target.pointInside(laser.getEnd())){
								targetHit = j;
								laserHit = i;
							}
						}
					}catch(IndexOutOfBoundsException e){
						Log.i("Why","Why does this happen");
					}catch(NullPointerException e){
						
					}
				}
			}catch(IndexOutOfBoundsException e){
				Log.i("Why","Why does this happen here also");
			}
		}
		
		if(laserHit > -1){
			try{
				lasers.get(laserHit).setHit();
				hitFound = true;
			}catch(IndexOutOfBoundsException e){
				
			}
		}
		if(targetHit > -1){
			try{
				((Target) items.get(targetHit)).setHit();
				hitFound = true;
			}catch(IndexOutOfBoundsException e){
			}
		}
		
		return hitFound;
	}
}