package com.team8.framusic.Activity;

import com.team8.framusic.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Layout2Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout2);
		findViews();
		setListeners();
		/*
		TableLayout table = new TableLayout(this);
	    for (int i = 0; i < 2; i++) {

	        TableRow tr = new TableRow(getBaseContext());
	        tr.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

	        ImageView view = new ImageView(this);
	        view.setImageResource(R.drawable.sample_0);
	        tr.addView(view);
	        
	        table.addView(tr);
	        
	    }
	    */
	}

	Button btn1, btn2;
	ImageView image1, image2;
	
	private void findViews() {
		
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		image1 = (ImageView)findViewById(R.id.image1);
		image2 = (ImageView)findViewById(R.id.image2);
	}
	
	private void setListeners() {
		
		btn1.setOnClickListener(button1);
		btn2.setOnClickListener(button2);
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
	
    private Button.OnClickListener button2 = new Button.OnClickListener()
    {
		public void onClick(View v)
		{
			Intent intent = new Intent(Layout2Activity.this, GridViewActivity.class);
			startActivity(intent);
		}
    };
	
}
