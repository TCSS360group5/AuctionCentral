package exceptions;

/**
 * This exception class is for throwing an exception when the number of maximum future auctions is reached.
 * 
 * @author Demy
 */
public class AuctionBackwardsTimeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	/**
	 * Constructs new AuctionBackwardsTimeException.
	 * 
	 * @author Demy
	 */
	public AuctionBackwardsTimeException() {
		myExceptionString = "Auction end cannot be before the start";
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