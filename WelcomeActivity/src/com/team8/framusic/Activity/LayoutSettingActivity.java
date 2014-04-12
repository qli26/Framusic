package com.team8.framusic.Activity;
import com.team8.framusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LayoutSettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_layout_setting);
		findViews();
		setListeners();
	}

	ImageButton layout1,
				layout2,
				layout3,
				layout4,
				layout5,
				layout6;
	
	
	private void findViews() {
		layout1 = (ImageButton) findViewById(R.id.layout1);
		layout2 = (ImageButton) findViewById(R.id.layout2);
		layout3 = (ImageButton) findViewById(R.id.layout3);
		layout4 = (ImageButton) findViewById(R.id.layout4);
		layout5 = (ImageButton) findViewById(R.id.layout5);
		layout6 = (ImageButton) findViewById(R.id.layout6);
	}
	
	private void setListeners() {
		
		layout1.setOnClickListener(Layout1);
		layout2.setOnClickListener(Layout2);
		layout3.setOnClickListener(Layout3);
		layout4.setOnClickListener(Layout4);
		layout5.setOnClickListener(Layout5);
		layout6.setOnClickListener(Layout6);
		
	}


	
	private ImageButton.OnClickListener Layout1 = new Button.OnClickListener()
    {
		public void onClick(View v)
		{
			Intent intent = new Intent(LayoutSettingActivity.this, Layout1Activity.class);
        	startActivity(intent);
		}
    };
	
    private ImageButton.OnClickListener Layout2 = new Button.OnClickListener()
    {
		public void onClick(View v)
		{
			Intent intent = new Intent(LayoutSettingActivity.this, Layout2Activity.class);
        	startActivity(intent);    
		}
    };
    
    private ImageButton.OnClickListener Layout3 = new Button.OnClickListener()
    {
		public void onClick(View v)
		{
			Intent intent = new Intent(LayoutSettingActivity.this, Layout3Activity.class);
        	startActivity(intent);
		}
    };
    
    private ImageButton.OnClickListener Layout4 = new Button.OnClickListener()
    {
		public void onClick(View v)
		{
			Intent intent = new Intent(LayoutSettingActivity.this, Layout4Activity.class);
        	startActivity(intent);
		}
    };
    
    private ImageButton.OnClickListener Layout5 = new Button.OnClickListener()
    {
		public void onClick(View v)
		{
			Intent intent = new Intent(LayoutSettingActivity.this, Layout5Activity.class);
        	startActivity(intent);
		}
    };
    
    private ImageButton.OnClickListener Layout6 = new Button.OnClickListener()
    {
		public void onClick(View v)
		{
			Intent intent = new Intent(LayoutSettingActivity.this, Layout6Activity.class);
        	startActivity(intent);
		}
    };
	
}
