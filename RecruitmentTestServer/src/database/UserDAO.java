package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Use one DAO per table or view.
 * CRUD methods - Create, Retrieve, Update, Delete
 */
public class UserDAO {
	
	public boolean addUser(UserBean user) {
		try(Connection conn = DatabaseConnectionPool.getConnection()) {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO users (first_name, surname) VALUES (?, ?)");
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getSurname());
			statement.executeUpdate();
			statement.close();
			return true;
		} catch(Exception e) {
			return false;
		}
	} 
	
	public UserBean getUser(int id) {
		return null;
	}
	
	public List<UserBean> getUsers() {
		List<UserBean> users = new ArrayList<>();
		try(Connection conn = DatabaseConnectionPool.getConnection()) {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT user_id, first_name, surname FROM users");
			while (rs.next()) {
				int userID = rs.getInt("user_id");
				String firstName = rs.getString("first_name");	
				String surname = rs.getString("surname");	
				UserBean user = new UserBean(userID, firstName, surname);
				users.add(user);
			}

		} catch(Exception e) {
			return null;
		}
		return users;
	}
	
	public void updateUser(UserBean user) {
		// do not update the id
	}
	
	public void deleteUser(int id) {
		
	}
}
