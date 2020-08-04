package fr.entasia.errors;

public class LibraryException extends EntasiaException {

	public LibraryException(){
		super();
	}

	public LibraryException(String s){
		super(s);
	}

	public LibraryException(Throwable thr){
		super(thr.getMessage());
	}
}
