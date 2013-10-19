package se.chalmers.dat255.craycray.service;

import se.chalmers.dat255.craycray.R;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.media.*;

public class MusicService extends Service implements MediaPlayer.OnErrorListener{
	
	private final IBinder musicBinder = new ServiceBinder(); 
	MediaPlayer musicPlayer;
	private int length = 0;
	
	public MusicService(){}

	public class ServiceBinder extends Binder{

		public MusicService getService(){
			return MusicService.this;
		}
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		return musicBinder;
	}

	
	@Override
	public void onCreate(){
		super.onCreate();
		musicPlayer = MediaPlayer.create(this, R.raw.narwhals);
		Log.w("music", "musicplayer created with narwhals song");
		musicPlayer.setOnErrorListener(this);
		
		if(musicPlayer != null){
			musicPlayer.setLooping(true);
			musicPlayer.setVolume(100, 100);
			Log.w("music", "musicPlayer is not null and set to loop");
		}

		musicPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

			public boolean onError(MediaPlayer mp, int what, int
					extra){

				onError(musicPlayer, what, extra);
				return true;
			}
		});
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		if(musicPlayer != null){
			try{
				musicPlayer.stop();
				musicPlayer.release();
			}finally{
				musicPlayer = null;
			}
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		musicPlayer.start();
		Log.w("music", "music started");
		return START_STICKY;
	}
	
	public void pauseMusic(){
		if(musicPlayer.isPlaying()){
			musicPlayer.pause();
			length = musicPlayer.getCurrentPosition();
			Log.w("music", "music paused");
		}
	}
	
	public void resumeMusic(){
		if(musicPlayer.isPlaying() == false){
			musicPlayer.seekTo(length);
			musicPlayer.start();
			Log.w("music", "music resumed");
		}
	}
	
	public void stopMusic(){
		musicPlayer.stop();
		Log.w("music", "music stopped");
		musicPlayer.release();
		musicPlayer = null;
	}
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Toast.makeText(this, "Party music failed!", Toast.LENGTH_SHORT);
		if(musicPlayer != null){
			try{
				musicPlayer.stop();
				musicPlayer.release();
			}finally{
				musicPlayer = null;
			}
		}
		return false;
	}

}
