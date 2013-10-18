package se.chalmers.dat255.craycray.activity;

import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.model.NeedsModel;
import se.chalmers.dat255.craycray.model.RussianRouletteModel;
import se.chalmers.dat255.craycray.util.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class RussianActivity extends Activity {

	RussianRouletteModel rModel;
	NeedsModel model = NeedsModel.getInstance();
	
	ImageView crayView;
	Vibrator vib;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		rModel = new RussianRouletteModel();
		setContentView(R.layout.activity_russian);
		crayView = (ImageView) findViewById(R.id.scaredCrayCray);
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
		Log.w("russian", "inside playrussian");
		Intent intent = new Intent();

		rModel.play();
		Log.w("russian", "efter rModel.play()");
		setResult(RESULT_OK, intent);
		finish();
		
		Log.w("russian", "playRussian finished");
	}
}
