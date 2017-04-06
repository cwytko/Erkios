package ras.clientLogic;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ras.conexion.ConfigurationMachine;
import ras.interfaces.DBRASSchemeInterface;

public class DBRASSchemeClient {
	
	private DBRASSchemeInterface stubDBRAS = null;
	private static final int PORT = DBRASSchemeInterface.PORT;
	private static Registry registry;
	
	public DBRASSchemeClient() throws RemoteException{
		/*
		try {
			registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), PORT);
			
			//Registry registry = LocateRegistry.getRegistry(
			//		null, PORT, new SslRMIClientSocketFactory());
			//		new RMISSLClientSocketFactory());
			setStubDBRAS((DBRASSchemeInterface)registry.lookup("DBRASSchemeInterface"));
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Object> future = executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				// TODO Auto-generated method stub
				try {
					registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), PORT);
					setStubDBRAS((DBRASSchemeInterface)registry.lookup("DBRASSchemeInterface"));
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
		
		try {
			future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
		} catch (InterruptedException | TimeoutException | ExecutionException e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			throw new RemoteException("DBRASSchemeClient err: TIMEOUT");
		}
	}

	public DBRASSchemeInterface getStubDBRAS() {
		return stubDBRAS;
	}

	private void setStubDBRAS(DBRASSchemeInterface stubDBRAS) {
		this.stubDBRAS = stubDBRAS;
	}
	/*
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws RemoteException{
		List<ST_classRAS> listClassRAS = null;
		DBRASSchemeClient stub = new DBRASSchemeClient();
		try {
			listClassRAS = (List<ST_classRAS>)(Object)stub.getStubDBRAS().selectGenericQuery("from sra_classRAS", null);
			for(ST_classRAS test:listClassRAS)
				System.out.println(test.getNameClass());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("DBRASSchemeInterface err: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	*/
}
