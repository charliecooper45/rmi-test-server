import static org.junit.Assert.assertEquals;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.DAOFactory;
import database.DatabaseConnectionPool;
import database.UserBean;
import database.UserDAO;


public class UserDAOTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// runs once before all tests
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// runs once after all tests
		DatabaseConnectionPool.destroy();
	}

	@Before
	public void setUp() throws Exception {
		// runs before every test
	}

	@After
	public void tearDown() throws Exception {
		// runs after every test
	}

	@Test
	public void testDemo() {
		int value = 7;
		value += 2;
		
		assertEquals("Check that arithmetic works in Java!", 9, value);
	}
	
	@Test
	public void testAddUser() {
		UserBean user1 = new UserBean("Fred", "Whitton");
		UserBean user2 = new UserBean("Charlie", "Smith");
		UserDAO dao = DAOFactory.getUserDAO();
		
		dao.addUser(user1);
		dao.addUser(user2);
		
		List<UserBean> users = dao.getUsers();
		
		Assert.assertTrue("user1 should be in the database.", users.contains(user1));
		Assert.assertTrue("user2 should be in the database.", users.contains(user2));
	}

}
