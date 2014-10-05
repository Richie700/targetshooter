package com.tanqbay.targetshooter;


import android.graphics.Canvas;

public class Item implements Comparable {
	
	public static final int GUN_TYPE = 1;
	public static final int TARGET_TYPE = 2;
	public static final int LASERBEAM_TYPE = 3;
	
	protected DrawingSurface drawingSurface;
	protected boolean readyToBeRemoved = false;
	protected int drawOrder = 0;
	
	public Item(DrawingSurface drawingSurface){
		this.drawingSurface = drawingSurface;
	}
	
	public void drawSelf(Canvas canvas){
		
	}
	
	public void update(double timeDifference){
		
	}
	
	public int getType(){
		return 0;
	}
	
	public boolean isReadyToBeRemoved(){
		return readyToBeRemoved;
	}
	
	public void setReadyToBeRemoved(boolean readyToBeRemoved){
		this.readyToBeRemoved = readyToBeRemoved;
	}
	
	public boolean pointInside(float X,float Y){
		return false;
	}
	
	public static double distance(double startx,double starty,double endx,double endy){
		
		double result = Math.sqrt((Math.pow(Math.abs(startx - endx),2) + Math.pow(Math.abs(starty - endy),2)));
		
		return result;
	}
	
	public float[] getPosition(){
		return new float[]{0,0};
	}
	
	public float getRadius(){
		return 0;
	}
	
	public boolean checkForCollision(){
		return false;
	}
	
	public int getDrawOrder(){
		return drawOrder;
	}
	
	 @Override
  public int compareTo(Item item) {
        return drawOrder.compareTo(item.getDrawOrder());
  }
}