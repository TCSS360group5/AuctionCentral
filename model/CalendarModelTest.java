package model;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import exceptions.*;

public class CalendarModelTest {
	CalendarModel myCalendar;
	
	/**
	 * @throws java.lang.Exception
	 */
	
	int maxFutureAuctions;
	int maxDaysInFuture;
	int maxAuctionsPerWeek;
	int maxAuctionsPerDay;

	@Before
	public void setUp() throws Exception {
		myCalendar = new CalendarModel();
		maxFutureAuctions = AuctionsAtCapacityException.MAX_FUTURE_AUCTIONS;
		maxDaysInFuture = AuctionTooFarAwayException.MAX_DAYS_AWAY;
		maxAuctionsPerWeek = AuctionsAtCapacityForWeekException.MAX_AUCTIONS_FOR_WEEK;
		maxAuctionsPerDay = AuctionsPerDayException.MAX_AUCTIONS_PER_DAY;
	}
	
	@Test
	public void testAddAuctionOnEmptyCalendar()
	{
		boolean exceptionThrown;
		
		assertEquals(myCalendar.getAllFutureAuctions().size(), 0);
		AuctionModel auctionToAdd = new AuctionModel("testOrg", "testUserName", LocalDateTime.of(2015, 12, 25, 12, 30), LocalDateTime.of(2015, 12, 25, 14, 30));
		try
		{
			myCalendar.addAuction(auctionToAdd);
			exceptionThrown = false;
		}
		catch(Exception e)
		{
			exceptionThrown = true;
		}
		assertEquals(myCalendar.getAllFutureAuctions().size(), 1);
		assertFalse(exceptionThrown);
	}
	
	@Test
	public void testAddAuctionOnCalendarWithAuctionsAtCapacity()
	{
		boolean errorMessage;
		LocalDateTime startDate = LocalDateTime.now();
		
		for(int i = 1; i <= maxFutureAuctions; i++)
		{
			try
			{
				myCalendar.addAuction(new AuctionModel("testOrg" + i, "testUserName" + i, startDate.plusDays(i), startDate.plusDays(i).plusHours(1)));
			}
			catch(Exception e)
			{
				errorMessage = false;
			}
		}
		
		assertEquals(myCalendar.getAllFutureAuctions().size(), maxFutureAuctions);
		try
		{
			myCalendar.addAuction(new AuctionModel("testOrg", "testUserName", startDate.plusDays(maxFutureAuctions + 1),  startDate.plusDays(maxFutureAuctions + 1).plusHours(1)));
			errorMessage = false;
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("The number of future auctions is already at its capacity of " + maxFutureAuctions))
			{
				errorMessage = true;
			}
			else
				errorMessage = false;
		}
		
		assertTrue(errorMessage);
		assertEquals(myCalendar.getAllFutureAuctions().size(), maxFutureAuctions);
	}
	
	@Test
	public void testAddAuctionOnCalendarWithAuctionsOneBelowCapacity()
	{	
		boolean exceptionThrown;
		LocalDateTime startDate = LocalDateTime.now();
		
		for(int i = 1; i < maxFutureAuctions; i++)
		{
			try
			{
				myCalendar.addAuction(new AuctionModel("testOrg" + i, "testUserName" + i, startDate.plusDays(i), startDate.plusDays(i).plusHours(1)));
			}
			catch(Exception e)
			{
				exceptionThrown = true;
			}
		}
		assertEquals(myCalendar.getAllFutureAuctions().size(), (maxFutureAuctions - 1));
		
		try
		{
			myCalendar.addAuction(new AuctionModel("testOrg", "testUserName", startDate.plusDays(maxFutureAuctions), startDate.plusDays(maxFutureAuctions).plusHours(1)));
			exceptionThrown = false;
		}
		catch(Exception e)
		{
			exceptionThrown = true;
		}
		
		assertEquals(myCalendar.getAllFutureAuctions().size(), maxFutureAuctions);
		assertFalse(exceptionThrown);
	}
	
	@Test
	public void testAddAuctionOnAuctionDateLessThanSpecifiedDaysInFuture()
	{
		boolean exceptionThrown;
		assertEquals(myCalendar.getAllFutureAuctions().size(), 0);
		AuctionModel auctionToAdd = new AuctionModel("testOrg", "testUserName", LocalDateTime.now().plusDays(maxDaysInFuture - 10), LocalDateTime.now().plusDays(maxDaysInFuture - 10).plusHours(2));
		try
		{
			myCalendar.addAuction(auctionToAdd);
			exceptionThrown = false;
		}
		catch(Exception e)
		{
			exceptionThrown = true;
		}
		assertEquals(myCalendar.getAllFutureAuctions().size(), 1);
		assertFalse(exceptionThrown);
		
	}
	
	@Test
	public void testAddAuctionOnAuctionDateAtNumberOfSpecifiedDaysInFuture()
	{
		boolean exceptionThrown;
		assertEquals(myCalendar.getAllFutureAuctions().size(), 0);
		
		try
		{
			myCalendar.addAuction(new AuctionModel("testOrg", "testUserName", LocalDateTime.now().plusDays(maxDaysInFuture),  LocalDateTime.now().plusDays(maxDaysInFuture).plusHours(1)));
			exceptionThrown = false;
		}
		catch(Exception e)
		{
			exceptionThrown = true;
		}
		
		assertFalse(exceptionThrown);
		assertEquals(myCalendar.getAllFutureAuctions().size(), 1);
		
	}
	
	@Test
	public void testAddAuctionOnAuctionDateMoreThanSpecifiedDaysInFuture()
	{
		boolean errorMessage;
		assertEquals(myCalendar.getAllFutureAuctions().size(), 0);
		
		try
		{
			myCalendar.addAuction(new AuctionModel("testOrg", "testUserName", LocalDateTime.now().plusDays(maxDaysInFuture).plusDays(1),  LocalDateTime.now().plusDays(maxDaysInFuture).plusDays(1).plusHours(1)));
			errorMessage = false;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			if(e.getMessage().equals("An auction may not be scheduled more than " + maxDaysInFuture + " days in the future."))
			{
				errorMessage = true;
			}
			else
				errorMessage = false;
		}
		
		assertTrue(errorMessage);
		assertEquals(myCalendar.getAllFutureAuctions().size(), 0);
		
	}
	
	/*
	@Test
	public void testAddAuctionOnWeekCapacityLessThanOne()
	{
		LocalDateTime weekStart = LocalDateTime.now();
		LocalDateTime midWeek = LocalDateTime.
				
		if(maxAuctionsPerWeek >= (maxAuctionsPerDay * 7))
		{
			
		}
		else
		{
			
		}
		for(int i = 1; i < maxAuctionsPerWeek; i++)
		{
			
		}
	}*/
	
	
	
	//@Test
	//public void testAddAuctionOnAuctionDateWithOneLessThanSpecifiedWeekCapacity
	
	/*
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
	}*/
	

}