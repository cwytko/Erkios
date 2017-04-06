package ras.clientLogic;

import java.rmi.AccessException;
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
import ras.interfaces.SwitchRASInterface;
import ras.interfaces.Utilities.Components;

public class SwitchClient {
	
	private SwitchRASInterface stubSwitch = null;
	private static final int PORTSwitchSensor1 = SwitchRASInterface.PORTSwitchSensor1;
	private static final int PORTSwitchSensor2 = SwitchRASInterface.PORTSwitchSensor2;
	private static final int PORTSwitchActuator = SwitchRASInterface.PORTSwitchActuator;
	private static Registry registry;
	
	public SwitchRASInterface getStubSwitch() {
		return stubSwitch;
	}
	private void setStubSwitch(SwitchRASInterface stubSwitch) {
		this.stubSwitch = stubSwitch;
	}
	public static Registry getRegistry() {
		return registry;
	}
	
	public SwitchClient(final String component) throws AccessException, RemoteException, NotBoundException{
		/*
		int port = component.equals(Components.SwitchActuator.toString()) ? PORTSwitchActuator : 
			component.equals(Components.SwitchSensor1.toString()) ? PORTSwitchSensor1 : 
			component.equals(Components.SwitchSensor2.toString()) ? PORTSwitchSensor2 : -1;
		if(port < 0)
			throw new NotBoundException("SwitchClient err: Port used is not allowed for "+component);
		
		registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), port);
		if(component.equals(Components.SwitchActuator.toString()))
			setStubSwitch((SwitchRASInterface)registry.lookup(Components.SwitchActuator.toString()+"Interface"));
		else{
			if(component.equals(Components.SwitchSensor1.toString()))
				setStubSwitch((SwitchRASInterface)registry.lookup(Components.SwitchSensor1.toString()+"Interface"));
			else{
				if(component.equals(Components.SwitchSensor2.toString()))
					setStubSwitch((SwitchRASInterface)registry.lookup(Components.SwitchSensor2.toString()+"Interface"));
				else
					throw new RemoteException("Registry of "+component+" interface was not allowed");
			}
		}
		*/
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Object> future = executor.submit(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				// TODO Auto-generated method stub
				try {
					int port = component.equals(Components.SwitchActuator.toString()) ? PORTSwitchActuator : 
						component.equals(Components.SwitchSensor1.toString()) ? PORTSwitchSensor1 : 
						component.equals(Components.SwitchSensor2.toString()) ? PORTSwitchSensor2 : -1;
					if(port < 0){
						System.out.println(ras.interfaces.Utilities.separator3+"SwitchClient err: Port used is not allowed for "+component);
						throw new NotBoundException("SwitchClient err: Port used is not allowed for "+component);
					}
					registry = LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), port);
					if(component.equals(Components.SwitchActuator.toString()))
						setStubSwitch((SwitchRASInterface)registry.lookup(Components.SwitchActuator.toString()+"Interface"));
					else{
						if(component.equals(Components.SwitchSensor1.toString()))
							setStubSwitch((SwitchRASInterface)registry.lookup(Components.SwitchSensor1.toString()+"Interface"));
						else{
							if(component.equals(Components.SwitchSensor2.toString()))
								setStubSwitch((SwitchRASInterface)registry.lookup(Components.SwitchSensor2.toString()+"Interface"));
							else
								throw new RemoteException("Registry of "+component+" interface was not allowed");
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
			System.err.println(ras.interfaces.Utilities.separator3+"SwitchClient err:"+e.getMessage());
		}
	}
}
