/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.team8.framusic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.team8.framusic.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Context mContext = null;
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
	private Button mPreferenceButton;
	private Button mChangeFolderButton;
	private Button mLayoutSettingButton;
	private Button mMusicSettingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;
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
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

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

		this.mPreferenceButton = (Button) findViewById(R.id.preference);
		this.mLayoutSettingButton = (Button) findViewById(R.id.layoutSetting);
		this.mMusicSettingButton = (Button) findViewById(R.id.musicSetting);
		this.mChangeFolderButton = (Button) findViewById(R.id.changeFolder);

		// if (savedInstanceState == null) {
		// // selectItem(0);
		// }
		mActionBarOn = true;
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
		rl.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mActionBar = getActionBar();
				if (mActionBarOn) {
					hideAll();
				} else {
					showAll();
				}
				return false;
			}
		});
	}

	public void hideAll() {
		Animation fadeout = AnimationUtils.loadAnimation(mContext,
				R.anim.fadeout);
		mActionBar.hide();

		mPreferenceButton.startAnimation(fadeout);
		mPreferenceButton.setVisibility(View.INVISIBLE);

		mLayoutSettingButton.startAnimation(fadeout);
		mLayoutSettingButton.setVisibility(View.INVISIBLE);

		mMusicSettingButton.startAnimation(fadeout);
		mMusicSettingButton.setVisibility(View.INVISIBLE);

		mChangeFolderButton.startAnimation(fadeout);
		mChangeFolderButton.setVisibility(View.INVISIBLE);
		mActionBarOn = false;
	}

	public void showAll() {
		Animation fadein = AnimationUtils
				.loadAnimation(mContext, R.anim.fadein);
		mActionBar.show();

		mPreferenceButton.setAnimation(fadein);
		mPreferenceButton.setVisibility(View.VISIBLE);

		mLayoutSettingButton.setAnimation(fadein);
		mLayoutSettingButton.setVisibility(View.VISIBLE);

		mMusicSettingButton.setAnimation(fadein);
		mMusicSettingButton.setVisibility(View.VISIBLE);

		mChangeFolderButton.setAnimation(fadein);
		mChangeFolderButton.setVisibility(View.VISIBLE);
		mActionBarOn = true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
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
		// switch (item.getItemId()) {
		// case R.id.action_websearch:
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
		// default:
		return super.onOptionsItemSelected(item);
		// }
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
		case 0:
			Toast toast = Toast.makeText(mContext, mPreferenceTitle[position],
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		case 1:
			Toast toast1 = Toast.makeText(mContext, mPreferenceTitle[position],
					Toast.LENGTH_SHORT);
			toast1.setGravity(Gravity.CENTER, 0, 0);
			toast1.show();
		case 2:
			Toast toast2 = Toast.makeText(mContext, mPreferenceTitle[position],
					Toast.LENGTH_SHORT);
			toast2.setGravity(Gravity.CENTER, 0, 0);
			toast2.show();
		case 3:
			Toast toast3 = Toast.makeText(mContext, mPreferenceTitle[position],
					Toast.LENGTH_SHORT);
			toast3.setGravity(Gravity.CENTER, 0, 0);
			toast3.show();
		case 4:
			Toast toast4 = Toast.makeText(mContext, mPreferenceTitle[position],
					Toast.LENGTH_SHORT);
			toast4.setGravity(Gravity.CENTER, 0, 0);
			toast4.show();
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int[] origin = new int[2];
		switch (event.getAction()) {
		// ´¥ÃþÆÁÄ»Ê±¿Ì
		case MotionEvent.ACTION_DOWN:
			Toast t = Toast.makeText(mContext,
					event.getX() + ", " + event.getY(), Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
			break;
		// ´¥Ãþ²¢ÒÆ¶¯Ê±¿Ì
		case MotionEvent.ACTION_MOVE:
			break;
		// ÖÕÖ¹´¥ÃþÊ±¿Ì
		case MotionEvent.ACTION_UP:
			Toast t1 = Toast.makeText(mContext,
					event.getX() + ", " + event.getY(), Toast.LENGTH_LONG);
			t1.setGravity(Gravity.CENTER, 0, 0);
			t1.show();
			break;
		}

		return super.onTouchEvent(event);
	}
}