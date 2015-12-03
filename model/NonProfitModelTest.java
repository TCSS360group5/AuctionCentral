package model;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;


public class NonProfitModelTest {
NonProfitModel myNPO;
AuctionModel currentAuction;
AuctionModel OverAYearAgoAuction;
AuctionModel AYearAgoAuction;
AuctionModel LessThanAYearAgoAuction;
LocalDateTime OverAYearAgo;
LocalDateTime AYearAgo;
LocalDateTime LessThanAYearAgo;
	
	@Before
	public void setUp() throws Exception {
		myNPO = new NonProfitModel("Billy", UserModel.UserType.NPO, "BAuction", LocalDate.now().minusDays(365));
		LocalDateTime now = LocalDateTime.now();
		
		 OverAYearAgo = now.minusDays(366);
		 AYearAgo = now.minusDays(365);
		 LessThanAYearAgo = now.minusDays(364);
		OverAYearAgoAuction = new AuctionModel(myNPO.getNPOName(), myNPO.getUserName(), OverAYearAgo, OverAYearAgo.plusHours(2));
		AYearAgoAuction = new AuctionModel(myNPO.getNPOName(), myNPO.getUserName(), AYearAgo, AYearAgo.plusHours(2));;
		LessThanAYearAgoAuction = new AuctionModel(myNPO.getNPOName(), myNPO.getUserName(), LessThanAYearAgo, LessThanAYearAgo.plusHours(2));;
	}

	@Test
	public void testCheck365OverAYear() {		
		myNPO.setLastAuctionDate(OverAYearAgo.toLocalDate());		
		//assertTrue(myNPO.check365(LocalDate.now()));
		boolean thrown = false;

		  try {
			  myNPO.check365(LocalDate.now());
		  } catch (AuctionException e) {
		    thrown = true;
		  }

		  assertTrue(thrown);
	}
	
	@Test
	public void testCheck365OneYear() {		
		myNPO.setLastAuctionDate(AYearAgo.toLocalDate());	
		//assertFalse(myNPO.check365(LocalDate.now()));
		boolean thrown = false;

		  try {
			  myNPO.check365(LocalDate.now());
		  } catch (AuctionException e) {
		    thrown = true;
		  }

		  assertTrue(thrown);
	}
	
	@Test
	public void testCheck365LessThanOneYear() {		
		myNPO.setLastAuctionDate(LessThanAYearAgo.toLocalDate());	
		//assertFalse(myNPO.check365(LocalDate.now()));
		boolean thrown = false;

		  try {
			  myNPO.check365(LocalDate.now());
		  } catch (AuctionException e) {
		    thrown = true;
		  }

		  assertTrue(thrown);
	}
}
