/*
 * CrayCray - A game formed as a pet in you android device for the user to take care of.
 *
 * Copyright (C) 2013  Sofia Edstrom, Emma Gustafsson, Patricia Paulsson, 
 * Josefin Ondrus, Hanna Materne, Elin Ljunggren & Jonathan Thunberg.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/.
 * 
 * To contact the creators of this program, please make use of any of the email addresses below.
 * hanna.materne@gmail.com, elin.l.ljunggren@gmail.com, sofia.edstrom@galaxen.se
 * chorriballong@gmail.com, jonathan.thunberg@gmail.com, emma.i.gustafsson@gmail.com,
 * josefin.ondrus@gmail.com
 *   
 */

package se.chalmers.dat255.craycray;

import se.chalmers.dat255.craycray.database.DatabaseAdapter;
import se.chalmers.dat255.craycray.database.DatabaseConstants;



import se.chalmers.dat255.craycray.model.DeadException;
import se.chalmers.dat255.craycray.model.NeedsModel;
import se.chalmers.dat255.craycray.notifications.NotificationSender;
import se.chalmers.dat255.craycray.util.TimeUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	//The buttons of the application
	private Button feedButton;
	private Button cuddleButton;
	private Button cleanButton;
	private Button energyButton;
	private Button removePooButton;

	//The bars of the application
	private ProgressBar foodBar;
	private ProgressBar cuddleBar;
	private ProgressBar cleanBar;
	private ProgressBar energyBar;

	private ImageView crayCray;

	private ImageView pooImage;
	// private String deathCause; on�dig?


	private NeedsModel model;
	private boolean isDead = false;
	private Thread t;
	private AlertDialog.Builder alertDialog;
	
	private boolean cleanability = true;
	
	private DatabaseAdapter dbA;

	NotificationSender notifications = new NotificationSender(this);

	// A Handler to take care of updates in UI-thread
	// When sendMessage method is called, this is where the message is sent
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			//sets/updates the values of the progressbars
			foodBar.setProgress(model.getHungerLevel());
			cuddleBar.setProgress(model.getCuddleLevel());
			cleanBar.setProgress(model.getCleanLevel());
			energyBar.setProgress(model.getEnergyLevel());

			//force imageview to update
			crayCray.invalidate();
			
			if (msg.obj instanceof DeadException) {
				DeadException exception = (DeadException) msg.obj;
				isDead = true;
//				String message = exception.getDeathCause();
//				alertDialog.setMessage(message);
//				alertDialog.show();
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Button - variables set to xml ID
		final ImageButton feedButton = (ImageButton) findViewById(R.id.feedButton);
		feedButton.setImageResource(R.drawable.button_food);

		final ImageButton cleanButton = (ImageButton) findViewById(R.id.cleanButton);
		cleanButton.setImageResource(R.drawable.button_clean);

		final ImageButton cuddleButton = (ImageButton) findViewById(R.id.cuddleButton);
		cuddleButton.setImageResource(R.drawable.button_happiness);

		final ImageButton energyButton = (ImageButton) findViewById(R.id.energyButton);
		energyButton.setImageResource(R.drawable.button_energy);

		final ImageButton removePooButton = (ImageButton) findViewById(R.id.removePooButton);
		removePooButton.setImageResource(R.drawable.button_poo);
		
		//Bar - variables set to xml ID
		foodBar = (ProgressBar) findViewById(R.id.foodBar);
		cuddleBar = (ProgressBar) findViewById(R.id.cuddleBar);
		cleanBar = (ProgressBar) findViewById(R.id.cleanBar);
		energyBar = (ProgressBar) findViewById(R.id.energyBar);
		crayCray = (ImageView) findViewById(R.id.crayCray);
		
		model = NeedsModel.getInstance();

		//sets the latest values of the progressbars
		foodBar.setProgress(model.getHungerLevel());
		cuddleBar.setProgress(model.getCuddleLevel());
		cleanBar.setProgress(model.getCleanLevel());
		energyBar.setProgress(model.getEnergyLevel());
		
//		alertDialog = new AlertDialog.Builder(this);
//		alertDialog.setTitle("Game Over");
//		alertDialog.setPositiveButton("New Game",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//
//					}
//				});
//		alertDialog.setNegativeButton("Cancel",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//
//					}
//				});

		t = new Thread(new Runnable() {

			@Override
			public void run() {
				
				while (true) {
					try {
						model.setHungerLevel(model.getHungerLevel()-1);
						model.setCleanLevel(model.getCleanLevel() - 3);
						model.setCuddleLevel(model.getCuddleLevel() - 1);
						model.setPooLevel(model.getPooLevel() -10);
						model.setEnergyLevel(model.getEnergyLevel() - 1);
						
						//check if pooImage should be drawn or not
						drawPooImage(model.getPooLevel());
						
						cleanButton.setClickable(cleanability);
						
						//update the expression of CrayCray
						setCrayExpression(1, model.getCleanLevel());
						setCrayExpression(2, model.getHungerLevel());
						
						//if he is dirty send a dirty-notification
						if(model.getCleanLevel()==0){
							notifications.sendDirtyNotification();
						}
						
						handler.sendMessage(handler.obtainMessage());

						Thread.sleep(2000);

					} catch (Exception e) {
						if (e instanceof DeadException) {
							setCrayExpression(2,0);
							notifications.sendDeadNotification();
							Message msg = Message.obtain();
							msg.obj = e;
							handler.sendMessage(msg);
							break;
						}
					}

				}
			}

		});

		dbA = new DatabaseAdapter(getBaseContext());
		// checks if the database exists
		if (dbA.getValue("Firsttime") == -1) {
			dbA.addValue("Firsttime", 1);
			dbA.addValue(DatabaseConstants.HUNGER, model.getHungerLevel());
			dbA.addValue(DatabaseConstants.CUDDLE, model.getCuddleLevel());
			dbA.addValue(DatabaseConstants.POO, model.getPooLevel());
			dbA.addValue(DatabaseConstants.CLEAN, model.getCleanLevel());
			dbA.addStringValue(DatabaseConstants.TIME,
					TimeUtil.getCurrentTime());
		} else {
			int differenceInSeconds = TimeUtil.compareTime(dbA
					.getStringValue(DatabaseConstants.TIME));
			Log.w("Database",
					differenceInSeconds + ", "
							+ dbA.getValue(DatabaseConstants.HUNGER));
			try {
				model.setHungerLevel(dbA.getValue(DatabaseConstants.HUNGER)
						+ differenceInSeconds * (-1));
				model.setCuddleLevel(dbA.getValue(DatabaseConstants.CUDDLE)
						+ differenceInSeconds * (-3));
				model.setCleanLevel(dbA.getValue(DatabaseConstants.CLEAN)
						+ differenceInSeconds * (-2));
				model.setPooLevel(dbA.getValue(DatabaseConstants.POO));
			} catch (DeadException e) {
				if (e instanceof DeadException) {
					setCrayExpression(2,0);
					notifications.sendDeadNotification();
					Message msg = Message.obtain();
					msg.obj = e;
					handler.sendMessage(msg);
				}
			}

		}

		foodBar.setProgress(model.getHungerLevel());
		cuddleBar.setProgress(model.getCuddleLevel());
		cleanBar.setProgress(model.getCleanLevel());
		energyBar.setProgress(model.getEnergyLevel());
	}

	@Override
	public void onStart() {
		super.onStart();
		t.start();

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
	public void onDestroy() {
		dbA.updateValue(DatabaseConstants.HUNGER, model.getHungerLevel());
		dbA.updateValue(DatabaseConstants.CUDDLE, model.getCuddleLevel());
		dbA.updateValue(DatabaseConstants.CLEAN, model.getCleanLevel());
		dbA.updateValue(DatabaseConstants.POO, model.getPooLevel());
		dbA.updateStringValue(DatabaseConstants.TIME, TimeUtil.getCurrentTime());
		super.onDestroy();
		//varf�r superanropet sist, ska det inte vara f�rst?
	}

	/**
	 * increases hungerlevel by 5
	 */
	public void feed(View view) {
		try {
			model.setHungerLevel(model.getHungerLevel() + 5);
			
		} catch (DeadException e) {
			//handled elsewhere?
		}
		if(model.getHungerLevel()>50){
			setCrayExpression(-1, -1);
		}
		handler.sendMessage(handler.obtainMessage());
	
//		String feed = new String("" + model.getHungerLevel());
	}

	/**
	 * increases cleanlevel by 10
	 */
	public void clean(View view) {
		if(cleanability){
			model.setCleanLevel(model.getCleanLevel() + 10);
			if(model.getCleanLevel()>50){
				setCrayExpression(-1, -1);
			}
		
		handler.sendMessage(handler.obtainMessage());
		}
	}
	
	/**
	 * increases cuddlelevel by 7
	 */
	public void cuddle(View view) {

		model.setCuddleLevel(model.getCuddleLevel() + 7);
		handler.sendMessage(handler.obtainMessage());

	}

	/**
	 * increases energylevel by 50
	 */
	public void sleep(View view) {
		model.setEnergyLevel(model.getEnergyLevel() + 50);
		handler.sendMessage(handler.obtainMessage());

	}

	/**
	 * removes poo from screen 
	 * and increses poolevel by 50
	 * @param view
	 */
	public void removePoo(View view) {
		model.setPooLevel(model.getPooLevel() + 50);
		drawPooImage(model.getPooLevel());
		cleanability = true;
		handler.sendMessage(handler.obtainMessage());

	}

	public void cure(View view) {
		model.setIllness(false);
		handler.sendMessage(handler.obtainMessage());

	}
	
	
	/**
	 * Check if pooImage should be drawn or not.
	 * @param level
	 */
	public void drawPooImage(int level){

		pooImage = (ImageView) findViewById(R.id.pooImage);
		if(level <= 100 && level > 50 ){
			setPoo(2);
			handler.sendMessage(handler.obtainMessage());
		}else if(level<=50 && level >= 20){
			System.out.println("Pooped!!!!!");
			setPoo(1);
			cleanability = false;
			handler.sendMessage(handler.obtainMessage());
		}else if(level < 20){
			model.setCleanLevel(model.getCleanLevel() - 5);
		}
		
	}
	

	
	/**
	 * set correct image of craycray based on the different levels.
	 * 
	 * @param mode
	 *            which level to check
	 * @param level
	 *            the value of the level
	 */
	public void setCrayExpression(int mode, int level) {
		int expression;
		switch (mode) {

		// check dirtyLvl
		case 1:
			if (level < 50) {
				System.out.println("inside case 1 (dirty)" + level);
				expression = R.drawable.dirty_baby;
				crayCray.setImageResource(expression);
				
			}if( level <= 20){
				expression = R.drawable.sick_baby;		//If CrayCray has too low cleanLevel he will
				crayCray.setImageResource(expression); 	// become sick and show a pic of a sick CrayCray
				model.setIllness(true);
			}
			break;
		// check hungryLvl
		case 2:
			if (level == 0) {
				expression = R.drawable.dead_baby;
				crayCray.setImageResource(expression);

			} else if (level < 50) {
				System.out.println("inside case 1 (hungry)" + level);
				expression = R.drawable.feed_baby;
				crayCray.setImageResource(expression);

			}
			break;
		default:
			System.out.println("inside base-case" + level);
			expression = R.drawable.regular_baby;
			crayCray.setImageResource(expression);
		}
	}
	public void setPoo(int pooOrNot){
		int image;
		switch (pooOrNot){
		
		case 1:
			image = R.drawable.poo;
			pooImage.setImageResource(image);
			break;
		
		case 2:
			image = R.drawable.invisible;
			pooImage.setImageResource(image);
			break;
		
		default:
			image = R.drawable.invisible;
			pooImage.setImageResource(image);
		}
	}

}
