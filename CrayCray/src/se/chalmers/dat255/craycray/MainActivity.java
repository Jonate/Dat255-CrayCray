package se.chalmers.dat255.craycray;

//import se.chalmers.dat255.craycray.controller.NeedsThread;
import se.chalmers.dat255.craycray.model.NeedsModel;
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
	//private NeedsThread needs;
	private NeedsModel hunger;
	private Thread t;

	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg){

			feedView.setText("" + hunger.getHungerCount());

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		feedView = (TextView)findViewById(R.id.feedTextView);
		hunger = NeedsModel.getInstance();
		feedView.setText("" + hunger.getHungerCount());

		t = new Thread(new Runnable(){

			@Override
			public void run(){

				while(true){
					try{
						hunger.setHungerCount(hunger.getHungerCount()-1);
						handler.sendMessage(handler.obtainMessage());
						Thread.sleep(1000);
					}catch(Exception e){
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
		hunger.setHungerCount(hunger.getHungerCount() + 5);
		handler.sendMessage(handler.obtainMessage());
		String feed = new String("" + hunger.getHungerCount());
		Log.w("Thread", feed);
		//feedView.setText(feed);
	}

}