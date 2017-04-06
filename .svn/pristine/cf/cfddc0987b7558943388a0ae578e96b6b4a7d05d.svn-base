package ras.ssl;

import java.rmi.registry.LocateRegistry;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

import ras.interfaces.DBRASSchemeInterface;

public class RMIRegistry {
	private int port;
	public int getPort() {
		return port;
	}

	private void setPort(int port) {
		this.port = port;
	}

	public RMIRegistry(int port) throws Exception{
		setPort(port);
	}
	
	public void initiateRMI() throws Exception{
		LocateRegistry.createRegistry(getPort(), 
				new SslRMIClientSocketFactory(), 
				new SslRMIServerSocketFactory(null, null, true));
		System.out.println("RMI registry running on port "+getPort());
        
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (Exception e) {
					System.out.println("Error RMI registry:"+e.getMessage());
					e.printStackTrace();
					System.exit(1);
				}
			}
		}).start();
	}
    public static void main(String[] args) throws Exception {
        // Start RMI registry on port 3000
        LocateRegistry.createRegistry(DBRASSchemeInterface.PORT, new SslRMIClientSocketFactory(), new SslRMIServerSocketFactory(null,null, true));
        System.out.println("RMI registry running on port "+DBRASSchemeInterface.PORT);
        // Sleep forever
        Thread.sleep(Long.MAX_VALUE);
    }
}
