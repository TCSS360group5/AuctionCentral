package exceptions;

/**
 * This exception class allows exceptions to be thrown that specify
 * what went wrong with the auction.
 */
public class AuctionException extends Exception {


	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	public AuctionException (String theString) {
		myExceptionString = theString;
	}
	
	public String getExceptionString()
	{
		return myExceptionString;
	}

}
