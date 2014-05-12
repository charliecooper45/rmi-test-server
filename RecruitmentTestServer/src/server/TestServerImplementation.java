package server;

import interfaces.TestServerInterface;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;

import database.DAOFactory;
import database.DatabaseConnectionPool;
import database.UserBean;

public class TestServerImplementation extends UnicastRemoteObject implements TestServerInterface {
	private static final long serialVersionUID = -5232884387242771078L;

	// holds all the users connected to the server (maximum 100)
	private BlockingQueue<String> users = new ArrayBlockingQueue<>(100);

	protected TestServerImplementation() throws RemoteException {
		super();
	}

	@Override
	public String connectToServer(String userName) throws RemoteException {
		// check if there is a user with the same name connected
		if (users.contains(userName)) {
			return "Username in use";
		} else {
			try {
				users.put(userName);
			} catch (InterruptedException e) {
				return "Error connecting to the server.";
			}
			String databaseConnection = testDatabaseConnection();
			return databaseConnection;
		}
	}

	@Override
	public String addUser(String firstName, String surname) {
		System.out.println("Server: adding user with firstname - " + firstName + " and surname - " + surname);
		boolean userAdded = DAOFactory.getUserDAO().addUser(new UserBean(firstName, surname));

		if (userAdded) {
			return null;
		} else {
			return "Unable to add user to the database";
		}
	}

	@Override
	public List<UserBean> listUsers() throws RemoteException {
		List<UserBean> users = DAOFactory.getUserDAO().getUsers();
		return users;
	}

	@Override
	public boolean uploadFile(String fileName, RemoteInputStream remoteFileData) throws RemoteException {
		System.out.println("Uploading a file here..");
		try {
			InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);

			// ... copy fileData to local storage ...
			return storeFile(fileData, "C:/Users/Charlie/Desktop/TestStoredDocument/testdocument.txt");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean storeFile(InputStream inStream, String filePath) {
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inStream);
			FileOutputStream outputStream;

			outputStream = new FileOutputStream(filePath);

			int size = 0;
			byte[] byteBuff = new byte[1024];
			while ((size = bufferedInputStream.read(byteBuff)) != -1) {
				outputStream.write(byteBuff, 0, size);
			}

			outputStream.close();
			bufferedInputStream.close();
			
			System.out.println("finished writing to the file");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
	}

	private String testDatabaseConnection() {
		try (Connection conn = DatabaseConnectionPool.getConnection()) {
			return null;
		} catch (Exception e) {
			return "cannot connect to the database";
		}
	}
}
