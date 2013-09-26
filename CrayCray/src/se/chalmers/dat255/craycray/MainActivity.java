package se.chalmers.dat255.craycray;

import java.util.Calendar;

import se.chalmers.dat255.craycray.database.DatabaseAdapter;
import se.chalmers.dat255.craycray.database.DatabaseConstants;
import se.chalmers.dat255.craycray.model.NeedsModel;
import se.chalmers.dat255.craycray.util.TimeUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {


	private TextView feedView;
	private NeedsModel hunger;

	private DatabaseAdapter dbA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		feedView = (TextView)findViewById(R.id.feedTextView);
		hunger = NeedsModel.getInstance();

		dbA= new DatabaseAdapter(getBaseContext());
		//checks if the database exists
		if(dbA.getValue("Firsttime")==-1){
			dbA.addValue("Firsttime", 1);
			dbA.addValue(DatabaseConstants.HUNGER, hunger.getHungerCount());
			dbA.addStringValue(DatabaseConstants.TIME, TimeUtil.getCurrentTime());
		}else{
			int differenceInSeconds=TimeUtil.compareTime(dbA.getStringValue(DatabaseConstants.TIME));
			Log.w("Database",differenceInSeconds+"");

		}			
		hunger.setHungerCount(dbA.getValue(DatabaseConstants.HUNGER));
		feedView.setText("" + hunger.getHungerCount());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Updates the database if the application is shut down
	 */
	@Override
	public void onDestroy(){
		dbA.updateValue(DatabaseConstants.HUNGER, hunger.getHungerCount());	
		dbA.updateStringValue(DatabaseConstants.TIME, TimeUtil.getCurrentTime());
		super.onDestroy();
	}

	//Changes the hungerlevel indicator int in the TextView 
	public void feed(View view){
		hunger.setHungerCount(hunger.getHungerCount() + 1);
		String feed = new String("" + hunger.getHungerCount());
		feedView.setText(feed);
	}

}
