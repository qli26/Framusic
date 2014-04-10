package com.team8.framusicv2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team8.framusicv2.musicplay.MusicPlayer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayBackgroundMusicActivity extends Activity {
	private Context mContext = null;
	private String mWhoCalledMe;
	private boolean mFirstTimeOpen;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPreferenceTitle;

	private int[] images = new int[] { R.drawable.ic_action_settings,
			R.drawable.ic_action_settings, R.drawable.ic_action_settings,
			R.drawable.ic_action_settings, R.drawable.ic_action_help,
			R.drawable.ic_action_about };

	private int[] imagesLine = new int[] { R.drawable.line, R.drawable.line,
			R.drawable.line, R.drawable.line, R.drawable.line, R.drawable.line };

	private boolean mActionBarOn;
	private ActionBar mActionBar;

	private Button mShuffle;
	private Button mPrevious;
	private Button mPlayStopMusic;
	private Button mNext;
	private Button mRepeat;

	private BroadcastReceiver batteryLevelRcvr;
	private IntentFilter batteryLevelFilter;

	private int prog = 0;
	private boolean alarmOnOff;
	private int startTimeHour;
	private int startTimeMinute;
	private int stopTimeHour;
	private int stopTimeMinute;

	private PendingIntent mAlarmStopSender;
	private PendingIntent mAlarmStartSender;
	boolean playing = true;
	private Button btn_play_or_pause;
	private Button btn_previous;
	private Button btn_next;
	private MusicPlayer musicPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_display_background_music);

		mContext = this;
		this.getSharedPreferences();
		this.setFromPreferencesValue();
		this.saveSharedPreferences();
		processExtraData();

		/* copy from framusicp */
		mTitle = mDrawerTitle = getTitle();
		mPreferenceTitle = getResources().getStringArray(
				R.array.preference_array);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		setAdapter();
		// mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		// R.layout.drawer_list_item, mPreferenceTitle));

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer

		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {

			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				if (!mActionBarOn) {
					showAll();
				}
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				mActionBar = getActionBar();
				if (mActionBarOn) {
					hideAll();
				}
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mActionBarOn = true;
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
		rl.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (mActionBarOn) {
					hideAll();
				} else {
					showAll();
				}
				return false;
			}
		});

		mHandler.sendEmptyMessageDelayed(HIDEALLELEMENTINSCREEN, 5000);

		this.mShuffle = (Button) findViewById(R.id.shuffle);
		this.mPrevious = (Button) findViewById(R.id.previous);
		this.mPlayStopMusic = (Button) findViewById(R.id.play_stop_music);
		this.mNext = (Button) findViewById(R.id.next);
		this.mRepeat = (Button) findViewById(R.id.repeat);

		monitorBatteryState();

		/* copy from framusicp */

		Button reset = (Button) findViewById(R.id.reset);
		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFirstTimeOpen = true;
				saveSharedPreferences();
			}

		});

		musicPlayer = new MusicPlayer(mContext);

		mPlayStopMusic = (Button) findViewById(R.id.play_stop_music);
		mPrevious = (Button) findViewById(R.id.previous);
		mNext = (Button) findViewById(R.id.next);

		mPlayStopMusic.setOnClickListener(musicListener);
		mPrevious.setOnClickListener(musicListener);
		mNext.setOnClickListener(musicListener);
		try {
			if (playing) {
				playing = false;
				musicPlayer.pause();
				mPlayStopMusic
						.setBackgroundResource(R.drawable.ic_action_play);
				Toast.makeText(mContext, "Pause", Toast.LENGTH_LONG)
						.show();
				stopAlarm();
				startAlarm();
			} else {
				playing = true;
				musicPlayer.play();
				mPlayStopMusic
						.setBackgroundResource(R.drawable.ic_action_pause);
				Toast.makeText(mContext, "Playing", Toast.LENGTH_LONG)
						.show();
				stopAlarm();
				startAlarm();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private OnClickListener musicListener = new OnClickListener() {

		public void onClick(View v) {
			Button button = (Button) v;
			try {
				switch (v.getId()) {
				case R.id.play_stop_music:
					if (playing) {
						playing = false;
						musicPlayer.pause();
						mPlayStopMusic
								.setBackgroundResource(R.drawable.ic_action_play);
						Toast.makeText(mContext, "Pause", Toast.LENGTH_LONG)
								.show();
						stopAlarm();
						startAlarm();
					} else {
						playing = true;
						musicPlayer.play();
						mPlayStopMusic
								.setBackgroundResource(R.drawable.ic_action_pause);
						Toast.makeText(mContext, "Playing", Toast.LENGTH_LONG)
								.show();
						stopAlarm();
						startAlarm();
					}
					break;
				case R.id.previous:
					mPlayStopMusic
					.setBackgroundResource(R.drawable.ic_action_pause);
					musicPlayer.previous();
					break;
				case R.id.next:
					mPlayStopMusic
					.setBackgroundResource(R.drawable.ic_action_pause);
					musicPlayer.next();
					break;
				}
			} catch (IOException e) {
				Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT);
			}
		}
	};

	private void processExtraData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		// use the data received here
		getSharedPreferences();
		Bundle b = intent.getBundleExtra("CALLING_INFO");
		if (b != null) {
			mWhoCalledMe = b.getString("WHO_CALLED_ME");
		}
		if (alarmOnOff == true) {
			stopAlarm();
			startAlarm();
		} else {
			stopAlarm();
		}
	}

	protected void getSharedPreferences() {
		SharedPreferences sp = mContext.getSharedPreferences("Setting",
				MODE_PRIVATE);

		mFirstTimeOpen = sp.getBoolean("FIRST_TIME_OPEN", true);
	}

	protected void setFromPreferencesValue() {

		if (this.mFirstTimeOpen == true) {
			this.mFirstTimeOpen = false;
		}
		System.out.println();
	}

	protected void saveSharedPreferences() {
		SharedPreferences sp = mContext.getSharedPreferences("Setting",
				MODE_PRIVATE);

		Editor editor = sp.edit();
		editor.putBoolean("FIRST_TIME_OPEN", mFirstTimeOpen);
		editor.commit();
	}

	/* copy from framusicp */
	public void hideAll() {
		Animation fadeout = AnimationUtils.loadAnimation(mContext,
				R.anim.fadeout);
		mActionBar.hide();

		LinearLayout musicPlayBar = (LinearLayout) findViewById(R.id.musicControlBar);
		musicPlayBar.startAnimation(fadeout);
		musicPlayBar.setVisibility(View.INVISIBLE);

		mActionBarOn = false;
		mHandler.removeMessages(1);
	}

	final int HIDEALLELEMENTINSCREEN = 1;
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HIDEALLELEMENTINSCREEN:
				hideAll();
				break;

			default:
				break;
			}
		};
	};

	public void showAll() {
		Animation fadein = AnimationUtils
				.loadAnimation(mContext, R.anim.fadein);
		mActionBar.show();

		mActionBarOn = true;

		LinearLayout musicPlayBar = (LinearLayout) findViewById(R.id.musicControlBar);
		musicPlayBar.startAnimation(fadein);
		musicPlayBar.setVisibility(View.VISIBLE);

		mHandler.removeMessages(HIDEALLELEMENTINSCREEN);
		mHandler.sendEmptyMessageDelayed(HIDEALLELEMENTINSCREEN, 5000);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		// case R.id.:
		// // create intent to perform web search for this planet
		// Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		// intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
		// // catch event that there's no activity to handle intent
		// if (intent.resolveActivity(getPackageManager()) != null) {
		// startActivity(intent);
		// } else {
		// Toast.makeText(this, R.string.app_not_available,
		// Toast.LENGTH_LONG).show();
		// }
		// return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		// update selected item and title, then close the drawer
		switch (position) {
		case 0:// Gallery
			Intent intentChoosePicFoldersActivity = new Intent(mContext,
					ChoosePicFoldersActivity.class);
			Bundle bChoosePicFoldersActivity = new Bundle();
			bChoosePicFoldersActivity.putString("WHO_CALLED_ME",
					ChoosePicFoldersActivity.class.toString());
			intentChoosePicFoldersActivity.putExtra("CALLING_INFO",
					bChoosePicFoldersActivity);
			startActivity(intentChoosePicFoldersActivity);

			break;
		case 1:// Layout
			Intent intentLayoutSettingActivity = new Intent(mContext,
					LayoutSettingActivity.class);
			Bundle bLayoutSettingActivity = new Bundle();
			bLayoutSettingActivity.putString("WHO_CALLED_ME",
					LayoutSettingActivity.class.toString());
			intentLayoutSettingActivity.putExtra("CALLING_INFO",
					bLayoutSettingActivity);
			startActivity(intentLayoutSettingActivity);

			break;
		case 2:// Playlist
			Intent intentMusicSettingActivity = new Intent(mContext,
					MusicSettingActivity.class);
			Bundle bMusicSettingActivity = new Bundle();
			bMusicSettingActivity.putString("WHO_CALLED_ME",
					MusicSettingActivity.class.toString());
			intentMusicSettingActivity.putExtra("CALLING_INFO",
					bMusicSettingActivity);
			startActivity(intentMusicSettingActivity);

			break;
		case 3:
			Intent intentSettingPreference = new Intent(mContext,
					SettingPreference.class);
			startActivity(intentSettingPreference);
			break;
		case 4:// Wizard
			Intent intentWizardActivity = new Intent(mContext,
					WizardActivity.class);
			Bundle bWizardActivity = new Bundle();
			bWizardActivity.putString("WHO_CALLED_ME",
					WizardActivity.class.toString());
			intentWizardActivity.putExtra("CALLING_INFO", bWizardActivity);
			startActivity(intentWizardActivity);

			break;
		case 5:
			Intent intentAboutPreference = new Intent(mContext,
					AboutPreference.class);
			startActivity(intentAboutPreference);
			break;
		}
		mDrawerList.setItemChecked(position, true);
		// setTitle(mPreferenceTitle[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void setAdapter() {
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < mPreferenceTitle.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("preferenceTitle", this.mPreferenceTitle[i]);
			listItem.put("images", images[i]);
			listItem.put("imageLine", imagesLine[i]);
			listItems.add(listItem);
		}
		PreferenceSimpleAdapter adapter = new PreferenceSimpleAdapter(mContext,
				listItems, R.layout.drawer_list_element, new String[] {
						"preferenceTitle", "images", "imageLine" }, new int[] {
						R.id.SettingTitle, R.id.SettingImage,
						R.id.SettingLineImage });

		mDrawerList.setAdapter(adapter);
	}

	private void monitorBatteryState() {
		batteryLevelRcvr = new BroadcastReceiver() {

			public void onReceive(Context context, Intent intent) {
				StringBuilder sb = new StringBuilder();
				int rawlevel = intent.getIntExtra("level", -1);
				int scale = intent.getIntExtra("scale", -1);
				int status = intent.getIntExtra("status", -1);
				int health = intent.getIntExtra("health", -1);
				int level = -1; // percentage, or -1 for unknown
				if (rawlevel >= 0 && scale > 0) {
					level = (rawlevel * 100) / scale;
				}
				sb.append("The phone");
				if (BatteryManager.BATTERY_HEALTH_OVERHEAT == health) {
					sb.append("'s battery feels very hot!");
				} else {
					switch (status) {
					case BatteryManager.BATTERY_STATUS_UNKNOWN:
						sb.append("no battery.");
						break;
					case BatteryManager.BATTERY_STATUS_CHARGING:
						sb.append("'s battery");
						if (level <= 33)
							sb.append(" is charging, battery level is low"
									+ "[" + level + "]");
						else if (level <= 84)
							sb.append(" is charging." + "[" + level + "]");
						else
							sb.append(" will be fully charged.");
						break;
					case BatteryManager.BATTERY_STATUS_DISCHARGING:
					case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
						if (level == 0)
							sb.append(" needs charging right away.");
						else if (level > 0 && level <= prog) // add what to do
																// in
																// this part for
																// low
																// battery
							sb.append(" is about ready to be recharged, battery level is low"
									+ "[" + level + "]");
						else
							sb.append("'s battery level is" + "[" + level + "]");
						break;
					case BatteryManager.BATTERY_STATUS_FULL:
						sb.append(" is fully charged.");
						break;
					default:
						sb.append("'s battery is indescribable!");
						break;
					}
				}
				sb.append(' ');
				Toast t = Toast.makeText(mContext, sb, Toast.LENGTH_SHORT);
				t.setGravity(Gravity.CENTER, 0, 0);
				t.show();
			}
		};
		batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryLevelRcvr, batteryLevelFilter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(batteryLevelRcvr);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);// must store the new intent unless getIntent() will
							// return the old one
		processExtraData();
	}

	private void stopAlarm() {
		// TODO Auto-generated method stub
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		if (mAlarmStopSender != null) {
			am.cancel(mAlarmStopSender);
			mAlarmStopSender = null;
		}
		if (mAlarmStartSender != null) {
			am.cancel(mAlarmStartSender);
			mAlarmStartSender = null;
		}
	}

	private void startAlarm() {
		// TODO Auto-generated method stub

		if (playing == true) {
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			// long firstTime = SystemClock.elapsedRealtime();

			Intent iStop = new Intent(mContext, MusicAlarmControl.class);
			iStop.putExtra("TYPE", "stopMusic");
			mAlarmStopSender = PendingIntent.getService(mContext, 0, iStop, 1);
			Date t = new Date();
			t.setTime(System.currentTimeMillis());
			t.setHours(this.stopTimeHour);
			t.setMinutes(this.stopTimeMinute);
			t.setSeconds(0);
			long a = t.getTime();
			long b = System.currentTimeMillis();
			if (a > b) {
				Toast.makeText(mContext, "Alarm for stop music is set",
						Toast.LENGTH_SHORT).show();
				am.set(AlarmManager.RTC_WAKEUP, t.getTime(), mAlarmStopSender);
			}
		} else {
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			Intent iStart = new Intent(mContext, MusicAlarmControl.class);
			iStart.putExtra("TYPE", "startMusic");
			mAlarmStartSender = PendingIntent
					.getService(mContext, 0, iStart, 1);
			Date t = new Date();
			t.setTime(System.currentTimeMillis());
			t.setHours(this.startTimeHour);
			t.setMinutes(this.startTimeMinute);
			t.setSeconds(0);
			if (t.getTime() > System.currentTimeMillis()) {

				Toast.makeText(mContext, "Alarm for start music is set",
						Toast.LENGTH_SHORT).show();
				am.set(AlarmManager.RTC_WAKEUP, t.getTime(), mAlarmStartSender);
			}
		}
	}

}
