import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;


public class FileSaving {

	private static final String myUserFileString = "users.txt";
	private static final String myAuctionFileString = "auction.txt";
	private File myUserFile;
	private File myAuctionFile;
	
	public FileSaving()
	{
		//if the file doesn't exist, it creates it
		if (!(new File(myUserFileString).isFile())) 
		{
			outputUsers(new File(myUserFileString), new ArrayList<User>());
		}
		if (!(new File(myAuctionFileString).isFile())) 
		{
			outputAuctions(new File(myAuctionFileString), new ArrayList<Auction>(), new Calendar());
		}
		
		myUserFile = new File(myUserFileString);
		myAuctionFile = new File(myAuctionFileString);
	}
	
	protected void loadAll(ArrayList<User> myUserList, ArrayList<Auction> myAuctionList, Calendar myCalendar) 
	{	
				loadUsers(myUserFile, myUserList);
				loadAuctions(myUserList, myAuctionFile, myAuctionList, myCalendar);
	}
	
	protected void saveAll(ArrayList<User> myUserList, ArrayList<Auction> myAuctionList, Calendar myCalendar) 
	{
		outputUsers(myUserFile, myUserList);
		outputAuctions(myAuctionFile, myAuctionList, myCalendar);		
	}
	
	/**
	 * Read in text file of existing users using a Scanner and import them into an ArrayList of users.
	 *  
	 * @param theFile the file being read
	 * @param theUserList the list that the users from the file are loading into
	 */
	public static void loadUsers(File theFile, ArrayList<User> theUserList) 
	{
		try 
		{
	        Scanner s = new Scanner(theFile);
	        while (s.hasNext()) {
	        	String userName = s.nextLine();
	        	String userType = s.nextLine();
	        	switch(userType) {
	        	case "EMPLOYEE":
	        		theUserList.add(new Employee(userName, User.UserType.EMPLOYEE));
	        		break;
	        	case "NPO":
	        		String userNPOname = s.nextLine();	 
	        		if(s.hasNextInt()) {		// if in the user file there is a date saved, it means the NPO has an auction
		        		int year = s.nextInt();	        		
		        		int month = s.nextInt();
		        		int day = s.nextInt();
		        		s.nextLine(); //clears the line
		        		theUserList. add(new NonProfit(userName, User.UserType.NPO, userNPOname, LocalDate.of(year, month, day)));
	        		} else {					// this NPO doesn't have an auction saved, last parameter is set appropriately
		        		theUserList. add(new NonProfit(userName, User.UserType.NPO, userNPOname, LocalDate.now().minusYears(1)));
	        		}

	        		break;
	        	case "BIDDER":
        			theUserList.add(new Bidder(userName, User.UserType.BIDDER));
	        	}
	        }
	        s.close();
	    } 
	    catch (FileNotFoundException e) 
		{
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Read in text file of existing auctions using a Scanner. Import the auctions into an ArrayList of auctions, and add to Calendar.
	 * 
	 * @param theAuctionFile the file of previous auctions
	 * @param theAuctionList the list the auctions will be loaded into
	 * @param theCalendar the main calendar for this program
	 */
	public void loadAuctions(ArrayList<User> myUserList, File theAuctionFile, ArrayList<Auction> theAuctionList, Calendar theCalendar) {
		try 
		{
	        Scanner s = new Scanner(theAuctionFile);
	        while (s.hasNext()) {
		        String orgName = s.nextLine();
		        int month = s.nextInt();
		        int day = s.nextInt();
		        int year = s.nextInt();
		        int startHour = s.nextInt();
		        int startMinute = s.nextInt();
		        int endHour = s.nextInt();
		        int endMinute = s.nextInt();
		        s.nextLine();
		        String userName = s.nextLine();
		        int NumItems = s.nextInt();
		        s.nextLine();
		        List<Item> ItemList = new ArrayList<Item>();
				for (int j = 0; j < NumItems; j++)
				{
					String ItemName = s.nextLine();
					Double StartingBid = s.nextDouble();
					s.nextLine();
					String Description = s.nextLine();
					
					Item oneItem = new Item(ItemName, StartingBid, Description);
					
					Map<User, Double> bidList = new HashMap<User, Double>();
					int NumBids = s.nextInt();
					if (NumBids > 0)
					{
						for (int k = 0; k < NumBids; k++) 
						{
							String Bidder = s.next();
							Double theBid = s.nextDouble();
							User theUser = FindUser(Bidder, myUserList);
							if (theUser != null)
							{
								bidList.put(theUser, theBid);
								((Bidder)theUser).addBid(oneItem, theBid);
							} 
							else 
							{
								System.out.println("User not found in incomming bid list");
							}
						}
						oneItem.setBids(bidList);						
					}	
					ItemList.add(oneItem);
					if(s.hasNextLine()) 		// bug fix for input mismatch exceptions
					{
						s.nextLine();
					}
				}			
		        Auction newAuction = new Auction(orgName, userName, LocalDateTime.of(year, month, day, startHour, startMinute), 
		        		LocalDateTime.of(year, month, day, endHour, endMinute), ItemList);
		        theAuctionList.add(newAuction);
		        User userToAdd = FindUserByNPOName(orgName, myUserList);
		        ((NonProfit)userToAdd).setAuction(newAuction);
		        theCalendar.addAuction(newAuction);
		        
		        if(s.hasNextLine()) 			// bug fix for input mismatch exceptions
		        {
		        s.nextLine();
		        }
		        
	        }
	        s.close();
	    } 
	    catch (FileNotFoundException e) 
		{
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Prints the existing users to the users.txt file.
	 * 
	 * @param theUserFile the file of users
	 * @param theUserList the list of users
	 */
	public static void outputUsers(File theUserFile, ArrayList<User> theUserList) 
	{
		PrintStream outputUsers = null;		// to write users to file
		try 
		{
			outputUsers = new PrintStream(theUserFile);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		
		for(int i = 0; i < theUserList.size(); i++) 
		{
			User tempUser = theUserList.get(i);
			outputUsers.println(tempUser.getUserName());
			outputUsers.println(tempUser.getUserType());
			if (tempUser.getUserType() == User.UserType.NPO)
			{		
				NonProfit tempNPOUser = (NonProfit) tempUser;
				outputUsers.println(tempNPOUser.getNPOName());
				if(tempNPOUser.hasAuction()) {
					LocalDate LastAuctionDate = tempNPOUser.getLastAuctionDate();
					outputUsers.println(LastAuctionDate.getYear() + " " + LastAuctionDate.getMonthValue() + " " + LastAuctionDate.getDayOfMonth());
				}
			}
		}
		outputUsers.close();
	}
	
	/**
	 * Outputs the existing auctions to the auctions.txt file.
	 * 
	 * @param theAuctionFile the file of auctions
	 * @param theAuctionList the list of auctions
	 */
	public static void outputAuctions(File theAuctionFile, ArrayList<Auction> theAuctionList, Calendar myCalendar) 
	{		
		Map<LocalDate, ArrayList<Auction>> auctionMap = myCalendar.myAuctionByDateList;
		Collection<ArrayList<Auction>> auctionLists = auctionMap.values();
		theAuctionList.clear();
		
		Iterator<ArrayList<Auction>> it = auctionLists.iterator();
		while(it.hasNext()) {
			ArrayList<Auction> auctionList = (ArrayList<Auction>) it.next();
			for(int i = 0; i < auctionList.size(); i++) {
				theAuctionList.add(auctionList.get(i));
			}
		}
		
		PrintStream outputAuctions = null;
		
		try 
		{
			outputAuctions = new PrintStream(theAuctionFile);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		
		for(int i = 0; i < theAuctionList.size(); i++)
		{
			Auction auction = theAuctionList.get(i);
			outputAuctions.println(auction.getAuctionOrg()); 
			outputAuctions.println(auction.getStartTime().getMonthValue()); 
			outputAuctions.println(auction.getStartTime().getDayOfMonth());
			outputAuctions.println(auction.getStartTime().getYear());
			outputAuctions.println(auction.getStartTime().getHour() + " " + auction.getStartTime().getMinute());
			outputAuctions.println(auction.getEndTime().getHour() + " " + auction.getEndTime().getMinute());			
			outputAuctions.println(auction.getUserName());
			List<Item> ItemList = auction.getAuctionItems();
			int ItemListSize;
			if (ItemList != null)
			{
				ItemListSize = ItemList.size();
				outputAuctions.println(ItemListSize);
				for (int j = 0; j < ItemListSize; j++)
				{
					Item oneItem = ItemList.get(j);
				
					outputAuctions.println(oneItem.myItemName);
					outputAuctions.println(oneItem.myStartingBid);
					outputAuctions.println(oneItem.myDescription);				
					Map<User, Double> bidList = oneItem.getBids();
					outputAuctions.println(bidList.size());
					for (Entry<User, Double> entry: bidList.entrySet()) 
					{
						outputAuctions.println(entry.getKey().getUserName());
						outputAuctions.println(entry.getValue());
					}
				}
			}
			outputAuctions.println();
		}
		outputAuctions.close();
	}
	
	/**
	 * Finds a NonProfit Organization member by the name of their organization.
	 * 
	 * @param NPOName the name of the organization
	 * @return the user with that NPO name
	 */
	private User FindUserByNPOName(String NPOName, ArrayList<User> myUserList)
	{
		User answerUser = null;
		for (User theUser: myUserList)
		{
			if(theUser.getUserType() == User.UserType.NPO)
			{
				if(((NonProfit)theUser).getNPOName().equals(NPOName))
					{
						answerUser = theUser;
					}
			}
		}
		return answerUser;
	}
	
	/**
	 * Check the list of users to see if this user has registered already.
	 * 
	 * @param theUserName the user name being searched for
	 * @return a user if one exists, null otherwise
	 */
	protected User FindUser(String theUserName, ArrayList<User> myUserList)
	{
		User answerUser = null;
		for (User theUser: myUserList)
		{
			if (theUser.getUserName().compareTo(theUserName) == 0)
			{
				answerUser = theUser;
			} 
		}
		return answerUser;
	}
}
