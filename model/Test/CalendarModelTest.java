package model.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import model.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for the CalendarModel class. Tests all of the business rules.
 * 
 * @author Demetra Loulias, UWT Group 5
 */
public class CalendarModelTest {
	
	// calendar to add to
	CalendarModel myCalendar;
	// tomorrow's localdatetime starting at midnight, makes tests less time-dependent on when they are run
	LocalDateTime dayStart;

	@Before
	public void setUp()
	{
		myCalendar = new CalendarModel();
		dayStart = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0));
	}
	
	/**
	 * Add one auction to an empty calendar.
	 * 
	 * @result the Auction will successfully be added to the calendar.
	 */
	@Test
	public void testAddAuctionOnEmptyCalendar()
	{
		boolean exceptionThrown;
		
		// no auctions initially in calendar
		assertEquals(myCalendar.numFutureAuctions(), 0);
		
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
		
		// number of auctions increases by 1
		assertEquals(myCalendar.numFutureAuctions(), 1);
		// no exception thrown
		assertFalse(exceptionThrown);
	}
	
	/**
	 * Add all 25 auctions, the try to add another one.
	 * 
	 * @result the final auction will not be added.
	 */
	@Test
	public void testAddAuctionOnCalendarWithAuctionsAtCapacity()
	{
		boolean errorMessage;
		
		// add 25 auctions
		for(int i = 1; i <= 25; i++)
		{
			try
			{
				myCalendar.addAuction(new AuctionModel("testOrg" + i, "testUserName" + i, dayStart.plusDays(i), dayStart.plusDays(i).plusHours(1)));
			}
			catch(Exception e)
			{
				
			}
		}
		
		// there should now be 25 auctions in the calendar
		assertEquals(myCalendar.numFutureAuctions(), 25);
		
		// add one more auction
		try
		{
			myCalendar.addAuction(new AuctionModel("testOrg", "testUserName", dayStart.plusDays(25 + 1),  dayStart.plusDays(25 + 1).plusHours(1)));
			errorMessage = false;
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("The number of future auctions is already at its capacity of 25."))
			{
				// true if this error message is received
				errorMessage = true;
			}
			else
				errorMessage = false;
		}
		
		// assert proper error message
		assertTrue(errorMessage);
		// assert auction actually not added
		assertEquals(myCalendar.numFutureAuctions(), 25);
	}
	
	/**
	 * Add 24 auctions, then add one more.
	 * 
	 * @result the auction will be successfully added.
	 */
	@Test
	public void testAddAuctionOnCalendarWithAuctionsOneBelowCapacity()
	{	
		boolean exceptionThrown;
		
		// add 24 auctions
		for(int i = 1; i < 25; i++)
		{
			try
			{
				myCalendar.addAuction(new AuctionModel("testOrg" + i, "testUserName" + i, dayStart.plusDays(i), dayStart.plusDays(i).plusHours(1)));
			}
			catch(Exception e)
			{
				exceptionThrown = true;
			}
		}
		
		// assert that all auctions have been added
		assertEquals(myCalendar.numFutureAuctions(), 24);
		
		// add one final auction
		try
		{
			myCalendar.addAuction(new AuctionModel("testOrg", "testUserName", dayStart.plusDays(25), dayStart.plusDays(25).plusHours(1)));
			exceptionThrown = false;
		}
		catch(Exception e)
		{
			exceptionThrown = true;
		}
		
		// assert that final auction was actually added
		assertEquals(myCalendar.numFutureAuctions(), 25);
		// assert that no exception was thrown
		assertFalse(exceptionThrown);
	}
	
	/**
	 * Add an auction less than 90 days in the future.
	 * 
	 * @result the auction will be successfully added.
	 */
	@Test
	public void testAddAuctionOnAuctionDateLessThanSpecifiedDaysInFuture()
	{
		boolean exceptionThrown;
		
		// assert empty calendar
		assertEquals(myCalendar.getAllFutureAuctions().size(), 0);
		
		// create auction 80 days in the future
		AuctionModel auctionToAdd = new AuctionModel("testOrg", "testUserName", dayStart.plusDays(80), dayStart.plusDays(80).plusHours(2));
		try
		{
			myCalendar.addAuction(auctionToAdd);
			exceptionThrown = false;
		}
		catch(Exception e)
		{
			exceptionThrown = true;
		}
		
		// assert that auction was actually added
		assertEquals(myCalendar.numFutureAuctions(), 1);
		// assert no error thrown
		assertFalse(exceptionThrown);
	}
	
	/**
	 * Add an auction 90 days in the future.
	 * 
	 * @result the auction will be successfully added.
	 */
	@Test
	public void testAddAuctionOnAuctionDateAtNumberOfSpecifiedDaysInFuture()
	{
		boolean exceptionThrown;
		
		//assert empty calendar
		assertEquals(myCalendar.getAllFutureAuctions().size(), 0);
		
		try
		{
			// add auction 90 days in future (dayStart is already 1 day ahead of current day, so add 89
			myCalendar.addAuction(new AuctionModel("testOrg", "testUserName", dayStart.plusDays(89), dayStart.plusDays(89).plusHours(1)));
			exceptionThrown = false;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			exceptionThrown = true;
		}
		
		// assert no exception thrown
		assertFalse(exceptionThrown);
		// assert auction was acutally added
		assertEquals(myCalendar.getAllFutureAuctions().size(), 1);
		
	}
	
	/**
	 * Add an auction 91 days in the future.
	 * 
	 * @return the auction will not be added.
	 */
	@Test
	public void testAddAuctionOnAuctionDateMoreThanSpecifiedDaysInFuture()
	{
		boolean errorMessage;
		
		// assert empty calendar
		assertEquals(myCalendar.getAllFutureAuctions().size(), 0);
		
		try
		{
			// add auction 90 days in future
			myCalendar.addAuction(new AuctionModel("testOrg", "testUserName", dayStart.plusDays(89).plusDays(1),  dayStart.plusDays(89).plusDays(1).plusHours(1)));
			errorMessage = false;
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("An auction may not be scheduled more than " + 90 + " days in the future. Or anytime in the past."))
			{
				// only true if this error message is received
				errorMessage = true;
			}
			else
				errorMessage = false;
		}
		
		// assert proper error message received
		
		assertTrue(errorMessage);
		// assert auction wasn't added
		assertEquals(myCalendar.numFutureAuctions(), 0);
		
	}
	
	/**
	 * Add auctions with exactly 2 hours between them.
	 * 
	 * @result the auction will be successfully added.
	 */
	@Test
	public void testAddAuctionOnAuctionsWithTwoHoursBetween()
	{
		boolean firstAdded;
		boolean secondAdded;
		
		// auctions to add
		AuctionModel testAuction1 = new AuctionModel("testOrg1", "testUserName1", dayStart, dayStart.plusHours(3));
		AuctionModel testAuction2 = new AuctionModel("testOrg2", "testUserName2", dayStart.plusHours(5), dayStart.plusHours(6));
		
		// add first auction
		try
		{
			myCalendar.addAuction(testAuction1);
			firstAdded = true;
		}
		catch(Exception e)
		{
			firstAdded = false;
		}
		
		// assert auction actually added to calendar
		assertEquals(myCalendar.numFutureAuctions(),1);
		// assert no exception thrown
		assertTrue(firstAdded);
		
		// add second auction
		try
		{
			myCalendar.addAuction(testAuction2);
			secondAdded = true;
		}
		catch(Exception e)
		{
			secondAdded = false;
		}
		
		// assert no exception thrown
		assertTrue(secondAdded);
		// assert second auction actually added
		assertEquals(2, myCalendar.numFutureAuctions());
	}
	
	/**
	 * Add two auctions with 1 hour and 59 minutes between them.
	 * 
	 * @result the second auction will not be added.
	 */
	@Test
	public void testAddAuctionOnAuctionsWithOneMinuteUnderTimeBetween()
	{
		boolean firstAdded;
		boolean errorMessage;

		// auctions to add
		AuctionModel testAuction1 = new AuctionModel("testOrg1", "testUserName1", dayStart, dayStart.plusHours(3));
		AuctionModel testAuction2 = new AuctionModel("testOrg2", "testUserName2", dayStart.plusHours(5).minusMinutes(1), dayStart.plusHours(6));
		
		// add first auction
		try
		{
			myCalendar.addAuction(testAuction1);
			firstAdded = true;
		}
		catch(Exception e)
		{
			firstAdded = false;
		}
		
		// assert no exception thrown
		assertTrue(firstAdded);
		// assert auction added
		assertEquals(1, myCalendar.numFutureAuctions());
		
		// add second auction
		try
		{
			myCalendar.addAuction(testAuction2);
			errorMessage = false;
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("There must be at least " + 2 + " hours between the end of one auction and the start of another."))
			{
				// true if this particular error message received
				errorMessage = true;
			}
			else
				errorMessage = false;
		}
		
		// assert proper error message
		assertTrue(errorMessage);
		// assert auction not actually added
		assertEquals(1, myCalendar.numFutureAuctions());
	}
	
	/**
	 * Add two auctions with 2 hours and 1 minute between them.
	 * 
	 * @result both auctions will be added.
	 */
	@Test
	public void testAddAuctionOnAuctionsWithOneMinuteOverTimeBetween()
	{
		boolean firstAdded;
		boolean secondAdded;

		// auctions to be added
		AuctionModel testAuction1 = new AuctionModel("testOrg1", "testUserName1", dayStart, dayStart.plusHours(3));
		AuctionModel testAuction2 = new AuctionModel("testOrg2", "testUserName2", dayStart.plusHours(5).plusMinutes(1), dayStart.plusHours(6));
		
		// add first auction
		try
		{
			myCalendar.addAuction(testAuction1);
			firstAdded = true;
		}
		catch(Exception e)
		{
			firstAdded = false;
		}
		
		// assert no exception thrown
		assertTrue(firstAdded);
		// assert auction added
		assertEquals(1, myCalendar.numFutureAuctions());
		
		// add second auction 
		try
		{
			myCalendar.addAuction(testAuction2);
			secondAdded = true;
		}
		catch(Exception e)
		{
			secondAdded = false;
		}
		
		// assert no exception thrown
		assertTrue(secondAdded);
		// assert auction added
		assertEquals(2, myCalendar.numFutureAuctions());
	}
	
	/**
	 * Add auctions with overlapping times.
	 * 
	 * @result the second auction will be added
	 */
	@Test
	public void testAddAuctionOnAuctionsWithOverlappingTimes()
	{
		boolean firstAdded;
		boolean errorMessage;
		
		// the auctions to be added
		AuctionModel testAuction1 = new AuctionModel("testOrg1", "testUserName1", dayStart, dayStart.plusHours(3));
		AuctionModel testAuction2 = new AuctionModel("testOrg2", "testUserName2", dayStart.plusHours(1), dayStart.plusHours(4));
		// add first auction
		try
		{
			myCalendar.addAuction(testAuction1);
			firstAdded = true;
		}
		catch(Exception e)
		{
			firstAdded = false;
		}
		
		// assert no exception thrown
		assertTrue(firstAdded);
		// assert auction added
		assertEquals(1, myCalendar.numFutureAuctions());
		
		// add second auction
		try
		{
			myCalendar.addAuction(testAuction2);
			errorMessage = false;
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("There must be at least " + 2 + " hours between the end of one auction and the start of another."))
			{
				// true only if this error message received
				errorMessage = true;
			}
			else
				errorMessage = false;
		}
		
		// assert proper error message received
		assertTrue(errorMessage);
		// assert auction not added
		assertEquals(1, myCalendar.numFutureAuctions());
		
	}
	
	/**
	 * Add 2 auctions on the same day, then try to add a 3rd.
	 * 
	 * @result 3rd auction will not be added.
	 */
	@Test
	public void testAddAuctionOnCalendarDayWithAuctionsAtCapacityForDay()
	{
		boolean firstAdded;
		boolean secondAdded;
		boolean errorMessage;
		
		
		
		// the auctions to be added
		AuctionModel testAuction1 = new AuctionModel("testOrg1", "testUserName1", dayStart, dayStart.plusHours(1));
		AuctionModel testAuction2 = new AuctionModel("testOrg2", "testUserName2", dayStart.plusHours(3), dayStart.plusHours(4));
		AuctionModel testAuction3 = new AuctionModel("testOrg3", "testUserName3", dayStart.plusHours(6), dayStart.plusHours(7));
		
		// add first auction
		try
		{
			myCalendar.addAuction(testAuction1);
			firstAdded = true;
		}
		catch(Exception e)
		{
			firstAdded = false;
		}
		
		// assert first auction actually added
		assertEquals(1, myCalendar.numFutureAuctions());
		// assert no error thrown
		assertTrue(firstAdded);
		
		// add second auction
		try
		{
			myCalendar.addAuction(testAuction2);
			secondAdded = true;
		}
		catch(Exception e)
		{
			secondAdded = false;
		}
		
		// assert second auction actually added
		assertEquals(2, myCalendar.numFutureAuctions());
		// assert no error thrown
		assertTrue(secondAdded);
		
		// add third auction
		try
		{
			myCalendar.addAuction(testAuction3);
			errorMessage = false;
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("There are already " + 2 + " scheduled for the date you entered."))
			{
				// true if proper error message is received
				errorMessage = true;
			}
			else
				errorMessage = false;
		}
		
		// assert proper error message thrown
		assertTrue(errorMessage);
		// assert third auction not added
		assertEquals(2, myCalendar.numFutureAuctions());
		
	}	
	
	/**
	 * Add one auction on a day, then try to add a second.
	 * 
	 * @result the auctions will both be added.
	 */
	@Test
	public void testAddAuctionOnCalendarDayWithAuctionsOneUnderCapacityForDay()
	{
		boolean firstAdded;
		boolean secondAdded;
		
		// the auctions to be added
		AuctionModel testAuction1 = new AuctionModel("testOrg1", "testUserName1", dayStart, dayStart.plusHours(1));
		AuctionModel testAuction2 = new AuctionModel("testOrg2", "testUserName2", dayStart.plusHours(3), dayStart.plusHours(4));
		
		// add first auction
		try
		{
			myCalendar.addAuction(testAuction1);
			firstAdded = true;
		}
		catch(Exception e)
		{
			firstAdded = false;
		}
		
		// assert first auction actually added
		assertEquals(1, myCalendar.numFutureAuctions());
		// assert no error thrown
		assertTrue(firstAdded);
		
		// add second auction
		try
		{
			myCalendar.addAuction(testAuction2);
			secondAdded = true;
		}
		catch(Exception e)
		{
			secondAdded = false;
		}
		
		// assert second auction actually added
		assertEquals(2, myCalendar.numFutureAuctions());
		// assert no error thrown
		assertTrue(secondAdded);
	}
	
	/**
	 * Add 4 auctions in a 3 day before and 3 day after time period of an auction to be added.
	 * 
	 * @result the 5th auction will be added.
	 */
	@Test
	public void testAddAuctionOnCalendarWeekWithAuctionsOneUnderWeekCapacity()
	{
		AuctionModel[] auctions = new AuctionModel[4];
		boolean auctionsAdded[] = new boolean[4];
		boolean finalAdded;
		
		dayStart = dayStart.plusDays(7);
		
		// the auctions to be added
		auctions[0] = new AuctionModel("testOrg1", "testUserName1", dayStart.minusDays(2), dayStart.minusDays(2).plusHours(1));
		auctions[1] = new AuctionModel("testOrg2", "testUserName2", dayStart.minusDays(2).plusHours(3), dayStart.minusDays(2).plusHours(4));
		auctions[2] = new AuctionModel("testOrg3", "testUserName3", dayStart.minusDays(1), dayStart.minusDays(1).plusHours(1));
		auctions[3] = new AuctionModel("testOrg4", "testUserName4", dayStart.minusDays(1).plusHours(3), dayStart.minusDays(1).plusHours(4));
		AuctionModel finalAuction = new AuctionModel("testOrg5", "testUserName5", dayStart, dayStart.plusHours(1));
		
		// add 4 auctions
		for(int i = 0; i < 4; i++)
		{
			try
			{
				myCalendar.addAuction(auctions[i]);
				auctionsAdded[i] = true;
			}
			catch(Exception e)
			{
				auctionsAdded[i] = false;
			}
		}
		// check that no exceptions were thrown
		for(int i = 0; i < 4; i++)
		{
			assertTrue(auctionsAdded[i]);
		}
		
		// assert that there are actually 4 auctions in the calendar
		assertEquals(4, myCalendar.numFutureAuctions());
		
		try
		{
			myCalendar.addAuction(finalAuction);
			finalAdded = true;
		}
		catch(Exception e)
		{
			finalAdded = false;
		}
		
		// assert no errors thrown
		assertTrue(finalAdded);
		// assert that auction was actually added to calendar
		assertEquals(5, myCalendar.numFutureAuctions());
	}
	
	/**
	 * Add 5 auctions in a 3 day before and 3 day after time period of an auction to be added. 
	 * 
	 * @result the 6th auction will not be added.
	 */
	@Test
	public void testAddAuctionOnCalendarWeekWithAuctionsAtWeekCapacity()
	{
		AuctionModel[] auctions = new AuctionModel[5];
		boolean auctionsAdded[] = new boolean[5];
		boolean errorMessage;
		
		dayStart = dayStart.plusDays(7);
		
		// the auctions to be added
		auctions[0] = new AuctionModel("testOrg1", "testUserName1", dayStart.minusDays(2), dayStart.minusDays(2).plusHours(1));
		auctions[1] = new AuctionModel("testOrg2", "testUserName2", dayStart.minusDays(2).plusHours(3), dayStart.minusDays(2).plusHours(4));
		auctions[2] = new AuctionModel("testOrg3", "testUserName3", dayStart.minusDays(1), dayStart.minusDays(1).plusHours(1));
		auctions[3] = new AuctionModel("testOrg4", "testUserName4", dayStart.minusDays(1).plusHours(3), dayStart.minusDays(1).plusHours(4));
		auctions[4] = new AuctionModel("testOrg5", "testUserName5", dayStart, dayStart.plusHours(1));
		AuctionModel finalAuction = new AuctionModel("testOrg6", "testUserName6", dayStart, dayStart.plusHours(1));
		
		// add 5 auctions
		for(int i = 0; i < 5; i++)
		{
			try
			{
				myCalendar.addAuction(auctions[i]);
				auctionsAdded[i] = true;
			}
			catch(Exception e)
			{
				auctionsAdded[i] = false;
			}
		}
		// check that no exceptions were thrown
		for(int i = 0; i < 5; i++)
		{
			assertTrue(auctionsAdded[i]);
		}
		
		// assert that there are actually 5 auctions in the calendar
		assertEquals(5, myCalendar.numFutureAuctions());
		
		try
		{
			myCalendar.addAuction(finalAuction);
			errorMessage = false;
		}
		catch(Exception e)
		{
			if(e.getMessage().equals("There area already " + 5 + " auctions scheduled in the 3 days before and after the date of this auction."))
				errorMessage = true;
			else
				errorMessage = false;
		}
		
		// assert proper error thrown
		assertTrue(errorMessage);
		// assert that auction was not calendar
		assertEquals(5, myCalendar.numFutureAuctions());
	}
	
	/**
	 * Add 5 auctions in the current month and then make sure there are 5 auctions returned when.
	 * GetAuctionForMonth is called.
	 * 
	 * @return Number of auctions for current month = 5
	 */
	@Test
	public void testGetAuctionsForCurrentMonthOnCalendarWith5AuctionsForCurrentMonth()
	{
		AuctionModel[] auctions = new AuctionModel[5];
		
		int year = LocalDate.now().getYear();
		int month = LocalDate.now().getMonthValue();
		
		for(int  i = 1; i <= 5; i++)
		{
			auctions[i-1] = new AuctionModel("testOrg" + i, "testUsername" + i, LocalDateTime.of(year, month, i, 2, 0), LocalDateTime.of(year, month, i, 5, 0) );
		}
		
		for(int  i = 0; i < 5; i++)
		{
			// so we can add past auctions
			myCalendar.addSavedAuction(auctions[i]);
		}
		
		Map<LocalDate, ArrayList<AuctionModel>> monthAuctions = myCalendar.getAuctionsForCurrentMonth();
		System.out.println(monthAuctions.size());
		assertEquals(monthAuctions.size(), 5);
	}
}
