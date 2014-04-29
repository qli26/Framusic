package com.team8.framusicv2.musicplay;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

public class MusicPlayer implements Parcelable {
	private String filename;
	private MediaPlayer mediaPlayer;
	private int currentPosition;
	private ArrayList<File> musicList = new ArrayList<File>();
	private int currentMusic = 0;

	public MusicPlayer(Context mContext) {
		mediaPlayer = new MediaPlayer();

		File findMusic = new File(Environment.getExternalStorageDirectory(), "");
		if (findMusic.isDirectory()) {
			if (findMusic.exists()) {
				File[] files = findMusic.listFiles();
				for (File f : files) {

					if (f.getAbsolutePath().toString().contains(".mp3")) {
						System.out.println(f.getAbsolutePath().toString());
						musicList.add(f);
					}
					;
				}
				if (musicList.size() == 0) {
					Toast.makeText(mContext, "No music!", Toast.LENGTH_LONG)
							.show();
				}
				System.out.println("");
			}
		}
	}
	public MusicPlayer() {
		mediaPlayer = new MediaPlayer();	
	}

	protected void destroyPlayer() {
		mediaPlayer.release();
	}

	public void pause() {
		//if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		//} 
	}

	public void previous() {
		currentMusic--;
		if (currentMusic < 0) {
			currentMusic = 0;
		}
		try {
			play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void next() {
		currentMusic++;
		if (currentMusic > musicList.size() - 1) {
			currentMusic = musicList.size() - 1;
		}

		try {
			play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 把播放动作抽出来，当度定义为play方法
	 * 
	 * @throws IOException
	 */
	public void play() throws IOException {
		File androidFile = this.musicList.get(currentMusic);
		mediaPlayer.reset();
		mediaPlayer.setDataSource(androidFile.getAbsolutePath());
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	public void pauseByInterrupt() {
		// 如果音乐正在播放，则取得音乐的当前位置
		if (mediaPlayer.isPlaying()) {
			currentPosition = mediaPlayer.getCurrentPosition();
			mediaPlayer.stop();
		}
	}

	public void resumeFromInterrupt() {
		if (currentPosition > 0 && filename != null) {
			try {
				play();
				mediaPlayer.seekTo(currentPosition);
				currentPosition = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isPlaying(){
		return mediaPlayer.isPlaying();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 在系统杀死activity后，当用户接完电话后，会重新回到这个activity</br>
	 * 系统会自动帮我们创建这个activity，先执行onCreate()方法</br>
	 * 在执行onRestoreInstanceState()方法，我们在这个方法中</br> Bundle中保存的数据恢复
	 */
	// @Override
	// protected void onRestoreInstanceState(Bundle savedInstanceState) {
	// super.onRestoreInstanceState(savedInstanceState);
	// filename=savedInstanceState.getString("filename");
	// currentPosition=savedInstanceState.getInt("currentPosition");
	// }

	/**
	 * 当一些位置情况发生的时候，导致Activity不可见了</br>
	 * 系统可能会杀死掉这个activity，如果我们想再系统杀死activity</br>
	 * 之前缓存一些需要的数据,这里保存的数据是播放的文件和当前播放的位置
	 */
	// @Override
	// protected void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// outState.putString("filename", filename);
	// outState.putInt("currentPosition", currentPosition);
	// }
}