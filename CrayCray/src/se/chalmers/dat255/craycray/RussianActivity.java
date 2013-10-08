package se.chalmers.dat255.craycray;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RussianActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_russian);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.russian, menu);
		return true;
	}

}
