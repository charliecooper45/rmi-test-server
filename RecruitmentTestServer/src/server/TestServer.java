package server;
import interfaces.TestServerInterface;

import java.rmi.Naming;


public class TestServer {
	public static void main(String[] args) {
		try {
			TestServerInterface serverInterface = new TestServerImplementation();
			Naming.rebind("TestServer", serverInterface);

			System.out.println("Server ready for use...");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
