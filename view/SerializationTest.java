package view;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;

import model.AuctionModel;
import model.ItemModel;
import model.UserModel;

import org.junit.Before;
import org.junit.Test;

public class SerializationTest {
	AuctionModel myAuction;
	 
	UserModel myUser;
	LocalDateTime myDate;

	@Before
	public void setUp() throws Exception {
		myDate = LocalDateTime.of(2015, 12, 15, 12, 30);
		myAuction = new AuctionModel("Org Name", "UserForSerialize", myDate, myDate.plusHours(2));
		myUser = new UserModel("UserForSerialize", UserModel.UserType.NPO);
	}

	@Test
	public void testSerialize() {
//		class what{
//			ArrayList<UserModel> myUserList;
//			ArrayList<AuctionModel> myAuctionList;
//			what(ArrayList<UserModel> theUserList, ArrayList<AuctionModel> theAuctionList) {
//				myUserList = theUserList;
//				myAuctionList = theAuctionList;
//			}
//		}
		try
		{
			 Serialization.SerializeAuctions("auction.ser", myAuction);
		} catch (IOException io) {
			System.out.println(io.getMessage());
			fail("IOException");
		}
	}

	@Test
	public void testDeSerialize() {
		AuctionModel tempModel = new AuctionModel("", "", myDate.plusDays(3), myDate.plusDays(3).plusHours(3));
		try
		{
			tempModel = (AuctionModel) Serialization.DeSerialize("auction.ser");
		} catch (IOException io) {
			System.out.println(io.getMessage());
			fail("IOException");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			fail("ClassNotFoundException");
		}
		assertTrue(compareAuction(tempModel, myAuction));
//		assertEquals(tempModel.myAuctionName, myAuction.myAuctionName);
//		assertEquals(tempModel.myOrgName, myAuction.myOrgName);
//		assertEquals(tempModel.getStartTime(), myAuction.getStartTime());
//		assertEquals(tempModel.getEndTime(), myAuction.getEndTime());
	}
	
	private boolean compareAuction(AuctionModel Auction1, AuctionModel Auction2)
	{
		boolean answer = true;
		if (Auction1.myAuctionName.compareTo(Auction2.myAuctionName) != 0)
		{
			answer = false;
		}
		if (Auction1.myOrgName.compareTo(Auction2.myOrgName) != 0)
		{
			answer = false;
		}
		if (Auction1.getUserName().compareTo(Auction2.getUserName()) != 0)
		{
			answer = false;
		}
		if (Auction1.getStartTime().compareTo(Auction2.getStartTime()) != 0)
		{
			answer = false;
		}
		if (Auction1.getEndTime().compareTo(Auction2.getEndTime()) != 0)
		{
			answer = false;
		}
		if (Auction1.myInventory != null && Auction2.myInventory == null)
		{
			answer = false;
		} 
		else if (Auction1.myInventory == null && Auction2.myInventory != null)
		{
			answer = false;
		}
		else if (Auction1.myInventory == null && Auction2.myInventory == null)
		{
			answer = true;
		} 
		else if (Auction1.myInventory.size() != Auction2.myInventory.size())
		{
			answer = false;
		}
		else 
		{
			for (int i = 0; i < Auction1.myInventory.size(); i++)
			{
				boolean tempAnswer = compareItem(Auction1.myInventory.get(i), Auction2.myInventory.get(i));
				if (!tempAnswer){
					answer = false;
				}
			}
		}
		
		return answer;
	}
	
	private boolean compareItem(ItemModel Item1, ItemModel Item2)
	{
		boolean answer = true;
//		  String myItemName;
//		  String myDescription;
//		  double myStartingBid;
//		  Map<UserModel, Double> myBids;
		if (Item1.getItemName().compareTo(Item2.getItemName()) != 0)
		{
			answer = false;
		}
		if (Item1.getDescription().compareTo(Item2.getDescription()) != 0)
		{
			answer = false;
		}
		if (Item1.getStartingBid() != Item2.getStartingBid())
		{
			answer = false;
		}
		if (Item1.getDescription().compareTo(Item2.getDescription()) != 0)
		{
			answer = false;
		}
		if (Item1.getDescription().compareTo(Item2.getDescription()) != 0)
		{
			answer = false;
		}
		if (Item1.getBids() != null && Item2.getBids() == null)
		{
			answer = false;
		} 
		else if (Item1.getBids() == null && Item2.getBids() != null)
		{
			answer = false;
		}
		else if (Item1.getBids() == null && Item2.getBids() == null)
		{
			answer = true;
		} 
		else if (Item1.getBids().size() != Item2.getBids().size())
		{
			answer = false;
		}
		else 
		{
			for (int i = 0; i < Item1.getBids().size(); i++)				
			{
				boolean tempAnswer = compareBid(Item1.getBids(), Item2.getBids());
				if (!tempAnswer)
				{
					answer = false;
				}
			}
		}
		return answer;		
	}
	
	private boolean compareBid(Map<UserModel, Double> Bids1, Map<UserModel, Double> Bids2)
	{
		boolean answer = true;
		for (Entry<UserModel, Double> theEntry: Bids1.entrySet())
		{
			if (Bids1.containsKey(theEntry.getKey()))
			{
				if (Bids2.get(theEntry.getKey()).compareTo(theEntry.getValue()) != 0)
				{
					answer = false;
				}
			} else {
				answer = false;
			}
		}
		return answer;
	}
}
