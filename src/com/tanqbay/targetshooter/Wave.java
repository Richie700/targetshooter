package com.tanqbay.targetshooter;

import java.util.Random;

public class Wave{
	
	private int lowestNumberOfTargets = 2;//2
	private int highestNumberOfTargets = 4;//4
	private int numberOfTargets = lowestNumberOfTargets;
	private int totalTargets = 5;
	private int targetsAdded = 0;
	private int waveNumber = 1;
	private Random rand;
	
	public Wave(){
		rand = new Random();
	}
	
	public Wave(int waveNumber,int lowestNumberOfTargets,int highestNumberOfTargets,int totalTargets){
		
		rand = new Random();
		
		this.waveNumber = waveNumber;
		this.lowestNumberOfTargets = lowestNumberOfTargets;
		this.highestNumberOfTargets = highestNumberOfTargets;
	 this.totalTargets = totalTargets;
	 
	 numberOfTargets = lowestNumberOfTargets;
	 
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
		return new Wave(waveNumber++,lowestNumberOfTargets++,highestNumberOfTargets++,totalTargets + 1);
	}
	
	
}