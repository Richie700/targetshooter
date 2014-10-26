package com.tanqbay.targetshooter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class TargetShooterGame extends Game {
   
   private Gun gun;
   private City city;
   private DisplayContainer displayContainer;
   private Score score;
   private PauseButton pauseButton;
   private Wave wave;
   private int hits;
   
   private ArrayList<Item> friends = new ArrayList<Item>();
   private ArrayList<Item> enemies = new ArrayList<Item>();
   private ArrayList<Item> uiitems = new ArrayList<Item>();
   
   
   public TargetShooterGame(DrawingSurface drawingSurface) {
      super(drawingSurface);
      
      hits = 0;
   		
   			wave = new Wave();
   			
   			createItems(drawingSurface);
      
   }
   
   
   private void createItems(DrawingSurface drawingSurface){
		
		displayContainer = new DisplayContainer(drawingSurface);
		uiitems.add(displayContainer);
		
		gun = new Gun(drawingSurface);
		friends.add(gun);
		
		city = new City(drawingSurface);
		friends.add(city);
		
		score = new Score(drawingSurface);
		uiitems.add(score);
		
		pauseButton = new PauseButton(drawingSurface);
		uiitems.add(pauseButton);
		
	}
	
	public void update(DrawingSurface drawingSurface, double timeDifference){
    super.update(drawingSurface,timeDifference);
    
    removeItems(enemies);
    removeItems(friends);
    removeItems(uiitems);
    
    checkForHits(enemies,friends);
    checkForHits(friends,enemies);
    
    wave.addTargetIfNeeded(drawingSurface,enemies);
   	
   	if(wave.isComplete()){
   			WaveComplete waveComplete = new WaveComplete(drawingSurface,wave.getWaveNumber());
   			uiitems.add(waveComplete);
   			
   			wave = wave.getNextWave();
   		}
   	
   			if(city.getShieldStrength() <= 0){
   				gameOver(drawingSurface);
   			}
   			
   			score.setHits(hits);
   			score.setShieldStrength(city.getShieldStrength());
   			
 }
 
 private void removeItems(ArrayList<Item> items){
    for(int i = items.size() - 1;i >= 0;i--){
       if(items.get(i).isReadyToBeRemoved()){
          items.remove(i);
       }
    }
 }
 
 private void checkForHits(targets,weapons){
    for(int i = targets.size() - 1;i >= 0;i--){
       Item target = targets.get(i);
       for(int j = weapons.size() - 1;j >= 0;j--){
          if(weapons.get(j).getType() == GameItem.WEAPONFIRE_TYPE){
             Item weapon = weapons.get(i);
             if(target.collisionDetected(weapon)){
                target.setReadyToBeRemoved(true);
                weapon.setReadyToBeRemoved(true);
                addHit();
             }
          }
       }
    }
 }
 
 protected ArrayList<Item> getItems(){
    ArrayList<Item> items = new ArrayList<Item>(enemies);
    items.addAll(friends);
    items.addAll(uiitems);
    
    return items;
 }
 
 private void gameOver(DrawingSurface drawingSurface){
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
		uiitems.add(loseDisplay);
		
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
 
 public ArrayList<Item> getFriends(){
    return friends;
 }
 
 public ArrayList<Item> getEnemies(){
    return enemies;
 }
 
 public void addEnemy(Item item){
    enemies.add(item);
 }
 
 public void addFriend(Item item){
    friends.add(item);
 }
 
 public void addUIItem(Item item){
    uiitems.add(item);
 }
 
}