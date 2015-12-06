package view;
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

import model.AuctionModel;
import model.BidderModel;
import model.CalendarModel;
import model.EmployeeModel;
import model.ItemModel;
import model.NonProfitModel;
import model.UserModel;


public class FileSavingOld {

	private static final String myUserFileString = "users.txt";
	private static final String myAuctionFileString = "auction.txt";
	private File myUserFile;
	private File myAuctionFile;
	
	public FileSavingOld()
	{
		//if the file doesn't exist, it creates it
		if (!(new File(myUserFileString).isFile())) 
		{
			outputUsers(new File(myUserFileString), new ArrayList<UserModel>());
		}
		if (!(new File(myAuctionFileString).isFile())) 
		{
			outputAuctions(new File(myAuctionFileString), new ArrayList<AuctionModel>(), new CalendarModel());
		}
		
		myUserFile = new File(myUserFileString);
		myAuctionFile = new File(myAuctionFileString);
	}
	
	protected void loadAll(ArrayList<UserModel> myUserList, ArrayList<AuctionModel> myAuctionList, CalendarModel myCalendar) 
	{	
				loadUsers(myUserFile, myUserList);
				loadAuctions(myUserList, myAuctionFile, myAuctionList, myCalendar);
	}
	
	protected void saveAll(ArrayList<UserModel> myUserList, ArrayList<AuctionModel> myAuctionList, CalendarModel myCalendar) 
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
	public static void loadUsers(File theFile, ArrayList<UserModel> theUserList) 
	{
		try 
		{
	        Scanner s = new Scanner(theFile);
	        while (s.hasNext()) {
	        	String userName = s.nextLine();
	        	String userType = s.nextLine();
	        	switch(userType) {
	        	case "EMPLOYEE":
	        		theUserList.add(new EmployeeModel(userName, UserModel.UserType.EMPLOYEE));
	        		break;
	        	case "NPO":
	        		String userNPOname = s.nextLine();	 
	        		if(s.hasNextInt()) {		// if in the user file there is a date saved, it means the NPO has an auction
		        		int year = s.nextInt();	        		
		        		int month = s.nextInt();
		        		int day = s.nextInt();
		        		s.nextLine(); //clears the line
		        		theUserList. add(new NonProfitModel(userName, UserModel.UserType.NPO, userNPOname, LocalDate.of(year, month, day)));
	        		} else {					// this NPO doesn't have an auction saved, last parameter is set appropriately
		        		theUserList. add(new NonProfitModel(userName, UserModel.UserType.NPO, userNPOname, LocalDate.now().minusYears(1)));
	        		}

	        		break;
	        	case "BIDDER":
        			theUserList.add(new BidderModel(userName, UserModel.UserType.BIDDER));
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
	public void loadAuctions(ArrayList<UserModel> myUserList, File theAuctionFile, ArrayList<AuctionModel> theAuctionList, CalendarModel theCalendar) {
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
		        List<ItemModel> ItemList = new ArrayList<ItemModel>();
				for (int j = 0; j < NumItems; j++)
				{
					String ItemName = s.nextLine();
					Double StartingBid = s.nextDouble();
					s.nextLine();
					String Description = s.nextLine();
					
					ItemModel oneItem = new ItemModel(ItemName, StartingBid, Description);
					
					Map<UserModel, Double> bidList = new HashMap<UserModel, Double>();
					int NumBids = s.nextInt();
					if (NumBids > 0)
					{
						for (int k = 0; k < NumBids; k++) 
						{
							String Bidder = s.next();
							Double theBid = s.nextDouble();
							UserModel theUser = FindUser(Bidder, myUserList);
							if (theUser != null)
							{
								bidList.put(theUser, theBid);
								((BidderModel)theUser).addBid(oneItem, theBid);
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
		        AuctionModel newAuction = new AuctionModel(orgName, userName, LocalDateTime.of(year, month, day, startHour, startMinute), 
		        		LocalDateTime.of(year, month, day, endHour, endMinute), ItemList);
		        theAuctionList.add(newAuction);
		        UserModel userToAdd = FindUserByNPOName(orgName, myUserList);
		        ((NonProfitModel)userToAdd).setAuction(newAuction);
		        try {
					theCalendar.addAuction(newAuction);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}
		        
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
	public static void outputUsers(File theUserFile, ArrayList<UserModel> theUserList) 
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
			UserModel tempUser = theUserList.get(i);
			outputUsers.println(tempUser.getUserName());
			outputUsers.println(tempUser.getUserType());
			if (tempUser.getUserType() == UserModel.UserType.NPO)
			{		
				NonProfitModel tempNPOUser = (NonProfitModel) tempUser;
				outputUsers.println(tempNPOUser.getNPOName());
				if(tempNPOUser.getAuction() != null) {
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
	public static void outputAuctions(File theAuctionFile, ArrayList<AuctionModel> theAuctionList, CalendarModel myCalendar) 
	{		
		Map<LocalDate, ArrayList<AuctionModel>> auctionMap = myCalendar.myAuctionByDateList;
		Collection<ArrayList<AuctionModel>> auctionLists = auctionMap.values();
		theAuctionList.clear();
		
		Iterator<ArrayList<AuctionModel>> it = auctionLists.iterator();
		while(it.hasNext()) {
			ArrayList<AuctionModel> auctionList = (ArrayList<AuctionModel>) it.next();
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
			AuctionModel auction = theAuctionList.get(i);
			outputAuctions.println(auction.getAuctionOrg()); 
			outputAuctions.println(auction.getStartTime().getMonthValue()); 
			outputAuctions.println(auction.getStartTime().getDayOfMonth());
			outputAuctions.println(auction.getStartTime().getYear());
			outputAuctions.println(auction.getStartTime().getHour() + " " + auction.getStartTime().getMinute());
			outputAuctions.println(auction.getEndTime().getHour() + " " + auction.getEndTime().getMinute());			
			outputAuctions.println(auction.getUserName());
			List<ItemModel> ItemList = auction.getAuctionItems();
			int ItemListSize;
			if (ItemList != null)
			{
				ItemListSize = ItemList.size();
				outputAuctions.println(ItemListSize);
				for (int j = 0; j < ItemListSize; j++)
				{
					ItemModel oneItem = ItemList.get(j);
				
					outputAuctions.println(oneItem.getItemName());
					outputAuctions.println(oneItem.getStartingBid());
					outputAuctions.println(oneItem.getDescription());				
					Map<UserModel, Double> bidList = oneItem.getBids();
					outputAuctions.println(bidList.size());
					for (Entry<UserModel, Double> entry: bidList.entrySet()) 
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
	private UserModel FindUserByNPOName(String NPOName, ArrayList<UserModel> myUserList)
	{
		UserModel answerUser = null;
		for (UserModel theUser: myUserList)
		{
			if(theUser.getUserType() == UserModel.UserType.NPO)
			{
				if(((NonProfitModel)theUser).getNPOName().equals(NPOName))
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
	protected UserModel FindUser(String theUserName, ArrayList<UserModel> myUserList)
	{
		UserModel answerUser = null;
		for (UserModel theUser: myUserList)
		{
			if (theUser.getUserName().compareTo(theUserName) == 0)
			{
				answerUser = theUser;
			} 
		}
		return answerUser;
	}
}
