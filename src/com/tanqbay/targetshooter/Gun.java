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
		super(drawingSurface);
		
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
	
	public void handleTouchEvent(SimpleMotionEvent event,DrawingSurface drawingSurface){
		
		float y = event.getYCoord();
		float x = event.getXCoord();
		
		if(!drawingSurface.getPaused()){
			if(y > drawingSurface.getHeight() / 6.0 
				&& event.isDown()){
				fire(drawingSurface);
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
	
	/*public boolean checkForHit(ArrayList<Item> items){
		
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
	}*/
}