package server;

import interfaces.LoginInterface;
import interfaces.TestServerInterface;

import java.rmi.server.UnicastRemoteObject;


/** Implements the server object that allows clients to login. */
public class LoginImplementation extends UnicastRemoteObject implements LoginInterface {
	private static final long serialVersionUID = -8022166461274734761L;
	
	/** The real server object */
	private TestServerInterface myServer;

	/** Class constructor. @param theServer The real server object. */
	public LoginImplementation(TestServerInterface theServer) throws java.rmi.RemoteException {
		myServer = theServer;
	}

	/** Allows a client to login and get an interface to the server. */
	@Override
	public TestServerInterface login(String username, String password) throws java.rmi.RemoteException, SecurityException {
		
		// Check if this user can login. If not, an exception is thrown
		// Checks if the user is known and the password matches
		String realPassword = "letmein";

		if ((realPassword == null) || !realPassword.equals(password)) {
			throw new SecurityException("Password is incorrect.");
		}
		
		// Return a reference to a proxy object that encapsulates the access
		// to the server, for this client
		return new ServerProxy("Charlie", "standard", myServer);
	}
}
