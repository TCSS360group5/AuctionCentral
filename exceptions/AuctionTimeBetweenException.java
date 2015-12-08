package exceptions;

/**
 * This exception class is for throwing an exception when the number of maximum future auctions is reached.
 * 
 * @author Demy
 */
public class AuctionTimeBetweenException extends Exception {
	
	public static final int HOURS_BETWEEN_AUCTION = 2;

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	/**
	 * Constructs new AuctionTimeBetweenException.
	 * 
	 * @author Demy
	 */
	public AuctionTimeBetweenException() {
		myExceptionString = "There must be at least " + HOURS_BETWEEN_AUCTION + " hours between the end of one auction" 
							+ " and the start of another.";
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