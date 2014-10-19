package com.tanqbay.targetshooter;

import java.util.Random;
import java.util.ArrayList;

public class Wave{
	
	private int lowestNumberOfTargets;//2
	private int highestNumberOfTargets;//4
	private int numberOfTargets;
	private int totalTargets;
	private int targetsAdded = 0;
	private int waveNumber;
	private Random rand;
	
	public Wave(){
		rand = new Random();
		
		this.waveNumber = 1;
		this.lowestNumberOfTargets = 2;
		this.highestNumberOfTargets = 4;
	 this.totalTargets = 5;
  
  numberOfTargets = lowestNumberOfTargets;
	 
  
	}
	
	public Wave(int waveNumber,int lowestNumberOfTargets,int highestNumberOfTargets,int totalTargets){
		
		rand = new Random();
		
		this.waveNumber = waveNumber;
		this.lowestNumberOfTargets = lowestNumberOfTargets;
		this.highestNumberOfTargets = highestNumberOfTargets;
	 this.totalTargets = totalTargets;
	 
	 numberOfTargets = lowestNumberOfTargets;
	 
	}
	
	public void addTargetIfNeeded(DrawingSurface drawingSurface,ArrayList<Item> items){
	   if(shouldAddTarget(items)){
   				addTarget(drawingSurface,items);
   	}
	}
	
	private void addTarget(DrawingSurface drawingSurface,ArrayList<Item> items){
		
		items.add((Item) getNextTarget(drawingSurface));
		
		adjustTargetNumber();
		
	}
	
	private Target getNextTarget(DrawingSurface drawingSurface){
	   
	   float shipTypeSelector = rand.nextFloat();
	   
	   Target nextTarget;
	   
	   if(shipTypeSelector > 0.80){
	      nextTarget = new Bomber(drawingSurface);
	   }else if(shipTypeSelector > 0.60){
	      nextTarget = new Ship(drawingSurface);
	   }else{
	      nextTarget = new GunShip(drawingSurface);
	   }
	   
	   return nextTarget;
	}
	
	
	private void adjustTargetNumber(){
		if(numberOfTargets < highestNumberOfTargets && rand.nextFloat() < 0.1){
			numberOfTargets++;
		}
		if(numberOfTargets > lowestNumberOfTargets && rand.nextFloat() > 0.95){
			numberOfTargets--;
		}
		
		targetsAdded++;
		
	}
	
	private boolean shouldAddTarget(ArrayList<Item> items){
			return currentNumberOfTargets(items) < numberOfTargets;
	}
	
	public boolean isComplete(){
		return targetsAdded > totalTargets;
	}
	
	public int getWaveNumber(){
		return waveNumber;
	}
	
	public Wave getNextWave(){
		return new Wave(++waveNumber,++lowestNumberOfTargets,++highestNumberOfTargets,totalTargets + 1);
	}
	
	private int currentNumberOfTargets(ArrayList<Item> items){
		
		int Total = 0;
		
		for(int i = 0;i < items.size();i++){
			try{
				if(items.get(i).getType() == Item.TARGET_TYPE){
					Total++;
				}
			}catch(IndexOutOfBoundsException e){
				
			}
		}
		
		return Total;
	}
	
}