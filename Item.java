import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Item
{
  String myItemName;
  String myDescription;
  double mySellingPrice;
  double myStartingBid;
  Map<User, Double> myBids;
  
  public Item(String theItemName, double theStartingBid, String theDescription)
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
  
  public Map<User, Double> getBids()
  {
	  //LIST ALL BIDS
	  return myBids;
  }
  
  public void setBids(Map<User, Double> theBids)
  {
	  myBids = theBids;
  }
  
  public void bidOnItem(User user, double bid)
  {
	  	  myBids.put(user, bid);
  }
  
  public void updateBid(User user, double newBid)
  {
	 myBids.put(user, newBid);
  }
  
  public String toString() {
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + myItemName + "\n");
	  answer.append("Description: " + myDescription + "\n");
	  answer.append("Starting Bid: " + myStartingBid + "\n");
	  answer.append("Selling Price: " + mySellingPrice + "\n");
	  return answer.toString();
  }
  
  public String toStringWithBids() {
	  StringBuilder answer = new StringBuilder();
	  answer.append("Name: " + myItemName + "\n");
	  answer.append("Description: " + myDescription + "\n");
	  answer.append("Starting Bid: " + myStartingBid + "\n");
	  answer.append("Selling Price: " + mySellingPrice + "\n");
	  for (Entry<User, Double> entry : myBids.entrySet()) 
	  {
		  answer.append(entry.getKey() + " " + entry.getValue() + "\n");
	  }
	  return answer.toString();
  }
  
}

