import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
	NonProfit myNPO;
	Employee myEmployee;
	Bidder myBidder;
	
	@Before
	public void setUp() throws Exception {
		myNPO = new NonProfit("Billy", User.UserType.NPO, "BAuction", LocalDate.now().minusYears(1).minusDays(1));
		myEmployee = new Employee("Betty", User.UserType.EMPLOYEE);
		myBidder = new Bidder("Sam", User.UserType.BIDDER);
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
