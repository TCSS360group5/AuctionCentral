package view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import model.AuctionModel;
import model.CalendarModel;
import model.NonProfitModel;
import model.UserModel;

/**
 * This is where file saving happens including saving to files and loading from
 * files.
 * 
 * @author UWT Group 5, Quinn Cox, Shannon
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
	private FileSaving() {
	}

	/**
	 * This method loads the users and aucitons and uses the default file names.
	 * 
	 * @param myUserList
	 *            This is the user list that the file will load into.
	 * @param myAuctionList
	 *            This is the auction list that the file will load into.
	 * @param myCalendar
	 *            This is the calendar that the file will load into.
	 * @author Quinn
	 */
	protected static void loadAll(ArrayList<UserModel> theUserList,
			ArrayList<AuctionModel> theAuctionList, CalendarModel theCalendar) {
		loadAll(theUserList, theAuctionList, theCalendar, USER_SER_STRING,
				AUCTION_SER_STRING);
	}

	/**
	 * By calling this one method the users and auctions are loaded from the
	 * specified files.
	 * 
	 * @param myUserList
	 *            This is the user list that the file will load into.
	 * @param myAuctionList
	 *            This is the auction list that the file will load into.
	 * @param myCalendar
	 *            This is the calendar that the file will load into.
	 * @author Quinn
	 */
	public static void loadAll(ArrayList<UserModel> myUserList,
			ArrayList<AuctionModel> myAuctionList, CalendarModel myCalendar,
			String theUserFileString, String theAuctionFileString) {
		loadUsers(theUserFileString, myUserList);
		loadAuctions(myUserList, theAuctionFileString, myAuctionList,
				myCalendar);
	}

	/**
	 * This method saves all the users and auctions from the program with the
	 * default file names.
	 * 
	 * @param myUserList
	 *            This is the user list that the file will read from.
	 * @param myAuctionList
	 *            This is the auction list that the file will read from.
	 * @param myCalendar
	 *            This is the calendar that the file will read from.
	 * @author Quinn
	 */
	public static void saveAll(ArrayList<UserModel> theUserList,
			ArrayList<AuctionModel> theAuctionList, CalendarModel theCalendar) {
		saveAll(theUserList, theAuctionList, theCalendar, USER_SER_STRING,
				AUCTION_SER_STRING);
	}

	/**
	 * This method saves all the users and auctions from the program with the
	 * specified file names.
	 * 
	 * @param myUserList
	 *            This is the user list that the file will read from.
	 * @param myAuctionList
	 *            This is the auction list that the file will read from.
	 * @param myCalendar
	 *            This is the calendar that the file will read from.
	 * @author Quinn
	 */
	public static void saveAll(ArrayList<UserModel> theUserList,
			ArrayList<AuctionModel> theAuctionList, CalendarModel theCalendar,
			String theUserFileString, String theAuctionFileString) {
		try {
			serializeObject(theUserFileString, (Object) theUserList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		outputAuctions(theAuctionFileString, theAuctionList, theCalendar);
	}

	/**
	 * Read in serialized file of existing users and import them into an
	 * ArrayList of users.
	 * 
	 * @param theFile
	 *            the file being read
	 * @param theUserList
	 *            the list that the users from the file are loading into
	 * @author Quinn
	 */
	public static void loadUsers(String theFile,
			ArrayList<UserModel> theUserList) {
		try {
			Object temp = deSerializeObject(theFile);

			if (temp instanceof ArrayList<?>) {
				ArrayList<?> temp2 = (ArrayList<?>) temp;
				if (temp2.size() > 0) {
					for (Object element : temp2) {
						if (element instanceof UserModel) {
							theUserList.add((UserModel) element);
						}
					}
				} else {
					theUserList = new ArrayList<UserModel>();
				}
			}
		} catch (IOException e) {
			System.out.println("User file not found.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read in the Serialized file of existing auctions. Import the auctions
	 * into an ArrayList of auctions, and add to Calendar.
	 * 
	 * @param theAuctionFile
	 *            the file of previous auctions
	 * @param theAuctionList
	 *            the list the auctions will be loaded into
	 * @param theCalendar
	 *            the main calendar for this program
	 * @author Quinn, Shannon
	 */
	public static void loadAuctions(ArrayList<UserModel> myUserList,
			String theAuctionFileString,
			ArrayList<AuctionModel> theAuctionList, CalendarModel theCalendar) {
		try {
			Object temp = deSerializeObject(theAuctionFileString);

			if (temp instanceof ArrayList<?>) {
				ArrayList<?> temp2 = (ArrayList<?>) temp;
				if (temp2.size() > 0) {
					for (Object element : temp2) {
						if (element instanceof AuctionModel) {
							AuctionModel newAuction = (AuctionModel) element;
							theAuctionList.add(newAuction);
							String orgName = newAuction.getAuctionOrg();
							UserModel userWithAuction = FindUserByNPOName(
									orgName, myUserList);
							((NonProfitModel) userWithAuction)
									.setAuction(newAuction);
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
			System.out.println("Auction file not found.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Outputs the existing auctions to the file specified by the incomming file
	 * string.
	 * 
	 * @param theAuctionFile
	 *            the file of auctions
	 * @param theAuctionList
	 *            the list of auctions
	 * @author Quinn
	 */
	public static void outputAuctions(String theAuctionFile,
			ArrayList<AuctionModel> theAuctionList, CalendarModel myCalendar) {
		Map<LocalDate, ArrayList<AuctionModel>> auctionMap = myCalendar.myAuctionByDateList;
		Collection<ArrayList<AuctionModel>> auctionLists = auctionMap.values();
		theAuctionList.clear();

		Iterator<ArrayList<AuctionModel>> it = auctionLists.iterator();
		while (it.hasNext()) {
			ArrayList<AuctionModel> auctionList = (ArrayList<AuctionModel>) it
					.next();
			for (int i = 0; i < auctionList.size(); i++) {
				theAuctionList.add(auctionList.get(i));
			}
		}
		try {
			serializeObject(theAuctionFile, (Object) theAuctionList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds a NonProfit Organization member by the name of their organization.
	 * 
	 * @param NPOName
	 *            the name of the organization
	 * @return the user with that NPO name
	 * @author Demy
	 */
	private static UserModel FindUserByNPOName(String NPOName,
			ArrayList<UserModel> myUserList) {
		UserModel answerUser = null;
		for (UserModel theUser : myUserList) {
			if (theUser.getUserType() == UserModel.UserType.NPO) {
				if (((NonProfitModel) theUser).getNPOName().equals(NPOName)) {
					answerUser = theUser;
				}
			}
		}
		return answerUser;
	}

	/**
	 * Check the list of users to see if this user has registered already.
	 * 
	 * @param theUserName
	 *            the user name being searched for
	 * @return a user if one exists, null otherwise
	 * @author Shannon
	 */
	public static UserModel findUserByName(String theUserName,
			ArrayList<UserModel> myUserList) {
		UserModel answerUser = null;
		for (UserModel theUser : myUserList) {
			if (theUser.getUserName().compareTo(theUserName) == 0) {
				answerUser = theUser;
			}
		}
		return answerUser;
	}

	/**
	 * This method actually does the serialization. It takes the output file
	 * name and saves the object into that file.
	 * 
	 * @param OutputFileName
	 *            This is the name of the file that you want the object to be
	 *            saved to.
	 * @param theObject
	 *            This is the object that you are saving.
	 * @throws IOException
	 *             This is thrown if there is a problem saving to a file with
	 *             that file name.
	 * @author Quinn
	 */
	public static void serializeObject(String OutputFileName, Object theObject)
			throws IOException {
		FileOutputStream OutputFile = new FileOutputStream(OutputFileName);
		ObjectOutputStream output = new ObjectOutputStream(OutputFile);
		output.writeObject(theObject);
		output.close();
		OutputFile.close();
	}

	/**
	 * This method does the de-serialization. It takes the input file name and
	 * gets the object stored in that file and returns it.
	 * 
	 * @param theInputFileName
	 *            This string is the name of the input file that has the stored
	 *            data on it.
	 * @return returns the object that was stored in the file.
	 * @throws IOException
	 *             This exception is thrown if the file could not be found or
	 *             there was a problem reading the file.
	 * @throws ClassNotFoundException
	 *             This exception is thrown if the object in the file could not
	 *             be read.
	 * @author Quinn
	 */
	public static Object deSerializeObject(String theInputFileName)
			throws IOException, ClassNotFoundException {
		Object theObject;
		FileInputStream InputFile = new FileInputStream(theInputFileName);
		ObjectInputStream input = new ObjectInputStream(InputFile);
		theObject = input.readObject();
		input.close();
		InputFile.close();
		return theObject;
	}
}
