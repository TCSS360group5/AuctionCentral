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
	public ArrayList<Command> ExecuteCommand(Command theCommand, Calendar theCalendar,
			Auction theAuction, Item theItem) {
		ArrayList<Command> answer = new ArrayList<Command>();
		Scanner user_input = new Scanner( System.in );
		switch (theCommand) {
			case VIEWAUCTION:
				answer.add(User.Command.GOBACK);
				answer.add(User.Command.VIEWITEM);
				break;
			case VIEWCALENDAR:				// the bidder can't view the calendar ??? unless we are using this to print the current auctions? - shannon
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
				answer.add(User.Command.VIEWBIDS);
				break;
			case BID:
				System.out.println("Please Enter a Bid:");
				bidOnItem(user_input, theItem);
				break;
			case EDITBID:
				System.out.println("This is the previous Bid: $");
				System.out.println(myBids.get(theItem).toString());
				System.out.println("Please Enter a new Bid:");
				bidOnItem(user_input, theItem);
				break;
			case VIEWBIDS:
				Scanner myScanner = new Scanner(System.in);
				//answer.add(User.Command.VIEWCALENDAR);
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
					System.out.println("Would you like to edit a bid? (Enter 0 to exit this menu, 1 to edit a bid)");
					int decision = myScanner.nextInt();
					if(decision < 0 || decision > 1)
					{
						do
						{
							System.out.println("Invalid input. Would you like to edit the auction? (Enter 0 to go back, 1 to edit)");
							decision = user_input.nextInt();
						} while(decision < 0 || decision > 1);
					}
					if(decision == 1)
					{
						System.out.println("Enter the number of the bid you would like to edit.");
						int auctionNum = myScanner.nextInt();
						if(auctionNum > bidsToEdit.size())
						{
							System.out.println("Invalid bid.");
						}
						else
						{
							System.out.println("Previous bid: " + bidsToEdit.get(auctionNum - 1).getBids().get(this));
							System.out.println("Please Enter a new Bid:");
							bidOnItem(myScanner, bidsToEdit.get(auctionNum - 1));
						}
					}
				}
				break;
			default:
				break;
		}
		return answer;
	}
	
	// used for file loading
	public void addBid(Item theItem, double theBid)
	{
		myBids.put(theItem, theBid);
	}
	
	/**
	 * @param user_input
	 * @param theItem
	 */
	private void bidOnItem(Scanner user_input,Item theItem) {
		double bid = user_input.nextDouble();
		if (theItem.getStartingBid() > bid) {
			System.out.println("Bid is below minimum bid for this item.");
		}else {
			myBids.put(theItem, bid);
			theItem.bidOnItem(this, bid);
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

}