package model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * 
 * @author Brendan, Shannon
 *
 */
public class ItemModelTest {

	ItemModel myTestItem;
	BidderModel myTestBidder;
	double bidTooLow = 23;
	double theNextBid = 41;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myTestItem = new ItemModel("Shirt", 10, "Blue shirt");
		myTestBidder = new BidderModel("brendo", UserModel.UserType.BIDDER);
	}

	/**
	 * Test method for {@link ItemModel#Item(java.lang.String, double, java.lang.String)}.
	 */
	@Test
	public void testItemConstructor() {
		assertEquals(myTestItem.getItemName(), "Shirt");
	}

	@Test
	public void testItemBidAddedOneBid() {
		myTestItem.bidOnItem(myTestBidder, theNextBid);
		myTestItem.getBids().get(myTestBidder);
		assertTrue(myTestItem.getBids().get(myTestBidder).doubleValue() == theNextBid);			
	}
	
	@Test
	public void testItemBidBidChanged() {
		int numBids = myTestItem.getBids().size();
		myTestItem.bidOnItem(myTestBidder, theNextBid);
		assertEquals(numBids + 1, myTestItem.getBids().size());			
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
