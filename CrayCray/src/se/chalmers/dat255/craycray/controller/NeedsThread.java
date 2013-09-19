package se.chalmers.dat255.craycray.controller;

import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.model.NeedsModel;

public class NeedsThread {
	
	private static NeedsThread instance = null;
	
	private NeedsThread(){
		
		Thread background=new Thread(new Runnable() {
			
			NeedsModel needs = NeedsModel.getInstance();

			@Override
			public void run(){
				while(true){
					try{
						needs.setHungerCount(needs.getHungerCount()-1);
						String feed = new String("" + needs.getHungerCount());
						//TextView feedView = (TextView)findViewById(R.id.)
						//feedView.setText(feed);
						Thread.sleep(1000);
					}catch(Exception e){
						
					}
				}
			}
		});
		
		background.start();
	}

	public static NeedsThread getInstance(){
		if(instance == null){
			return new NeedsThread();
		}else{
			return instance;
		}
	}
}
