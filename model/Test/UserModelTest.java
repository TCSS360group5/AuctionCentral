package model.Test;

import java.time.LocalDate;
import static org.junit.Assert.*;
import model.BidderModel;
import model.EmployeeModel;
import model.NonProfitModel;
import model.UserModel;

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
	public void testEquals() {
		NonProfitModel tempNPO = new NonProfitModel("Billy", UserModel.UserType.NPO, "BAuction", LocalDate.now().minusYears(1).minusDays(1));
		assertTrue(myNPO.equals(tempNPO));
	}

}
