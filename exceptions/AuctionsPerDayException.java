package exceptions;

/**
 * This exception class is for throwing an exception when the number of maximum future auctions is reached.
 * 
 * @author Demy
 */
public class AuctionsPerDayException extends Exception 
{

	public static final int MAX_AUCTIONS_PER_DAY = 2;

	private static final long serialVersionUID = 1L;
	
	String myExceptionString = "";
	
	/**
	 * Constructs new AuctionsPerDayException.
	 * 
	 * @author Demy
	 */
	public AuctionsPerDayException()
	{
		myExceptionString = "There are already " + MAX_AUCTIONS_PER_DAY + " scheduled for the date you entered.";
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