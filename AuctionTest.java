import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Cox Family
 *
 */
public class AuctionTest {

	Auction testAuction;
	Auction testAuctionWithItems;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		testAuction = new Auction("My Org Name", "steve", now.plusDays(2), now.plusDays(2).plusHours(2));
	}

	/**
	 * Test method for {@link Auction#Auction(java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime)}.
	 */
	@Test
	public void testAuctionStringStringLocalDateTimeLocalDateTime() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#Auction(java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime, java.util.List)}.
	 */
	@Test
	public void testAuctionStringStringLocalDateTimeLocalDateTimeListOfItem() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#getAuctionItems()}.
	 */
	@Test
	public void testGetAuctionItems() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#addItem(Item)}.
	 */
	@Test
	public void testAddItem() {
		Item myItem = new Item("Puppy", 120, "Happy puppy ready for a new home");
		testAuction.addItem(myItem);
	}

	/**
	 * Test method for {@link Auction#editItemName(Item, java.lang.String)}.
	 */
	@Test
	public void testEditItemName() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#editItemStartingBid(Item, double)}.
	 */
	@Test
	public void testEditItemStartingBid() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#editItemDescription(Item, java.lang.String)}.
	 */
	@Test
	public void testEditItemDescription() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#getStartTime()}.
	 */
	@Test
	public void testGetStartTime() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#setStartTime(java.time.LocalDateTime)}.
	 */
	@Test
	public void testSetStartTime() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#getEndTime()}.
	 */
	@Test
	public void testGetEndTime() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#setEndTime(java.time.LocalDateTime)}.
	 */
	@Test
	public void testSetEndTime() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Auction#setAuctionDate(java.time.LocalDateTime, java.time.LocalDateTime)}.
	 */
	@Test
	public void testSetAuctionDate() {
		fail("Not yet implemented"); // TODO
	}

}
