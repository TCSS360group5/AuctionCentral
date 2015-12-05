package view;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import model.AuctionModel;
import model.BidderModel;
import model.CalendarModel;
import model.EmployeeModel;
import model.ItemModel;
import model.NonProfitModel;
import model.UserModel;
import model.UserModel.UserType;

/**
 * 
 * @author Quinn Cox, UWT Group 5
 *
 */
public class FileSaving {

	private static final String myUserSerString = "userList.ser";
	private static final String myAuctionSerString = "auctionList.ser";
//	private static final String myUserFileString = "users.txt";
//	private static final String myAuctionFileString = "auction.txt";
//	private File myUserFile;
//	private File myAuctionFile;
	
	public FileSaving()
	{
		//if the file doesn't exist, it creates it
//		if (!(new File(myUserFileString).isFile())) 
//		{
//			outputUsers(new File(myUserFileString), new ArrayList<UserModel>());
//		}
//		if (!(new File(myAuctionFileString).isFile())) 
//		{
//			//outputAuctionsOld(new File(myAuctionFileString), new ArrayList<AuctionModel>(), new CalendarModel());
//		}
//		
//		myUserFile = new File(myUserFileString);
//		myAuctionFile = new File(myAuctionFileString);
	}
	
	/**
	 * 
	 * @param myUserList
	 * @param myAuctionList
	 * @param myCalendar
	 */
	protected void loadAll(ArrayList<UserModel> myUserList, ArrayList<AuctionModel> myAuctionList, CalendarModel myCalendar) 
	{	
				loadUsers(myUserSerString, myUserList);				
				loadAuctions(myUserList, myAuctionSerString, myAuctionList, myCalendar);
				//loadUsersOld(myUserFile, myUserList);
				//loadAuctionsOld(myUserList, myAuctionFile, myAuctionList, myCalendar);
	}
	
	/**
	 * 
	 * @param myUserList
	 * @param myAuctionList
	 * @param myCalendar
	 */
	protected void saveAll(ArrayList<UserModel> myUserList, ArrayList<AuctionModel> myAuctionList, CalendarModel myCalendar) 
	{
		try {
			Serialization.serializeObject(myUserSerString, (Object) myUserList);
			//Serialization.serializeObject(myAuctionFileString, (Object) myUserList);
			//Serialization.serializeObject(myCalendarFileString, (Object) myCalendar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputAuctions(myAuctionSerString, myAuctionList, myCalendar);		
	}
	
	/**
	 * Read in serialized file of existing users and import them into an ArrayList of users.
	 *  
	 * @param theFile the file being read
	 * @param theUserList the list that the users from the file are loading into
	 */
	public static void loadUsers(String theFile, ArrayList<UserModel> theUserList) 
	{
		try {
			Object temp = Serialization.deSerializeObject(myUserSerString);
			
			if (temp instanceof ArrayList<?>) {
				ArrayList<?> temp2 = (ArrayList<?>) temp;
				if (temp2.size() > 0) {
					for (Object element: temp2) {
						if (element instanceof UserModel) {
							theUserList.add((UserModel) element);
						}
					}
				} else {
					theUserList = new ArrayList<UserModel>();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		for (UserModel oneUser: theUserList) {
			UserType a = oneUser.getUserType();
			switch (a){
			case BIDDER:
				oneUser = (BidderModel)oneUser;
				break;
			case EMPLOYEE:
				oneUser = (EmployeeModel)oneUser;
				break;
			case NPO:
				oneUser = (NonProfitModel)oneUser;
				break;
			default:
				break;			
			}				
		}
	}
	
	/**
	 * Read in the Serialized file of existing auctions. 
	 * Import the auctions into an ArrayList of auctions, and add to Calendar.
	 * 
	 * @param theAuctionFile the file of previous auctions
	 * @param theAuctionList the list the auctions will be loaded into
	 * @param theCalendar the main calendar for this program
	 */
	public void loadAuctions(ArrayList<UserModel> myUserList, String theAuctionFileString, ArrayList<AuctionModel> theAuctionList, CalendarModel theCalendar) {
		try {
			Object temp = Serialization.deSerializeObject(theAuctionFileString);
			
			if (temp instanceof ArrayList<?>) {
				ArrayList<?> temp2 = (ArrayList<?>) temp;
				if (temp2.size() > 0) {
					for (Object element: temp2) {
						if (element instanceof AuctionModel) {
							AuctionModel newAuction = (AuctionModel) element;
							theAuctionList.add(newAuction);
							String orgName = newAuction.getAuctionOrg();
						    UserModel userWithAuction = FindUserByNPOName(orgName, myUserList);
						    ((NonProfitModel)userWithAuction).setAuction(newAuction);
						    try {
								theCalendar.addAuction(newAuction);
							} catch (Exception e) {
								e.getMessage();
							}
						}
					}
				} else {
					theAuctionList = new ArrayList<AuctionModel>();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
//		Scanner s = new Scanner(theAuctionFile);
//		while (s.hasNext()) {
//		    String orgName = s.nextLine();
//		    int month = s.nextInt();
//		    int day = s.nextInt();
//		    int year = s.nextInt();
//		    int startHour = s.nextInt();
//		    int startMinute = s.nextInt();
//		    int endHour = s.nextInt();
//		    int endMinute = s.nextInt();
//		    s.nextLine();
//		    String userName = s.nextLine();
//		    int NumItems = s.nextInt();
//		    s.nextLine();
//		    List<ItemModel> ItemList = new ArrayList<ItemModel>();
//			for (int j = 0; j < NumItems; j++)
//			{
//				String ItemName = s.nextLine();
//				Double StartingBid = s.nextDouble();
//				s.nextLine();
//				String Description = s.nextLine();
//				
//				ItemModel oneItem = new ItemModel(ItemName, StartingBid, Description);
//				
//				Map<UserModel, Double> bidList = new HashMap<UserModel, Double>();
//				int NumBids = s.nextInt();
//				if (NumBids > 0)
//				{
//					for (int k = 0; k < NumBids; k++) 
//					{
//						String Bidder = s.next();
//						Double theBid = s.nextDouble();
//						UserModel theUser = FindUser(Bidder, myUserList);
//						if (theUser != null)
//						{
//							bidList.put(theUser, theBid);
//							((BidderModel)theUser).addBid(oneItem, theBid);
//						} 
//						else 
//						{
//							System.out.println("User not found in incomming bid list");
//						}
//					}
//					oneItem.setBids(bidList);						
//				}	
//				ItemList.add(oneItem);
//				if(s.hasNextLine()) 		// bug fix for input mismatch exceptions
//				{
//					s.nextLine();
//				}
//			}			
//		    AuctionModel newAuction = new AuctionModel(orgName, userName, LocalDateTime.of(year, month, day, startHour, startMinute), 
//		    		LocalDateTime.of(year, month, day, endHour, endMinute), ItemList);
//		    theAuctionList.add(newAuction);
//		    UserModel userToAdd = FindUserByNPOName(orgName, myUserList);
//		    ((NonProfitModel)userToAdd).setAuction(newAuction);
//		    try {
//				theCalendar.addAuction(newAuction);
//			} catch (Exception e) {
//				e.getMessage();
//			}
//		    
//		    if(s.hasNextLine()) 			// bug fix for input mismatch exceptions
//		    {
//		    s.nextLine();
//		    }
//		    
//		}
//		s.close();
	}
	
	/**
	 * Outputs the existing users to the serialization file.
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
	public static void outputAuctions(String theAuctionFile, ArrayList<AuctionModel> theAuctionList, CalendarModel myCalendar) 
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
		try {
			Serialization.serializeObject(theAuctionFile, (Object) theAuctionList);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
//	/**
//	 * Read in text file of existing users using a Scanner and import them into an ArrayList of users.
//	 *  
//	 * @param theFile the file being read
//	 * @param theUserList the list that the users from the file are loading into
//	 */
//	public static void loadUsersOld(File theFile, ArrayList<UserModel> theUserList) 
//	{
//		try 
//		{
//	        Scanner s = new Scanner(theFile);
//	        while (s.hasNext()) {
//	        	String userName = s.nextLine();
//	        	String userType = s.nextLine();
//	        	switch(userType) {
//	        	case "EMPLOYEE":
//	        		theUserList.add(new EmployeeModel(userName, UserModel.UserType.EMPLOYEE));
//	        		break;
//	        	case "NPO":
//	        		String userNPOname = s.nextLine();	 
//	        		if(s.hasNextInt()) {		// if in the user file there is a date saved, it means the NPO has an auction
//		        		int year = s.nextInt();	        		
//		        		int month = s.nextInt();
//		        		int day = s.nextInt();
//		        		s.nextLine(); //clears the line
//		        		theUserList. add(new NonProfitModel(userName, UserModel.UserType.NPO, userNPOname, LocalDate.of(year, month, day)));
//	        		} else {					// this NPO doesn't have an auction saved, last parameter is set appropriately
//		        		theUserList. add(new NonProfitModel(userName, UserModel.UserType.NPO, userNPOname, LocalDate.now().minusYears(1)));
//	        		}
//
//	        		break;
//	        	case "BIDDER":
//        			theUserList.add(new BidderModel(userName, UserModel.UserType.BIDDER));
//	        	}
//	        }
//	        s.close();
//	    } 
//	    catch (FileNotFoundException e) 
//		{
//	        e.printStackTrace();
//	    }
//	}
	
//	/**
//	 * Read in text file of existing auctions using a Scanner. Import the auctions into an ArrayList of auctions, and add to Calendar.
//	 * 
//	 * @param theAuctionFile the file of previous auctions
//	 * @param theAuctionList the list the auctions will be loaded into
//	 * @param theCalendar the main calendar for this program
//	 */
//	public void loadAuctionsOld(ArrayList<UserModel> myUserList, File theAuctionFile, ArrayList<AuctionModel> theAuctionList, CalendarModel theCalendar) {
//		try 
//		{
//	        Scanner s = new Scanner(theAuctionFile);
//	        while (s.hasNext()) {
//		        String orgName = s.nextLine();
//		        int month = s.nextInt();
//		        int day = s.nextInt();
//		        int year = s.nextInt();
//		        int startHour = s.nextInt();
//		        int startMinute = s.nextInt();
//		        int endHour = s.nextInt();
//		        int endMinute = s.nextInt();
//		        s.nextLine();
//		        String userName = s.nextLine();
//		        int NumItems = s.nextInt();
//		        s.nextLine();
//		        List<ItemModel> ItemList = new ArrayList<ItemModel>();
//				for (int j = 0; j < NumItems; j++)
//				{
//					String ItemName = s.nextLine();
//					Double StartingBid = s.nextDouble();
//					s.nextLine();
//					String Description = s.nextLine();
//					
//					ItemModel oneItem = new ItemModel(ItemName, StartingBid, Description);
//					
//					Map<UserModel, Double> bidList = new HashMap<UserModel, Double>();
//					int NumBids = s.nextInt();
//					if (NumBids > 0)
//					{
//						for (int k = 0; k < NumBids; k++) 
//						{
//							String Bidder = s.next();
//							Double theBid = s.nextDouble();
//							UserModel theUser = FindUser(Bidder, myUserList);
//							if (theUser != null)
//							{
//								bidList.put(theUser, theBid);
//								((BidderModel)theUser).addBid(oneItem, theBid);
//							} 
//							else 
//							{
//								System.out.println("User not found in incomming bid list");
//							}
//						}
//						oneItem.setBids(bidList);						
//					}	
//					ItemList.add(oneItem);
//					if(s.hasNextLine()) 		// bug fix for input mismatch exceptions
//					{
//						s.nextLine();
//					}
//				}			
//		        AuctionModel newAuction = new AuctionModel(orgName, userName, LocalDateTime.of(year, month, day, startHour, startMinute), 
//		        		LocalDateTime.of(year, month, day, endHour, endMinute), ItemList);
//		        theAuctionList.add(newAuction);
//		        UserModel userToAdd = FindUserByNPOName(orgName, myUserList);
//		        ((NonProfitModel)userToAdd).setAuction(newAuction);
//		        try {
//					theCalendar.addAuction(newAuction);
//				} catch (Exception e) {
//					e.getMessage();
//				}
//		        
//		        if(s.hasNextLine()) 			// bug fix for input mismatch exceptions
//		        {
//		        s.nextLine();
//		        }
//		        
//	        }
//	        s.close();
//	    } 
//	    catch (FileNotFoundException e) 
//		{
//	        e.printStackTrace();
//	    }
//	}
}
