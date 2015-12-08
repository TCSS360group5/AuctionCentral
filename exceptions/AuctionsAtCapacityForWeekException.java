package exceptions;

/**
 * This exception class is for throwing an exception when the number of maximum future auctions is reached.
 * 
 * @author Demy
 */
public class AuctionsAtCapacityForWeekException extends Exception 
{

	public static final int MAX_AUCTIONS_FOR_WEEK = 5;

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	/**
	 * Constructs new AuctionsAtCapacityForWeekException.
	 * 
	 * @author Demy
	 */
	public AuctionsAtCapacityForWeekException()
	{
		myExceptionString = "There area already " + MAX_AUCTIONS_FOR_WEEK + " auctions scheduled in the 3 days before and"
							+ " after the date of this auction.";
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