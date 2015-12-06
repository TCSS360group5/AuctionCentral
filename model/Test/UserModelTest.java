package model.Test;

import java.time.LocalDate;

import model.BidderModel;
import model.EmployeeModel;
import model.NonProfitModel;
import model.UserModel;

import org.junit.Before;

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

	//nothing to test.

}
