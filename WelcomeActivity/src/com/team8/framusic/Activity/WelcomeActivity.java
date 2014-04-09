package com.team8.framusic.Activity;

import java.util.Timer;
import java.util.TimerTask;

import com.team8.framusic.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class WelcomeActivity extends Activity {
	private Context mContext = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_welcome);
		
		mContext = this;

		final Timer timer = new Timer();
		final TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				Intent intent = new Intent(mContext, WizardActivity.class);
				startActivity(intent);
				finish();
			} 
		};
		timer.schedule(timerTask, 2500);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
