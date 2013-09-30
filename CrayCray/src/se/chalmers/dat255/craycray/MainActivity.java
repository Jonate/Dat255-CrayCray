package se.chalmers.dat255.craycray;

import se.chalmers.dat255.craycray.graphics.DrawingPanel;
import se.chalmers.dat255.craycray.model.NeedsModel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	

	private TextView feedView;
	private NeedsModel hunger;
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		feedView = (TextView)findViewById(R.id.feedTextView);
//		hunger = NeedsModel.getInstance();
//		feedView.setText("" + hunger.getHungerCount());
		DrawingPanel panel = (DrawingPanel) findViewById(R.id.SurfaceView01);
		panel.setOnClickListener(clickListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Changes the hungerlevel indicator int in the TextView 
	public void feed(View view){
		hunger.setHungerCount(hunger.getHungerCount() + 1);
		String feed = new String("" + hunger.getHungerCount());
		feedView.setText(feed);
	}
	
	private OnClickListener clickListener = new OnClickListener(){
		private boolean isDrawn = false;
		
		@Override
		public void onClick (View view){
			if(!isDrawn){
				DrawingPanel panel = (DrawingPanel)findViewById(R.id.SurfaceView01);
				panel.startDrawPanel();
				isDrawn = true;
			}
		}
	};
}
