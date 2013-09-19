package se.chalmers.dat255.craycray.controller;

import android.util.Log;
import android.widget.TextView;
import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.model.NeedsModel;

public class NeedsThread  implements Runnable{
	
	private NeedsModel needs;
	private TextView text;
	
	public NeedsThread(TextView view){
		text = view;
	}
	
	
	@Override
	public void run() {
		
		needs = NeedsModel.getInstance();
		
		while(true){
			
			try{
				
				needs.setHungerCount(needs.getHungerCount()-1);
				String feed = new String("" + needs.getHungerCount());
				Log.w("Thread", feed);
				text.setText(feed);
				Thread.sleep(1000);
				
			}catch(Exception e){
				Log.w("Thread", "hej");
			}
		}
		
	}

}
