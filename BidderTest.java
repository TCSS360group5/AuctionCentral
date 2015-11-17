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
public class BidderTest {
	
	Bidder myBidder;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myBidder = new Bidder("charlie", User.UserType.BIDDER);
	}

//	/**
//	 * Test method for {@link Bidder#ExecuteCommand(User.Command, Calendar, Auction, Item)}.
//	 */
//	@Test
//	public void testExecuteCommand() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link Bidder#Bidder(java.lang.String, User.UserType)}.
	 */
	@Test
	public void testBidder() {
		myBidder = new Bidder("steve", User.UserType.BIDDER);
		assertEquals(myBidder.getUserName(), "steve");
		assertEquals(myBidder.getUserType(), User.UserType.BIDDER);
	}

	/**
	 * Test method for {@link Bidder#bid(Item, double)}.
	 */
	@Test
	public void testBidAddedOne() {
		Item testItem = new Item("vase", 20, "puple vase");
		int bidsBefore = testItem.getBids().size();
		myBidder.bid(testItem, 25);
		int bidsAfter = testItem.getBids().size();
		assertTrue((bidsBefore + 1) == bidsAfter);
	}
	
	/**
	 * Test method for {@link Bidder#bid(Item, double)}.
	 */
	@Test
	public void testBidAddedValue() {
		Item testItem = new Item("vase", 20, "puple vase");
		myBidder.bid(testItem, 25);
		Double bidAmount = testItem.getBids().get(myBidder);
		assertTrue(bidAmount == 25);
	}
	
	/**
	 * Test method for {@link Bidder#bid(Item, double)}.
	 */
	@Test
	public void testBidBidTooLow() {
		Item testItem = new Item("vase", 20, "puple vase");
		int bidsBefore = testItem.getBids().size();
		myBidder.bid(testItem, 19);
		int bidsAfter = testItem.getBids().size();
		assertTrue(bidsBefore == bidsAfter);
	}
}
