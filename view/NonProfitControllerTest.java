package view;
import java.time.LocalDate;
import java.time.LocalDateTime;

import model.*;

import org.junit.Before;

public class NonProfitControllerTest {
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

//	@Test
//	public void testExecuteCommand() {
//		fail("Not yet implemented"); // TODO
//	}

//	@Test
//	public void testCheck365OverAYear() {		
//		myNPO.setLastAuctionDate(OverAYearAgo.toLocalDate());		
//		assertTrue(myNPO.check365(LocalDate.now()));
//	}
//	
//	@Test
//	public void testCheck365OneYear() {		
//		myNPO.setLastAuctionDate(AYearAgo.toLocalDate());	
//		assertFalse(myNPO.check365(LocalDate.now()));
//	}
//	
//	@Test
//	public void testCheck365LessThanOneYear() {		
//		myNPO.setLastAuctionDate(LessThanAYearAgo.toLocalDate());	
//		assertFalse(myNPO.check365(LocalDate.now()));
//	}

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
