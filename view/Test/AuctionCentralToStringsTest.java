package view;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AuctionModel;
import model.ItemModel;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the AuctionCentralToString class.
 * @author TCSS 360 Group 5
 *
 */
public class AuctionCentralToStringsTest {

	AuctionCentralToStrings myTestString;
	AuctionModel myTestModel;
	LocalDateTime myTime = LocalDateTime.now();
	
	/**
	 * 
	 * @throws java.lang.Exception
	 * @author Shannon
	 */
	@Before
	public void setUp() throws Exception {
		myTestString = new AuctionCentralToStrings();
	}

	/**
	 * Test method for {@link view.AuctionCentralToStrings#auctionToString(model.AuctionModel)}.
	 */
	@Test
	public void testAuctionToString() {
		ArrayList<ItemModel> items = new ArrayList<>();
		items.add(new ItemModel("Cat", 10, "Fluffy"));
		AuctionModel auction = new AuctionModel("Goodwill", "shmurphy", myTime,
				myTime.plusHours(2), items);
		LocalDateTime startDateTime = auction.getStartTime();
		LocalDateTime endDateTime = auction.getEndTime();
		assertEquals(myTestString.auctionToString(auction), "Name: " + auction.getAuctionName() + "\n" +
				"Organization name: " + auction.getAuctionOrg() + "\n" + 
				 "Date: " + startDateTime.getMonth() + " " + startDateTime.getDayOfMonth() +", " + startDateTime.getYear() + "\n" +
				"Start time: " + startDateTime.getHour() + ":" + startDateTime.getMinute() + "\n" +
				"End: " + endDateTime.getHour() + ":" + endDateTime.getMinute() + "\n" +
				"Items: " + items.size()
				);
	}

	/**
	 * Test method for {@link view.AuctionCentralToStrings#itemToString(model.ItemModel)}.
	 */
	@Test
	public void testItemToString() {
		ItemModel item = new ItemModel("Cat", 20, "Fluffy");
		assertEquals(myTestString.itemToString(item), "Name: " + item.getItemName() + "\n" +
		"Description: " + item.getDescription() + "\n" + 
		"Starting Bid: " + String.format("%.2f", item.getStartingBid()) + "\n");
	}

}
