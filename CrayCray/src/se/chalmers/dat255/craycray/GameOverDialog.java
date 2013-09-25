package se.chalmers.dat255.craycray;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.Menu;


public class GameOverDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Game Over").setMessage("CrayCray is dead!").setPositiveButton("New Game", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
			
			}
		});
		
		return builder.create();
	}

}
