package view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the file saving functions.
 * 
 * @author TCSS 360 Group 5
 *
 */
public class FileSavingTest {
	
	FileSaving myTestFileSaver;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myTestFileSaver = new FileSaving();
	}

	/**
	 * Test method for {@link view.FileSaving#FileSaving()}.
	 */
	@Test
	public void testFileSaving() {
		// not sure we need to test this
	}

	/**
	 * Test method for {@link view.FileSaving#saveAll(java.util.ArrayList, java.util.ArrayList, model.CalendarModel)}.
	 */
	@Test
	public void testSaveAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#loadUsers(java.io.File, java.util.ArrayList)}.
	 */
	@Test
	public void testLoadUsers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#loadAuctions(java.util.ArrayList, java.io.File, java.util.ArrayList, model.CalendarModel)}.
	 */
	@Test
	public void testLoadAuctions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#outputUsers(java.io.File, java.util.ArrayList)}.
	 */
	@Test
	public void testOutputUsers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#outputAuctions(java.io.File, java.util.ArrayList, model.CalendarModel)}.
	 */
	@Test
	public void testOutputAuctions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link view.FileSaving#FindUser(java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	public void testFindUser() {
		fail("Not yet implemented");
	}

}
