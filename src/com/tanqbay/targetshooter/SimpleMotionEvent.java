package com.tanqbay.targetshooter;

import android.view.MotionEvent;

public class SimpleMotionEvent{
	
	private float xCoord;
	private float yCoord;
	
	private boolean isDownEvent = false;
	private boolean isMoveEvent = false;
	private boolean isUpEvent = false;
	
	public SimpleMotionEvent(MotionEvent event){
		int index = event.getActionIndex();
				
			xCoord = event.getX(index);
			yCoord = event.getY(index);
			
			if(event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
				isDownEvent = true;
			}
			
			if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
				isMoveEvent = true;
			}
				
			if(event.getActionMasked() == MotionEvent.ACTION_UP){
				isUpEvent = true;
			}
			
	}
	
	public float getYCoord(){
		return yCoord;
	}
	
	public float getXCoord(){
		return xCoord;
	}
	
	public boolean isDown(){
		return isDownEvent;
	}
	
	public boolean isMove(){
		return isMoveEvent;
	}
	
	public boolean isUp(){
		return isUpEvent;
	}
	
}