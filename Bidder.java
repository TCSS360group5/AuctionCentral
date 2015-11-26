import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * @author Group 5
 * The bidder class represents the bidders that use our system and produces
 * the menus and allows the bidder to bid and edit his bid.
 */
public class Bidder extends User
{
  private Map<Item, Double> myBids;

	/**
	 * @param theUserName
	 * @param theUserType  This is an enum of one of the three user types.
	 * 
	 * This creates a bidder.
	 */
	public Bidder(String theUserName, UserType theUserType)
	  {
		  super(theUserName, theUserType);
		  myBids = new HashMap<Item, Double>();
	  }
	  
    /**
     * This function accepts the item and the bid and then puts the bid in the item.
     * 
	 * @param theItem
	 * @param theBid
	 * @return if fail return false, also print to console why failed.
	 */
	public boolean bid(Item theItem, double theBid){
		  boolean answer = false;
		  boolean allDone = false;
		  if (theItem.getStartingBid() > theBid) {
			  allDone = true;
			  System.out.println("Bid does not meet minimum bid for this item.");
		  }
		  if (!allDone) {
			  myBids.put(theItem, theBid);
			  theItem.bidOnItem(this, theBid);
			  answer = true;
		  }	  	  
		return answer;
	  }
	
	/* (non-Javadoc)
	 * @see User#ExecuteCommand(User.Command, Calendar, Auction, Item)
	 */

	@Override
	public boolean ExecuteCommand(Command theCommand, Calendar theCalendar,
			Auction theAuction, Item theItem) {
		boolean answer = false;
		Scanner user_input = new Scanner( System.in );
		switch (theCommand) {
			case BID:
				bidOnItem(user_input, theItem);
				answer = true;
				break;
			case EDITBID:
				System.out.println("This is the previous Bid: $");
				System.out.println(myBids.get(theItem).toString());
				System.out.println("Please Enter a new Bid:");
				bidOnItem(user_input, theItem);
				answer = true;
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
				answer = true;
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
	}
	
	public ArrayList<Command> GetMenu(Command theCurrentState, Item theItem) {
		ArrayList<Command> answer = new ArrayList<Command>();
		switch (theCurrentState)
		 {
			case VIEWBIDDERAUCTIONS:
				answer.add(User.Command.GOBACK);
				answer.add(User.Command.VIEWITEM);
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
				answer.add(User.Command.VIEWBIDDERAUCTIONS);
				answer.add(User.Command.VIEWBIDS);
				break;
			case BID:
				answer.add(User.Command.VIEWCALENDAR);
				answer.add(User.Command.VIEWBIDS);
				break;
			default:
				break;
		 }
		return answer;
	}
	
	@Override
	public User.Command goForwardState(User.Command theCurrentState, User.Command theCurrentCommand)
	{
		User.Command answer = theCurrentState;
		if (theCurrentCommand == User.Command.VIEWMAINMENU)
		 {
			 answer = User.Command.VIEWMAINMENU;
		 } 
		 else if (theCurrentCommand == User.Command.VIEWBIDDERAUCTIONS)
		 {
			 //this should be uncommented when implemented:
			 //answer = User.Command.VIEWBIDDERAUCTIONS;
			 System.out.println("Auctions for Bidders not implemented yet.");
		 }
		 else if (theCurrentCommand == User.Command.VIEWMYAUCTION)
		 {
			 answer = User.Command.VIEWMYAUCTION;
		 }				 
		return answer;
	}


	@Override
	public User.Command goBackState(User.Command theCurrentState) 
	{
	  User.Command answer = null;
		switch (theCurrentState)
		 {
		 	case VIEWBIDDERAUCTIONS:
		 		answer = User.Command.VIEWMAINMENU;
		 		break;
	 		case VIEWITEM:
	 			answer = User.Command.VIEWBIDDERAUCTIONS;
	 			break;						
	 		default:
	 			System.out.println("Cannot Go Back");
	 			break;						 
		 }		
		return answer;
	}

	public void addBid(Item theItem, double theBid)
	{
		myBids.put(theItem, theBid);
	}
}