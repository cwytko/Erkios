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
import ras.interfaces.LTSActuatorInterface;
import ras.interfaces.LTSSensorInterface;
import ras.interfaces.Utilities.Components;

public class LTSClient {
	private LTSSensorInterface stubLTSSensor = null;
	private LTSActuatorInterface stubLTSActuator = null;
	private static final int PORTLTSSensor = LTSSensorInterface.PORT;
	private static final int PORTLTSActuator = LTSActuatorInterface.PORT;
	private static Registry registry;
	
	public LTSSensorInterface getStubLTSSensor() {
		return stubLTSSensor;
	}

	private void setStubLTSSensor(LTSSensorInterface stubLTSSensor) {
		this.stubLTSSensor = stubLTSSensor;
	}

	public LTSActuatorInterface getStubLTSActuator() {
		return stubLTSActuator;
	}

	private void setStubLTSActuator(LTSActuatorInterface stubLTSActuator) {
		this.stubLTSActuator = stubLTSActuator;
	}

	public LTSClient(final String component) throws RemoteException, NotBoundException{
		/*
		int port = component.equals(Components.LTSSensor.toString()) ? PORTLTSSensor : 
			component.equals(Components.LTSActuator.toString()) ? PORTLTSActuator : -1;
		if(port < 0)
			throw new NotBoundException("LTSClient err: Port used is not allowed for "+component);
		
		registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), port);
		if(component.equals(Components.LTSSensor.toString()))
			setStubLTSSensor((LTSSensorInterface)registry.lookup("LTSSensorInterface"));
		else{
			if(component.equals(Components.LTSActuator.toString()))
				setStubLTSActuator((LTSActuatorInterface)registry.lookup("LTSActuatorInterface"));
			else
				throw new RemoteException("LTSClient err: Registry of "+component+" interface was not allowed");
		}
		*/
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Object> future = executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				// TODO Auto-generated method stub
				try {
					int port = component.equals(Components.LTSSensor.toString()) ? PORTLTSSensor : 
						component.equals(Components.LTSActuator.toString()) ? PORTLTSActuator : -1;
					if(port < 0){
						System.out.println(ras.interfaces.Utilities.separator3+"LTSClient err: Port used is not allowed for "+component);
						throw new NotBoundException("LTSClient err: Port used is not allowed for "+component);
					}
					registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), port);
					if(component.equals(Components.LTSSensor.toString()))
						setStubLTSSensor((LTSSensorInterface)registry.lookup("LTSSensorInterface"));
					else{
						if(component.equals(Components.LTSActuator.toString()))
							setStubLTSActuator((LTSActuatorInterface)registry.lookup("LTSActuatorInterface"));
						else{
							System.out.println(ras.interfaces.Utilities.separator3+"LTSClient err: Registry of "+component+" interface was not allowed");
							throw new RemoteException(ras.interfaces.Utilities.separator3+"LTSClient err: Registry of "+component+" interface was not allowed");
						}
					}
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
			System.err.println(ras.interfaces.Utilities.separator3+"LTSClient err:"+e.getMessage());
			throw new RemoteException("LTSClient err:"+e.getMessage());
		}
	}
}
