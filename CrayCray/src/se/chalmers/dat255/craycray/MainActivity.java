package se.chalmers.dat255.craycray;
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
import android.os.Looper;
import android.os.Message;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;




public class MainActivity extends Activity{

	// The buttons of the application
	private Button feedButton;
	private Button cuddleButton;
	private Button cleanButton;
	private Button energyButton;
	private Button removePooButton;

	// The bars of the application
	private ProgressBar foodBar;
	private ProgressBar cuddleBar;
	private ProgressBar cleanBar;
	private ProgressBar energyBar;
	private ImageView crayView;

	private ImageView pooImage;
	// private String deathCause; on�dig?

	private NeedsModel model;
	private Thread t;
	
	private final int HUNGER = 1;
	private final int CLEANNESS = 2;
	private final int HAPPINESS = 3;
	private final int ENERGY = 4;

	private boolean cleanability = true;

	private DatabaseAdapter dbA;

	NotificationSender notifications = new NotificationSender(this);

	// A Handler to take care of updates in UI-thread
	// When sendMessage method is called, this is where the message is sent
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			// sets/updates the values of the progressbars
			foodBar.setProgress(model.getHungerLevel());
			cuddleBar.setProgress(model.getCuddleLevel());
			cleanBar.setProgress(model.getCleanLevel());
			energyBar.setProgress(model.getEnergyLevel());

			//force imageview to update
			crayView.invalidate();

			if(msg.obj instanceof DeadException){
				announceDeath();

			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Button - variables set to xml ID
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

		final ImageButton cureButton = (ImageButton) findViewById(R.id.cureButton);
		cureButton.setImageResource(R.drawable.button_cure);

		//Bar - variables set to xml ID
		foodBar = (ProgressBar) findViewById(R.id.foodBar);
		cuddleBar = (ProgressBar) findViewById(R.id.cuddleBar);
		cleanBar = (ProgressBar) findViewById(R.id.cleanBar);
		energyBar = (ProgressBar) findViewById(R.id.energyBar);
		crayView = (ImageView) findViewById(R.id.crayCray);
	
		//Sets the color of the progressbar
		foodBar.getProgressDrawable().setColorFilter(Color.parseColor("#33FF99"), Mode.MULTIPLY);
		cuddleBar.getProgressDrawable().setColorFilter(Color.parseColor("#FF3366"), Mode.MULTIPLY);
		cleanBar.getProgressDrawable().setColorFilter(Color.parseColor("#66FFFF"), Mode.MULTIPLY);
		energyBar.getProgressDrawable().setColorFilter(Color.parseColor("#FFFF66"), Mode.MULTIPLY);

		model = NeedsModel.getInstance();

		// sets the latest values of the progressbars
		foodBar.setProgress(model.getHungerLevel());
		cuddleBar.setProgress(model.getCuddleLevel());
		cleanBar.setProgress(model.getCleanLevel());
		energyBar.setProgress(model.getEnergyLevel());


		t = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					try {
						model.setHungerLevel(model.getHungerLevel() - 1);
						model.setCleanLevel(model.getCleanLevel() - 3);
						model.setCuddleLevel(model.getCuddleLevel() - 1);
						model.setPooLevel(model.getPooLevel() - 10);
						model.setEnergyLevel(model.getEnergyLevel() - 1);

						// check if pooImage should be drawn or not
						cleanButton.setClickable(cleanability);
						
						System.out.println("Hunger" +model.getHungerLevel());
						System.out.println("Clean" + model.getCleanLevel());
						System.out.println("Cuddle" + model.getCuddleLevel());
						System.out.println("Energy" + model.getEnergyLevel());
						
						
						// update the expression of CrayCray
						setCrayExpression(CLEANNESS, model.getCleanLevel());
						setCrayExpression(HUNGER, model.getHungerLevel());
//						setCrayExpression(HAPPINESS, model.getCuddleLevel());
//						setCrayExpression(ENERGY, model.getEnergyLevel());
						drawPooImage(model.getPooLevel());
						// if he is dirty send a dirty-notification
						if (model.getCleanLevel() == 0) {
							notifications.sendDirtyNotification();
						}

						handler.sendMessage(handler.obtainMessage());

						Thread.sleep(2000);

					} catch (Exception e) {
						if (e instanceof DeadException) {
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
		super.onDestroy();
		dbA.updateValue(DatabaseConstants.HUNGER, model.getHungerLevel());
		dbA.updateValue(DatabaseConstants.CUDDLE, model.getCuddleLevel());
		dbA.updateValue(DatabaseConstants.CLEAN, model.getCleanLevel());
		dbA.updateValue(DatabaseConstants.POO, model.getPooLevel());
		dbA.updateStringValue(DatabaseConstants.TIME, TimeUtil.getCurrentTime());
	}
	
	/**
	 * increases hungerlevel by 5
	 */
	public void feed(View view) {
		try {
			model.setHungerLevel(model.getHungerLevel() + 5);

		} catch (DeadException e) {
			// handled elsewhere?
		}
		if (model.getHungerLevel() > 50) {
			setCrayExpression(-1, -1);
		}
		handler.sendMessage(handler.obtainMessage());
	}

	/**
	 * increases cleanlevel by 10
	 */
	public void clean(View view) {
		if (cleanability) {
			model.setCleanLevel(model.getCleanLevel() + 10);
			if (model.getCleanLevel() > 50) {
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
	 * removes poo from screen and increses poolevel by 50
	 * 
	 * @param view
	 */
	public void removePoo(View view) {
		model.setPooLevel(100);
		drawPooImage(model.getPooLevel());
		cleanability = true;
		handler.sendMessage(handler.obtainMessage());

	}

	public void cure(View view) {
		if (model.isIll()) {
			cleanability=true;
			model.setIllness(false);
			handler.sendMessage(handler.obtainMessage());
		}
	}
	public void makeIll(){
		int illImage;
		if(model.isIll()){
			illImage = R.drawable.sick_baby;
			crayView.setImageResource(illImage);
			handler.sendMessage(handler.obtainMessage());
		}
	}
	/**
	 * Check if pooImage should be drawn or not
	 * @param level
	 */
	public void drawPooImage(int level){
		pooImage= (ImageView) findViewById(R.id.pooImage);
		if(level <= 100 && level > 50){
			setPoo(2);
			handler.sendMessage(handler.obtainMessage());
		}else if(level <= 50 && level>=20){
			setPoo(1);
			cleanability = false;
			handler.sendMessage(handler.obtainMessage());
		}else if(level < 20){
			model.setCleanLevel(model.getCleanLevel() -5);
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
	public synchronized void setCrayExpression(int mode, int level) {
		int expression;
		switch (mode) {

		// check dirtyLvl
		case CLEANNESS:
			if (level < 50) {
				System.out.println("inside case 1 (dirty)" + level);
				expression = R.drawable.dirty_baby;
			}
			if(level < 20){
				makeIll();
				System.out.println("makeIll has been runned" + level);
			}
		// check hungryLvl
		case HUNGER:

			if (level == 0) {
				expression = R.drawable.dead_baby;
				crayView.setImageResource(expression);
				System.out.println("cray starved to death");
			} else if (level < 50) {
				System.out.println("inside case 1 (hungry)" + level);

				expression = R.drawable.feed_baby;
				crayView.setImageResource(expression);

			}
			break;
//			// check hungryLvl
//			case HAPPINESS:
//				if (level == 0) {
//					expression = R.drawable.dead_baby;
//					crayCray.setImageResource(expression);
//
//				} else if (level < 50) {
//					System.out.println("inside case 1 (hungry)" + level);
//					expression = R.drawable.feed_baby; 
//					crayCray.setImageResource(expression);
//
//				}
//				break;
		default:
			System.out.println("inside base-case" + level);
			expression = R.drawable.regular_baby;
			crayView.setImageResource(expression);
		}
	}	
	

	
	/**
	 * set image of poo or an "invisible" picture 
	 * to visualize removing the poopicture
	 * @param pooOrNot
	 */
	public void setPoo(int pooOrNot){
		int image;
		switch (pooOrNot) {

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

	/**
	 * Creates a pop-up with a death announcement
	 */
	public AlertDialog.Builder createDeathAlert(){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Game Over");
		alertDialog.setPositiveButton("New Game",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		
		return alertDialog;
		
	}
	
	/**
	 * Checks if the window is in focus,
	 * if the window is in focus a pop-up with a death announcement shows up
	 * if the window is not in focus a notification with a death announcement shows up
	 */
	public void announceDeath(){
		setCrayExpression(2,0);
		if(!hasWindowFocus()){
			notifications.sendDeadNotification();
		} else{
			String message = model.getDeathCause();
			createDeathAlert().setMessage(message).show();
		}
	}

}
