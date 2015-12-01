/**
 * 
 */
package model;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
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
	 * Test method for {@link model.BidderModel#BidderModel(java.lang.String, model.UserModel.UserType)}.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testBidderModel() {
		assertEquals(myTestBidder.getUserName(), "Shannon");
		assertEquals(myTestBidder.getUserType(), UserModel.UserType.BIDDER);
	}

	/**
	 * Test method for {@link model.BidderModel#getBids()}.
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
	 * Test method for {@link model.BidderModel#addBid(model.ItemModel, double)}.
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
