package com.team8.framusicv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChoosePicFoldersActivity extends Activity {

	private Context mContext = null;
	private String mWhoCalledMe;
	private boolean mFirstTimeOpen;
	private String[] mWhoCanCallMe = new String[] {
			LayoutSettingActivity.class.toString(),
			DisplayBackgroundMusicActivity.class.toString(), };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_choose_pic_folders);

		mContext = this;
		getSharedPreferences();
		processExtraData();

		Button next = (Button) findViewById(R.id.button2);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mFirstTimeOpen) {
					Intent intent = new Intent(mContext,
							MusicSettingActivity.class);
					Bundle b = new Bundle();
					b.putString("WHO_CALLED_ME",
							MusicSettingActivity.class.toString());
					intent.putExtra("CALLING_INFO", b);
					startActivity(intent);
				}
				else{
					if(mWhoCalledMe.equals(mWhoCanCallMe[0])){
						Intent intent = new Intent(mContext,
								LayoutSettingActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								LayoutSettingActivity.class.toString());
						//b.putStringArrayList("photoschosen", selectedPhoto);
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					}
					else if(mWhoCalledMe.equals(mWhoCanCallMe[0])){
						Intent intent = new Intent(mContext,
								DisplayBackgroundMusicActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								DisplayBackgroundMusicActivity.class.toString());
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
