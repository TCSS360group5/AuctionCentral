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
public class ItemTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link Item#Item(java.lang.String, double, java.lang.String)}.
	 */
	@Test
	public void testItem() {
		fail("Not yet implemented");
	}
	
	Item itemToTest;
	Bidder bidderToTest;
	
	double bidTooLow = 23;
	double theNextBid = 41;
	//User bidderToTest;
	
	@Before
	public void setupBefore()
	{
		bidderToTest = new Bidder("brendo", User.UserType.BIDDER);
	}

	@Test
	public void testItemBidAddedOneBid() {
		
		//User bidderToTest = new User("brendo", User.UserType.BIDDER);
		Item itemToTest = new Item("Chair", 25, "nice flashy chair");
		itemToTest.bidOnItem(bidderToTest, theNextBid);
		itemToTest.getBids().get(bidderToTest);
		assertTrue(itemToTest.getBids().get(bidderToTest).doubleValue() == theNextBid);			
	}
	
	@Test
	public void testItemBidBidChanged() {
		
		//User bidderToTest = new User("brendo", User.UserType.BIDDER);
		Item itemToTest = new Item("Chair", 25, "nice flashy chair");
		int numBids = itemToTest.getBids().size();
		itemToTest.bidOnItem(bidderToTest, theNextBid);
		assertEquals(numBids + 1, itemToTest.getBids().size());			
	}


	/**
	 * Test method for {@link Item#getBids()}.
	 */
	@Test
	public void testGetBids() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Item#bidOnItem(User, double)}.
	 */
	@Test
	public void testBidOnItem() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Item#updateBid(User, double)}.
	 */
	@Test
	public void testUpdateBid() {
		fail("Not yet implemented");
	}

}
