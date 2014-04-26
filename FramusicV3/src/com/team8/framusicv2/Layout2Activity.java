package com.team8.framusicv2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Layout2Activity extends Activity {

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.layout2);
		mContext = this;
		findViews();
		setListeners();
	}

	Button btn1, btn2, btn_save;
	ImageView image1, image2;
	Bitmap bm1, bm2;
	
	private void findViews() {
		
		btn1 = (Button) findViewById(R.id.btn2_1);
		btn2 = (Button) findViewById(R.id.btn2_2);
		btn_save = (Button) findViewById(R.id.btn_save2);
		image1 = (ImageView) findViewById(R.id.image2_1);
		image2 = (ImageView) findViewById(R.id.image2_2);
		
	}

	private void setListeners() {
		btn1.setOnClickListener(button1);
		btn2.setOnClickListener(button2);
		btn_save.setOnClickListener(button_save);
	}
	
	private Button.OnClickListener button1 = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(Layout2Activity.this,
					SinglePhotoSelectActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", 1);
			bundle.putString("WhoCalledMe", Layout2Activity.class.toString());
			intent.putExtra("Layout", bundle);
			startActivity(intent);
		}
	};

	private Button.OnClickListener button2 = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(Layout2Activity.this,
					SinglePhotoSelectActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", 2);
			bundle.putString("WhoCalledMe", Layout2Activity.class.toString());
			intent.putExtra("Layout", bundle);
			startActivity(intent);
		}
	};
	
	private Button.OnClickListener button_save = new Button.OnClickListener() {
		public void onClick(View v) {
			Bitmap s1 = add2BitmapHorizontal(bm1, bm2);
			long a = System.currentTimeMillis();
			saveMyBitmap(new Long(a).toString(), s1);
			Intent i = new Intent(mContext, LayoutSettingActivity.class);
			startActivity(i);
			finish();
		}

	};
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);

		Bundle b = intent.getBundleExtra("Bundle");
		try {
			setImage(b.getInt("position"), b.getString("PicPath"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void setImage(int position, String path) throws IOException {
		File file = new File(path);
		Uri uri = Uri.parse("file://" + file.getPath());
		switch (position) {
		case 1: {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(
					this.getContentResolver(), uri);
			bm1 = bitmap;
			image1.setImageBitmap(bitmap);
			btn1.setVisibility(btn1.INVISIBLE);
			break;
		}
		case 2: {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(
					this.getContentResolver(), uri);
			bm2 = bitmap;
			image2.setImageBitmap(bitmap);
			btn2.setVisibility(btn2.INVISIBLE);
			break;
		}
		
		}
	}
	
	private Bitmap add2BitmapHorizontal(Bitmap first, Bitmap second) {
		int width = first.getWidth() + second.getWidth() + 5;
		int height = Math.max(first.getHeight(), second.getHeight());
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(first, 0, 0, null);
		canvas.drawBitmap(second, first.getWidth() + 5, 0, null);
		return result;
	}
	
	public void saveMyBitmap(String bitName, Bitmap mBitmap) {
		File f = new File(Environment.getExternalStorageDirectory()
				+ "/DCIM/Camera/" + bitName + ".png");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap,
				bitName, "");
		
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
