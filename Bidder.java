import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bidder extends User
{
  private Map<Item, Double> myBids;
  
  public Bidder(String theUserName, UserType theUserType)
  {
	  super(theUserName, theUserType);
	  myBids = new HashMap<Item, Double>();
  }
  
  //if fail return false, also print to console why failed.
  public boolean bid(Item theItem, double theBid){
	  boolean answer = false;
	  boolean allDone = false;
	  if (theItem.getStartingBid() < theBid) {
		  allDone = true;
		  System.out.println("Bid does not meet minimum bid for this item.");
	  }
	  if (!allDone) {
		  myBids.put(theItem, theBid);
		  answer = true;
	  }	  
	return answer;
  }

@Override
public ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar,
		Auction theAuction, Item theItem) {
	ArrayList<Command> answer = new ArrayList<Command>();
	Scanner user_input = new Scanner( System.in );
	switch (theCommand) {
		case VIEWAUCTION:
			answer.add(User.Command.GOBACK);
			answer.add(User.Command.VIEWITEM);
			break;
		case VIEWCALENDAR:
			answer.add(User.Command.GOBACK);
			answer.add(User.Command.VIEWAUCTION);
			break;
		case VIEWITEM:
			answer.add(User.Command.GOBACK);
			if(myBids.containsKey(theItem)) {
				answer.add(User.Command.EDITBID);
			} else {
				answer.add(User.Command.BID);
			}				
			break;
		case VIEWMAINMENU:
			answer.add(User.Command.VIEWCALENDAR);
			break;
		case BID:
			bidOnItem(user_input, theItem);
			break;
		case EDITBID:
			System.out.println("This is the previous Bid: $");
			System.out.println(myBids.get(theItem).toString());
			bidOnItem(user_input, theItem);
			break;
		default:
			break;
	}
	user_input.close();
	return answer;
}

private void bidOnItem(Scanner user_input,Item theItem) {
	double bid;
	System.out.println("Please Enter a Bid:");
	bid = user_input.nextDouble();
	if (theItem.getStartingBid() > bid) {
		System.out.println("Bid is below minimum bid for this item.");
	}else {
		myBids.put(theItem, bid);
	}
}
  
  // bids on items
  // views auctions
  // chooses auctions
  // views items
  // updates credit card info
}