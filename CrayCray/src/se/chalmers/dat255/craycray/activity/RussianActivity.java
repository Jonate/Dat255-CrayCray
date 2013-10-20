package se.chalmers.dat255.craycray.activity;

import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.model.NeedsModel;
import se.chalmers.dat255.craycray.model.RussianRouletteModel;
import se.chalmers.dat255.craycray.util.Constants;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

/**
 * An Activity for playing a simple game of Russian Roulette 
 * with 5/6 chance to win. If you loose you die.
 */
public class RussianActivity extends Activity {

	RussianRouletteModel rModel;
	NeedsModel model = NeedsModel.getInstance();
	
	private ImageView crayView;
	private Vibrator vib;
	private MediaPlayer musicPlayer;
	private int lengthPlayed;
	private boolean firstTime = true;
	private boolean isMute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Checks if the user has set the music to mute in MainActivity
		Intent intent = getIntent();
		isMute = intent.getBooleanExtra(Constants.EXTRA_MESSAGE, false);

		//If the user has set the music to mute in MainActivity, the musicplayer never is created
		if(isMute == false){
			musicPlayer = MediaPlayer.create(this, R.raw.dylan_palme_the_crazies_are_out_tonight);
			musicPlayer.seekTo(99000);
			musicPlayer.start();
		}
		
		firstTime = false;
		vib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		rModel = new RussianRouletteModel();
		setContentView(R.layout.activity_russian);
		crayView = (ImageView) findViewById(R.id.scaredCrayCray);
		if(model.getCleanLevel()<50){
			crayView.setImageResource(R.drawable.scared_dbaby);
		}else{
			crayView.setImageResource(R.drawable.scared_baby);
		}
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		if(musicPlayer!= null){
			musicPlayer.pause();
			lengthPlayed = musicPlayer.getCurrentPosition();
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(musicPlayer != null){
			if(firstTime){
				musicPlayer.seekTo(lengthPlayed);
			}
			musicPlayer.start();
		}
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(musicPlayer!= null){
			musicPlayer.release();
			musicPlayer = null;
		}
	}

	
	/*
	 * Disable back button
	 */
	@Override
	public void onBackPressed() {
	}

	/**
	 * Plays Russian Roulette and returns to MainActivity
	 * with the result.
	 * @param view
	 */
	public void playRussian(View view){
		vib.vibrate(500);
		rModel.play();
		
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		
		finish();
	}
}
