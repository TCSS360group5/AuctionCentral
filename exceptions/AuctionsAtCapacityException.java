package exceptions;

/**
 * This exception class is for throwing an exception when the number of maximum future auctions is reached.
 * 
 * @author Demy
 */
public class AuctionsAtCapacityException extends Exception {

	public static final int MAX_FUTURE_AUCTIONS = 25;

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	/**
	 * Constructs new AuctionsAtCapacityException.
	 * 
	 * @author Demy
	 */
	public AuctionsAtCapacityException () {
		myExceptionString = "The number of future auctions is already at its capacity of " + MAX_FUTURE_AUCTIONS + ".";
	}
	
	@Override 
	/**
	 * Gets the error message.
	 * 
	 * @return Exception error message.
	 * 
	 * @author Demy
	 */
	public String getMessage()
	{
		return myExceptionString;
	}

}