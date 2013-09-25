package se.chalmers.dat255.craycray;

//import se.chalmers.dat255.craycray.controller.NeedsThread;
import se.chalmers.dat255.craycray.model.DeadException;
import se.chalmers.dat255.craycray.model.NeedsModel;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {


	private TextView feedView;
	private TextView cuddleView;
	private TextView cleanView;
	private String deathCause;
	//private NeedsThread needs;
	private NeedsModel model;
	private Thread t;

	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg){

			feedView.setText("" + model.getHungerLevel());
			cuddleView.setText("" + model.getCuddleLevel());
			cleanView.setText("" + model.getCleanLevel());

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		feedView = (TextView)findViewById(R.id.feedTextView);
		cleanView = (TextView)findViewById(R.id.cleanTextView);
		cuddleView = (TextView)findViewById(R.id.cuddleTextView);
		model = NeedsModel.getInstance();
		feedView.setText("" + model.getHungerLevel());
		cleanView.setText("" + model.getCleanLevel());
		cuddleView.setText("" + model.getCuddleLevel());

		t = new Thread(new Runnable(){

			@Override
			public void run(){

				while(true){
					try{
						model.setHungerLevel(model.getHungerLevel()-1);
						model.setCleanLevel(model.getCleanLevel() - 3);
						model.setCuddleLevel(model.getCuddleLevel() - 2);
						handler.sendMessage(handler.obtainMessage());
						Thread.sleep(1000);
					}catch(Exception e){
						if(e instanceof DeadException){
							
						}
						Log.w("Thread", e);
					}
				}
			}
		});

	}

	@Override 
	public void onStart(){
		super.onStart();
		t.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void feed(View view){
		try{
			model.setHungerLevel(model.getHungerLevel() + 5);
		}catch(DeadException e){
			
		}
		handler.sendMessage(handler.obtainMessage());
		String feed = new String("" + model.getHungerLevel());
		Log.w("Thread", feed);
		//feedView.setText(feed);
	}
	
	public void clean(View view){
		
		model.setCleanLevel(model.getCleanLevel() + 10);
		handler.sendMessage(handler.obtainMessage());
		
	}
	
	public void cuddle(View view){
		
		model.setCuddleLevel(model.getCuddleLevel() + 7);
		handler.sendMessage(handler.obtainMessage());
		
	}
	

}