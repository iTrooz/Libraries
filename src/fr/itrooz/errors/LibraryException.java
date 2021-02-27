package fr.itrooz.errors;

public class LibraryException extends RuntimeException {

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
