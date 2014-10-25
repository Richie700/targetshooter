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
		
		rotation = (float) Math.PI;
		destinationRotation = (float) Math.PI;
	}
	
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		super.update(timeDifference,drawingSurface);
			
		calculateDestinationRotation();
		calculateDestination((TargetShooterGame) drawingSurface.getGame());
			
		if(Math.abs(rotation - destinationRotation) > Epsilon){
		   if(destinationRotation > rotation){
		      rotation += 0.03;
		   }else{
			      rotation -= 0.03;
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
	
	protected void calculateDestination(TargetShooterGame game){
		
		Destination = new float[]{Position[0],surfaceHeight + radius};
		
		avoidGunBarrel(game);
		
		avoidLasers(game);
		
	}
	
	private void avoidGunBarrel(TargetShooterGame game){
		
		Gun gun = game.getGun();
		
		if(gun != null && Position[1] < gun.getPivot()[1]){
			double[] barrelEnd = gun.getBarrelPosition();
			double gunAngle = -gun.getAngle();
			
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
				
				
				
				Destination = new float[]{newX,newY};
			}
		}
	}
	
	protected void avoidCollision(Item item){
		//if(item.getType() != LASERBEAM_TYPE){
			if(item.getPosition()[0] < getPosition()[0]){
				Destination = new float[]{surfaceWidth,getPosition()[1]};
			}else{
				Destination = new float[]{0,getPosition()[1]};
			}
		//}
	}
	
	
	
	private void avoidLasers(TargetShooterGame game){
		ArrayList<Item> items = game.getFriends();
		
		double shortestDistance = surfaceHeight;
		
		
			LaserBeam laser;
			double[] end;
			double laserAngle;
			double dtl = surfaceHeight;
			
			for(int i = 0;i < items.size();i++){
				try{
				 if(items.get(i).getType() == LASERBEAM_TYPE){
   					laser = (LaserBeam) items.get(i);
   					end = laser.getEnd();
   					laserAngle = laser.getAngle();
   					dtl = distance(Position[0],Position[1],end[0],end[1]);
   					
   					
   					if(dtl < laserTooClose && dtl < shortestDistance && end[1] > Position[1]){
   						shortestDistance = dtl;
   						
   							
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
   							
   							
   							Destination = new float[]{newX,newY};
   							
   						}
   					}
   				}catch(NullPointerException e){
   					e.printStackTrace();
   				}
   			
   		}
		}
}