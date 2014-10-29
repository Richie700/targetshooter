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
		
		reloadBombs(drawingSurface);
		
		shipImage = BitmapFactory.decodeResource(drawingSurface.getContext().getResources(),R.drawable.bomber1);
		radius = 35;
	}
	
	private void reloadBombs(DrawingSurface drawingSurface){
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
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		super.update(timeDifference,drawingSurface);
		
		timeSinceLastDrop += timeDifference;
		
		if(Position[1] < 0 - distanceToBombReload){
			reloadBombs(drawingSurface);
		}
		
		
	}
	
	protected void calculateDestinationRotation(){
		destinationRotation = (float) 0;
	}
	
	protected void calculateDestination(TargetShooterGame game){
		
		if(bombs.size() > 0){
			super.calculateDestination(game);
			
			if(Position[1] > surfaceHeight * (1.0/3.0)){
				Velocity[1] = -10;
				dropBomb(game);
			}
		}else{
			Destination = new float[]{Position[0],(float) -distanceToBombReload};
			//destinationRotation = (float) (2 * Math.PI);
		}
		
	}
	
	
	private void dropBomb(TargetShooterGame game){
		if(timeSinceLastDrop >= bombDropRate && bombs.size() > 0){
			
			Bomb bomb = bombs.get(bombs.size() - 1);
			
			bomb.setPosition(new float[]{bomb.getPosition()[0],Position[1] + radius});
			bomb.setDestination(new float[]{bomb.getPosition()[0],surfaceHeight + bomb.getRadius()});
			
			game.addEnemy(bomb);
			
			timeSinceLastDrop = 0;
			
			
			
			bombs.remove(bombs.size() - 1);
		}
	}
}