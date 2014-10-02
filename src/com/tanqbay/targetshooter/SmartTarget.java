package com.tanqbay.targetshooter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;
import android.util.Log;
import android.graphics.Paint;

public class SmartTarget extends Target {
	
	private float laserTooClose = 250;
	private float Epsilon = (float) 0.01;
	//private float actualMaxAcceleration = 1000;
	
	protected float rotation;
	protected float destinationRotation;
	
	public SmartTarget(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		//Velocity = new float[]{0,0};
		//Acceleration = new float[]{0,0};
		
		
		//Destination = new float[]{surfaceWidth / 2,surfaceHeight + radius};
		rotation = (float) Math.PI;
		destinationRotation = (float) Math.PI;
	}
	
	
	public void update(double timeDifference){
		super.update(timeDifference);
		//Log.i("rotation",String.valueOf(rotation));
		//Log.i("destinationRotation",String.valueOf(destinationRotation));
		if(!drawingSurface.getPaused()){
			
			calculateDestinationRotation();
			
			calculateDestination();
			
			if(Math.abs(rotation - destinationRotation) > Epsilon){
				if(destinationRotation > rotation){
					rotation += 0.03;
				}else{
					rotation -= 0.03;
				}
			}
		}
	}
	
	protected void calculateDestinationRotation(){
		destinationRotation = (float) Math.asin((Position[0] - Destination[0]) / (Position[1] - Destination[1]));
		
		if(Float.isNaN(destinationRotation)){
			destinationRotation = (float) Math.acos((Position[1] - Destination[1]) / (Position[0] - Destination[0]));
			if(Destination[0] < Position[0]){
				destinationRotation = destinationRotation + (float) Math.PI;
			}
		}
		
		destinationRotation = destinationRotation + (float) (Math.PI);
		
		if(Math.abs(destinationRotation - rotation) > (Math.PI)){
			destinationRotation = destinationRotation - (float) (2 * Math.PI);
		}
	}
	
	protected void calculateDestination(){
		
		Destination = new float[]{Position[0],drawingSurface.getHeight() + radius};
		
		avoidGunBarrel();
		
		avoidLasers();
		
	}
	
	private void avoidGunBarrel(){
		if(drawingSurface.getGun() != null && Position[1] < drawingSurface.getGun().getPivot()[1]){
			double[] barrelEnd = drawingSurface.getGun().getBarrelPosition();
			double gunAngle = -drawingSurface.getGun().getAngle();
			
			double dtg = distance(Position[0],Position[1],barrelEnd[0],barrelEnd[1]);
			
			if(dtg < laserTooClose){
				double checkDistance = barrelEnd[0] - Position[0];
				double yDist = checkDistance / (float) Math.tan(gunAngle);
				float newX;
				float newY;
				
				if(gunAngle < 0){
					if((barrelEnd[1] - Position[1]) < yDist){
						newX = Position[0] + 20;
						newY = Position[1] + 10;
					}else{
						newX = Position[0] - 20;
						newY = Position[1] + 10;
					}
				}else{
					if((barrelEnd[1] - Position[1]) < yDist){
						newX = Position[0] - 20;
						newY = Position[1] + 10;
					}else{
						newX = Position[0] + 20;
						newY = Position[1] + 10;
					}
				}
				
				// Don't fly too close to gun
				/*if(distance(drawingSurface.getGun().getPivot()[0],drawingSurface.getGun().getPivot()[1],Position[0],Position[1]) < drawingSurface.getGun().getLength()){
					if(Position[0] < drawingSurface.getGun().getPivot()[0]){
						newX = (float) (drawingSurface.getGun().getPivot()[0] - drawingSurface.getGun().getLength());
					}else{
						newX = (float) (drawingSurface.getGun().getPivot()[0] + drawingSurface.getGun().getLength());
					}
				}*/
				
				Destination = new float[]{newX,newY};
			}
		}
	}
	
	protected void avoidCollision(Item item){
		//if(item.getType() != LASERBEAM_TYPE){
			if(item.getPosition()[0] < getPosition()[0]){
				Destination = new float[]{drawingSurface.getWidth(),getPosition()[1]};
			}else{
				Destination = new float[]{0,getPosition()[1]};
			}
		//}
	}
	
	/*protected void avoidCollisions(){
		for(int i = 0;i < drawingSurface.getItems().size();i++){
			Item item = drawingSurface.getItems().get(i);
			if(collisionDetected(item)){
				if(item.getPosition()[0] < getPosition()[0]){
					Destination = new float[]{drawingSurface.getWidth(),getPosition()[1]};
				}else{
					Destination = new float[]{0,getPosition()[1]};
				}
			}
		}
	}*/
	
	private void avoidLasers(){
		ArrayList<LaserBeam> lasers = drawingSurface.getGun().getLasers();
		
		double shortestDistance = drawingSurface.getHeight();
		
		//Log.i("Lasers",String.valueOf(lasers.size()));
		
		if(lasers.size() > 0){
			LaserBeam laser;
			double[] end;
			double laserAngle;
			double dtl = drawingSurface.getHeight();
			
			for(int i = 0;i < lasers.size();i++){
				try{
					laser = lasers.get(i);
					end = laser.getEnd();
					laserAngle = laser.getAngle();
					dtl = distance(Position[0],Position[1],end[0],end[1]);
					
					//Log.i("PosX",String.valueOf(Position[0]));
					//Log.i("PosY",String.valueOf(Position[1]));
					//Log.i("endX",String.valueOf(end[0]));
					//Log.i("endY",String.valueOf(end[1]));
					//Log.i("Distance To Laser",String.valueOf(dtl));
					
					if(dtl < laserTooClose && dtl < shortestDistance && end[1] > Position[1]){
						shortestDistance = dtl;
						/*
						float newX;
						float newY;
						
						if(end[0] > Position[0]){
							newX = Position[0] - 20;
							newY = Position[1];
						}else{
							newX = Position[0] + 20;
							newY = Position[1];
						}
						*/
							
							double checkDistance = end[0] - Position[0];
							double yDist = checkDistance / (float) Math.tan(laserAngle);
							float newX;
							float newY;
							
							if(laserAngle < 0){
								if((end[1] - Position[1]) < yDist){
									newX = Position[0] + 20;
									newY = Position[1] + 10;
								}else{
									newX = Position[0] - 20;
									newY = Position[1] + 10;
								}
							}else{
								if((end[1] - Position[1]) < yDist){
									newX = Position[0] - 20;
									newY = Position[1] + 10;
								}else{
									newX = Position[0] + 20;
									newY = Position[1] + 10;
								}
							}
							
							/*float newX = Position[0] - (20 * ((end[0] - Position[0]) / (float) dtl));
							float newY = Position[1] - (20 * ((end[1] - Position[1]) / (float) dtl));
							newY = Math.max(Position[1] + 10,newY);
							if(end[0] > Position[0]){
								newX = newX - 20;
								if(newX <= 0){
									newX = newX + 40;
								}
							}else if(end[0] <= Position[0]){
								newX = newX + 20;
								if(newX >= surfaceWidth){
									newX = newX - 40;
								}
							}*/
							Destination = new float[]{newX,newY};
							
							/*if(end[0] > Position[0]){
								Destination = new float[]{0,Position[1]};
							}else{
								Destination = new float[]{surfaceWidth,Position[1]};
							}*/
						
					}
				}catch(NullPointerException e){
					e.printStackTrace();
				}
			}
		}
	}
	
}
