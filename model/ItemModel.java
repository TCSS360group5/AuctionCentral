package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an item for AuctionCentral.
 * 
 * @author Brendan, Quinn
 */
public class ItemModel implements Serializable {
	/** Required for serialization */
	private static final long serialVersionUID = -6836348504277068061L;

	private String myItemName;
	private String myDescription;
	private double myStartingBid;
	private Map<UserModel, Double> myBids;

	/**
	 * Contstructs a new ItemModel.
	 * 
	 * @param theItemName Name of item to be added.
	 * @param theStartingBid Starting bid of item to be added.
	 * @param theDescription Description of item to be added.
	 * 
	 * @author Brendan
	 */
	public ItemModel(String theItemName, double theStartingBid,
			String theDescription) {
		myBids = new HashMap<>();
		myItemName = theItemName;
		myStartingBid = theStartingBid;
		myDescription = theDescription;
	}

	/**
	 * Sets a new name for the ItemModel.
	 * 
	 * @param theItemName The new name for the ItemModel.
	 * @return true iff name was changed.
	 * 
	 * @author Brendan
	 */
	public boolean setItemName(String theItemName) {
		myItemName = theItemName;
		return true;
	}

	/**
	 * Sets the starting bid of an ItemModel.
	 * 
	 * @param theStartingBid The new starting bid for the ItemModel.
	 * @return true iff the starting bid was changed.
	 * 
	 * @author Brendan
	 */
	public boolean setStartingBid(double theStartingBid) {
		myStartingBid = theStartingBid;
		return true;
	}

	/**
	 * Sets the description of an ItemModel.
	 * 
	 * @param theDescription The new description for the ItemModel.
	 * @return true iff the description was changed.
	 * 
	 * @author Brendan
	 */
	public boolean setDescription(String theDescription) {
		myDescription = theDescription;
		return true;
	}

	/**
	 * Gets the name of an ItemModel.
	 * 
	 * @return The itemName stored in the ItemModel.
	 * 
	 * @author Brendan
	 */
	public String getItemName() {
		return myItemName;
	}

	/**
	 * Gets the starting bid of an ItemModel.
	 * 
	 * @return The starting bid.
	 * 
	 * @author Brendan
	 */
	public double getStartingBid() {
		return myStartingBid;
	}

	/**
	 * Gets the description of an ItemModel.
	 * 
	 * @return The description stored in the ItemModel.
	 * 
	 * @author Brendan
	 */
	public String getDescription() {
		return myDescription;
	}

	/**
	 * Gets the list of bids for an ItemModel.
	 * 
	 * @return Map containing users as keys and their bid as the values.
	 * 
	 * @author Brendan
	 */
	public Map<UserModel, Double> getBids() {
		return myBids;
	}

	/**
	 * Sets the bids for an ItemModel.
	 * 
	 * @param The Bids to be added.
	 * 
	 * @author Brendan
	 */
	public void setBids(Map<UserModel, Double> theBids) {
		myBids = theBids;
	}

	/**
	 * Method to bid on an ItemModel.
	 * 
	 * @param user The user that placed the bid.
	 * @param bid The bid that the user placed.
	 * 
	 * @author Brendan
	 */
	public void bidOnItem(UserModel user, double bid) {
		myBids.put(user, bid);
	}

	/**
	 * Checks if the bid is an acceptable value.
	 * 
	 * @param theBid The value of a bid to be added.
	 * @return true iff theBid is equal to or greater than the starting bid.
	 * 
	 * @author Brendan
	 */
	public boolean isBidAboveStartingBid(Double theBid) {
		return (theBid >= myStartingBid);
	}

	/**
	 * Updates a bid placed by a user.
	 * 
	 * @param user The user who changed the bid.
	 * @param newBid The user's new bid.
	 * 
	 * @author Brendan
	 */
	public void updateBid(UserModel user, double newBid) {
		myBids.put(user, newBid);
	}


	/**
	 * Compares this object to another object.
	 * 
	 * @param theOther
	 *            this is the other object being compared.
	 * @return returns true if the objects have the same values in their fields
	 * @author Quinn
	 */
	@Override
	public boolean equals(Object theOther) {
		boolean answer = true;
		if (theOther instanceof ItemModel) {
			ItemModel theOtherModel = (ItemModel) theOther;
			if (theOtherModel.myItemName.compareTo(myItemName) != 0) {
				answer = false;
			}
			if (theOtherModel.myDescription.compareTo(myDescription) != 0) {
				answer = false;
			}
			if (theOtherModel.myStartingBid != myStartingBid) {
				answer = false;
			}
		} else {
			answer = false;
		}
		return answer;
	}

	/**
	 * This method produces the hashCode of this object.
	 * 
	 * @return returns the hashCode of this object.
	 * @author Quinn
	 */
	@Override
	public int hashCode() {
		int answer = 0;
		answer += myItemName.hashCode();
		answer += myDescription.toString().hashCode();
		answer += myStartingBid;
		return answer;
	}

}
