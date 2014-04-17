package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface TestServerInterface extends Remote {
	public boolean connectToServer(String userName) throws RemoteException;
}
