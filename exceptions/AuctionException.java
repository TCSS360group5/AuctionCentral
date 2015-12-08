package exceptions;

/**
 * This exception class allows exceptions to be thrown that specify
 * what went wrong with the auction.
 * 
 * @author Quinn
 */
public class AuctionException extends Exception {


	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	/**
	 * Constructs new AuctionException with specified error message.
	 * 
	 * @author Quinn
	 */
	public AuctionException (String theString) {
		myExceptionString = theString;
	}
	
	/**
	 * Gets the error message.
	 * 
	 * @return Exception error message.
	 * 
	 * @author Quinn
	 */
	public String getExceptionString()
	{
		return myExceptionString;
	}

}
