package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The bidder class represents the bidders that use our system and produces the
 * menus and allows the bidder to bid and edit his bid.
 * 
 * @author Group5
 */
public class BidderModel extends UserModel {

	private static final long serialVersionUID = -1714111610542462428L;

	/**
	 * List of bids for this bidder
	 */
	private Map<ItemModel, Double> myBids;

	/**
	 * Creates a bidder with the specified name and type.
	 * 
	 * @param theUserName
	 *            This is the name of the user
	 * @param theUserType
	 *            This is an enum of one of the three user types.
	 * @author Quinn
	 */
	public BidderModel(String theUserName, UserType theUserType) {
		super(theUserName, theUserType);
		myBids = new HashMap<ItemModel, Double>();
	}

	/**
	 * This is a getter for the list of all the bids for this user.
	 * 
	 * @return returns a list of all bids for the user
	 * @author Quinn
	 */
	public Map<ItemModel, Double> getBids() {
		return myBids;
	}

	/**
	 * Adds a bid on an item to this Bidder's bids.
	 * 
	 * @param theItem
	 *            The item being bid on.
	 * @param theBid
	 *            The bid amount as a double
	 * @author Quinn
	 */
	public void addBid(ItemModel theItem, double theBid) {
		myBids.put(theItem, theBid);
	}
}