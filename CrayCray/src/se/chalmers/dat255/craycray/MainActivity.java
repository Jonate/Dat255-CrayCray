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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {


	private TextView feedView;
	private TextView cuddleView;
	private TextView cleanView;

	private NeedsModel model;
	private Thread t;
	private AlertDialog.Builder alertDialog;

	private DatabaseAdapter dbA;
	NotificationSender notifications = new NotificationSender(this);

	//A Handler to take care of updates in UI-thread
	//When sendMessage method is called, this is where the message is sent
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);

			if(msg.obj instanceof DeadException){
				DeadException exception = (DeadException)msg.obj;
				String message = exception.getDeathCause();
				alertDialog.setMessage(message);
				alertDialog.show();
			}



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

			alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("Game Over");
			alertDialog.setPositiveButton("New Game", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){

				}
			});
			alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int id){

				}
			});



			t = new Thread(new Runnable(){

				@Override
				public void run(){

					while(true){
						try{
							model.setHungerLevel(model.getHungerLevel()-1);
							model.setCleanLevel(model.getCleanLevel() - 3);
							model.setCuddleLevel(model.getCuddleLevel() - 2);
							model.setPooLevel(model.getPooLevel() - 9);

							if(model.hasPooed()){
								//show poo on the screen, unimplemented
							}
							if(model.isIll()){
								//show a ill craycray, unimplemented
							}
							handler.sendMessage(handler.obtainMessage());
							Thread.sleep(100);
						}catch(Exception e){
							if(e instanceof DeadException){
								Message msg = Message.obtain();
								msg.obj = e;
								handler.sendMessage(msg);
								break;
							}
						}
					}
				}
			});


			dbA= new DatabaseAdapter(getBaseContext());
			//checks if the database exists
			if(dbA.getValue("Firsttime")==-1){
				dbA.addValue("Firsttime", 1);
				dbA.addValue(DatabaseConstants.HUNGER, model.getHungerLevel());
				dbA.addValue(DatabaseConstants.CUDDLE, model.getCuddleLevel());
				dbA.addValue(DatabaseConstants.POO, model.getPooLevel());
				dbA.addValue(DatabaseConstants.CLEAN, model.getCleanLevel());
				dbA.addStringValue(DatabaseConstants.TIME, TimeUtil.getCurrentTime());
			}else{
				int differenceInSeconds=TimeUtil.compareTime(dbA.getStringValue(DatabaseConstants.TIME));
				Log.w("Database", differenceInSeconds+", "+ dbA.getValue(DatabaseConstants.HUNGER));
				try{
					model.setHungerLevel(dbA.getValue(DatabaseConstants.HUNGER)+differenceInSeconds*(-1));
					model.setCuddleLevel(dbA.getValue(DatabaseConstants.CUDDLE)+differenceInSeconds*(-3));
					model.setCleanLevel(dbA.getValue(DatabaseConstants.CLEAN)+differenceInSeconds*(-2));
					model.setPooLevel(dbA.getValue(DatabaseConstants.POO));
				}catch(DeadException e){
					if(e instanceof DeadException){
						Message msg = Message.obtain();
						msg.obj = e;
						handler.sendMessage(msg);
					}
				}

			}
			
			
			feedView.setText("" + model.getHungerLevel());
			cuddleView.setText("" + model.getCuddleLevel());
			cleanView.setText("" + model.getCleanLevel());
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

		/**
		 * Updates the database if the application is shut down
		 */
		@Override
		public void onDestroy(){
			dbA.updateValue(DatabaseConstants.HUNGER, model.getHungerLevel());
			dbA.updateValue(DatabaseConstants.CUDDLE, model.getCuddleLevel());
			dbA.updateValue(DatabaseConstants.CLEAN, model.getCleanLevel());
			dbA.updateValue(DatabaseConstants.POO, model.getPooLevel());
			dbA.updateStringValue(DatabaseConstants.TIME, TimeUtil.getCurrentTime());
			super.onDestroy();
		}


		//Changes the hungerlevel indicator int in the TextView 
		public void feed(View view){
			try{
				model.setHungerLevel(model.getHungerLevel() + 5);
			}catch(DeadException e){

			}
			handler.sendMessage(handler.obtainMessage());
			String feed = new String("" + model.getHungerLevel());

		}

		public void clean(View view){

			model.setCleanLevel(model.getCleanLevel() + 10);
			handler.sendMessage(handler.obtainMessage());

		}

		public void cuddle(View view){

			model.setCuddleLevel(model.getCuddleLevel() + 7);
			handler.sendMessage(handler.obtainMessage());

		}

		public void removePoop(View view){
			model.setHasPooedOrNot(false);
			handler.sendMessage(handler.obtainMessage());

		}

		public void cure(View view){
			model.setIllness(false);
			handler.sendMessage(handler.obtainMessage());

		}


	}
