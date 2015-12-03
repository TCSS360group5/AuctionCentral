package model;
import java.util.HashMap;
import java.util.Map;


/**
 * The bidder class represents the bidders that use our system and produces
 * the menus and allows the bidder to bid and edit his bid.
 * 
 * @author Group5
 */
public class BidderModel extends UserModel
{
  private Map<ItemModel, Double> myBids;

	/**
	 * Creates a bidder.
	 * @param theUserName
	 * @param theUserType  This is an enum of one of the three user types.
	 */
	public BidderModel(String theUserName, UserType theUserType)
	  {
		  super(theUserName, theUserType);
		  myBids = new HashMap<ItemModel, Double>();
	  }
	
	public  Map<ItemModel, Double> getBids()
	{
		return myBids;
	}
	  
	/**
	 * Adds a bid on an item to this Bidder's bids.
	 * @param theItem
	 * @param theBid
	 */
	public void addBid(ItemModel theItem, double theBid)
	{
		myBids.put(theItem, theBid);
	}
}