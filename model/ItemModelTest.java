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
public class ItemModelTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link ItemModel#Item(java.lang.String, double, java.lang.String)}.
	 */
	@Test
	public void testItem() {
		fail("Not yet implemented");
	}
	
	ItemModel itemToTest;
	BidderModel bidderToTest;
	
	double bidTooLow = 23;
	double theNextBid = 41;
	//User bidderToTest;
	
	@Before
	public void setupBefore()
	{
		bidderToTest = new BidderModel("brendo", UserModel.UserType.BIDDER);
	}

	@Test
	public void testItemBidAddedOneBid() {
		
		//User bidderToTest = new User("brendo", User.UserType.BIDDER);
		ItemModel itemToTest = new ItemModel("Chair", 25, "nice flashy chair");
		itemToTest.bidOnItem(bidderToTest, theNextBid);
		itemToTest.getBids().get(bidderToTest);
		assertTrue(itemToTest.getBids().get(bidderToTest).doubleValue() == theNextBid);			
	}
	
	@Test
	public void testItemBidBidChanged() {
		
		//User bidderToTest = new User("brendo", User.UserType.BIDDER);
		ItemModel itemToTest = new ItemModel("Chair", 25, "nice flashy chair");
		int numBids = itemToTest.getBids().size();
		itemToTest.bidOnItem(bidderToTest, theNextBid);
		assertEquals(numBids + 1, itemToTest.getBids().size());			
	}


	/**
	 * Test method for {@link ItemModel#getBids()}.
	 */
	@Test
	public void testGetBids() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ItemModel#bidOnItem(UserModel, double)}.
	 */
	@Test
	public void testBidOnItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ItemModel#updateBid(UserModel, double)}.
	 */
	@Test
	public void testUpdateBid() {
		fail("Not yet implemented");
	}

}
