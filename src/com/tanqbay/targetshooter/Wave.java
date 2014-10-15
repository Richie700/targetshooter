package com.tanqbay.targetshooter;

import java.util.Random;

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
	
	public Target getNextTarget(){
	   
	   float shipTypeSelector = rand.nextFloat();
	   
	   Target nextTarget
	   
	   if(shipTypeSelector > 0.80){
	      nextTarget = (Target) new Bomber(this);
	   }else if(shipTypeSelector > 0.60){
	      nextTarget = (Target) new Ship(this);
	   }else{
	      nextTarget = (Target) new GunShip(this);
	   }
	   
	   return nextTarget;
	}
	
	public boolean addShip(){
		
		
		
		return rand.nextFloat() < 0.8;
	}
	
	public void adjustTargetNumber(){
		if(numberOfTargets < highestNumberOfTargets && rand.nextFloat() < 0.1){
			numberOfTargets++;
		}
		if(numberOfTargets > lowestNumberOfTargets && rand.nextFloat() > 0.95){
			numberOfTargets--;
		}
		
		targetsAdded++;
		
	}
	
	public boolean shouldAddTarget(int currentNumber){
			return currentNumber < numberOfTargets;
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
	
	
}