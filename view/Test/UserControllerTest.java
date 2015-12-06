package view.Test;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.BidderController;
import view.EmployeeController;
import view.NonProfitController;

public class UserControllerTest {
	NonProfitController myNPO;
	EmployeeController myEmployee;
	BidderController myBidder;
	
	@Before
	public void setUp() throws Exception {
//		myNPO = new NonProfitController("Billy", UserController.UserType.NPO, "BAuction", LocalDate.now().minusYears(1).minusDays(1));
//		myEmployee = new EmployeeController("Betty", UserController.UserType.EMPLOYEE);
//		myBidder = new BidderController("Sam", UserController.UserType.BIDDER);
	}

	@Test
	public void testUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteCommand() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
