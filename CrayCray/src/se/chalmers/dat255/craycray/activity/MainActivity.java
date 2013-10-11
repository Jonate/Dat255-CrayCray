package se.chalmers.dat255.craycray.activity;
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



import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.database.DatabaseAdapter;
import se.chalmers.dat255.craycray.database.DatabaseConstants;
import se.chalmers.dat255.craycray.model.DeadException;
import se.chalmers.dat255.craycray.model.NeedsModel;
import se.chalmers.dat255.craycray.notifications.NotificationSender;
import se.chalmers.dat255.craycray.util.Constants;
import se.chalmers.dat255.craycray.util.TimeUtil;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.AlertDialog; 
import android.content.DialogInterface;




public class MainActivity extends Activity{

	MainActivity main = this;

	private boolean isActive;

	// The buttons of the application
	private ImageButton  feedButton;
	private ImageButton  cuddleButton;
	private ImageButton  cleanButton;
	private ImageButton  energyButton;
	private ImageButton  removePooButton;
	private ImageButton  cureButton;
	
	private ImageButton happypotionButton;
	private ImageButton russianButton;


	// The bars of the application
	private ProgressBar foodBar;
	private ProgressBar cuddleBar;
	private ProgressBar cleanBar;
	private ProgressBar energyBar;
	private ImageView crayView;
	private ImageView pooImage;

	private NeedsModel model;
	private Thread t;

	private final int HUNGER = 1;
	private final int CLEANNESS = 2;
	private final int HAPPINESS = 3;
	private final int ENERGY = 4;

	private boolean cleanability = true;

	private DatabaseAdapter dbA;
	private NotificationSender notifications = new NotificationSender(this);
	

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

				DeadException e = (DeadException)msg.obj;
				announceDeath(e);
				disableInteractions();
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		isActive = true;
		Log.w("russian", "testing testing");

		// Button - variables set to xml ID
		feedButton = (ImageButton) findViewById(R.id.feedButton);
		feedButton.setImageResource(R.drawable.button_food);

		cleanButton = (ImageButton) findViewById(R.id.cleanButton);
		cleanButton.setImageResource(R.drawable.button_clean);

		cuddleButton = (ImageButton) findViewById(R.id.cuddleButton);
		cuddleButton.setImageResource(R.drawable.button_happiness);

		energyButton = (ImageButton) findViewById(R.id.energyButton);
		energyButton.setImageResource(R.drawable.button_energy);

		removePooButton = (ImageButton) findViewById(R.id.removePooButton);
		removePooButton.setImageResource(R.drawable.button_poo);

		cureButton = (ImageButton) findViewById(R.id.cureButton);
		cureButton.setImageResource(R.drawable.button_cure);
<<<<<<< HEAD:CrayCray/src/se/chalmers/dat255/craycray/MainActivity.java
		 
=======
		
		happypotionButton = (ImageButton) findViewById(R.id.happypotionButton);
		
		russianButton = (ImageButton) findViewById(R.id.russianButton);
		

>>>>>>> 74e2a0439b36ece1c18871bf7f56e0d6a2c0b820:CrayCray/src/se/chalmers/dat255/craycray/activity/MainActivity.java
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
		cureButton.setVisibility(ImageView.INVISIBLE);

		// sets the latest values of the progressbars
		foodBar.setProgress(model.getHungerLevel());
		cuddleBar.setProgress(model.getCuddleLevel());
		cleanBar.setProgress(model.getCleanLevel());
		energyBar.setProgress(model.getEnergyLevel());

		if(t == null){
			t = new Thread(new Runnable() {

				@Override
				public void run() {

					while (true) {
						try {
							if(isActive){
								//If MainActivity is active decrease need levels
								//and set CrayCrayexpressions accordingly
								model.setHungerLevel(model.getHungerLevel() - 1);
								model.setCleanLevel(model.getCleanLevel() - 3);
								model.setCuddleLevel(model.getCuddleLevel() - 1);
								model.setPooLevel(model.getPooLevel() - 10);

								setCrayExpression(ENERGY, model.getEnergyLevel());

								if(model.isSleeping()){
									System.out.println("is sleeping in thread" + model.isSleeping());
									model.setEnergyLevel(model.getEnergyLevel() + 5);
									feedButton.setClickable(false);
									cuddleButton.setClickable(false);
									cleanButton.setClickable(false);
									energyButton.setClickable(false);
									removePooButton.setClickable(false);
									cureButton.setClickable(false);

								}else{
									System.out.println("is not in thread" + model.isSleeping());
									model.setEnergyLevel(model.getEnergyLevel() - 1);

									setCrayExpression(CLEANNESS, model.getCleanLevel());
									setCrayExpression(HUNGER, model.getHungerLevel());
									setCrayExpression(HAPPINESS, model.getCuddleLevel());

									feedButton.setClickable(true);
									cuddleButton.setClickable(true);
									cleanButton.setClickable(true);
									energyButton.setClickable(true);
									removePooButton.setClickable(true);
									cureButton.setClickable(true);

								}
							}

<<<<<<< HEAD:CrayCray/src/se/chalmers/dat255/craycray/MainActivity.java
		t = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					try {
						model.setHungerLevel(model.getHungerLevel() - 1);

						model.setCleanLevel(model.getCleanLevel() - 3);
						model.setCuddleLevel(model.getCuddleLevel() - 1);
						model.setPooLevel(model.getPooLevel() - 10);
					
						setCrayExpression(ENERGY, model.getEnergyLevel());
						
						if(model.isSleeping()){
							System.out.println("is sleeping in thread" + model.isSleeping());
							model.setEnergyLevel(model.getEnergyLevel() + 5);
							feedButton.setClickable(false);
							cuddleButton.setClickable(false);
							cleanButton.setClickable(false);
							energyButton.setClickable(false);
							removePooButton.setClickable(false);
							cureButton.setClickable(false);
							
							
						}else{
							System.out.println("is not in thread" + model.isSleeping());
							model.setEnergyLevel(model.getEnergyLevel() - 1);
							
							setCrayExpression(CLEANNESS, model.getCleanLevel());
							setCrayExpression(HUNGER, model.getHungerLevel());
							setCrayExpression(HAPPINESS, model.getCuddleLevel());
							
							feedButton.setClickable(true);
							cuddleButton.setClickable(true);
							cleanButton.setClickable(true);
							energyButton.setClickable(true);
							removePooButton.setClickable(true);
							cureButton.setClickable(true);
							

						}
						

=======
							// check if pooImage should be drawn or not
							cleanButton.setClickable(cleanability);
>>>>>>> 74e2a0439b36ece1c18871bf7f56e0d6a2c0b820:CrayCray/src/se/chalmers/dat255/craycray/activity/MainActivity.java

//							if CrayCray is sick send an ill-notification
//							if(model.isIll()){
//								if(!hasWindowFocus()){
//									notifications.sendIllNotification();
//								}
//							}

							//if CrayCray is dirty send a dirty-notification
							if(model.getCleanLevel()<20){
								if(!hasWindowFocus()){
									notifications.sendDirtyNotification();
								}
							}

							// update the expression of CrayCray
							drawPooImage(model.getPooLevel());

							handler.sendMessage(handler.obtainMessage());

							Thread.sleep(1000);

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
		}



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
		if(!t.isAlive()){
			t.start();
		}

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
		handler.sendMessage(handler.obtainMessage());
		model.setSleep(true);

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

			setCrayExpression(CLEANNESS, model.getCleanLevel());
			setCrayExpression(HUNGER, model.getHungerLevel());
			setCrayExpression(HAPPINESS, model.getCuddleLevel());

		}
	}

	/**
	 * Called when user clicks to play russian roulette
	 * @param view
	 */
	public void playRussianRoulette(View view){
		createRussianAlert().show();
	}
	
	/**
	 * Called when user clicks to drink Happy Potion
	 * @param view
	 */
	public void happyPotion(View view){
		//setDrunkExpression for some period of time
		model.setCuddleLevel(model.getCuddleLevel()+17);
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
	public void setCrayExpression(int mode, int level) {
		int expression;
		switch (mode) {

		case ENERGY:
			if(level >= 100){
				model.setSleep(false);

			}else if (level == 0 || model.isSleeping()) {
				expression = R.drawable.sleeping_baby;
				crayView.setImageResource(expression);
				model.setSleep(true);
			}
			break;

			// check dirtyLvl
		case CLEANNESS:
			if (level < 50) {
				System.out.println("inside case 1 (dirty)" + level);
				expression = R.drawable.dirty_baby;
				crayView.setImageResource(expression);
			}
			// check hungryLvl
		case HUNGER:

			if (level == 0) {
				expression = R.drawable.dead_baby;
				crayView.setImageResource(expression);

			} else if (level < 50) {
				System.out.println("inside case 1 (hungry)" + level);

				expression = R.drawable.feed_baby;
				crayView.setImageResource(expression);

			}
			break;
			// check cuddleLvl
		case HAPPINESS:
			if (level > 70) {
				expression = R.drawable.happy_baby;
				crayView.setImageResource(expression);

			} else if(level < 10){
				expression = R.drawable.crying_baby;
				crayView.setImageResource(expression);
			}
			break;

		default:
			System.out.println("inside base-case" + level);
			expression = R.drawable.regular_baby;
			crayView.setImageResource(expression);
		}
		handler.sendMessage(handler.obtainMessage());
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
<<<<<<< HEAD:CrayCray/src/se/chalmers/dat255/craycray/MainActivity.java
	
	
	
=======

>>>>>>> 74e2a0439b36ece1c18871bf7f56e0d6a2c0b820:CrayCray/src/se/chalmers/dat255/craycray/activity/MainActivity.java
	/**
	 * Creates a pop-up asking if the user really wants to
	 * play Russian Roulette.
	 */
	public AlertDialog.Builder createRussianAlert(){

		isActive = false;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Russian Roulette");
		alertDialog.setMessage("Do you really want to play? No turning back...");
		alertDialog.setPositiveButton("Hell Yeah!",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent rusIntent = new Intent(main, RussianActivity.class);
				startActivityForResult(rusIntent, Constants.RUSSIAN_REQUEST_CODE);
			}
		});
		alertDialog.setNegativeButton("God no!",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				isActive = true;
			}
		});

		return alertDialog;

	}

	/**
	 * Tells the user CrayCray has died, usually by a pop-up. 
	 * If the the program is not active a notification will
	 * be sent instead.
	 */
	public void announceDeath(DeadException e){
		setCrayExpression(2,0);
		if(!hasWindowFocus()){
			notifications.sendDeadNotification();
		} else{
			String message = e.getDeathCause();
			createDeathAlert().setMessage(message).show();
		}
		model.minAllNeeds();
	}
	
	/**
	 * Disables all buttons etc and thereby makes it unable
	 * to interact with CrayCray.
	 */
	public void disableInteractions(){
		feedButton.setClickable(false);
		cuddleButton.setClickable(false);
		cleanButton.setClickable(false);
		energyButton.setClickable(false);
		removePooButton.setClickable(false);
		cureButton.setClickable(false); 
		happypotionButton.setClickable(false);
		russianButton.setClickable(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// Check which request we're responding to
		if (requestCode == Constants.RUSSIAN_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				boolean result = bundle.getBoolean("key");
				if(result == Constants.RUSSIAN_LOOSE){
					Log.w("russian", "result = loose in onactivityres");
					DeadException e = new DeadException(Constants.RUSSIAN_DEATH);
					Message msg = Message.obtain();
					msg.obj = e;
					handler.sendMessage(msg);

					Log.w("russian", "handler message with dedex sent in onactivityresult");
				}
			}
		}
		isActive = true;
	}
<<<<<<< HEAD:CrayCray/src/se/chalmers/dat255/craycray/MainActivity.java
	 
=======


>>>>>>> 74e2a0439b36ece1c18871bf7f56e0d6a2c0b820:CrayCray/src/se/chalmers/dat255/craycray/activity/MainActivity.java
}
