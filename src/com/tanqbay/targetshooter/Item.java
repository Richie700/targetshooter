package com.tanqbay.targetshooter;


import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;

public class Item implements Comparable<Item>,Parcelable {
		
   protected boolean readyToBeRemoved = false;
   protected int drawOrder = 0;
   protected float surfaceWidth;
   protected float surfaceHeight;
         	
	public Item(DrawingSurface drawingSurface){
		surfaceWidth = (float) drawingSurface.getWidth();
		surfaceHeight = (float) drawingSurface.getHeight();
	}
	
	public void drawSelf(Canvas canvas){
		
	}
	
	public void update(double timeDifference,DrawingSurface drawingSurface){
		
	}
	
	public void handleTouchEvent(SimpleMotionEvent event,DrawingSurface drawingSurface){
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
        return new Integer(drawOrder).compareTo(new Integer(item.getDrawOrder()));
  }
  
  public int describeContents() {
         return 0;
     }

     public void writeToParcel(Parcel out, int flags) {
         //out.writeInt(mData);
     }

     public static final Parcelable.Creator<Item> CREATOR
             = new Parcelable.Creator<Item>() {
         public Item createFromParcel(Parcel in) {
             return new Item(in);
         }

         public Item[] newArray(int size) {
             return new Item[size];
         }
     };
     
     private Item(Parcel in) {
         //mData = in.readInt();
     }
     
     protected SimpleMotionEvent getEvent(MotionEvent event){
     		return new SimpleMotionEvent(event);
     }
}