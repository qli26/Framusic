package com.team8.framusic.Activity;

import java.util.Timer;
import java.util.TimerTask;

import com.team8.framusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class SlideShowActivity extends Activity{
	 	
	 
    private ImageView imageView;
    int i=0;
    int imgid[]={R.drawable.sample_0,R.drawable.sample_1,R.drawable.sample_2,R.drawable.sample_3,R.drawable.sample_4,R.drawable.sample_5,
            R.drawable.sample_6};
    RefreshHandler refreshHandler=new RefreshHandler();
    Button ctrl; 
    
    
    
    class RefreshHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            SlideShowActivity.this.updateUI();
        }
        public void sleep(long delayMillis){
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };
    
    AnimationSet animationSet;
    public void updateUI(){

    	refreshHandler.sleep(4000);
        imageView.setImageResource(imgid[i]);
        
        animationSet = new AnimationSet(true);       
        Animation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        Animation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        animationSet.addAnimation(fadeInAnimation);
        animationSet.addAnimation(fadeOutAnimation);
        fadeInAnimation.setDuration(1000);
        fadeInAnimation.setStartOffset(0);
        fadeOutAnimation.setDuration(1000);
        fadeOutAnimation.setStartOffset(2000 + 1000);
        imageView.startAnimation(animationSet); 
  
        i = (i+1)%imgid.length;

    }
   
    private void Listener() {
    	ctrl = (Button)findViewById(R.id.control_btn);
    	ctrl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					finish();
			}
		});
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_show);
        this.imageView=(ImageView)this.findViewById(R.id.imageView);
        updateUI();
        Listener();
    }


}