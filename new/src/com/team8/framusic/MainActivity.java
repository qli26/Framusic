package com.team8.framusic;



import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements communicator{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void respond(int i)
	 {
		 android.app.FragmentManager manager =getFragmentManager();
		 fragmentB  f2= (fragmentB)manager.findFragmentById(R.id.fragment2);
		 f2.changedata(i);
	 }
}
