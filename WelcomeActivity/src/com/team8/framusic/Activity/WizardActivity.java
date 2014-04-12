package com.team8.framusic.Activity;

import com.team8.framusic.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class WizardActivity extends Activity {

	private Context mContext = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_wizard);
		mContext = this;
		
		Button next = (Button) findViewById(R.id.button1);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, ChoosePicFoldersActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
