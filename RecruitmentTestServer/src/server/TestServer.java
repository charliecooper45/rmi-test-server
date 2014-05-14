package server;
import interfaces.LoginInterface;
import interfaces.TestServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;


public class TestServer {
	public static void main(String[] args) {
		try {
			TestServerInterface theServer = new TestServerImplementation();
			LoginInterface loginServer = new LoginImplementation(theServer);
			
			//TODO: Add registry code here
			//TODO: Create the "InvalidUserException" class
			Naming.rebind("TestLoginServer", loginServer);
			
			System.out.println(new Date() + ": server up and running...");
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
