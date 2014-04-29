package com.team8.framusicv2;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MusicSettingActivity extends Activity {
	private Context mContext;
	private boolean mFirstTimeOpen;
	private String mWhoCalledMe;
	private String[] mWhoCanCallMe = new String[] {
			ChoosePicFoldersActivity.class.toString(),
			DisplayBackgroundMusicActivity.class.toString(), };
	ListView musiclist;
    Cursor musiccursor;
    int music_column_index;
    int count;
    MediaPlayer mMediaPlayer;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_music_setting);
		mContext = this;
		getSharedPreferences();
		processExtraData();
		init_phone_music_grid();		
		
		
		Button b = (Button) findViewById(R.id.button6);
		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mFirstTimeOpen) {
					Intent intent = new Intent(mContext,
							DisplayBackgroundMusicActivity.class);
					Bundle b = new Bundle();
					b.putString("WHO_CALLED_ME",
							DisplayBackgroundMusicActivity.class.toString());
					intent.putExtra("CALLING_INFO", b);
					startActivity(intent);
				} else {
					if (mWhoCalledMe
							.equals(DisplayBackgroundMusicActivity.class
									.toString())) {
						Intent intent = new Intent(mContext,
								DisplayBackgroundMusicActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								DisplayBackgroundMusicActivity.class.toString());
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					}
					else if (mWhoCalledMe
							.equals(DisplayBackgroundMusicActivity.class
									.toString())) {
						Intent intent = new Intent(mContext,
								ChoosePicFoldersActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								ChoosePicFoldersActivity.class.toString());
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					}
				}
				finish();
			}
			
		});
		
		
	}
	
	private void init_phone_music_grid() {
        System.gc();
        String[] proj = { MediaStore.Audio.Media._ID,
MediaStore.Audio.Media.DATA,
MediaStore.Audio.Media.DISPLAY_NAME,
MediaStore.Video.Media.SIZE };
        musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
proj, null, null, null);
        count = musiccursor.getCount();
        musiclist = (ListView) findViewById(R.id.PhoneMusicList);
        musiclist.setAdapter(new MusicAdapter(getApplicationContext()));

        musiclist.setOnItemClickListener(musicgridlistener);
        mMediaPlayer = new MediaPlayer();
  }
	
	private OnItemClickListener musicgridlistener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position,
long id) {
              System.gc();
              music_column_index = musiccursor
.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
              musiccursor.moveToPosition(position);
              String filename = musiccursor.getString(music_column_index);

              try {
                    if (mMediaPlayer.isPlaying()) {
                          mMediaPlayer.reset();
                    }
                    mMediaPlayer.setDataSource(filename);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
              } catch (Exception e) {

              }
        }
  };

  public class MusicAdapter extends BaseAdapter {
        private Context mContext;

        public MusicAdapter(Context c) {
              mContext = c;
        }

        public int getCount() {
              return count;
        }

        public Object getItem(int position) {
              return position;
        }

        public long getItemId(int position) {
              return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            System.gc();
            TextView tv = new TextView(mContext.getApplicationContext());
            String id = null;
            if (convertView == null) {
                  music_column_index = musiccursor
.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                  musiccursor.moveToPosition(position);
                  id = musiccursor.getString(music_column_index);
                  tv.setText(id);
            } else
                  tv = (TextView) convertView;
            return tv;
      }
  }
	
	private void processExtraData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		// use the data received here
		getSharedPreferences();
		Bundle b = intent.getBundleExtra("CALLING_INFO");
		mWhoCalledMe = b.getString("WHO_CALLED_ME");
	}
	
	protected void Selectmusic(){
		//get selectedItem
		//create a dialog
			//in the dialog, it player touch ok:	//pass ( if it's an uri, transfer to full file path) selected items to next activity(display one);
			//otherwise, cancel the playlist.
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
}
