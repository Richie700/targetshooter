package com.tanqbay.targetshooter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if(savedInstanceState != null){
        		restoreFromBundle(savedInstanceState);
        }
    }
					
					@Override
					public void onSaveInstanceState(Bundle savedInstanceState) {
					    // Save the user's current game state
					    //savedInstanceState
					    
					    // Always call the superclass so it can save the view hierarchy state
					    super.onSaveInstanceState(savedInstanceState);
					}
					
					
					private void restoreFromBundle(Bundle savedInstanceState){
							DrawingSurface drawingSurface = (DrawingSurface) findViewById(R.id.MainDrawingSurface);
					}
					
					
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onStop(){
    	super.onPause();
    	LinearLayout MC = (LinearLayout) findViewById(R.id.MainContainer);
    	
    	MC.removeViewAt(0);
    }
    
}