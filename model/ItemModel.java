package model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class represents an item for AuctionCentral.
 * 
 * @author TCSS 360 Group 5
 */
public class ItemModel  implements Serializable
{
	/** Required for serialization */
	private static final long serialVersionUID = -6836348504277068061L;
	
	private String myItemName;
	private String myDescription;
	private double myStartingBid;
	private Map<UserModel, Double> myBids;
  
  public ItemModel(String theItemName, double theStartingBid, String theDescription)
  {
	  myBids = new HashMap<>();
	  myItemName = theItemName;
	  myStartingBid = theStartingBid;
	  myDescription = theDescription;	  
  }
  
  public boolean setItemName(String theItemName)
  {
	  myItemName = theItemName;
	  return true;
  }
  
  public boolean setStartingBid(double theStartingBid)
  {	  
	  myStartingBid = theStartingBid;
	  return true;	  
  }
  
  public boolean setDescription(String theDescription)
  {
	  myDescription = theDescription;
	  return true;
  }
  
  public String getItemName()
  {
	  return myItemName;
  }
  
  public double getStartingBid()
  {
	  return myStartingBid;
  }
  
  public String getDescription()
  {
	  return myDescription;
  }
  
  public Map<UserModel, Double> getBids()
  {
	  return myBids;
  }
  
  public void setBids(Map<UserModel, Double> theBids)
  {
	  myBids = theBids;
  }
  
  public void bidOnItem(UserModel user, double bid)
  {
	  	  myBids.put(user, bid);
  }
  
  public boolean isBidAboveStartingBid(Double theBid)
  {
	  return (theBid >= myStartingBid);
  }
  
  public void updateBid(UserModel user, double newBid)
  {
	 myBids.put(user, newBid);
  }
  
  public String toString() {
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + myItemName + "\n");
	  answer.append("Description: " + myDescription + "\n");
	  answer.append("Starting Bid: " + myStartingBid + "\n");
	  return answer.toString();
  }
  
  public String toStringWithBids() {
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + myItemName + "\n");
	  answer.append("Description: " + myDescription + "\n");
	  answer.append("Starting Bid: " + myStartingBid + "\n");
	  for (Entry<UserModel, Double> entry : myBids.entrySet()) 
	  {
		  answer.append(entry.getKey() + " " + entry.getValue() + "\n");
	  }
	  return answer.toString();
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
	public boolean equals(Object theOther) 
	{
		boolean answer = true;
		if (theOther instanceof ItemModel) 
		{
			ItemModel theOtherModel = (ItemModel) theOther;
			if (theOtherModel.myItemName.compareTo(myItemName) != 0) 
			{
				answer = false;
			}
			if (theOtherModel.myDescription.compareTo(myDescription) != 0)
			{
				answer = false;
			}
			if (theOtherModel.myStartingBid != myStartingBid) 
			{
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

