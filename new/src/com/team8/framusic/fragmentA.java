package com.team8.framusic;




import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class fragmentA extends Fragment implements AdapterView.OnItemClickListener{

	 ListView list1;
	 communicator communicator;
		public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
			return inflater.inflate(R.layout.fragment1,container,false);
}
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			list1= (ListView) getActivity().findViewById(R.id.listview);
			communicator = (communicator) getActivity();
			ArrayAdapter<?> adapter=ArrayAdapter.createFromResource(getActivity(), R.array.titles,android.R.layout.simple_list_item_1)	;
			list1.setAdapter(adapter);
			list1.setOnItemClickListener((OnItemClickListener) this);
		}
  
		public void onItemClick(AdapterView<?> adapterview, View view, int i, long l) {
			// TODO Auto-generated method stub
			communicator.respond(i);
		}
}
