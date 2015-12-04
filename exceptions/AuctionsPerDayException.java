package exceptions;

/**
 * This exception class is for throwing an exception when the number of maximum future auctions is reached.
 */
public class AuctionsPerDayException extends Exception 
{

	public static final int MAX_AUCTIONS_PER_DAY = 2;

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	public AuctionsPerDayException()
	{
		myExceptionString = "There are already " + MAX_AUCTIONS_PER_DAY + " scheduled for the date you entered.";
	}
	
	public String getMessage()
	{
		return myExceptionString;
	}

}