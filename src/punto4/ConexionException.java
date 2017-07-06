package punto4;

public class ConexionException extends RuntimeException{
	public ConexionException(String message) {
		super(message);
	}
	
	public ConexionException(String message, Throwable cause) {
		super(message, cause);
	}

}
