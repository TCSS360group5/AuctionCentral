package model.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import model.BidderModel;
import model.ItemModel;
import model.UserModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the ItemModel test.
 * 
 * @author TCSS 360 Group 5
 */
public class ItemModelTest {

	ItemModel myTestItem;
	BidderModel myTestBidder;
	double bidTooLow = 23;
	double theNextBid = 41;
	
	/**
	 * Sets up the fields for the tests.
	 * 
	 * @throws java.lang.Exception
	 * 
	 * @author Brendan, Shannon
	 */
	@Before
	public void setUp() throws Exception {
		myTestItem = new ItemModel("Shirt", 10, "Blue shirt");
		myTestBidder = new BidderModel("brendo", UserModel.UserType.BIDDER);
	}

	/**
	 * Test method for the ItemModel constructor.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testItemConstructor() {
		assertEquals(myTestItem.getItemName(), "Shirt");
	}
	
	/**
	 * Test method for making sure the bids are updated when the item is bidded on.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testItemBidBidChanged() {
		int numBids = myTestItem.getBids().size();
		myTestItem.bidOnItem(myTestBidder, theNextBid);
		assertEquals(numBids + 1, myTestItem.getBids().size());			
	}


	/**
	 * Test method for the getBids() methods.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testGetBids() {
		myTestItem.bidOnItem(myTestBidder, theNextBid);
		Map<UserModel, Double> bids = new HashMap<>();
		bids.put(myTestBidder, theNextBid);
		assertEquals(myTestItem.getBids(), bids);
	}

	/**
	 * Test method for bidOnItem() method.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testBidOnItem() {
		myTestItem.bidOnItem(myTestBidder, theNextBid);
		assertEquals(myTestItem.getBids().get(myTestBidder).doubleValue(), theNextBid, 0);	
	}

	/**
	 * Test method for the updateBid() method.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testUpdateBid() {
		myTestItem.updateBid(myTestBidder, 50);
		assertEquals(myTestItem.getBids().get(myTestBidder).doubleValue(), 50, 0);
	}
}
