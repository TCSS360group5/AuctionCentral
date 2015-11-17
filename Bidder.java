import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
			case VIEWAUCTIONS:
				answer.add(User.Command.GOBACK);
				answer.add(User.Command.VIEWITEM);
				break;
//			case VIEWCALENDAR:				// the bidder can't view the calendar ??? unless we are using this to print the current auctions? - shannon
//				answer.add(User.Command.GOBACK);
//				answer.add(User.Command.VIEWAUCTION);
//				break;
			case VIEWITEM:
				answer.add(User.Command.GOBACK);
				if(myBids.containsKey(theItem)) {
					answer.add(User.Command.EDITBID);
				} else {
					answer.add(User.Command.BID);
				}				
				break;
			case VIEWMAINMENU:
				//answer.add(User.Command.VIEWCALENDAR);
				answer.add(User.Command.VIEWAUCTIONS);
				answer.add(User.Command.VIEWBIDS);
				break;
			case BID:
				bidOnItem(user_input, theItem);
				break;
			case EDITBID:
				System.out.println("This is the previous Bid: $");
				System.out.println(myBids.get(theItem).toString());
				bidOnItem(user_input, theItem);
				break;
			case VIEWBIDS:
				Scanner myScanner = new Scanner(System.in);
				if(myBids.size() == 0)
				{
					System.out.println("You have not placed any bids yet.");
				}
				else{
					ArrayList<Item> bidsToEdit = new ArrayList<Item>();
					System.out.println("Bids you have placed:");
					int i = 1;
					for(Entry<Item, Double> entry : myBids.entrySet())
					{
						System.out.println(i + ") Item Name: " + entry.getKey().getItemName() + " Your bid: " + entry.getValue());
						bidsToEdit.add(entry.getKey());
					}
					System.out.println("Which would you like to edit?");
					int auctionNum = myScanner.nextInt();
					if(auctionNum > bidsToEdit.size())
					{
						System.out.println("Invalid bid.");
					}
					else
					{
						bidOnItem(myScanner, bidsToEdit.get(auctionNum - 1));
					}
				}
				break;
			default:
				break;
		}
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
			System.out.println("Bid entered.");
		}
		
		
		  
		  // I guess we don't need this but this checks to see if that bid is the highest, if it is, updates item's bid fields
	//	  Map<User, Double> itemBidMap = theItem.myBids;
	//	  Collection<Double> itemBids = itemBidMap.values();
	//	  System.out.println(itemBids);
	//	  if(itemBids.size() == 0) {
	//		  theItem.setHighestBid(bid);
	//	  } else {
	//		  Iterator it = itemBids.iterator();
	//		  while(it.hasNext()) {
	//		  		System.out.println(it.next());
	//			  if(bid > (Double) it.next()) {
	//					  theItem.setHighestBid(bid);
	//				  }
	//		  	}
	//	  }
	
	}
	
	public Command getMovementCommand(Command myCurrentState) {
		return myCurrentState;
	}

	public User.Command goBackState(User.Command theCurrentState) 
	{
	  User.Command answer = null;
		switch (theCurrentState)
		 {
		 	case VIEWAUCTIONS:
		 		answer = User.Command.VIEWMAINMENU;
		 		break;
	 		case VIEWITEM:
	 			answer = User.Command.VIEWAUCTIONS;
	 			break;						
	 		default:
	 			break;						 
		 }		
		return answer;
	}
  
  // bids on items
  // views auctions
  // chooses auctions
  // views items
  // updates credit card info
}