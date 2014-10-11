package com.tanqbay.targetshooter;

import java.util.Random;

public class Wave{
	
	private int lowestNumberOfTargets = 2;//2
	private int highestNumberOfTargets = 4;//4
	private int NumberOfTargets = lowestNumberOfTargets;
	private Random rand;
	
	public Wave(){
		rand = new Random();
	}
	
	public Wave(int lowestNumberOfTargets,int highestNumberOfTargets){
		
		NumberOfTargets = lowestNumberOfTargets;
		
		this.lowestNumberOfTargets = lowestNumberOfTargets;
		this.highestNumberOfTargets = highestNumberOfTargets;
	
	}
	
	public boolean addShip(){
		return rand.nextFloat() < 0.8;
	}
	
	public void adjustTargetNumber(){
		if(NumberOfTargets < highestNumberOfTargets && rand.nextFloat() < 0.1){
			NumberOfTargets++;
		}
		if(NumberOfTargets > lowestNumberOfTargets && rand.nextFloat() > 0.95){
			NumberOfTargets--;
		}
		
	}
	
	public boolean addTarget(int currentNumber)
		return currentNumber < NumberOfTargets;
}