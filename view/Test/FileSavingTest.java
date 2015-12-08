package view.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import model.AuctionModel;
import model.ItemModel;
import model.UserModel;

import org.junit.Before;
import org.junit.Test;

import view.FileSaving;

/**
 * Tests the file saving functions.
 * 
 * @author TCSS 360 Group 5
 *
 */
public class FileSavingTest {
	
	AuctionModel myAuction;
	AuctionModel myAuctionWithItems;
	AuctionModel myAuctionWithItemsWithBids;
	UserModel myNPOUser;
	UserModel myEmployeeUser;
	UserModel myBidderUser;
	LocalDateTime myDate;

	/**
	 * This class creates all the files needed to set up for the testing.  Including creating and then 
	 * serializing an auction, an auction with items, an auction with items and bids, and the three users.
	 * 
	 * @throws Exception
	 * @author Quinn
	 */
	@Before
	public void setUp(){
		myDate = LocalDateTime.of(2015, 12, 15, 12, 30);
		myAuction = new AuctionModel("Org Name", "UserForSerialize", myDate, myDate.plusHours(2));
		try
		{
			FileSaving.serializeObject("auction.ser", myAuction);
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
		ItemModel item1 = new ItemModel("chair", 50, "grey Chair");
		ItemModel item2 = new ItemModel("vase", 200.00, "glass vase");
		ArrayList<ItemModel> ItemList = new ArrayList<ItemModel>();
		ItemList.add(item1);
		ItemList.add(item2);
		myAuctionWithItems = new AuctionModel("Another Org Name", "User2ForSerialize", myDate, myDate.plusHours(2), ItemList);
		try
		{
			FileSaving.serializeObject("auctionwithitems.ser", myAuctionWithItems);
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
		myNPOUser = new UserModel("UserForSerialize", UserModel.UserType.NPO);
		UserModel User2 = new UserModel ("2ndUser", UserModel.UserType.NPO);
		ItemModel item3 = new ItemModel("chair", 50, "grey Chair");
		ItemModel item4 = new ItemModel("vase", 200.00, "glass vase");
		item3.bidOnItem(myNPOUser, 60);
		item4.bidOnItem(User2, 300);
		ArrayList<ItemModel> ItemList2  = new ArrayList<ItemModel>();
		ItemList2.add(item3);
		ItemList2.add(item4);
		myAuctionWithItemsWithBids = new AuctionModel("Likes bidding", "AnotherForSerialize", myDate, myDate.plusHours(2), ItemList2);
		try
		{
			FileSaving.serializeObject("auctionwithitemswithbids.ser", myAuctionWithItemsWithBids);
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

	/**
	 * Test method for {@link view.FileSaving#FileSaving()}.
	 */
	@Test
	public void testFileSaving() {
		// not sure we need to test this
	}

	/**
	 * Test method for {@link view.FileSaving#saveAll(java.util.ArrayList, java.util.ArrayList, model.CalendarModel)}.
	 */
	@Test
	public void testSaveAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#loadUsers(java.io.File, java.util.ArrayList)}.
	 */
	@Test
	public void testLoadUsers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#loadAuctions(java.util.ArrayList, java.io.File, java.util.ArrayList, model.CalendarModel)}.
	 */
	@Test
	public void testLoadAuctions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#outputUsers(java.io.File, java.util.ArrayList)}.
	 */
	@Test
	public void testOutputUsers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#outputAuctions(java.io.File, java.util.ArrayList, model.CalendarModel)}.
	 */
	@Test
	public void testOutputAuctions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#findUserByName(java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	public void testFindUser() {
		fail("Not yet implemented");
	}



	/**
	 * There isn't much to test here, as long as an exception isn't thrown we 
	 * believe it is good until we test deserialization.
	 * 
	 * @author Quinn
	 */
	@Test
	public void testSerialize() {
		try
		{
			FileSaving.serializeObject("auctionSerialize.ser", myAuction);
		} catch (IOException io) {
			System.out.println(io.getMessage());
			fail("IOException");
		}
	}

	/**
	 * This is the basic test of using deserialization to test a simple auction.
	 * @author Quinn
	 */
	@Test
	public void testDeSerializeAuction() {
		AuctionModel tempModel = null;
		try
		{
			tempModel = (AuctionModel) FileSaving.deSerializeObject("auction.ser");
		} catch (IOException io) {
			System.out.println(io.getMessage());
			fail("IOException");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("ClassNotFoundException");
		}
		assertTrue(compareAuction(tempModel, myAuction));
	}
	
	/**
	 * This is a more advanced test with an auction that has items.
	 * @author Quinn
	 */
	@Test
	public void testDeSerializeAuctionWithItems() {
		AuctionModel tempModel = null;
		try
		{
			tempModel = (AuctionModel) FileSaving.deSerializeObject("auctionwithitems.ser");
		} catch (IOException io) {
			System.out.println(io.getMessage());
			fail("IOException");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("ClassNotFoundException");
		}
		assertTrue(compareAuction(tempModel, myAuctionWithItems));
	}
	
	/**
	 * This is an even more advanced test with an auction that has item and those items have bids.
	 * @author Quinn
	 */
	@Test
	public void testDeSerializeAuctionWithItemsWithBids() {
		AuctionModel tempModel = null;
		try
		{
			tempModel = (AuctionModel) FileSaving.deSerializeObject("auctionwithitemswithbids.ser");
		} catch (IOException io) {
			System.out.println(io.getMessage());
			fail("IOException");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("ClassNotFoundException");
		}
		assertTrue(compareAuction(tempModel, myAuctionWithItemsWithBids));
	}
	
	/**
	 * This method compares 2 auctions to see if their fields are exactly the same.
	 * 
	 * @param theAuction1  This is the first auction to compare.
	 * @param theAuction2 	This is the second auction to compare.
	 * @return returns true if the auctions are the same and false otherwise.
	 * @author Quinn
	 */
	private boolean compareAuction(AuctionModel theAuction1, AuctionModel theAuction2)
	{
		boolean answer = true;
		if (theAuction1.myAuctionName.compareTo(theAuction2.myAuctionName) != 0)
		{
			answer = false;
		}
		if (theAuction1.myOrgName.compareTo(theAuction2.myOrgName) != 0)
		{
			answer = false;
		}
		if (theAuction1.getUserName().compareTo(theAuction2.getUserName()) != 0)
		{
			answer = false;
		}
		if (theAuction1.getStartTime().compareTo(theAuction2.getStartTime()) != 0)
		{
			answer = false;
		}
		if (theAuction1.getEndTime().compareTo(theAuction2.getEndTime()) != 0)
		{
			answer = false;
		}
		if (theAuction1.myInventory != null && theAuction2.myInventory == null)
		{
			answer = false;
		} 
		else if (theAuction1.myInventory == null && theAuction2.myInventory != null)
		{
			answer = false;
		}
		else if (theAuction1.myInventory == null && theAuction2.myInventory == null)
		{
			answer = true;
		} 
		else if (theAuction1.myInventory.size() != theAuction2.myInventory.size())
		{
			answer = false;
		}
		else 
		{
			for (int i = 0; i < theAuction1.myInventory.size(); i++)
			{
				boolean tempAnswer = compareItem(theAuction1.myInventory.get(i), theAuction2.myInventory.get(i));
				if (!tempAnswer){
					answer = false;
				}
			}
		}
		
		return answer;
	}
	
	/**
	 * This method compares two items to see if they are the same.
	 * 
	 * @param theItem1 This is the first ItemModel 
	 * @param theItem2 This is the second ItemModel 
	 * @return returns true if the items are the same, false otherwise.
	 * @author Quinn
	 */
	private boolean compareItem(ItemModel theItem1, ItemModel theItem2)
	{
		boolean answer = true;
		if (theItem1.getItemName().compareTo(theItem2.getItemName()) != 0)
		{
			answer = false;
		}
		if (theItem1.getDescription().compareTo(theItem2.getDescription()) != 0)
		{
			answer = false;
		}
		if (theItem1.getStartingBid() != theItem2.getStartingBid())
		{
			answer = false;
		}
		if (theItem1.getDescription().compareTo(theItem2.getDescription()) != 0)
		{
			answer = false;
		}
		if (theItem1.getDescription().compareTo(theItem2.getDescription()) != 0)
		{
			answer = false;
		}
		if (theItem1.getBids() != null && theItem2.getBids() == null)
		{
			answer = false;
		} 
		else if (theItem1.getBids() == null && theItem2.getBids() != null)
		{
			answer = false;
		}
		else if (theItem1.getBids() == null && theItem2.getBids() == null)
		{
			answer = true;
		} 
		else if (theItem1.getBids().size() != theItem2.getBids().size())
		{
			answer = false;
		}
		else 
		{
			for (int i = 0; i < theItem1.getBids().size(); i++)				
			{
				boolean tempAnswer = compareBid(theItem1.getBids(), theItem2.getBids());
				if (!tempAnswer)
				{
					answer = false;
				}
			}
		}
		return answer;		
	}
	
	/**
	 * This method compares 2 bid lists to see if they are the same.
	 * 
	 * @param theBidsList1 This is the first bid list to compare
	 * @param theBidsList2 This is the second bid list to compare
	 * @return returns true if the bid lists are the same
	 * @author Quinn
	 */
	private boolean compareBid(Map<UserModel, Double> theBidsList1, Map<UserModel, Double> theBidsList2)
	{
		boolean answer = true;
		for (Entry<UserModel, Double> theEntry: theBidsList1.entrySet())
		{
			if (theBidsList2.containsKey(theEntry.getKey()) && theBidsList1.containsKey(theEntry.getKey()))
			{
				if (theBidsList2.get(theEntry.getKey()).compareTo(theEntry.getValue()) != 0)
				{
					answer = false;
				}
			} else 
			{
				answer = false;
			}
		}
		return answer;
	}
}
