package model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Cox Family
 *
 */
public class BidderModelTest {
	
	BidderModel myBidder;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myBidder = new BidderModel("charlie", UserModel.UserType.BIDDER);
	}

//	/**
//	 * Test method for {@link Bidder#ExecuteCommand(User.Command, Calendar, Auction, Item)}.
//	 */
//	@Test
//	public void testExecuteCommand() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link BidderModel#Bidder(java.lang.String, UserModel.UserType)}.
	 */
	@Test
	public void testBidder() {
		myBidder = new BidderModel("steve", UserModel.UserType.BIDDER);
		assertEquals(myBidder.getUserName(), "steve");
		assertEquals(myBidder.getUserType(), UserModel.UserType.BIDDER);
	}

	/**
	 * Test method for {@link BidderModel#bid(ItemModel, double)}.
	 */
	@Test
	public void testBidAddedOne() {
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		int bidsBefore = testItem.getBids().size();
		//myBidder.bid(testItem, 25);
		int bidsAfter = testItem.getBids().size();
		assertTrue((bidsBefore + 1) == bidsAfter);
	}
	
	/**
	 * Test method for {@link BidderModel#bid(ItemModel, double)}.
	 */
	@Test
	public void testBidAddedValue() {
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		//myBidder.bid(testItem, 25);
		Double bidAmount = testItem.getBids().get(myBidder);
		assertTrue(bidAmount == 25);
	}
	
	/**
	 * Test method for {@link BidderModel#bid(ItemModel, double)}.
	 */
	@Test
	public void testBidBidTooLow() {
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		int bidsBefore = testItem.getBids().size();
		//myBidder.bid(testItem, 19);
		int bidsAfter = testItem.getBids().size();
		assertTrue(bidsBefore == bidsAfter);
	}
}
