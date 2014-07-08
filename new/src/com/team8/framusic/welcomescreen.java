package com.team8.framusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class welcomescreen extends Activity{
	private final int SPLASH_DISPLAY_LENGHT = 1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_screen);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); 
		 int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	                | View.SYSTEM_UI_FLAG_LOW_PROFILE
	                | View.SYSTEM_UI_FLAG_FULLSCREEN
	                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

	    getWindow().getDecorView().setSystemUiVisibility(mUIFlag);
		
		new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               // Intent mainIntent = new Intent(WelcomeActivity.this,WizardActivity.class);
               // WelcomeActivity.this.startActivity(mainIntent);
               // WelcomeActivity.this.finish();
            	startActivity(new Intent(welcomescreen.this, MainActivity.class));
            	finish();
            }
        },SPLASH_DISPLAY_LENGHT);
	}

	}

