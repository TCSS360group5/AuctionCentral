package model;
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
public class AuctionModelTest {

	AuctionModel testAuction;
	AuctionModel testAuctionWithItems;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LocalDateTime now = LocalDateTime.now();
		testAuction = new AuctionModel("My Org Name", "steve", now.plusDays(2), now.plusDays(2).plusHours(2));
	}

//	/**
//	 * Test method for {@link Auction#Auction(java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime)}.
//	 */
//	@Test
//	public void testAuctionStringStringLocalDateTimeLocalDateTime() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link Auction#Auction(java.lang.String, java.lang.String, java.time.LocalDateTime, java.time.LocalDateTime, java.util.List)}.
//	 */
//	@Test
//	public void testAuctionStringStringLocalDateTimeLocalDateTimeListOfItem() {
//		fail("Not yet implemented"); // TODO
//	}


	/**
	 * Test method for {@link AuctionModel#addItem(ItemModel)}.
	 */
	@Test
	public void testAddItem() {
		ItemModel myItem = new ItemModel("Puppy", 120, "Happy puppy ready for a new home");
		int beforeAuctionSize = testAuction.getAuctionItems().size();
		testAuction.addItem(myItem);
		int afterAuctionSize = testAuction.getAuctionItems().size();
		assertEquals(beforeAuctionSize + 1, afterAuctionSize);
	}

//	/**
//	 * Test method for {@link Auction#getStartTime()}.
//	 */
//	@Test
//	public void testGetStartTime() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link Auction#setStartTime(java.time.LocalDateTime)}.
//	 */
//	@Test
//	public void testSetStartTime() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link Auction#getEndTime()}.
//	 */
//	@Test
//	public void testGetEndTime() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link Auction#setEndTime(java.time.LocalDateTime)}.
//	 */
//	@Test
//	public void testSetEndTime() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	/**
//	 * Test method for {@link Auction#setAuctionDate(java.time.LocalDateTime, java.time.LocalDateTime)}.
//	 */
//	@Test
//	public void testSetAuctionDate() {
//		fail("Not yet implemented"); // TODO
//	}

}
