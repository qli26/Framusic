package com.team8.framusicv2;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MusicSettingActivity extends Activity {
	private Context mContext;
	private boolean mFirstTimeOpen;
	private String mWhoCalledMe;
	private String[] mWhoCanCallMe = new String[] {
			ChoosePicFoldersActivity.class.toString(),
			DisplayBackgroundMusicActivity.class.toString(), };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_music_setting);
		mContext = this;
		getSharedPreferences();
		processExtraData();
		
		Button b = (Button) findViewById(R.id.button6);
		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mFirstTimeOpen) {
					Intent intent = new Intent(mContext,
							DisplayBackgroundMusicActivity.class);
					Bundle b = new Bundle();
					b.putString("WHO_CALLED_ME",
							DisplayBackgroundMusicActivity.class.toString());
					intent.putExtra("CALLING_INFO", b);
					startActivity(intent);
				} else {
					if (mWhoCalledMe
							.equals(DisplayBackgroundMusicActivity.class
									.toString())) {
						Intent intent = new Intent(mContext,
								DisplayBackgroundMusicActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								DisplayBackgroundMusicActivity.class.toString());
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					}
					else if (mWhoCalledMe
							.equals(DisplayBackgroundMusicActivity.class
									.toString())) {
						Intent intent = new Intent(mContext,
								ChoosePicFoldersActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								ChoosePicFoldersActivity.class.toString());
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					}
				}
				finish();
			}
			
		});
	}
	
	private void processExtraData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		// use the data received here
		getSharedPreferences();
		Bundle b = intent.getBundleExtra("CALLING_INFO");
		mWhoCalledMe = b.getString("WHO_CALLED_ME");
	}
	
	protected void getSharedPreferences() {
		SharedPreferences sp = mContext.getSharedPreferences("Setting",
				MODE_PRIVATE);

		mFirstTimeOpen = sp.getBoolean("FIRST_TIME_OPEN", true);
	}

	protected void setFromPreferencesValue() {

		if (this.mFirstTimeOpen == true) {
			this.mFirstTimeOpen = false;
		}
		System.out.println();
	}

	protected void saveSharedPreferences() {
		SharedPreferences sp = mContext.getSharedPreferences("Setting",
				MODE_PRIVATE);

		Editor editor = sp.edit();
		editor.putBoolean("FIRST_TIME_OPEN", mFirstTimeOpen);
		editor.commit();
	}
}
