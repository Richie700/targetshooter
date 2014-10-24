package com.tanqbay.targetshooter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Game {
   
   private Gun gun;
   private City city;
   private DisplayContainer displayContainer;
   private Score score;
   private PauseButton pauseButton;
   private Wave wave;
   private int hits;
   private boolean Finished = false;
   private boolean paused = false;
   
   
   public Game(DrawingSurface drawingSurface) {
      
     hits = 0;
   		
   			wave = new Wave();
   			
   			createItems(drawingSurface);
		
      
   }
   
	
	private void createItems(DrawingSurface drawingSurface){
		
		ArrayList<Item> items = drawingSurface.getItems();
		
		displayContainer = new DisplayContainer(drawingSurface);
		items.add(displayContainer);
		
		gun = new Gun(drawingSurface);
		items.add(gun);
		
		city = new City(drawingSurface);
		items.add(city);
		
		score = new Score(drawingSurface);
		items.add(score);
		
		pauseButton = new PauseButton(drawingSurface);
		items.add(pauseButton);
		
		
	}
 
 public void update(DrawingSurface drawingSurface){
    
    ArrayList<Item> items = drawingSurface.getItems();
    
    wave.addTargetIfNeeded(drawingSurface,items);
   	
   	if(wave.isComplete()){
   			WaveComplete waveComplete = new WaveComplete(drawingSurface,wave.getWaveNumber());
   			items.add(waveComplete);
   			
   			wave = wave.getNextWave();
   		}
   	
   			if(city.getShieldStrength() <= 0){
   				gameOver(drawingSurface,items);
   			}
   			
   			score.setHits(hits);
   			score.setShieldStrength(city.getShieldStrength());
   			
 }
 
 
 
 private void gameOver(DrawingSurface drawingSurface,ArrayList<Item> items){
		//getPauseButton().PauseGame();
		Finished = true;
		boolean newHighScoreReached = false;
		
		SharedPreferences settings = ((Activity) drawingSurface.getContext()).getPreferences(Context.MODE_PRIVATE);
		int currentHighScore = settings.getInt("HighScore",0);
		
		if(currentHighScore < hits){
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("HighScore",hits);
			editor.commit();
			
			currentHighScore = hits;
			newHighScoreReached = true;
		}
		
		LoseDisplay loseDisplay = new LoseDisplay(drawingSurface,newHighScoreReached,currentHighScore,hits);
		items.add(loseDisplay);
		
	}
	
 public boolean getFinished(){
    return Finished;
 }
 
 public Gun getGun(){
		return gun;
	}
	
	public City getCity(){
		return city;
	}
	
	public void addHit(){
		hits++;
	}
 
 public void togglePause(){
		if(paused){
			pause();
		}else{
			unPause();
		}
	}
 
 public boolean getPaused(){
    return paused;
 }
 
 public void unPause(){
    paused = false;
 }
 
 public void pause(){
    paused = true;
 }
 
}