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
import ras.interfaces.CTSInterface;

public class CTSClient {
	
	private CTSInterface stubCTS = null;
	private static final int PORT = CTSInterface.PORT;
	private static Registry registry;
	
	public CTSInterface getStubCTS() {
		return stubCTS;
	}

	private void setStubCTS(CTSInterface stubCTS) {
		this.stubCTS = stubCTS;
	}

	public CTSClient() throws RemoteException, NotBoundException{
		/*
		try{
			registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), PORT);
			setStubCTS((CTSInterface)registry.lookup("CTSInterface"));
		} catch (NotBoundException e) {
			throw e;
		}
		*/
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Object> future = executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				// TODO Auto-generated method stub
				try {
					registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), PORT);
					setStubCTS((CTSInterface)registry.lookup("CTSInterface"));
				} catch (NotBoundException e) {
					// TODO: handle exception
					throw e;
				}
				return null;
			}
		});
		
		try {
			future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
		} catch (InterruptedException | TimeoutException | ExecutionException e) {
			// TODO: handle exception
			System.err.println(ras.interfaces.Utilities.separator3+"CTSClient err:"+e.getMessage());
			throw new RemoteException("CTSClient err: TIMEOUT");
		}
	}
	
	
}
