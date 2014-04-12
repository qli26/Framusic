package com.team8.framusic;



import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class fragmentB extends Fragment{

	TextView text;
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment2,container,false);
		
	}
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	text = (TextView) getActivity().findViewById(R.id.textView1);
 
    }
    
    public void changedata(int i)
    {
    	Resources res = getResources();
    	String[] descriptions=res.getStringArray(R.array.descriptions);
    	text.setText(descriptions[i]);
    }
}
