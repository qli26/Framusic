package com.team8.framusic.Activity;
import com.team8.framusic.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DisplayBackgroundMusicActivity extends Activity {
	private Context mContext = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_display_background_music);
		
		mContext = this;
		
		Button changeFolder = (Button) findViewById(R.id.changeFolder);
		changeFolder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, ChoosePicFoldersActivity.class);
				startActivity(intent);
			}
			
		});
		
		Button musicSetting = (Button) findViewById(R.id.musicSetting);
		musicSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, MusicSettingActivity.class);
				startActivity(intent);
			}
			
		});
		
		Button layoutSetting = (Button) findViewById(R.id.layoutSetting);
		layoutSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, LayoutSettingActivity.class);
				startActivity(intent);
			}
			
		});
		
		Button preference = (Button) findViewById(R.id.preference);
		preference.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, PreferenceSetActivity.class);
				startActivity(intent);
			}
			
		});
	
		Button gridView = (Button) findViewById(R.id.gridView);
		gridView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, GridViewActivity.class);
				startActivity(intent);
			}
		});
		
		Button slideShow = (Button) findViewById(R.id.slideShow);
		slideShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SlideShowActivity.class);
				startActivity(intent);
			}
		});
		
	}

}
