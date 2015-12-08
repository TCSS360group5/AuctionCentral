package exceptions;

/**
 * This exception class is for throwing an exception when an auction is scheduled too far in the future.
 *
 * @author Demy
 */
public class AuctionTooFarAwayException extends Exception {

	public static final int MAX_DAYS_AWAY = 90;

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	/**
	 * Constructs new AuctionTooFarAwayException.
	 * 
	 * @author Demy
	 */
	public AuctionTooFarAwayException() {
		myExceptionString = "An auction may not be scheduled more than " + MAX_DAYS_AWAY + " days in the future. Or anytime in the past.";
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
