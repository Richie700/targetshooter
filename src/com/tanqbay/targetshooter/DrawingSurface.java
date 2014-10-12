package com.tanqbay.targetshooter;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
	
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private Gun gun;
	private City city;
	private DisplayContainer displayContainer;
	
	private boolean Finished = false;
	private Wave wave;
	private Bitmap background;
	private int hits;
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
	
	private void constructorFunctions(){
		
		//background = BitmapFactory.decodeResource(context.getResources(),R.drawable.dense_star_field);
		
		SetupHolder();
	}
	
	private void SetupHolder(){
		drawingSurfaceHolder = getHolder();
        drawingSurfaceHolder.addCallback(this);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
		Log.i("Changed","Changed");
		
		createdOrChanged();
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		Log.i("Created","Created");
		
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
				
			hits = 0;
		
			setupWave();
		
			createItems();
		
			startAnimationThread();
			Finished = false;
		}catch(Exception e){
			DebugNotifier.notify(e,getContext());		
		}
	}
	
	private void setupWave(){
		wave = new Wave();
	}
	
	private void createItems(){
		items = new ArrayList<Item>();
		
		displayContainer = new DisplayContainer(this);
		items.add(displayContainer);
		
		gun = new Gun(this);
		items.add(gun);
		
		city = new City(this);
		items.add(city);
		
		
	}
	
	private void startAnimationThread(){
		this.thread = new AnimationThread(drawingSurfaceHolder,this,getContext());
		
		this.thread.setRunning(true);
		
		this.thread.start();
	}
	
	private void addTarget(){
		
		
		if(wave.isComplete()){
			WaveComplete waveComplete = new WaveComplete(this,wave.getWaveNumber());
			items.add(waveComplete);
			
			wave = wave.getNextWave();
			
			
		}
		
		
		if(wave.addShip()){
			Ship ship = new Ship(this);
			
			items.add(ship);
		}else{
			Bomber bomber = new Bomber(this);
			
			items.add(bomber);
		}
		
		wave.adjustTargetNumber();
		
		Collections.sort(items);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		Log.i("Surface","Destroyed");

		displayContainer.getPauseButton().PauseGame();
		
		//thread = null;
		
		//thread.interrupt();
		
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
			
			// Draw Items
			for(int i = 0;i < items.size();i++){
				items.get(i).drawSelf(canvas);
			}
		}
	}
	
	@Override
	public void onUpdate(double gameTime) {
		
		if(!Finished){
			double timeDifference = (gameTime - timer) / 1000;
			
			
			Target target;
			
			for(int i = items.size() - 1;i >= 0;i--){
				try{
					items.get(i).update(timeDifference,this);
					//Log.i("Type",String.valueOf(items.get(i).getType()));
					
					if(items.get(i).isReadyToBeRemoved()){
						items.remove(i);
					}
					
				}catch(IndexOutOfBoundsException e){
					
				}catch(NullPointerException e){
					
				}
			}
		
			timer = gameTime;
			
			if(wave.shouldAddTarget(numberOfTargets())){
				addTarget();
			}
			
			if(city.getShieldStrength() <= 0){
				gameOver();
			}
		}
		
	}
	
	private void gameOver(){
		getPauseButton().PauseGame();
		Finished = true;
		boolean newHighScoreReached = false;
		
		SharedPreferences settings = ((Activity) getContext()).getPreferences(Context.MODE_PRIVATE);
		int currentHighScore = settings.getInt("HighScore",0);
		
		if(currentHighScore < hits){
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("HighScore",hits);
			editor.commit();
			
			currentHighScore = hits;
			newHighScoreReached = true;
		}
		
		LoseDisplay loseDisplay = new LoseDisplay(this,newHighScoreReached,currentHighScore,hits);
		items.add(loseDisplay);
		
	}
	
	private int numberOfTargets(){
		
		int Total = 0;
		
		for(int i = 0;i < items.size();i++){
			try{
				if(items.get(i).getType() == Item.TARGET_TYPE){
					Total++;
				}
			}catch(IndexOutOfBoundsException e){
				
			}
		}
		
		return Total;
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(Finished){
			if(event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
				setupGame();
			}
		}else{
			int Index = event.getActionIndex();
				
			float XCoord = event.getX(Index);
			float YCoord = event.getY(Index);
			
			gun.handleTouchEvent(event,this);
			
			displayContainer.getPauseButton().handleTouchEvent(event);
			
			if(event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
				/*if(XCoord > surfaceWidth - (surfaceWidth / 5) && YCoord < surfaceHeight / 10){
					pauseButton.togglePause();
				}*/
			}
			
			
			if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
				
			}
				
			if(event.getActionMasked() == MotionEvent.ACTION_UP){
				
			}
		}
		return true;
	}
	
	public boolean getPaused(){
		return displayContainer.getPauseButton().getPaused();
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public Gun getGun(){
		return gun;
	}
	
	public int getHits(){
		return hits;
	}
	
	public void addHit(){
		hits++;
	}
	
	public PauseButton getPauseButton(){
		return displayContainer.getPauseButton();
	}
	
	public City getCity(){
		return city; 
	}
}