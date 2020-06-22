package fr.entasia.errors;

public class EntasiaException extends RuntimeException {

	public EntasiaException(){
		super();
	}

	public EntasiaException(String s){
		super(s);
	}

	public EntasiaException(Throwable thr){
		super(thr.getMessage());
	}
}
