import java.util.HashMap;
import java.util.Map;

public class Item
{
  String itemName;
  String description;
  double sellingPrice;
  double startingBid;
  Map<User, Double> bids;
  
  public Item(String theItemName, double theStartingBid, String theDescription)
  {
	  bids = new HashMap<>();
	  itemName = theItemName;
	  startingBid = theStartingBid;
	  description = theDescription;
	  
  }
  
  public boolean setItemName(String theItemName)
  {
	  itemName = theItemName;
	  return true;
  }
  
  public boolean setStartingBid(double theStartingBid)
  {
	  
	  startingBid = theStartingBid;
	  return true;
	  
  }
  
  public boolean setDescription(String theDescription)
  {
	  description = theDescription;
	  return true;
  }
  
  public String getItemName()
  {
	  return itemName;
  }
  
  public double getStartingBid()
  {
	  return startingBid;
  }
  
  public String getDescription()
  {
	  return description;
  }
  
  public Map<User, Double> getBids()
  {
	  //LIST ALL BIDS
	  return bids;
  }
  
  public void bidOnItem(User user, double bid)
  {
	  	  bids.put(user, bid);
  }
  
  public void updateBid(User user, double newBid)
  {
	 bids.put(user, newBid);
  }
  
}

