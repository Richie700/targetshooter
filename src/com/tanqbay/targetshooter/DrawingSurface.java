package com.tanqbay.targetshooter;

import android.content.Context;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout.LayoutParams;

/*
 * To Do List:
 * Save state when exiting and reload when coming back
 * --Save high score
 * Add graphics
 * 		2 kinds of ships
 * 		bombs
 * 		planet
 * 		gun
 * 		
 * Fix layering
 * Should we have some levels or increasing difficulty
 * Different damage to shield when different things hit
 * --look at unkillable ship bug
 * change shooting and rotating touch areas
 * show directions at the beginning
 * --Ships should try not to overlap
 * Add some loading
 */

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback,ISurface {

	public SurfaceHolder drawingSurfaceHolder;
	private AnimationThread thread; 
	private double timer;
	private Game game;
	private Bitmap background;
	//private AdView NextAd;
	//private boolean ShowPopup = true;
	
	
	public DrawingSurface(Context context) {
		super(context);
		
		constructorFunctions(context);
		
	}
	
	public DrawingSurface(Context context,AttributeSet attributes) {
		super(context,attributes);
		
		constructorFunctions(context);
        
	}
	
	private void constructorFunctions(Context context){
		
		//background = BitmapFactory.decodeResource(context.getResources(),R.drawable.dense_star_field);
		
		SetupHolder();
	}
	
	private void SetupHolder(){
		drawingSurfaceHolder = getHolder();
        drawingSurfaceHolder.addCallback(this);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		createdOrChanged();
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		createdOrChanged();
	}
	
	private void createdOrChanged(){
		setupGame();
	}
	
	private void setupGame(){
		try{
			if(this.thread != null){
				this.thread.setRunning(false);
			}
				
				game = new TargetShooterGame(this);
		
			startAnimationThread();
			
		}catch(Exception e){
			DebugNotifier.notify(e,getContext());		
		}
	}
	
	
	
	private void startAnimationThread(){
		this.thread = new AnimationThread(drawingSurfaceHolder,this,getContext());
		
		this.thread.setRunning(true);
		
		this.thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
   ((MainActivity) getContext()).finish();
	}

	@Override
	public void onInitalize() {
		
		
	}
	
	public void onRedraw(Canvas canvas){
		if(canvas != null){
			Paint clearPaint = new Paint();
			
			clearPaint.setColor(0xff000000);
			
			canvas.drawPaint(clearPaint);
			
			RectF rect = new RectF(0,0,getWidth(),getHeight());
			
			Rect sourceRect = new Rect(0,0,getWidth(),getHeight());
			
			//canvas.drawBitmap(background,sourceRect,rect,new Paint());
			
			game.draw(canvas);
		}
	}
	
	@Override
	public void onUpdate(double gameTime) {
		
		double timeDifference = 0;
		
		if(!game.getFinished()){
			if(!game.getPaused()){
			   timeDifference = (gameTime - timer) / 1000;
			}
			
			timer = gameTime;
			
			game.update(this,timeDifference);
			
		}
		
	}
		
	public boolean onTouchEvent(MotionEvent event){
		
		SimpleMotionEvent simpleEvent = new SimpleMotionEvent(event);
		
		if(game.getFinished()){
			if(simpleEvent.isDown()){
				setupGame();
			}
		}else{
			
			game.touchEvent(simpleEvent,this);
			
		}
		return true;
	}
	
	public Game getGame(){
	   return game;
	}
	
}