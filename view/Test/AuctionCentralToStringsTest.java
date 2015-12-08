package view.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AuctionModel;
import model.ItemModel;

import org.junit.Before;
import org.junit.Test;

import view.AuctionCentralToStrings;

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
	 * Tests to make sure that the toString method for an auction returns what we expect.
	 * 
	 * @author Shannon
	 */
	@Test
	public void testAuctionToString() {
		ArrayList<ItemModel> items = new ArrayList<>();
		items.add(new ItemModel("Cat", 10, "Fluffy"));
		AuctionModel auction = new AuctionModel("Goodwill", "shmurphy", myTime,
				myTime.plusHours(2), items);
		LocalDateTime startDateTime = auction.getStartTime();
		LocalDateTime endDateTime = auction.getEndTime();
		assertEquals(AuctionCentralToStrings.auctionToString(auction), "Name: " + auction.getAuctionName() + "\n" +
				"Organization name: " + auction.getAuctionOrg() + "\n" + 
				 "Date: " + startDateTime.getMonth() + " " + startDateTime.getDayOfMonth() +", " + startDateTime.getYear() + "\n" +
				"Start time: " + startDateTime.getHour() + ":" + startDateTime.getMinute() + "\n" +
				"End: " + endDateTime.getHour() + ":" + endDateTime.getMinute() + "\n" +
				"Items: " + items.size()
				);
	}

	/**
	 * Tests that the toString for the items returns what we expect.
	 */
	@Test
	public void testItemToString() {
		ItemModel item = new ItemModel("Cat", 20, "Fluffy");
		assertEquals(AuctionCentralToStrings.itemToString(item), "Name: " + item.getItemName() + "\n" +
		"Description: " + item.getDescription() + "\n" + 
		"Starting Bid: $" + String.format("%.2f", item.getStartingBid()) + "\n");
	}

}
