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
import ras.interfaces.RASInterface;

public class RASClient {
	private RASInterface stubRAS = null;
	private static final int PORT = RASInterface.PORTRAS;
	private static Registry registry;
	
	public RASInterface getStubRAS() {
		return stubRAS;
	}
	private void setStubRAS(RASInterface stubRAS) {
		this.stubRAS = stubRAS;
	}
	
	public RASClient() 
			throws RemoteException, NotBoundException{
		/*
		registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), PORT);
		setStubRAS((RASInterface)registry.lookup("RASInterface"));
		*/
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Object> future = executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				// TODO Auto-generated method stub
				try {
					registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), PORT);
					setStubRAS((RASInterface)registry.lookup("RASInterface"));
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
			System.err.println(ras.interfaces.Utilities.separator3+"RASClient err:"+e.getMessage());
		}
	}
}
