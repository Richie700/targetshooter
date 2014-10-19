package com.tanqbay.targetshooter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Score extends Item {
	
	//private double shieldStrength = 100;
	private Paint paint;
	private float lineHeight = 30;
	private double shieldStrength = 0;
	private int hits = 0;
	
	public Score(DrawingSurface drawingSurface){
		super(drawingSurface);
		
		drawOrder = 110;
		
		paint = new Paint();
		
		
		paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
		
		paint.setStrokeWidth(lineHeight);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setAntiAlias(true);
	}
	
	public void drawSelf(Canvas canvas){
		double leftCenter = surfaceWidth / 6.0;
		double center = surfaceWidth / 2.0;
		double rightCenter = surfaceWidth * (5.0/6.0);
		
		paint.setTextSize(20);
		paint.setColor(0xff000099);
		String hitLabel = "Hits";
		double hitLabelWidth = paint.measureText(hitLabel);
		double hitLabelStart = leftCenter - (hitLabelWidth / 2.0);
		canvas.drawText(hitLabel,(float) (hitLabelStart),lineHeight,paint);
		
		paint.setTextSize(25);
		String hitAmount = String.valueOf(hits);
		double hitAmountWidth = paint.measureText(hitAmount);
		double hitAmountStart = leftCenter - (hitAmountWidth / 2.0);
		
		paint.setColor(0xff666666);
		paint.setStrokeWidth((float) (lineHeight * 1.2));
		canvas.drawLine((float) hitAmountStart,(float) (lineHeight * 2.0),(float) (hitAmountWidth + hitAmountStart),(float) (float) (lineHeight * 2.0),paint);
		
		paint.setColor(0xff000099);
		canvas.drawText(hitAmount,(float) (hitAmountStart),(float) (lineHeight * 2.2),paint);
		
		paint.setTextSize(20);
		paint.setColor(0xff000099);
		String shieldStrengthLabel = "Shield Strength";
		double shieldStrengthLabelWidth = paint.measureText(shieldStrengthLabel);
		double shieldStrengthLabelStart = center - (shieldStrengthLabelWidth / 2.0);
		canvas.drawText(shieldStrengthLabel,(float) shieldStrengthLabelStart,lineHeight,paint);
		
		paint.setColor(0xff666666);
		paint.setStrokeWidth((float) (lineHeight * 1.2));
		canvas.drawLine((float) shieldStrengthLabelStart,(float) (lineHeight * 2.0),(float) (shieldStrengthLabelWidth + shieldStrengthLabelStart),(float) (float) (lineHeight * 2.0),paint);
		
		paint.setColor(0xff000099);
		paint.setStrokeWidth(lineHeight);
		canvas.drawLine((float) shieldStrengthLabelStart,(float) (lineHeight * 2.0),(float) ((shieldStrength / 100) * shieldStrengthLabelWidth + shieldStrengthLabelStart),(float) (float) (lineHeight * 2.0),paint);
		
		
	}
	
	public void setHits(int hits){
	   this.hits = hits;
	}
	
	public void setShieldStrength(double shieldStrength){
	   this.shieldStrength = shieldStrength;
	}
	
	
}