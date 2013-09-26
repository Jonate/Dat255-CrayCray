package se.chalmers.dat255.craycray;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.Menu;


public class GameOverDialog{
	
	AlertDialog.Builder alertDialog;

	public GameOverDialog(Activity a, Handler handler){
	
		alertDialog = new AlertDialog.Builder(a);
		alertDialog.setTitle("Game Over");
		alertDialog.setMessage("CrayCray is dead!");
		alertDialog.setPositiveButton("New Game", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				
			}
		});
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
			
			}
		});
		
		alertDialog.create();
		
	}

}
