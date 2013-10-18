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
import se.chalmers.dat255.craycray.model.DatabaseException;
import se.chalmers.dat255.craycray.model.NeedsModel;
import se.chalmers.dat255.craycray.notifications.NotificationSender;
import se.chalmers.dat255.craycray.util.Constants;
import se.chalmers.dat255.craycray.util.TimeUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

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
	private ImageButton aboutButton;
	private ImageButton newGameButton;

	// The bars of the application
	private ProgressBar foodBar;
	private ProgressBar cuddleBar;
	private ProgressBar cleanBar;
	private ProgressBar energyBar;
	private ImageView crayView;
	private ImageView pooImage;
	private View fade;
	private Vibrator vib;

	private NeedsModel model;
	private Thread t;

	private final int HUNGER = 1;
	private final int CLEANNESS = 2;
	private final int HAPPINESS = 3;
	private final int ENERGY = 4;
	private final int DRUNK = 5;

	private final int DEAD = 6;

	private final int POO = 1;
	private final int NOPOO = 2;

	private int drunkCount = Constants.MAX_DRUNK_COUNT;

	private boolean cleanability = true;
	private boolean isDrunk = false;

	private boolean hasSentIllNoti = false;
	private boolean hasSentDirtyNoti = false;

	private DatabaseAdapter dbA;
	private NotificationSender notifications = new NotificationSender(this);


	// A Handler to take care of updates in UI-thread
	// When sendMessage method is called, this is where the message is sent
	Handler handler = new Handler() {

		@Override
		public synchronized void handleMessage(Message msg) {
			super.handleMessage(msg);

			// sets/updates the values of the progressbars
			foodBar.setProgress((int)model.getHungerLevel());
			cuddleBar.setProgress((int)model.getCuddleLevel());
			cleanBar.setProgress((int)model.getCleanLevel());
			energyBar.setProgress((int)model.getEnergyLevel());

			// force imageview to update
			crayView.invalidate();

			if (msg.obj instanceof DatabaseException){
				setUpDatabase();

			} 
			if (msg.what == DEAD){
				announceDeath();
				model.minAllNeeds();
			}


		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
<<<<<<< HEAD
		
=======
		vib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

>>>>>>> button_graphics
		setContentView(R.layout.activity_main);
		fade=(View) findViewById(R.id.fade);
		model = NeedsModel.getInstance();
		dbA = DatabaseAdapter.getInstance(getBaseContext());

		isActive = true;

		initUi();
		

		try{
			Log.w("Database", "TRYYY");
			dbA.getValue(DatabaseConstants.HUNGER);
			Log.w("Database", "AFTER GET");
			int differenceInSeconds = TimeUtil.compareTime(dbA
					.getStringValue(DatabaseConstants.TIME));
			Log.w("Database", "DifferenceInSeconds"+ differenceInSeconds+" "+  Constants.THREAD_SLEEP_SEC);
			model.setHungerLevel(dbA.getValue(DatabaseConstants.HUNGER) + differenceInSeconds 
					* Constants.HUNGERLEVELDECREASE * Constants.THREAD_SLEEP_SEC);
			model.setCuddleLevel(dbA.getValue(DatabaseConstants.CUDDLE) + differenceInSeconds 
					* Constants.CUDDLELEVELDECREASE * Constants.THREAD_SLEEP_SEC);
			model.setCleanLevel(dbA.getValue(DatabaseConstants.CLEAN) + differenceInSeconds 
					* Constants.CLEANLEVELDECREASE * Constants.THREAD_SLEEP_SEC);
			model.setPooLevel(dbA.getValue(DatabaseConstants.POO) + differenceInSeconds 
					* Constants.POOLEVELDECREASE * Constants.THREAD_SLEEP_SEC);

			// Checks if CrayCray was healthy or ill at the last shutdown 
			// and give it the same value again.
			if(dbA.getValue(DatabaseConstants.ILL)==0){
				model.setIllness(false);
			}else{
				model.setIllCount(dbA.getValue(DatabaseConstants.ILL_COUNT) - differenceInSeconds
						* Constants.THREAD_SLEEP_SEC);
				model.setIllness(true);
			}

			// Checks if there was poop on the screen at the last shutdown
			// and in that case place the poop again.
			if(dbA.getValue(DatabaseConstants.POOPED)==0){
				model.setHasPooped(false);
			}else{
				model.setHasPooped(true);
			}

			// Checks if CrayCray was awake or not when the app was closed the last time.
			if(dbA.getValue(DatabaseConstants.SLEEPING)==0){
				double energy = dbA.getValue(DatabaseConstants.ENERGY) 
						+ differenceInSeconds * Constants.ENERGYLEVELDECREASE;
				// The energy level is set to 1 if it has reached a value of zero or lower
				// because CrayCray can't automatically go to sleep when the application is 
				// dead. The user needs to be able to feed or cure CrayCray the next time 
				// the app is started before he goes to sleep.
				if(energy <= 0){
					model.setEnergyLevel(1);

				} else {
					model.setEnergyLevel(energy);

				}
			} else {
				double energy = dbA.getValue(DatabaseConstants.ENERGY) 
						+ differenceInSeconds * Constants.ENERGYLEVELINCREASE;
				if(energy >= Constants.NEED_LEVEL_MAX){
					model.setEnergyLevel(Constants.NEED_LEVEL_MAX);
					model.setSleep(false);
				} else {
					model.setEnergyLevel(energy);
					model.setSleep(true);
				}
			}

		} catch(Exception e){
			if(e instanceof DatabaseException){
				Log.w("Database", "EXCEPTION");

				Message msg = Message.obtain();
				msg.obj = e;
				handler.sendMessage(msg);
			}
		}

		
		

		if(t == null){
			t = new Thread(new Runnable() {

				@Override
				public void run() {

					while (true) {
						try {

							if(isActive){

								// check if pooImage should be drawn or not

								model.setHungerLevel(model.getHungerLevel() + Constants.HUNGERLEVELDECREASE);
								model.setCleanLevel(model.getCleanLevel() + Constants.CLEANLEVELDECREASE);
								model.setCuddleLevel(model.getCuddleLevel() + Constants.CUDDLELEVELDECREASE);
								model.setPooLevel(model.getPooLevel() + Constants.POOLEVELDECREASE);

								setCrayExpression(ENERGY, model.getEnergyLevel());

								//Check if user should be able to clean CrayCray
								drawPooImage(model.getPooLevel());
								cleanButton.setClickable(cleanability);

								//deactivate buttons if CrayCray is sleeping
								//increase energy level when sleeping
								if (model.isSleeping()) {
									fade.setAlpha(0.5F);
									fade.invalidate();
									model.setEnergyLevel(model.getEnergyLevel() + Constants.ENERGYLEVELINCREASE);
									activatedButtons(false);

								} else {
									fade.setAlpha(0F);
									fade.invalidate();
									
									model.setEnergyLevel(model.getEnergyLevel() + Constants.ENERGYLEVELDECREASE);
									setCrayExpression(HAPPINESS, model.getCuddleLevel());
									setCrayExpression(HUNGER, model.getHungerLevel());
									setCrayExpression(CLEANNESS, model.getCleanLevel());

									activatedButtons(true);

								}

								// If window does not have focus an ill notification is sent.
								// remove 1 from illCount. 
								// Then checks if the count has reached zero and in that case CrayCray dies.
								if(model.isIll()){
									if(!hasWindowFocus()){
										if(!hasSentIllNoti){
											notifications.sendIllNotification();
											hasSentIllNoti = true;
										}
									}
									model.setIllCount(model.getIllCount() - 1);
								}

								System.out.println("PRINT IN THREAD:");
								System.out.println("Hunger" + model.getHungerLevel());
								System.out.println("Clean" + model.getCleanLevel());
								System.out.println("Cuddle" + model.getCuddleLevel());
								System.out.println("Energy" + model.getEnergyLevel());
								System.out.println("Poo" + model.getPooLevel());
								System.out.println("Ill" + model.getIllCount());

								// if CrayCray is dirty send a dirty-notification
								if (model.getCleanLevel() < 50) {
									if(!hasSentDirtyNoti){
										if (!hasWindowFocus()) {
											notifications.sendDirtyNotification();
											hasSentDirtyNoti = true;
										}
									}
								}

								//if CrayCray is drunk show drunkpicture
								if(isDrunk){
									setCrayExpression(DRUNK, 0);
									setDrunkCount(drunkCount -1);
								}
								//when drunkCount is 0 decide what picture to show and reset
								if(drunkCount == 0){
									isDrunk = false;
									model.setEnergyLevel(model.getEnergyLevel() + Constants.ENERGYLEVELDECREASE );
									setCrayExpression(HAPPINESS, model.getCuddleLevel());
									setCrayExpression(HUNGER, model.getHungerLevel());
									setCrayExpression(CLEANNESS, model.getCleanLevel());
									drunkCount = Constants.MAX_DRUNK_COUNT;
								}

								if(!model.isAlive()){
									Message msg = Message.obtain();
									msg.what = DEAD;
									handler.sendMessage(msg);
									break;
								}

								handler.sendMessage(handler.obtainMessage());

								Thread.sleep(Constants.THREAD_SLEEP);
							}

						} catch (Exception e) {

						}
					}
				}
			}
					);
		}	

		t.start();
	}

	private void initUi() {

		// Button - variables set to xml ID
		russianButton = (ImageButton) findViewById(R.id.russianButton);
		happypotionButton = (ImageButton) findViewById(R.id.happypotionButton);
		cureButton = (ImageButton) findViewById(R.id.cureButton);
		removePooButton = (ImageButton) findViewById(R.id.removePooButton);
		energyButton = (ImageButton) findViewById(R.id.energyButton);
		cuddleButton = (ImageButton) findViewById(R.id.cuddleButton);
		cleanButton = (ImageButton) findViewById(R.id.cleanButton);
		feedButton = (ImageButton) findViewById(R.id.feedButton);
		aboutButton = (ImageButton) findViewById(R.id.aboutButton);
		newGameButton = (ImageButton)findViewById(R.id.newGameButton);

		//Bar - variables set to xml ID
		foodBar = (ProgressBar) findViewById(R.id.foodBar);
		cuddleBar = (ProgressBar) findViewById(R.id.cuddleBar);
		cleanBar = (ProgressBar) findViewById(R.id.cleanBar);
		energyBar = (ProgressBar) findViewById(R.id.energyBar);
		crayView = (ImageView) findViewById(R.id.crayCray);

		// Sets the color of the progressbar
		foodBar.getProgressDrawable().setColorFilter(
				Color.parseColor("#33FF99"), Mode.MULTIPLY);
		cuddleBar.getProgressDrawable().setColorFilter(
				Color.parseColor("#FF3366"), Mode.MULTIPLY);
		cleanBar.getProgressDrawable().setColorFilter(
				Color.parseColor("#66FFFF"), Mode.MULTIPLY);
		energyBar.getProgressDrawable().setColorFilter(
				Color.parseColor("#FFFF66"), Mode.MULTIPLY);
		
		// sets the latest values of the progressbars
		foodBar.setProgress((int)model.getHungerLevel());
		cuddleBar.setProgress((int)model.getCuddleLevel());
		cleanBar.setProgress((int)model.getCleanLevel());
		energyBar.setProgress((int)model.getEnergyLevel());
	}


	@Override
	public void onStart() {
		super.onStart();
		if(!t.isAlive()){
			t.run();
		}
	}


	/**
	 * Updates the database if the application is shut down
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.w("Database", "DESTROY!!!!!");
		try{
			dbA.updateValue(DatabaseConstants.HUNGER, model.getHungerLevel());
			dbA.updateValue(DatabaseConstants.CUDDLE, model.getCuddleLevel());
			dbA.updateValue(DatabaseConstants.CLEAN, model.getCleanLevel());
			dbA.updateValue(DatabaseConstants.POO, model.getPooLevel());
			dbA.updateValue(DatabaseConstants.ENERGY, model.getEnergyLevel());
			dbA.updateValue(DatabaseConstants.ILL_COUNT, model.getIllCount());
			dbA.updateStringValue(DatabaseConstants.TIME, TimeUtil.getCurrentTime());

			// if the boolean values are true their value in the database will be 1
			// if not, the value will be 0
			if(model.isSleeping()){
				dbA.updateValue(DatabaseConstants.SLEEPING, 1);
			} else{
				dbA.updateValue(DatabaseConstants.SLEEPING, 0);
			}

			if(model.isIll()){
				dbA.updateValue(DatabaseConstants.ILL, 1);
			} else{
				dbA.updateValue(DatabaseConstants.ILL, 0);
			}

			if(model.hasPooped()){
				dbA.updateValue(DatabaseConstants.POOPED, 1);
			} else{
				dbA.updateValue(DatabaseConstants.POOPED, 0);
			}
		} catch(Exception e){
			setUpDatabase();
		}
		Log.w("Database","END DESTROY");
	}

	/**
	 * increases hungerlevel by constant HUNGERLEVELINCREASE
	 */
	public synchronized void feed(View view) {
		vib.vibrate(50);

		model.setHungerLevel(model.getHungerLevel() + Constants.HUNGERLEVELINCREASE);

		if (model.getHungerLevel() > 50) {
			setCrayExpression(-1, -1);
		}
		handler.sendMessage(handler.obtainMessage());
	}

	/**
	 * increases cleanlevel by constant CLEANLEVELINCREASE
	 */
	public synchronized void clean(View view) {
		vib.vibrate(50);
		if (cleanability) {
			model.setCleanLevel(model.getCleanLevel() + Constants.CLEANLEVELINCREASE);
			if (model.getCleanLevel() > 50) {
				setCrayExpression(-1, -1);
				hasSentDirtyNoti = false;
			}

			handler.sendMessage(handler.obtainMessage());
		}
	}

	/**
	 * increases cuddlelevel by constant CUDDLELVELINCREASE
	 */
	public synchronized void cuddle(View view) {
<<<<<<< HEAD

		model.setCuddleLevel(model.getCuddleLevel() + Constants.CUDDLELEVELINCREASE);
=======
		vib.vibrate(50);
		model.setCuddleLevel(model.getCuddleLevel() + 7);
>>>>>>> button_graphics
		handler.sendMessage(handler.obtainMessage());

	}

	/**
	 * increases energylevel
	 */
	public synchronized void sleep(View view) {
		vib.vibrate(50);
		model.setSleep(true);

	}

	/**
	 * removes poo from screen and increses poolevel by 100
	 * 
	 * @param view
	 */
	public synchronized void removePoo(View view) {
		vib.vibrate(50);
		if (model.hasPooped()) {
			model.setPooLevel(Constants.NEED_LEVEL_MAX);
			drawPooImage(model.getPooLevel());
			cleanability = true;
			model.setHasPooped(false);
		}
		handler.sendMessage(handler.obtainMessage());

	}

	/**
	 * cures the pet if it is ill
	 * @param view
	 */
	public synchronized void cure(View view) {
		vib.vibrate(50);
		if (model.isIll()) {
			cleanability = true;
			model.setIllness(false);
			hasSentIllNoti = false;
			model.setIllCount(Constants.ILL_COUNT);
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
		vib.vibrate(50);
		createRussianAlert().show();
	}

	/**
	 * Called when user clicks to drink Happy Potion
	 * @param view
	 */
	public void happyPotion(View view){
		vib.vibrate(50);
		//setDrunkExpression for some period of time
		isDrunk = true;
		model.setCuddleLevel(model.getCuddleLevel()+ Constants.CUDDLELEVELINCREASE*10);
	}

	private void setDrunkCount(int count){
		drunkCount = count;
	}

	/**
	 * Displays the instructions-pop up 
	 */
	public void howToPlay(View view) {
		createInstructionsAlert().show();
	}

	/**
	 * Displays the new game-pop up 
	 */
	public void newGame(View view){
		createNewGameAlert().show();
	}


	/**
	 * Check if pooImage should be drawn or not
	 * 
	 * @param level
	 */
	public synchronized void drawPooImage(double level) {
		pooImage = (ImageView) findViewById(R.id.pooImage);
		if (level <= 100 && level > 50) {
			setPoo(NOPOO);
			handler.sendMessage(handler.obtainMessage());
		} else if ((level <= 50 && level >= 20) && (!model.hasPooped())) {
			setPoo(POO);
			cleanability = false;
			model.setHasPooped(true);
			handler.sendMessage(handler.obtainMessage());
		} else if (level < 20) {
			model.setCleanLevel(model.getCleanLevel() + Constants.CLEANLEVELDECREASE);
		}
	}

	/**
	 * set image of poo or an "invisible" picture to visualize removing the
	 * poopicture
	 * 
	 * @param pooOrNot
	 */
	public synchronized void setPoo(int pooOrNot) {
		int image;
		switch (pooOrNot) {

		case POO:
			image = R.drawable.poo;
			pooImage.setImageResource(image);
			break;

		case NOPOO:
			image = R.drawable.invisible;
			pooImage.setImageResource(image);
			break;

		default:
			image = R.drawable.invisible;
			pooImage.setImageResource(image);
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
	public synchronized void setCrayExpression(int mode, double level) {

		switch (mode) {
		case ENERGY:
			if(level >= 100){
				model.setSleep(false);

			}else if (level == 0 || model.isSleeping()) {
				crayView.setImageResource(R.drawable.sleeping_baby);
				model.setSleep(true);
			}
			break;

			// check dirtyLvl
		case CLEANNESS:
			if (level >20 && level < 50) {
				crayView.setImageResource(R.drawable.dirty_baby);
			}
			if (level <= 20) {
				System.out.println("baby sick");
				crayView.setImageResource(R.drawable.sick_baby);
				model.setIllness(true);
			}
			break;

			// check hungryLvl
		case HUNGER:
			if (level == 0) {
				crayView.setImageResource(R.drawable.dead_baby);

			} else if (model.isIll()) {
				crayView.setImageResource(R.drawable.sick_baby);

			} else if (level < 50) {
				System.out.println("inside case 1 (hungry)" + level);
				crayView.setImageResource(R.drawable.feed_baby);

			}
			break;

			// check cuddleLvl
		case HAPPINESS:
			if (level > 70) {
				crayView.setImageResource(R.drawable.happy_baby);

			} else if(level < 10){
				crayView.setImageResource(R.drawable.crying_baby);
			}else{
				crayView.setImageResource(R.drawable.regular_baby);
			}
			break;

		case DRUNK:
			crayView.setImageResource(R.drawable.wasted_baby);
			break;

		default:
			System.out.println("inside base-case" + level);
			crayView.setImageResource(R.drawable.regular_baby);
		}
		handler.sendMessage(handler.obtainMessage());

	}	


	/**
	 * Creates a pop-up with a death announcement
	 */
	public AlertDialog.Builder createDeathAlert() {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Game Over");
		alertDialog.setPositiveButton("New Game",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				isActive = true;
				model.maxAllNeeds();
				dbA.resetDatabase();
				Intent newGame = new Intent(main, MainActivity.class);
				startActivity(newGame);
				finish();
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
	 * Creates a pop-up with instructions about how to play
	 */
	public AlertDialog.Builder createInstructionsAlert(){

		isActive = false;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("How to play");
		alertDialog.setMessage("CrayCray is happier alive. You'd better push the buttons and let it know!");
		alertDialog.setNeutralButton("Ok",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				isActive = true;
			}
		});

		return alertDialog;

	}

	/**
	 * Creates a pop-up asking if the user really wants to
	 * start a new game.
	 */
	public AlertDialog.Builder createNewGameAlert(){
		isActive = false;
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("New Game");
		alertDialog.setMessage("Do you really want to start a new game?");
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				isActive = true;
				model.maxAllNeeds();
				dbA.resetDatabase();
				Intent newGame = new Intent(main, MainActivity.class);
				startActivity(newGame);
				finish();

			}
		});
		alertDialog.setNegativeButton("No!", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				isActive = true;
			}
		});

		return alertDialog;
	}

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
	public void announceDeath() {
		setCrayExpression(HUNGER, 0);
		String message = model.getDeathCause();

		if(message == Constants.RUSSIAN_DEATH){
			createDeathAlert().setMessage(message).show();

		}else{
			if (!hasWindowFocus()) {
				notifications.sendDeadNotification();
				createDeathAlert().setMessage(message).show();
			}else {
				createDeathAlert().setMessage(message).show();
			}
		}
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// Check which request we're responding to
		if (requestCode == Constants.RUSSIAN_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				//				if(!model.isAlive()){
				//					Message msg = Message.obtain();
				//					msg.what = DEAD;
				//					handler.sendMessage(msg);
				//				}
				isActive = true;
			}
		}
	}

	public void setUpDatabase(){
		try{
			dbA.addValue(DatabaseConstants.HUNGER, model.getHungerLevel());
			dbA.addValue(DatabaseConstants.CUDDLE, model.getCuddleLevel());
			dbA.addValue(DatabaseConstants.POO, model.getPooLevel());
			dbA.addValue(DatabaseConstants.CLEAN, model.getCleanLevel());
			dbA.addValue(DatabaseConstants.ENERGY, model.getEnergyLevel());
			dbA.addValue(DatabaseConstants.ILL_COUNT, model.getIllCount());
			dbA.addStringValue(DatabaseConstants.TIME,
					TimeUtil.getCurrentTime());

			// if the boolean values are true their value in the database will be 1
			// if not, the value will be 0
			if(model.isSleeping()){
				dbA.addValue(DatabaseConstants.SLEEPING, 1);
			} else{
				dbA.addValue(DatabaseConstants.SLEEPING, 0);
			}

			if(model.isIll()){
				dbA.addValue(DatabaseConstants.ILL, 1);
			} else{
				dbA.addValue(DatabaseConstants.ILL, 0);
			}

			if(model.hasPooped()){
				dbA.addValue(DatabaseConstants.POOPED, 1);
			} else{
				dbA.addValue(DatabaseConstants.POOPED, 0);
			}
		} catch(Exception e){
			return;
		}
	}

	public synchronized void activatedButtons(boolean state){
		feedButton.setClickable(state);
		cuddleButton.setClickable(state);
		cleanButton.setClickable(state);
		energyButton.setClickable(state);
		removePooButton.setClickable(state);
		cureButton.setClickable(state);
		happypotionButton.setClickable(state);
		russianButton.setClickable(state);
	}

}

