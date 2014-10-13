package com.tanqbay.targetshooter;

import android.view.MotionEvent;

public class SimpleMotionEvent{
	
	public static final int DOWN = 1;
	public static final int MOVE = 2;
	public static final int UP = 3;
	
	
	private float xCoord;
	private float yCoord;
	
	private int type;
	
	public SimpleMotionEvent(MotionEvent event){
		int index = event.getActionIndex();
				
			xCoord = event.getX(index);
			yCoord = event.getY(index);
			
			if(event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
				type = DOWN;
			}
			
			
			if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
				type = MOVE;
			}
				
			if(event.getActionMasked() == MotionEvent.ACTION_UP){
				type = UP;
			}
			
	}
	
	public int getType(){
		return type;
	}
	
	public float getYCoord(){
		return yCoord;
	}
	
	public float getXCoord(){
		return xCoord;
	}
	
	public boolean isDown(){
		type == DOWN;
	}
	
	public boolean isMove(){
		type == MOVE;
	}
	
	public boolean isUp(){
		type == UP;
	}
	
}