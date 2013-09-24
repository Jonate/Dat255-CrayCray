package se.chalmers.dat255.craycray.controller;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.model.NeedsModel;

public class NeedsThread  implements Runnable{
	
	private NeedsModel needs;
	private Handler handler;
	
	public NeedsThread(Handler handler){
		handler = this.handler;
	}
	
	
	@Override
	public void run() {

		needs = NeedsModel.getInstance();
		
		while(true){
			
			try{
				
				needs.setHungerCount(needs.getHungerCount()-1);
				handler.obtainMessage().sendToTarget();
				String feed = new String("" + needs.getHungerCount());
				Log.w("Thread", feed);
				Thread.sleep(10000);
				
			}catch(Exception e){
				Log.w("Thread", e);
			}
		}
		
	}

}
