package model;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * Tests for the AuctionModel class.
 *
 */
public class AuctionModelTest {

	AuctionModel testAuction;
	AuctionModel testAuctionWithItems;
	LocalDateTime now = LocalDateTime.now();
	
	/**
	 * @throws java.lang.Exception
	 * @author Shannon
	 */
	@Before
	public void setUp() throws Exception {
		testAuction = new AuctionModel("My Org Name", "steve", now.plusDays(2), now.plusDays(2).plusHours(2));
	
		List<ItemModel> inventory = new ArrayList<ItemModel>();
		inventory.add(new ItemModel("item", 20, "cool item"));
		testAuctionWithItems = new AuctionModel("My Org Name", "steve", now.plusDays(2), now.plusDays(2).plusHours(2), inventory);
	}
	
	/**
	 * Test method for {@link Auction#Auction(java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime, java.util.List)}.
	 * @author Shannon
	 */
	@Test
	public void testAuctionModelCopyConstructorWithItems() {
		List<ItemModel> inventory = new ArrayList<ItemModel>();
		inventory.add(new ItemModel("item", 20, "cool item"));
		assertEquals(testAuctionWithItems.getAuctionItems().get(0).getItemName(), inventory.get(0).getItemName());
	}

	/**
	 * Test method for the getAuctionOrg() method.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testGetAuctionOrg() {
		assertEquals(testAuction.getAuctionOrg(), "My Org Name");
	}
	
	/**
	 * Test method for the setAuctionOrg() method.
	 * @author Shannon
	 */
	@Test
	public void testSetAuctionOrg() {
		testAuction.setAuctionOrg("Cats");
		assertEquals(testAuction.getAuctionOrg(), "Cats");
	}
	
	/**
	 * Test method for {@link AuctionModel#addItem(ItemModel)}.
	 * 
	 * @author 
	 */
	@Test
	public void testAddItem() {
		ItemModel myItem = new ItemModel("Puppy", 120, "Happy puppy ready for a new home");
		int beforeAuctionSize = testAuction.getAuctionItems().size();
		testAuction.addItem(myItem);
		int afterAuctionSize = testAuction.getAuctionItems().size();
		assertEquals(beforeAuctionSize + 1, afterAuctionSize);
	}

	/**
	 * Test method for {@link Auction#getStartTime()}.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testGetStartTime() {
		assertEquals(testAuction.getStartTime(), now.plusDays(2));
	}

	/**
	 * Test method for {@link Auction#setStartTime(java.time.LocalDateTime)}.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testSetStartTime() {
		testAuction.setStartTime(now.plusHours(2).plusMinutes(45));
		assertEquals(testAuction.getStartTime(), now.plusHours(2).plusMinutes(45));
	}

	/**
	 * Test method for {@link Auction#getEndTime()}.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testGetEndTime() {
		assertEquals(testAuction.getEndTime(), now.plusDays(2).plusHours(2));
	}

	/**
	 * Test method for {@link Auction#setEndTime(java.time.LocalDateTime)}.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testSetEndTime() {
		testAuction.setEndTime(now.plusHours(6));
		assertEquals(testAuction.getEndTime(), now.plusHours(6));
	}

	/**
	 * Test method for {@link Auction#setAuctionDate(java.time.LocalDateTime, java.time.LocalDateTime)}.
	 *
	 * @author Shannon
	 */
	@Test
	public void testSetAuctionDate() {
		testAuction.setAuctionDate(now.plusDays(3), now.plusDays(3).plusHours(3));
		assertEquals(testAuction.getStartTime(), now.plusDays(3));
		assertEquals(testAuction.getEndTime(), now.plusDays(3).plusHours(3));
	}

	/**
	 * Test method for the getAuctionName() method.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testGetAuctionName() {
		assertEquals(testAuction.getAuctionName(), "My-Org-Name-DECEMBER-2-2015");
	}
	
	/**
	 * Test method for the removeItem() method.
	 * @author Shannon
	 */
	@Test
	public void testRemoveItem() {
		ItemModel item = testAuctionWithItems.getAuctionItems().get(0);
		testAuctionWithItems.removeItem(item);
		assertTrue(testAuctionWithItems.getAuctionItems().isEmpty());
	}
	
	/**
	 * Test method for the getUserName() method.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testGetUserName() {
		assertEquals(testAuction.getUserName(), "steve");
	}
	
	// Do we need to test toString methods?? TODO
}
