package fr.entasia.errors;

public class LibrarieException extends EntasiaException {

	public LibrarieException(){
		super();
	}

	public LibrarieException(String s){
		super(s);
	}

	public LibrarieException(Throwable thr){
		super(thr.getMessage());
	}
}
