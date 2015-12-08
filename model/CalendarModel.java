package model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import exceptions.AuctionBackwardsTimeException;
import exceptions.AuctionTimeBetweenException;
import exceptions.AuctionTooFarAwayException;
import exceptions.AuctionsAtCapacityException;
import exceptions.AuctionsAtCapacityForWeekException;
import exceptions.AuctionsPerDayException;

/**
 * This class creates a calendar that contains all of the auctions and checks business rules before
 * adding an auction.
 * 
 * @author Demetra Loulias, UWT Group 5
 */
public class CalendarModel
{
	public Map<LocalDate, ArrayList<AuctionModel>> myAuctionByDateList;
  
	/**
	 * Creates a new Calendar with a Map to store Auctions.
	 */
	public CalendarModel()
	{
		myAuctionByDateList = new HashMap<LocalDate, ArrayList<AuctionModel>>();
	}
	
	/**
	 * Adds a given auction to the Calendar. Enforces all business rules regarding scheduling except for checking whether
	 * an NPO has already scheduled an auction for the year.
	 * 
	 * @param theAuction the Auction to be added.
	 * @return whether or not the auction was added.
	 * @throws AuctionsPerDayException 
	 * @throws AuctionTimeBetweenException 
	 * @throws AuctionBackwardsTimeException 
	 * @throws AuctionsAtCapacityForWeekException 
	 * @throws AuctionsAtCapacityException 
	 * @throws AuctionTooFarAwayException 
	 * 
	 * @author Demy
	 */
	public void addAuction(AuctionModel theAuction) throws AuctionTimeBetweenException, 
														   AuctionsPerDayException,
														   AuctionTooFarAwayException, 
														   AuctionsAtCapacityException, 
														   AuctionsAtCapacityForWeekException,
														   AuctionBackwardsTimeException
	{
		LocalDate auctionDate = theAuction.getStartTime().toLocalDate();
		LocalDateTime auctionStart = theAuction.getStartTime();
		LocalDateTime auctionEnd = theAuction.getEndTime();
		
		boolean futureAuctions = checkFutureAuctions();
		boolean ninetyDays = checkDaysAway(auctionStart);
		boolean weekCheck = checkWeek(auctionDate);
		boolean timeCheck = checkTimes(auctionStart, auctionEnd);
		
		// checking business rules
		if(futureAuctions && ninetyDays && weekCheck && timeCheck)
		{
			if(myAuctionByDateList.containsKey(auctionDate))
			{
				checkAuctionsForDay(theAuction);
			}
			else
			{
				ArrayList<AuctionModel> addList = new ArrayList<AuctionModel>();
				addList.add(theAuction);
				myAuctionByDateList.put(auctionDate, addList);
			}
		}
		else
		{
			determineError(futureAuctions, ninetyDays, weekCheck, timeCheck);
		}
		 
	}
	
	/**
	 * Determines which exception to throw.
	 * 
	 * @param theFutureAuctions
	 * @param theNinetyDays
	 * @param theWeekCheck
	 * @param theTimeCheck
	 * @throws AuctionTooFarAwayException 
	 * @throws AuctionsAtCapacityException 
	 * @throws AuctionsAtCapacityForWeekException 
	 * @throws AuctionBackwardsTimeException 
	 * 
	 * @author Demy
	 */
	private void determineError(boolean theFutureAuctions,
								boolean theNinetyDays, 
								boolean theWeekCheck, 
								boolean theTimeCheck) throws AuctionTooFarAwayException, 
															 AuctionsAtCapacityException,
															 AuctionsAtCapacityForWeekException, 
															 AuctionBackwardsTimeException
	{
		if(!theFutureAuctions)
		{
			throw new AuctionsAtCapacityException();
		}
		else if(!theNinetyDays)
		{
			throw new AuctionTooFarAwayException();
		}
		else if(!theWeekCheck)
		{
			throw new AuctionsAtCapacityForWeekException();
		}
		else if(!theTimeCheck)
		{
			throw new AuctionBackwardsTimeException();
		}
	}
	/**
	 * Checks to see if the auction starts and ends on the same calendar day (Not a business rule, but it's
	 * convenient for our program), as well as if the auction start is before the auction end.
	 * 
	 * @param theAuctionStart Auction start
	 * @param theAuctionEnd Auction end
	 * @return whether the time criteria mentioned in the description is met.
	 * 
	 * @author Demy
	 */
	private boolean checkTimes(LocalDateTime theAuctionStart, LocalDateTime theAuctionEnd)
	{
		return (theAuctionStart.toLocalDate().equals(theAuctionEnd.toLocalDate()) && theAuctionStart.isBefore(theAuctionEnd));
	}
	
	/**
	 * Checks the "No more than (number) of future auctions" business rule.
	 * Number is from the AuctionCapacityException MAX_FUTURE_AUCTIONS constant.
	 * 
	 * @return true iff there are already (number) auctions scheduled for the future.
	 */
	private boolean checkFutureAuctions()
	{
		int count = numFutureAuctions();

		if(count >= AuctionsAtCapacityException.MAX_FUTURE_AUCTIONS)
		{
			return false;
		}
		else
			return true;
	}
	
	/**
	 * Enforces the "An auction may not be scheduled more than (number) days from the current date" business rule.
	 * Number is from the AuctionTimeBetweenException MAX_DAYS_AWAY constant.
	 * 
	 * @param theAuctionStart the Auction start.
	 * @return true iff Auction start is (number) days after the current date.
	 * 
	 * @author Demy
	 */
	private boolean checkDaysAway(LocalDateTime theAuctionStart)
	{
		if(theAuctionStart.isAfter(LocalDateTime.now().plusDays(AuctionTooFarAwayException.MAX_DAYS_AWAY)) || theAuctionStart.isBefore(LocalDateTime.now().minusDays(1)))
		{
			return false;
		}
		else
			return true;
	}
	
	/**
	 * Checks the "no more than (number) auctions may be scheduled for any rolling 7 day period" business rule.
	 * Number is from the AuctionsAtCapacityForWeekException MAX_AUCTIONS_FOR_WEEK constant.
	 * 
	 * @param theAuctionDate the date of the Auction.
	 * @return true iff there are already (number) auctions scheduled for the 7 day period.
	 * 
	 * @author Demy
	 */
	private boolean checkWeek(LocalDate theAuctionDate)
	{
		int count = 0;
		 
		if(myAuctionByDateList.containsKey(theAuctionDate))
		{
			ArrayList<AuctionModel> dayOfAuctions = myAuctionByDateList.get(theAuctionDate);
			count += dayOfAuctions.size();
		}	
		for(int i = 1; i <=3; i++)
		{
			if(myAuctionByDateList.containsKey(theAuctionDate.plusDays(i)))
			{
				ArrayList<AuctionModel> dayAuctions = myAuctionByDateList.get(theAuctionDate.plusDays(i));
				System.out.println();
				count += dayAuctions.size();
			}
			if(myAuctionByDateList.containsKey(theAuctionDate.minusDays(i)))
			{
				ArrayList<AuctionModel> dayAuctions = myAuctionByDateList.get(theAuctionDate.minusDays(i));
				count += dayAuctions.size();
			}
		}
		
		if(count >= AuctionsAtCapacityForWeekException.MAX_AUCTIONS_FOR_WEEK)
			return false;
		else
			return true;
	}
	
	/**
	 * Enforces the "no more than (number1) auctions can be scheduled on the same day, and the star time of the
	 * second can be no earlier than (number2) hours after the end time of the first" business rule.
	 * Number1 is from the AuctionsPerDayException MAX_AUCTIONS_PER_DAY constant.
	 * Number2 is from the AuctionTimeBetweenException HOURS_BETWEEN_AUCTION constant.
	 * 
	 * @param theAuction the Auction to be added
	 * @return true iff there is room for the auction in the day and the hours between start and end of the other scheduled auction(s) is okay.
	 * @throws AuctionTimeBetweenException 
	 * @throws AuctionsPerDayException 
	 * 
	 * @author Demy
	 */
	private void checkAuctionsForDay(AuctionModel theAuction) throws AuctionTimeBetweenException, AuctionsPerDayException
	{
		LocalDate auctionDate = theAuction.getStartTime().toLocalDate();
		LocalDateTime auctionStart = theAuction.getStartTime();
		LocalDateTime auctionEnd = theAuction.getEndTime();
		  
		ArrayList<AuctionModel> dayAuctions = myAuctionByDateList.get(auctionDate);
	
		if(dayAuctions.size() < AuctionsPerDayException.MAX_AUCTIONS_PER_DAY)
		{
			int hoursBetween = AuctionTimeBetweenException.HOURS_BETWEEN_AUCTION;
			
			AuctionModel firstAuction = dayAuctions.get(0);
			if(auctionStart.isBefore(firstAuction.getStartTime()))
			{
				// at least 2 hours between actions
				if(auctionEnd.plusHours(hoursBetween).minusMinutes(1).isBefore(firstAuction.getStartTime()))
				{
					dayAuctions.add(theAuction);
					myAuctionByDateList.replace(auctionDate, dayAuctions);
				}	
				else
				{
					throw new AuctionTimeBetweenException();
				}
			}
			else
			{
				// at least 2 hours between actions
				if(auctionStart.isAfter(firstAuction.getEndTime().plusHours(hoursBetween).minusMinutes(1)))
				{
					dayAuctions.add(theAuction);
					myAuctionByDateList.replace(auctionDate, dayAuctions);
				}
				else
				{
					throw new AuctionTimeBetweenException();
				}
			}
		}
		// 2 auctions already scheduled for the day
		else
		{
			throw new AuctionsPerDayException();
		}
	}
	
	  
	/**
	 * Returns a sorted (by date) map of auctions for the current month.
	 * 
	 * @return sorted by date map of auctions for the current month.
	 * 
	 * @author Demy
	 */
	public Map<LocalDate, ArrayList<AuctionModel>> getAuctionsForCurrentMonth()
	{
		return getAuctionsForChosenMonth(LocalDate.now());
	}
	
	/**
	 * Returns a sorted (by date) map of auctions for the entered month.
	 * 
	 * @param theMonth the month to get a map for.
	 * @return sorted by date map of auctions for the entered month.
	 * 
	 * @author Demy
	 */
	public Map<LocalDate, ArrayList<AuctionModel>> getAuctionsForChosenMonth(LocalDate theMonth)
	{
		Map<LocalDate, ArrayList<AuctionModel>> returnMap = new TreeMap<LocalDate,ArrayList<AuctionModel>>();
		for(Entry<LocalDate, ArrayList<AuctionModel>> entry : myAuctionByDateList.entrySet())
		{
			if(entry.getKey().getMonthValue() == theMonth.getMonthValue())
			{
				returnMap.put(entry.getKey(), entry.getValue());
			}
		}
		  
		return returnMap;
		  
	}
	
	/**
	 * Gets a Map of all future Auctions.
	 * @return Map of all future Auctions.
	 * 
	 * @author Demy
	 */
	public Map<LocalDate, ArrayList<AuctionModel>> getAllFutureAuctions()
	{
		LocalDate yesterday = LocalDate.now().minusDays(1);
		Map<LocalDate, ArrayList<AuctionModel>> returnMap = new TreeMap<LocalDate,ArrayList<AuctionModel>>();
		for(Entry<LocalDate, ArrayList<AuctionModel>> entry : myAuctionByDateList.entrySet())
		{
			if(entry.getKey().isAfter(yesterday))
			{
				returnMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		return returnMap;
	}
	  
	/**
	 * Returns an auction with the given name and date.
	 * 
	 * @param auctionName the Auction name.
	 * @param auctionDate the Auction date.
	 * @return the chosen Auction.
	 * 
	 * @author Demy
	 */
	public AuctionModel viewAuction(String auctionName, LocalDate auctionDate)
	{
		ArrayList<AuctionModel> dayAuctions = myAuctionByDateList.get(auctionDate);
		if(dayAuctions.size() == 0)
		{
			return dayAuctions.get(0);
		}
		else
		{
			for(int i = 0; i < dayAuctions.size(); i++)
			{
				AuctionModel auction = dayAuctions.get(i);
				if(auction.getAuctionName().equals(auctionName))
				{
					return auction;
				}
			}
		}
		return null;
	}
	
	/**
	 * Removes the desired Auction. 
	 * 
	 * @param theAuction the Auction to be removed.
	 * 
	 * @author Demy
	 */
	public void removeAuction(AuctionModel theAuction) 
	{
		LocalDate date = theAuction.getStartTime().toLocalDate();
		ArrayList<AuctionModel> removeDay = myAuctionByDateList.get(date);
		  
		if(removeDay.size() == 1)
		{
			myAuctionByDateList.remove(date);
		}
		else
		{
			removeDay.remove(theAuction);
			myAuctionByDateList.replace(date, removeDay);
		}
	}
	  
	/**
	 * Returns a String of the auctions for the chosen month.
	 * 
	 * @param theDate the Date chosen (only the month matters)
	 * @return String representation for the chosen month.
	 * 
	 * @author Demy
	 */
	public String toString(LocalDate theDate) 
	{
		Map<LocalDate, ArrayList<AuctionModel>> displayMap = getAuctionsForChosenMonth(theDate);
		StringBuilder answer = new StringBuilder();
		for (Map.Entry<LocalDate,ArrayList<AuctionModel>> entry : displayMap.entrySet()) 
		{
			answer.append(entry.getKey());
			ArrayList<AuctionModel> Auctions = entry.getValue();
			for (int i = 0; i < Auctions.size(); i++)
			{
				answer.append(i + " " + Auctions.get(i).toString());
			}
		}
		return answer.toString();
	}
	
	/**
	 * Counts up the total number of future auctions.
	 * @return  number of auctions in the future.
	 * 
	 * @author Demy
	 */
	public int numFutureAuctions()
	{
		int count = 0;
				
		for(Entry<LocalDate, ArrayList<AuctionModel>> entry : myAuctionByDateList.entrySet())
		{
			if(entry.getKey().isAfter(LocalDate.now().minusDays(1)))
			{
				count += entry.getValue().size();
			}
		}
		
		return count;
	}
	
	/**
	 * Adds an auction to the calendar without checking business rules. Used when loading
	 * a file, to add past auctions without errors.
	 * 
	 * @param the Auction to add to the calendar
	 * 
	 * @author Demy
	 */
	public void addSavedAuction(AuctionModel theAuction)
	{
		ArrayList<AuctionModel> putAuction;
		LocalDate auctionDay = theAuction.getStartTime().toLocalDate();
		
		if(myAuctionByDateList.containsKey(auctionDay))
		{
			putAuction = myAuctionByDateList.get(auctionDay);
			myAuctionByDateList.replace(auctionDay, putAuction);
		}
		else
		{
			putAuction = new ArrayList<AuctionModel>();
			putAuction.add(theAuction);
			myAuctionByDateList.put(auctionDay, putAuction);
		}
	}
}
