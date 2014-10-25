package com.tanqbay.targetshooter;

import java.util.ArrayList;
import java.util.Collections;

import android.graphics.Canvas;

public class Game {
   
   private boolean Finished = false;
   private boolean paused = false;
   protected float surfaceWidth;
   protected float surfaceHeight;
   
   public Game(DrawingSurface drawingSurface) {
      surfaceWidth = (float) drawingSurface.getWidth();
      surfaceHeight = (float) drawingSurface.getHeight();
   }
   
   public void draw(Canvas canvas){
     
     ArrayList<Item> items = getItems();
     
     Collections.sort(items);
     
     for(int i = 0;i < items.size();i++){
   			   items.get(i).drawSelf(canvas);
   			}
   }
   
   public void update(DrawingSurface drawingSurface){
      ArrayList<Item> items = getItems();
      
      for(int i = items.size() - 1;i >= 0;i--){
      				try{
      					items.get(i).update(timeDifference,drawingSurface);
      					
      					if(items.get(i).isReadyToBeRemoved()){
      						items.remove(i);
      					}
      					
      				}catch(IndexOutOfBoundsException e){
      					
      				}catch(NullPointerException e){
      					
      				}
			    }

   }
	
   public void touchEvent(SimpleMotionEvent event,DrawingSurface drawingSurface){
      ArrayList<Item> items = getItems();
      
      for(int i = 0;i < items.size();i++){
      				items.get(i).handleTouchEvent(event,drawingSurface);
      }
   }
	
 public boolean getFinished(){
    return Finished;
 }
 
 public void togglePause(){
		if(paused){
			pause();
		}else{
			unPause();
		}
	}
 
 protected ArrayList<Item> getItems(){
    return new ArrayList<Item>();
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