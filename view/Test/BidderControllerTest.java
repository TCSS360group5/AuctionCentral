/**
 * JUnit tests for the BidderController class.
 * 
 * @author TCSS 360 Group 5
 */

package view.Test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.BidderController;
import model.*;

public class BidderControllerTest {
	
	BidderController myBidder;

	/**
	 * Sets up the Bidder to be used for testing.
	 * 
	 * @throws java.lang.Exception
	 * @author Shannon
	 */
	@Before
	public void setUp() throws Exception 
	{
		BidderModel bidderModel = new BidderModel("charlie", UserModel.UserType.BIDDER);
		myBidder = new BidderController(bidderModel);
	}

	/**
	 * Test for the BidderController constructor.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testBidderController() 
	{
		assertTrue(myBidder.getBidderModel().getUserName().equals("charlie"));
		assertTrue(myBidder.getBidderModel().getUserType().equals(UserModel.UserType.BIDDER));
	}

	/**
	 * Test for the bid(Item, double) method.
	 * Tests that the bids for an item are updated when a bid is made on that item.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testBidOnAddedOne() 
	{
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		int bidsBefore = testItem.getBids().size();
		myBidder.bid(testItem, 25);
		int bidsAfter = testItem.getBids().size();
		assertEquals(bidsBefore+1, bidsAfter);
	}
	
	/**
	 * Tests the bid method when a bidder places a bid one cent higher than the minimum.
	 * 
	 * @author Shannon, Demy
	 * @result Bid added.
	 */
	@Test
	public void testBidOnOneCentHigherThanMin() 
	{
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		assertTrue(myBidder.bid(testItem, 20.01));
	}
	
	/**
	 * Tests the bid method when a bidder bids the minimum bid.
	 * 
	 * @author Shannon, Demy
	 * @result Bid is not added.
	 */
	@Test
	public void testBidOnMinBid() 
	{
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		assertFalse(myBidder.bid(testItem, 15));
	}
}
