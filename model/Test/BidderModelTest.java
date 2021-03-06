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
 * Test methods for the BidderModel class.
 * 
 * @author Shannon
 *
 */
public class BidderModelTest {
	BidderModel myTestBidder;

	/**
	 * @throws java.lang.Exception
	 * 
	 * @author Shannon
	 */
	@Before
	public void setUp() throws Exception {
		myTestBidder = new BidderModel("Shannon", UserModel.UserType.BIDDER);
	}

	/**
	 * Test method for the Constructor.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testBidderModel() {
		assertEquals(myTestBidder.getUserName(), "Shannon");
		assertEquals(myTestBidder.getUserType(), UserModel.UserType.BIDDER);
	}

	/**
	 * Test method for the getBids() method.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testGetBids() {
		Map<ItemModel, Double> bids = new HashMap<>();
		ItemModel item = new ItemModel("laptop", 200, "Dell");
		bids.put(item, 250.00);
		myTestBidder.addBid(item, 250);
		assertEquals(myTestBidder.getBids(), bids);
	}

	/**
	 * Test method for the addBid() method.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testAddBid() {
		Map<ItemModel, Double> bids = new HashMap<>();
		ItemModel item = new ItemModel("laptop", 200, "Dell");
		bids.put(item, 250.00);
		myTestBidder.addBid(item, 250);
		assertEquals(myTestBidder.getBids(), bids);
	}
}
