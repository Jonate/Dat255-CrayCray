package se.chalmers.dat255.craycray.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DatabaseException extends Exception implements Parcelable  {

	private String cause;

	public DatabaseException(String cause){
		this.cause = cause;
		
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