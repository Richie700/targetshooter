package com.tanqbay.targetshooter;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Paint;
import android.util.FloatMath;
import android.util.Log;

public class Target extends Item {
	
	protected float maxSpeed = 160;
	protected float maxAcceleration = 500;
	
	protected float[] Velocity;
	protected float[] Acceleration;
	protected float[] Destination;
	protected float[] Position;
	protected float radius;
	//private float actualRadius = 50;
	protected boolean hit = false;
	protected boolean reachedBottom = false;
	//protected float speed;
	protected float startY = -50;
	
	
	public Target(DrawingSurface drawingSurface){
		super(drawingSurface);
		
		Random rand = new Random();
		
		float xpos = rand.nextFloat() * drawingSurface.getWidth();
		//float ypos = rand.nextFloat() * (surfaceHeight - 300);
		
		float ypos = startY;
		
		Position = new float[]{xpos,ypos};
		radius = (rand.nextFloat() * 30) + 20;
		
		Velocity = new float[]{0,0};
		Acceleration = new float[]{0,0};
		
		
		
		Destination = new float[]{Position[0],drawingSurface.getHeight() + radius};
		
		
	}
	
	
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		//Position[1] = Position[1] + (speed * (float) timeDifference);
		
		//radius = actualRadius * ((Position[1] - startY)/surfaceHeight);
		
		//Log.i("Speed",String.valueOf(speed));
		
		if(!drawingSurface.getPaused()){
			
			DetectCollisions(drawingSurface);
			
			float distanceToDestination = (float) distance(Position[0],Position[1],Destination[0],Destination[1]);
			
			Acceleration[0] = maxAcceleration * ((Destination[0] - Position[0]) /distanceToDestination);
			Acceleration[1] = maxAcceleration * ((Destination[1] - Position[1])/distanceToDestination);
			
			Velocity[0] = Velocity[0] + Acceleration[0] * (float) timeDifference;
			Velocity[1] = Velocity[1] + Acceleration[1] * (float) timeDifference;
			
			if(Velocity[1] < 0){
				//Velocity[1] = (float) 5;
			}
			
			if(Position[0] <= 0 + radius){
				Velocity[0] = 5;
				Destination = new float[]{Position[0],drawingSurface.getHeight()};
			}
			
			if(Position[0] >= drawingSurface.getWidth() - radius){
				Velocity[0] = -5;
				Destination = new float[]{Position[0],drawingSurface.getHeight()};
			}
			
			if(getSpeed() > maxSpeed){
				Velocity[0] = (float) 0.9 * Velocity[0];
				Velocity[1] = (float) 0.9 * Velocity[1];
			}
			
			Position[0] = Position[0] + (Velocity[0] * (float) timeDifference);
			Position[1] = Position[1] + (Velocity[1] * (float) timeDifference);
			
			
			if(Position[1] > drawingSurface.getCity().getShieldPosition()){
				setReachedBottom(drawingSurface);
				setReadyToBeRemoved(true);
			}
		}
	}
	
	protected void DetectCollisions( DrawingSurface drawingSurface ){
		for(int i = 0;i < drawingSurface.getItems().size();i++){
			Item item = drawingSurface.getItems().get(i);
			if(collisionDetected(item)){
				if(item.getType() == LASERBEAM_TYPE){
					setReadyToBeRemoved(true);
					item.setReadyToBeRemoved(true);
					drawingSurface.addHit();
				}else{
					avoidCollision(item);
				}
			}
		}
	}
	
	protected boolean collisionDetected(Item item){
		//Log.i("Equals",String.valueOf(item != this));
		boolean detected =  item != this
		&& item.checkForCollision()
		&& distance(item.getPosition()[0],item.getPosition()[1],getPosition()[0],getPosition()[1]) < getRadius() + item.getRadius();
		
		if(detected && getType() == TARGET_TYPE){
			Log.i("detected",String.valueOf(detected));
		}
		
		return detected;
	}
	
	protected void avoidCollision(Item item){
		
	}
	
	private float getSpeed(){
		float speed = Math.abs(FloatMath.sqrt((float) (Math.pow(Velocity[0],2) + Math.pow(Velocity[1],2))));
		
		return speed;
	}
	
	public float[] getPosition(){
		return Position;
	}
	
	public float getRadius(){
		return radius;
	}
	
	public boolean checkForCollision(){
		return true;
	}
	
	public boolean pointInside(double[] point){
		return distance(point[0],point[1],Position[0],Position[1]) < radius;
	}
	
	public void setHit(){
		hit = true;
	}
	
	public boolean getHit(){
		return hit;
	}
	
	public void setReachedBottom(DrawingSurface drawingSurface){
		reachedBottom = true;
		drawingSurface.getCity().reduceShield(10);
	}
	
	public boolean getReachedBottom(){
		return reachedBottom;
	}
	
	public int getType(){
		return TARGET_TYPE;
	}
}