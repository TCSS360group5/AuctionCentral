package view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import model.AuctionModel;
import model.BidderModel;
import model.CalendarModel;
import model.EmployeeModel;
import model.NonProfitModel;
import model.UserModel;
import model.UserModel.UserType;

/**
 * This is where file saving happens including saving to files and loading from files.
 * 
 * @author  UWT Group 5, Quinn Cox, Shannon
 *
 */
public class FileSaving {

	/**
	 * This is the file name of where the serialized users will be stored.
	 */
	private static final String USER_SER_STRING = "userList.ser";

	/**
	 * This is the file name of where the serialized auctions will be stored.
	 */
	private static final String AUCTION_SER_STRING = "auctionList.ser";
	
	/**
	 * prevents instantiation
	 */
	private FileSaving()
	{
	}
	
	/**
	 * By calling this one method the users and auctions are loaded
	 * 
	 * @param myUserList This is the user list that the file will load into.
	 * @param myAuctionList This is the auction list that the file will load into.
	 * @param myCalendar This is the calendar that the file will load into.
	 * @author Quinn
	 */
	protected static void loadAll(ArrayList<UserModel> myUserList, ArrayList<AuctionModel> myAuctionList, CalendarModel myCalendar) 
	{	
				loadUsers(USER_SER_STRING, myUserList);				
				loadAuctions(myUserList, AUCTION_SER_STRING, myAuctionList, myCalendar);
	}
	
	/**
	 * This method saves all the users and auctions from the program. 
	 * 
	 * @param myUserList This is the user list that the file will read from.
	 * @param myAuctionList This is the auction list that the file will read from.
	 * @param myCalendar This is the calendar that the file will read from.
	 * @author Quinn
	 */
	protected static void saveAll(ArrayList<UserModel> myUserList, ArrayList<AuctionModel> myAuctionList, CalendarModel myCalendar) 
	{
		try {
			Serialization.serializeObject(USER_SER_STRING, (Object) myUserList);
			//Serialization.serializeObject(myAuctionFileString, (Object) myUserList);
			//Serialization.serializeObject(myCalendarFileString, (Object) myCalendar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputAuctions(AUCTION_SER_STRING, myAuctionList, myCalendar);		
	}
	
	/**
	 * Read in serialized file of existing users and import them into an ArrayList of users.
	 *  
	 * @param theFile the file being read
	 * @param theUserList the list that the users from the file are loading into
	 * @author Quinn
	 */
	public static void loadUsers(String theFile, ArrayList<UserModel> theUserList) 
	{
		try {
			Object temp = Serialization.deSerializeObject(USER_SER_STRING);
			
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
	 * @author Quinn, Shannon
	 */
	public static void loadAuctions(ArrayList<UserModel> myUserList, String theAuctionFileString, ArrayList<AuctionModel> theAuctionList, CalendarModel theCalendar) {
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
	}

	/**
	 * Outputs the existing auctions to the file specified by the incomming file string.
	 * 
	 * @param theAuctionFile the file of auctions
	 * @param theAuctionList the list of auctions
	 * @author Quinn
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
	 * @author
	 */
	private static UserModel FindUserByNPOName(String NPOName, ArrayList<UserModel> myUserList)
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
	 * @author
	 */
	protected static UserModel findUserByName(String theUserName, ArrayList<UserModel> myUserList)
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
