package com.tanqbay.targetshooter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/*
 * Ship Ideas:
 * Bomber - stops and moves back and forth avoiding lasers and dropping bombs
 * Kamikaze flies into shield
 * 
 */

public class Ship extends SmartTarget {
	
	protected Bitmap shipImage;
	
	
	public Ship(DrawingSurface drawingSurface) {
		super(drawingSurface);
		
		
		Velocity = new float[]{0,0};
		Acceleration = new float[]{0,0};
		
		shipImage = BitmapFactory.decodeResource(drawingSurface.getContext().getResources(),R.drawable.ship1);
		
	}
	
	public void drawSelf(Canvas canvas){
		if(!hit){
			
			RectF rect = new RectF(-radius,-radius,radius,radius);
			
			Rect sourceRect = new Rect(0,0,shipImage.getWidth(),shipImage.getHeight());
			
			canvas.save(Canvas.MATRIX_SAVE_FLAG);
			
			canvas.translate(Position[0],Position[1]);
			canvas.rotate((float) (-1 * rotation * (180/Math.PI)));
			
			
			canvas.drawBitmap(shipImage,sourceRect,rect,new Paint());
			
			canvas.restore();
			
			/*destinationRotation = (float) Math.asin((Position[0] - Destination[0]) / (Position[1] - Destination[1]));
			
			if(Float.isNaN(destinationRotation)){
				destinationRotation = (float) Math.acos((Position[1] - Destination[1]) / (Position[0] - Destination[0]));
				if(Destination[0] < Position[0]){
					destinationRotation = destinationRotation + (float) Math.PI;
				}
			}
			
			destinationRotation = destinationRotation + (float) (Math.PI);
			*/
			
			
			//canvas.rotate((float) ((angle * (180/Math.PI)) + 180),Position[0],Position[1]);
			
			
			
			//canvas.rotate((float) (-1 * angle * (180/Math.PI)),Position[0],Position[1]);
			//canvas.translate(-Position[0],-Position[1]);
			//canvas.restore();
			
			//canvas.drawCircle(Position[0],Position[1],radius,paint);
			
			//paint.setStyle(Paint.Style.STROKE);
			
			//canvas.drawCircle(Destination[0],Destination[1],radius,paint);
			 
			 
		}
	}
	
	public void update(double timeDifference){
		super.update(timeDifference);
		
	}
	
}
