package model;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class UserModelTest {
	NonProfitModel myNPO;
	EmployeeModel myEmployee;
	BidderModel myBidder;
	
	@Before
	public void setUp() throws Exception {
		myNPO = new NonProfitModel("Billy", UserModel.UserType.NPO, "BAuction", LocalDate.now().minusYears(1).minusDays(1));
		myEmployee = new EmployeeModel("Betty", UserModel.UserType.EMPLOYEE);
		myBidder = new BidderModel("Sam", UserModel.UserType.BIDDER);
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
