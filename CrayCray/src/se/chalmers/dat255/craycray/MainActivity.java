package se.chalmers.dat255.craycray;

import se.chalmers.dat255.craycray.controller.NeedsThread;
import se.chalmers.dat255.craycray.model.NeedsModel;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	

	private TextView feedView;
	private NeedsThread needs;
	private NeedsModel hunger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		feedView = (TextView)findViewById(R.id.feedTextView);
		needs = NeedsThread.getInstance();
		hunger = NeedsModel.getInstance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void feed(View view){
		hunger.setHungerCount(hunger.getHungerCount() + 30);
		String feed = new String("" + hunger.getHungerCount());
		feedView.setText(feed);
	}

}
