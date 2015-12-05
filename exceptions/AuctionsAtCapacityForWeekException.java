package exceptions;

/**
 * This exception class is for throwing an exception when the number of maximum future auctions is reached.
 */
public class AuctionsAtCapacityForWeekException extends Exception 
{

	public static final int MAX_AUCTIONS_FOR_WEEK = 5;

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	public AuctionsAtCapacityForWeekException()
	{
		myExceptionString = "There area already " + MAX_AUCTIONS_FOR_WEEK + " auctions scheduled in the 3 days before and"
							+ " after the date of this auction.";
	}
	
	public String getMessage()
	{
		return myExceptionString;
	}

}