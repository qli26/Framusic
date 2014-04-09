package com.team8.framusic.Activity;

import com.team8.framusic.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Layout1Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout1);
		findViews();
		setListeners();
	}
	
	Button btn1;
	ImageView image1;
	
	private void findViews() {
		btn1 = (Button)findViewById(R.id.btn1);
		image1 = (ImageView)findViewById(R.id.image1);
	}
	
	private void setListeners() {
		btn1.setOnClickListener(button1);
	}
	
	private Button.OnClickListener button1 = new Button.OnClickListener()
    {
		public void onClick(View v)
		{
			btn1.setVisibility(View.GONE);
			image1.setImageResource(R.drawable.sample_0);
			//Intent intent = new Intent(Layout2Activity.this, GridViewActivity.class);
			//startActivity(intent);
		}
    };
	
}
