package model;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class CalendarModelTest {
	CalendarModel myCalendar;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myCalendar = new CalendarModel();
	}
	
	@Test
	public void testAddAuction()
	{
		AuctionModel testAuction1 = new AuctionModel("testOrg1" , "testAuction1", LocalDateTime.of(2015, 11, 20, 11, 30), LocalDateTime.of(2015, 11, 20, 13, 30));
		assertTrue("Auction added.", myCalendar.addAuction(testAuction1));
		assertNotEquals(myCalendar.toString(), "");
	}
	@Test
	public void testNinetyDayBusinessRule()
	{
		AuctionModel pastNinetyDays = new AuctionModel("testOrg" , "testAuction", LocalDateTime.of(2015, 2, 11, 11, 30), LocalDateTime.of(2015, 2, 11, 13, 30));
		assertFalse("Past 90 days", myCalendar.addAuction(pastNinetyDays));
	}
	
	@Test
	public void testWeekBusinessRule()
	{
		AuctionModel testAuction1 = new AuctionModel("testOrg1" , "testAuction1", LocalDateTime.of(2015, 11, 20, 11, 30), LocalDateTime.of(2015, 11, 20, 13, 30));
		AuctionModel testAuction2 = new AuctionModel("testOrg2" , "testAuction2", LocalDateTime.of(2015, 11, 21, 11, 30), LocalDateTime.of(2015, 11, 21, 13, 30));
		AuctionModel testAuction3 = new AuctionModel("testOrg3" , "testAuction3", LocalDateTime.of(2015, 11, 21, 6, 30), LocalDateTime.of(2015, 11, 21, 8, 30));
		AuctionModel testAuction4 = new AuctionModel("testOrg4" , "testAuction4", LocalDateTime.of(2015, 11, 22, 11, 30), LocalDateTime.of(2015, 11, 22, 13, 30));
		AuctionModel testAuction5 = new AuctionModel("testOrg5" , "testAuction5", LocalDateTime.of(2015, 11, 24, 11, 30), LocalDateTime.of(2015, 11, 24, 13, 30));
		AuctionModel testAuction6 = new AuctionModel("testOrg6" , "testAuction6", LocalDateTime.of(2015, 11, 23, 11, 30), LocalDateTime.of(2015, 11, 23, 13, 30));
		AuctionModel testAuction7 = new AuctionModel("testOrg7" , "testAuction7", LocalDateTime.of(2015, 11, 24, 16, 30), LocalDateTime.of(2015, 11, 24, 20, 30));
		
		assertTrue("Auction added.", myCalendar.addAuction(testAuction1));
		assertTrue("Auction added.", myCalendar.addAuction(testAuction2));
		assertTrue("Auction added.", myCalendar.addAuction(testAuction3));
		assertTrue("Auction added.", myCalendar.addAuction(testAuction4));
		assertTrue("Auction added.", myCalendar.addAuction(testAuction5));
		assertFalse("Auction not added.", myCalendar.addAuction(testAuction6));
		assertTrue("Auction added.", myCalendar.addAuction(testAuction7));
	}
	
	@Test
	public void testFutureAuctionsBusinessRule()
	{
		AuctionModel[] testAuctions = new AuctionModel[30];
		LocalDateTime firstDayStart = LocalDateTime.of(2015, 11, 20, 11, 30);
		LocalDateTime firstDayEnd = firstDayStart.plusHours(2);
		
		for(int i = 0; i < 30; i++)
		{
			testAuctions[i] = new AuctionModel("testOrg" + i, "testAuction" + i, firstDayStart.plusDays(i), firstDayEnd.plusDays(i));
		}
		
		for(int i = 0; i < 25; i++)
		{
			assertTrue("Auction added", myCalendar.addAuction(testAuctions[i]));
		}
		
		for(int i = 25; i < 30; i++)
		{
			assertFalse("Auction not added.", myCalendar.addAuction(testAuctions[i]));
		}
	}
	
	@Test
	public void testNumberOfAuctionsPerDayBusinessRule()
	{
		AuctionModel testAuction1 = new AuctionModel("testOrg1" , "testAuction1", LocalDateTime.of(2015, 11, 20, 5, 30), LocalDateTime.of(2015, 11, 20, 7, 30));
		AuctionModel testAuction2 = new AuctionModel("testOrg2" , "testAuction2", LocalDateTime.of(2015, 11, 20, 11, 30), LocalDateTime.of(2015, 11, 20, 12, 30));
		AuctionModel testAuction3 = new AuctionModel("testOrg3" , "testAuction3", LocalDateTime.of(2015, 11, 20, 15, 30), LocalDateTime.of(2015, 11, 20, 17, 30));
		AuctionModel testAuction4 = new AuctionModel("testOrg4" , "testAuction4", LocalDateTime.of(2015, 11, 20, 21, 30), LocalDateTime.of(2015, 11, 20, 22, 30));
		
		assertTrue("Auction added.", myCalendar.addAuction(testAuction1));
		assertTrue("Auction added.", myCalendar.addAuction(testAuction2));
		assertFalse("Auction not added.", myCalendar.addAuction(testAuction3));
		assertFalse("Auction not added.", myCalendar.addAuction(testAuction4));
	}
	
	@Test
	public void testTwoHourBusinessRule()
	{
		AuctionModel testAuction1 = new AuctionModel("testOrg1" , "testAuction1", LocalDateTime.of(2015, 11, 20, 5, 30), LocalDateTime.of(2015, 11, 20, 7, 30));
		AuctionModel testAuction2 = new AuctionModel("testOrg2" , "testAuction2", LocalDateTime.of(2015, 11, 20, 11, 30), LocalDateTime.of(2015, 11, 20, 12, 30));
		
		assertTrue(myCalendar.addAuction(testAuction1));
		assertTrue(myCalendar.addAuction(testAuction2));
		
		AuctionModel testAuction3 = new AuctionModel("testOrg3" , "testAuction3", LocalDateTime.of(2015, 11, 21, 5, 30), LocalDateTime.of(2015, 11, 21, 7, 30));
		AuctionModel testAuction4 = new AuctionModel("testOrg4" , "testAuction4", LocalDateTime.of(2015, 11, 21, 8, 30), LocalDateTime.of(2015, 11, 21, 12, 30));
		AuctionModel testAuction5 = new AuctionModel("testOrg5" , "testAuction5", LocalDateTime.of(2015, 11, 21, 9, 30), LocalDateTime.of(2015, 11, 21, 12, 30));
		
		assertTrue(myCalendar.addAuction(testAuction3));
		assertFalse(myCalendar.addAuction(testAuction4));
		assertTrue(myCalendar.addAuction(testAuction5));
		
		AuctionModel testAuction6 = new AuctionModel("testOrg6" , "testAuction6", LocalDateTime.of(2015, 11, 30, 11, 30), LocalDateTime.of(2015, 11, 30, 13, 30));
		AuctionModel testAuction7 = new AuctionModel("testOrg7" , "testAuction7", LocalDateTime.of(2015, 11, 30, 8, 30), LocalDateTime.of(2015, 11, 30, 12, 30));
		AuctionModel testAuction8 = new AuctionModel("testOrg8" , "testAuction8", LocalDateTime.of(2015, 11, 30, 7, 30), LocalDateTime.of(2015, 11, 30, 9, 30));
		
		assertTrue(myCalendar.addAuction(testAuction6));
		assertFalse(myCalendar.addAuction(testAuction7));
		assertTrue(myCalendar.addAuction(testAuction8));
	}
	

}
