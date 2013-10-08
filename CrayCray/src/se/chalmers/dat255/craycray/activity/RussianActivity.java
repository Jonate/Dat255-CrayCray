package se.chalmers.dat255.craycray.activity;

import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.model.DeadException;
import se.chalmers.dat255.craycray.model.RussianRouletteModel;
import se.chalmers.dat255.craycray.util.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class RussianActivity extends Activity {

	RussianRouletteModel rModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rModel = new RussianRouletteModel();
		setContentView(R.layout.activity_russian);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.russian, menu);
		return true;
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
		Intent intent = new Intent();
		String key = "key";
		try {
			rModel.play();
		} catch (DeadException e) {
			key = "loose";
			intent.putExtra(key, Constants.RUSSIAN_LOOSE);
			setResult(RESULT_OK, intent);
			finish();
		}
//		key = "win";
		intent.putExtra(key, Constants.RUSSIAN_WIN);
		setResult(RESULT_OK, intent);
		finish();
	}
}
