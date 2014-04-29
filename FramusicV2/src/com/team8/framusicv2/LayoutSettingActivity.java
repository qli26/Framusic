package com.team8.framusicv2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LayoutSettingActivity extends Activity {
	Context mContext = null;
	private String mWhoCalledMe;
	private boolean mFirstTimeOpen;
	private String[] mWhoCanCallMe = new String[] {
			WizardActivity.class.toString(),
			DisplayBackgroundMusicActivity.class.toString(),
			ChoosePicFoldersActivity.class.toString() };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_layout_setting);

		mContext = this;
		getSharedPreferences();
		processExtraData();

		Button next = (Button) findViewById(R.id.button4);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mFirstTimeOpen) {
					Intent intent = new Intent(mContext,
							ChoosePicFoldersActivity.class);
					Bundle b = new Bundle();
					b.putString("WHO_CALLED_ME",
							ChoosePicFoldersActivity.class.toString());
					intent.putExtra("CALLING_INFO", b);
					startActivity(intent);
				}
				else{
					if(mWhoCalledMe.equals(mWhoCanCallMe[0])){
						Intent intent = new Intent(mContext,
								WizardActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								WizardActivity.class.toString());
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					}
					if(mWhoCalledMe.equals(mWhoCanCallMe[1])){
						Intent intent = new Intent(mContext,
								DisplayBackgroundMusicActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								DisplayBackgroundMusicActivity.class.toString());
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					}
					if(mWhoCalledMe.equals(mWhoCanCallMe[2])){
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
		ArrayList<String> selectedItems = b.getStringArrayList("photos");
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
