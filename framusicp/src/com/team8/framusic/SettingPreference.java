package com.team8.framusic;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.view.MenuItem;

public class SettingPreference extends PreferenceActivity {

	private Context mContext = null;
	private ActionBar mActionBar = null;
	
	private Preference mBatterySaving = null;
	private Preference mBattryBottomLine = null;
	private Preference mAction = null;
	private SwitchPreference mStopSlide = null;
	private SwitchPreference mStopMusic = null;
	private SwitchPreference mQuitApp = null;
	
	private SwitchPreference mAlarm = null;
	private Preference mStartPlayingMusic = null;
	private Preference mStopPlayingMusic = null;
	
	public SettingPreference() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.addPreferencesFromResource(R.xml.setting);
		mContext = this;

		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);

		this.mBatterySaving = findPreference("battery_saving_key");
		this.mBattryBottomLine = findPreference("battery_bottom_line");
		//this.mAction = findPreference("action_key");
		this.mStopSlide = (SwitchPreference) findPreference("stop_slide");
		this.mStopMusic = (SwitchPreference) findPreference("stop_music");
		this.mQuitApp = (SwitchPreference) findPreference("quit_app");
		
		this.mAlarm =(SwitchPreference) findPreference("alarm_on_off");
		this.mStartPlayingMusic =findPreference("start_play_music_at");
		this.mStopPlayingMusic =findPreference("stop_play_music_at");
		
		if(mAlarm.isChecked()){
			mStartPlayingMusic.setEnabled(true);
			mStopPlayingMusic.setEnabled(true);
			mStartPlayingMusic.setSelectable(true);
			mStopPlayingMusic.setSelectable(true);
			mStopPlayingMusic.setLayoutResource(R.layout.preference);
			mStartPlayingMusic.setLayoutResource(R.layout.preference);
		}
		else{
			mStartPlayingMusic.setEnabled(false);
			mStopPlayingMusic.setEnabled(false);
			mStartPlayingMusic.setSelectable(false);
			mStopPlayingMusic.setSelectable(false);
			mStartPlayingMusic.setLayoutResource(R.layout.blank);
			mStopPlayingMusic.setLayoutResource(R.layout.blank);
		}
		
		mAlarm.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub
				if(mAlarm.isChecked()){
					mStartPlayingMusic.setEnabled(false);
					mStopPlayingMusic.setEnabled(false);
					mStartPlayingMusic.setSelectable(false);
					mStopPlayingMusic.setSelectable(false);
					mStartPlayingMusic.setLayoutResource(R.layout.blank);
					mStopPlayingMusic.setLayoutResource(R.layout.blank);
				}
				else{
					mStartPlayingMusic.setEnabled(true);
					mStopPlayingMusic.setEnabled(true);
					mStartPlayingMusic.setSelectable(true);
					mStopPlayingMusic.setSelectable(true);
					mStopPlayingMusic.setLayoutResource(R.layout.preference);
					mStartPlayingMusic.setLayoutResource(R.layout.preference);
				}
				return true;
			}

		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		// Handle action buttons

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
