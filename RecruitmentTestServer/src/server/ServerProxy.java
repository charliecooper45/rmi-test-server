package server;

import interfaces.TestServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.healthmarketscience.rmiio.RemoteInputStream;

import database.UserBean;


public class ServerProxy extends UnicastRemoteObject implements TestServerInterface{
	private static final long serialVersionUID = 881151330984641372L;

	/** A reference to the real server object */
	private TestServerInterface theServer;

	/** The user associated with this proxy */
	@SuppressWarnings("unused")
	private String userID;
	
	/** The user type of the user associated with this proxy **/
	private String userType;

	public ServerProxy(String userID, String userType, TestServerInterface theServer) throws RemoteException, SecurityException {
		this.theServer = theServer;
		this.userID = userID;
		this.userType = userType;
	}

	@Override
	public String addUser(String firstname, String surname) throws RemoteException, SecurityException {
		checkPermission("addUser");
		return theServer.addUser(firstname, surname);
	}


	@Override
	public List<UserBean> listUsers() throws RemoteException, SecurityException {
		checkPermission("listUsers");
		return theServer.listUsers();
	}


	@Override
	public boolean uploadFile(String fileName, RemoteInputStream fileData) throws RemoteException {
		checkPermission("uploadFile");
		return theServer.uploadFile(fileName, fileData);
	}
	
	/**
	* Check if the current client can call a certain method.
	* The check is made through JAAS and its policy file.
	* @param methodName The method that will be called.
	* @throws SecurityException If the client doesn't have the necessary
	 permissions.
	*/
	private void checkPermission(String methodName) throws SecurityException
	{
		if(userType.equals("administrator")) {
			return;
		} else if(methodName.equals("uploadFile")) {
			throw new SecurityException("Standard users cannot perform uploadFile");
		}
	}
}
