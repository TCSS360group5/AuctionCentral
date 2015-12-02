package view;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.*;

/**
 * Tests for the BidderController class.
 * 
 * @author TCSS 360 Group 5
 */
public class BidderControllerTest {
	
	BidderController myBidder;

	/**
	 * Sets up the Bidder to be used for testing.
	 * 
	 * @throws java.lang.Exception
	 * @author Shannon
	 */
	@Before
	public void setUp() throws Exception {
		BidderModel bidderModel = new BidderModel("charlie", UserModel.UserType.BIDDER);
		myBidder = new BidderController(bidderModel);
	}

	/**
	 * Test for the BidderController constructor.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testBidderController() {
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
	public void testBidAddedOne() {
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		int bidsBefore = testItem.getBids().size();
		myBidder.bid(testItem, 25);
		int bidsAfter = testItem.getBids().size();
		assertEquals(bidsBefore+1, bidsAfter);
	}
	
	/**
	 * Test for the bid(Item, double) method.
	 * Tests that the Bidder's bid is updated correctly to the new bid.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testBidAddedValue() {
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		myBidder.bid(testItem, 25);
		double bidAmount = testItem.getBids().get(myBidder.getBidderModel());
		assertEquals(bidAmount, 25, 0);
	}
	
	/**
	 * Test for the bid(ItemModel, double) method.
	 * Tests that a bid should fail when the bid is lower than the minimum bid.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testBidBidTooLow() {
		ItemModel testItem = new ItemModel("vase", 20, "puple vase");
		assertFalse(myBidder.bid(testItem, 15));
	}
}
