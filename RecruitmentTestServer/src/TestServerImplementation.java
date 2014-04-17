import interfaces.TestServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//TODO NEXT: Add database code
public class TestServerImplementation extends UnicastRemoteObject implements TestServerInterface{
	private static final long serialVersionUID = -5232884387242771078L;
	
	// holds all the users connected to the server
	private BlockingQueue<String> users = new ArrayBlockingQueue<>(100);
	
	protected TestServerImplementation() throws RemoteException {
		super();
	}

	@Override
	public boolean connectToServer(String userName) throws RemoteException {
		// check if there is a user with the same name connected
		if(users.contains(userName)) {
			return false;
		} else {
			try {
				users.put(userName);
			} catch (InterruptedException e) {
				return false;
			}
			return true;
		}
	}
}
