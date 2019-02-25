package net.mizobogames.fhbgds;

public class BSException extends Exception {

	private static final long serialVersionUID = 3375026487105167976L;

	public BSException() {
		super();
	}

	public BSException(String message) {
		super(message);
	}

	public BSException(Throwable cause) {
		super(cause);
	}

	public BSException(String message, Throwable cause) {
		super(message, cause);
	}

	public BSException(String message, Throwable cause, boolean enableSupression, boolean writableStacktrace) {
		super(message, cause, enableSupression, writableStacktrace);
	}
	
	@Override
	public StackTraceElement[] getStackTrace(){
		return super.getStackTrace();
	}

}
