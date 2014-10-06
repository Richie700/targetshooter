package com.tanqbay.targetshooter;

import java.util.ArrayList;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Bomber extends Ship {
	
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private int numberOfBombs;
	private double distanceToBombReload;
	private double bombDropRate = 5.0;
	private double timeSinceLastDrop = 0;
	
	public Bomber(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		distanceToBombReload = drawingSurface.getHeight();
		numberOfBombs = 3;
		timeSinceLastDrop = 0;
		
		reloadBombs();
		
		shipImage = BitmapFactory.decodeResource(drawingSurface.getContext().getResources(),R.drawable.bomber1);
		radius = 35;
	}
	
	private void reloadBombs(){
		if(bombs.size() == 0){
			for(int i = 1;i <= numberOfBombs;i++){
				bombs.add(new Bomb(drawingSurface));
			}
		}
	}
	
	public void drawSelf(Canvas canvas){
		super.drawSelf(canvas);
		
		float xPosition = Position[0];
		
		for(int i = 0;i < bombs.size();i++){
			if(i == 0){
				xPosition = Position[0];
			}
			
			if(i == 1){
				xPosition = Position[0] - radius;
			}
			
			if(i == 2){
				xPosition = Position[0] + radius;
			}
			
			bombs.get(i).setPosition(new float[]{xPosition,Position[1] + radius});
			bombs.get(i).drawSelf(canvas);
		}
	}
	
	public void update(double timeDifferenceDrawingSurface drawingSurface){
		super.update(timeDifference,drawingSurface);
		
		if(!drawingSurface.getPaused()){
			timeSinceLastDrop += timeDifference;
		}
		
		//calculateDestination();
		
		if(Position[1] < 0 - distanceToBombReload){
			reloadBombs();
		}
		
		
	}
	
	protected void calculateDestinationRotation(){
		destinationRotation = (float) 0;
	}
	
	protected void calculateDestination(DrawingSurface drawingSurface){
		
		if(bombs.size() > 0){
			super.calculateDestination();
			
			if(Position[1] > drawingSurface.getHeight() * (1.0/3.0)){
				Velocity[1] = -10;
				dropBomb();
			}
		}else{
			Destination = new float[]{Position[0],(float) -distanceToBombReload};
			//destinationRotation = (float) (2 * Math.PI);
		}
		
	}
	
	public void setHit(){
		super.setHit();
		dropBomb();
	}
	
	private void dropBomb(){
		if(timeSinceLastDrop >= bombDropRate && bombs.size() > 0){
			
			Bomb bomb = bombs.get(bombs.size() - 1);
			
			bomb.setPosition(new float[]{bomb.getPosition()[0],Position[1] + radius});
			bomb.setDestination(new float[]{bomb.getPosition()[0],drawingSurface.getHeight() + bomb.getRadius()});
			
			drawingSurface.getItems().add(bomb);
			
			timeSinceLastDrop = 0;
			
			
			
			bombs.remove(bombs.size() - 1);
		}
	}
}