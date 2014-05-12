package database;

public class DAOFactory {
	public static UserDAO getUserDAO() {
		return new UserDAO();
	}
}
