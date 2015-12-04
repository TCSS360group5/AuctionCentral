package exceptions;

/**
 * This exception class is for throwing an exception when the number of maximum future auctions is reached.
 */
public class AuctionBackwardsTimeException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	public AuctionBackwardsTimeException() {
		myExceptionString = "Auction end cannot be before the start";
	}
	
	public String getMessage()
	{
		return myExceptionString;
	}

}