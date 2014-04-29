package com.team8.framusicv2;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.team8.framusicv2.picture.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ChoosePicFoldersActivity extends BaseActivity {

	private Context mContext = null;
	private String mWhoCalledMe;
	private boolean mFirstTimeOpen;
	private String[] mWhoCanCallMe = new String[] {
			LayoutSettingActivity.class.toString(),
			DisplayBackgroundMusicActivity.class.toString(), };
	private ArrayList<String> imageUrls;
	private DisplayImageOptions options;
	private ImageAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.screen_choose_pic_folders);

		mContext = this;
		final String[] columns = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
		@SuppressWarnings("deprecation")
		Cursor imagecursor = managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy + " DESC");
		getSharedPreferences();
		processExtraData();

		Button next = (Button) findViewById(R.id.button2);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mFirstTimeOpen) {
					Intent intent = new Intent(mContext,
							MusicSettingActivity.class);
					Bundle b = new Bundle();
					b.putString("WHO_CALLED_ME",
							MusicSettingActivity.class.toString());
					intent.putExtra("CALLING_INFO", b);
					startActivity(intent);
				} else {
					if (mWhoCalledMe.equals(mWhoCanCallMe[0])) {
						Intent intent = new Intent(mContext,
								LayoutSettingActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								LayoutSettingActivity.class.toString());
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					} else if (mWhoCalledMe.equals(mWhoCanCallMe[0])) {
						Intent intent = new Intent(mContext,
								DisplayBackgroundMusicActivity.class);
						Bundle b = new Bundle();
						b.putString("WHO_CALLED_ME",
								DisplayBackgroundMusicActivity.class.toString());
						intent.putExtra("CALLING_INFO", b);
						startActivity(intent);
					}
				}
				finish();
			}
		});

		this.imageUrls = new ArrayList<String>();

		for (int i = 0; i < imagecursor.getCount(); i++) {
			imagecursor.moveToPosition(i);
			int dataColumnIndex = imagecursor
					.getColumnIndex(MediaStore.Images.Media.DATA);
			imageUrls.add(imagecursor.getString(dataColumnIndex));

			System.out.println("=====> Array path => " + imageUrls.get(i));
		}

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.stub_image)
				.showImageForEmptyUri(R.drawable.image_for_empty_url)
				.cacheInMemory().cacheOnDisc().build();

		imageAdapter = new ImageAdapter(this, imageUrls);

		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(imageAdapter);
	}

	@Override
	protected void onStop() {
		imageLoader.stop();
		super.onStop();
	}

	public void btnChoosePhotosClick(View v) {

		ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
		Toast.makeText(ChoosePicFoldersActivity.this,
				"Total photos selected: " + selectedItems.size(),
				Toast.LENGTH_SHORT).show();
		Log.d(ChoosePicFoldersActivity.class.getSimpleName(),
				"Selected Items: " + selectedItems.toString());
		// if & else : beware who called this activity
		Intent i = new Intent(this, DisplayBackgroundMusicActivity.class);
		Bundle b = new Bundle();
		b.putStringArrayList("photos", selectedItems);
		i.putExtra("Bundle", b);
		startActivity (i);
		//start new activitiy send b;
	}

	public class ImageAdapter extends BaseAdapter {

		ArrayList<String> mList;
		LayoutInflater mInflater;
		Context mContext;
		SparseBooleanArray mSparseBooleanArray;

		public ImageAdapter(Context context, ArrayList<String> imageList) {
			// TODO Auto-generated constructor stub
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
			mSparseBooleanArray = new SparseBooleanArray();
			mList = new ArrayList<String>();
			this.mList = imageList;

		}

		public ArrayList<String> getCheckedItems() {
			ArrayList<String> mTempArry = new ArrayList<String>();

			for (int i = 0; i < mList.size(); i++) {
				if (mSparseBooleanArray.get(i)) {
					mTempArry.add(mList.get(i));
				}
			}

			return mTempArry;
		}

		@Override
		public int getCount() {
			return imageUrls.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_multiphoto_item,
						null);
			}

			CheckBox mCheckBox = (CheckBox) convertView
					.findViewById(R.id.checkBox1);
			final ImageView imageView = (ImageView) convertView
					.findViewById(R.id.imageView1);
			imageLoader.init(ImageLoaderConfiguration
					.createDefault(getBaseContext()));
			imageLoader.displayImage("file://" + imageUrls.get(position),
					imageView, options, new SimpleImageLoadingListener() {

						@Override
						public void onLoadingComplete(Bitmap loadedImage) {
							Animation anim = AnimationUtils.loadAnimation(
									ChoosePicFoldersActivity.this,
									R.anim.fade_in);
							imageView.setAnimation(anim);
							anim.start();
						}

					});

			mCheckBox.setTag(position);
			mCheckBox.setChecked(mSparseBooleanArray.get(position));
			mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);

			return convertView;
		}

		OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				mSparseBooleanArray.put((Integer) buttonView.getTag(),
						isChecked);
			}
		};
	}

	private void processExtraData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		// use the data received here
		getSharedPreferences();
		Bundle b = intent.getBundleExtra("CALLING_INFO");
		mWhoCalledMe = b.getString("WHO_CALLED_ME");
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
