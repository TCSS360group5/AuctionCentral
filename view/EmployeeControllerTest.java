package view;
import static org.junit.Assert.*;
import model.*;

import org.junit.Before;
import org.junit.Test;


public class EmployeeControllerTest {
	
	EmployeeController myTestEmployee;
	
	@Before
	public void setUp() throws Exception {
		EmployeeModel employeeModel = new EmployeeModel("Shannon", UserModel.UserType.EMPLOYEE);
		myTestEmployee = new EmployeeController(employeeModel);
	}

	@Test
	public void testEmployeeModel() {
		assertEquals(myTestEmployee.getEmployeeModel().getUserName(), "Shannon");
	}
}
