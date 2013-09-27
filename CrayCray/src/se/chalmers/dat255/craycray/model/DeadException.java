package se.chalmers.dat255.craycray.model;

public class DeadException extends Exception {

	private String cause;

	public DeadException(String deathCause){
		cause = deathCause;
	}

	public String getDeathCause(){
		return cause;
	}

}
