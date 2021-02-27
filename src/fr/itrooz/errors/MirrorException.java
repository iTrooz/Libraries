package fr.itrooz.errors;

public class MirrorException extends LibraryException {

	public MirrorException(){
		super();
	}

	public MirrorException(String s){
		super(s);
	}

	public MirrorException(Throwable thr){
		super(thr.getMessage());
	}

}
