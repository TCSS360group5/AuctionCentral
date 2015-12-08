package view.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.*;

import org.junit.Before;
import org.junit.Test;

import view.NonProfitController;

public class NonProfitControllerTest {
	NonProfitController mytestNPO;
	
	@Before
	public void setUp() throws Exception {
		NonProfitModel NPO = new NonProfitModel("Shannon", UserModel.UserType.NPO, "Goodwill", LocalDate.now());
		mytestNPO = new NonProfitController(NPO);
	}
	
	@Test
	public void testNonProfitController() {
		assertEquals(mytestNPO.getNPOModel().getUserName(), "Shannon");
	}
}
