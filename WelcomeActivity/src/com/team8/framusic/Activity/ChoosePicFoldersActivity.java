package com.team8.framusic.Activity;

import com.team8.framusic.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChoosePicFoldersActivity extends Activity {
	
	private Context mContext = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_choose_pic_folders);
		
		mContext = this;
		
		Button next = (Button) findViewById(R.id.button2);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, DisplayBackgroundMusicActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
