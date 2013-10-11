package se.chalmers.dat255.craycray.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DeadException extends Exception implements Parcelable  {

	private String cause;

	public DeadException(String deathCause){
		cause = deathCause;
	}

	public String getDeathCause(){
		return cause;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
