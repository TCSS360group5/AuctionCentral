import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;


public class NonProfitTest {
NonProfit myNPO;
Auction currentAuction;
Auction OverAYearAgoAuction;
Auction AYearAgoAuction;
Auction LessThanAYearAgoAuction;
LocalDateTime OverAYearAgo;
LocalDateTime AYearAgo;
LocalDateTime LessThanAYearAgo;
	
	@Before
	public void setUp() throws Exception {
		myNPO = new NonProfit("Billy", User.UserType.NPO, "BAuction", LocalDate.now().minusDays(365));
		LocalDateTime now = LocalDateTime.now();
		
		 OverAYearAgo = now.minusDays(366);
		 AYearAgo = now.minusDays(365);
		 LessThanAYearAgo = now.minusDays(364);
		OverAYearAgoAuction = new Auction(myNPO.getNPOName(), myNPO.getUserName(), OverAYearAgo, OverAYearAgo.plusHours(2));
		AYearAgoAuction = new Auction(myNPO.getNPOName(), myNPO.getUserName(), AYearAgo, AYearAgo.plusHours(2));;
		LessThanAYearAgoAuction = new Auction(myNPO.getNPOName(), myNPO.getUserName(), LessThanAYearAgo, LessThanAYearAgo.plusHours(2));;
	}

//	@Test
//	public void testExecuteCommand() {
//		fail("Not yet implemented"); // TODO
//	}

	@Test
	public void testCheck365OverAYear() {		
		myNPO.setLastAuctionDate(OverAYearAgo.toLocalDate());		
		assertTrue(myNPO.check365(LocalDate.now()));
	}
	
	@Test
	public void testCheck365OneYear() {		
		myNPO.setLastAuctionDate(AYearAgo.toLocalDate());	
		assertFalse(myNPO.check365(LocalDate.now()));
	}
	
	@Test
	public void testCheck365LessThanOneYear() {		
		myNPO.setLastAuctionDate(LessThanAYearAgo.toLocalDate());	
		assertFalse(myNPO.check365(LocalDate.now()));
	}

//	@Test
//	public void testSetExistingActionStatus() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetNPOName() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testSetNPOName() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetLastAuctionDate() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testSetLastAuctionDate() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetAuction() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testSetAuction() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testHasAuction() {
//		fail("Not yet implemented"); // TODO
//	}

}
