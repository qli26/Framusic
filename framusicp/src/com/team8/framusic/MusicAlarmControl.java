package com.team8.framusic;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.Toast;

public class MusicAlarmControl extends Service {
	private Context mContext = null;
	private String mMyType = null;
	private Intent intent = null;

	public MusicAlarmControl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mContext = this;

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		this.intent = intent;
		return mBinder;
	}

	private final IBinder mBinder = new Binder() {
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,
				int flags) throws RemoteException {
			return super.onTransact(code, data, reply, flags);
		}
	};
	private int startTimeMinute;
	private int startTimeHour;
	private int stopTimeHour;
	private int stopTimeMinute;
	private boolean alarmOnOff;

	protected void getSharedPreferences() {
		SharedPreferences sp = mContext.getSharedPreferences("Setting",
				MODE_PRIVATE);

		startTimeHour = sp.getInt("START_TIME_HOUR", startTimeHour);
		startTimeMinute = sp.getInt("START_TIME_MUNITE", startTimeMinute);
		stopTimeHour = sp.getInt("STOP_TIME_HOUR", stopTimeHour);
		stopTimeMinute = sp.getInt("STOP_TIME_MINUTE", stopTimeMinute);

		alarmOnOff = sp.getBoolean("ALARM_ON_OFF", alarmOnOff);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		this.intent = intent;
		mMyType = intent.getStringExtra("TYPE");

		Toast.makeText(mContext, "startID:" + startId +" "+mMyType, Toast.LENGTH_LONG).show();
//get current music player and stop or start music
		
		
		// Done with our work... stop the service!
		MusicAlarmControl.this.stopSelf();
	}
}
