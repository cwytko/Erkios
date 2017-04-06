package ras.clientLogic;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.AccessException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ras.conexion.ConfigurationMachine;
import ras.interfaces.CTSInterface;
import ras.interfaces.LTSActuatorInterface;
import ras.interfaces.LTSSensorInterface;
import ras.interfaces.OPWANInterface;
import ras.interfaces.RASInterface;
import ras.interfaces.SwitchRASInterface;
import ras.interfaces.TestWANInterface;
import ras.interfaces.Utilities.Components;

public class HeartbeatClient {
	private CTSInterface stubCTS = null;
	private LTSActuatorInterface stubLTSActuator = null;
	private LTSSensorInterface stubLTSSensor = null;
	private OPWANInterface stubOPWAN = null;
	private RASInterface stubRAS = null;
	private SwitchRASInterface stubSwitchRAS = null;
	private TestWANInterface stubTestWAN = null;
	
	private static final int PORTCTS = CTSInterface.PORT;
	private static final int PORTLTSActuator = LTSActuatorInterface.PORT;
	private static final int PORTLTSSensor = LTSSensorInterface.PORT;
	private static final int PORTOPWAN = OPWANInterface.PORTOPWan;
	private static final int PORTRAS = RASInterface.PORTRAS;
	private static final int PORTSwitchSensor1 = SwitchRASInterface.PORTSwitchSensor1;
	private static final int PORTSwitchSensor2 = SwitchRASInterface.PORTSwitchSensor2;
	private static final int PORTSwitchActuator = SwitchRASInterface.PORTSwitchActuator;
	private static final int PORTTestWAN = TestWANInterface.PORT;
	
	private static Registry registry;

	public CTSInterface getStubCTS() {
		return stubCTS;
	}

	private void setStubCTS(CTSInterface stubCTS) {
		this.stubCTS = stubCTS;
	}

	public LTSActuatorInterface getStubLTSActuator() {
		return stubLTSActuator;
	}

	private void setStubLTSActuator(LTSActuatorInterface stubLTSActuator) {
		this.stubLTSActuator = stubLTSActuator;
	}

	public LTSSensorInterface getStubLTSSensor() {
		return stubLTSSensor;
	}

	private void setStubLTSSensor(LTSSensorInterface stubLTSSensor) {
		this.stubLTSSensor = stubLTSSensor;
	}

	public OPWANInterface getStubOPWAN() {
		return stubOPWAN;
	}

	private void setStubOPWAN(OPWANInterface stubOPWAN) {
		this.stubOPWAN = stubOPWAN;
	}

	public RASInterface getStubRAS() {
		return stubRAS;
	}

	private void setStubRAS(RASInterface stubRAS) {
		this.stubRAS = stubRAS;
	}

	public SwitchRASInterface getStubSwitchRAS() {
		return stubSwitchRAS;
	}

	private void setStubSwitchRAS(SwitchRASInterface stubSwitchRAS) {
		this.stubSwitchRAS = stubSwitchRAS;
	}

	public TestWANInterface getStubTestWAN() {
		return stubTestWAN;
	}

	private void setStubTestWAN(TestWANInterface stubTestWAN) {
		this.stubTestWAN = stubTestWAN;
	}

	public static Registry getRegistry() {
		return registry;
	}

	private static void setRegistry(Registry registry) {
		HeartbeatClient.registry = registry;
	}
	
	public HeartbeatClient(String component) throws AccessException, RemoteException, NotBoundException{
		int port = component.equals(Components.CTS.toString()) ? PORTCTS : 
				component.equals(Components.LTSActuator.toString()) ? PORTLTSActuator : 
				component.equals(Components.LTSSensor.toString()) ? PORTLTSSensor : 
				component.equals(Components.OpWAN.toString()) ? PORTOPWAN : 
				component.equals(Components.RAS.toString()) ? PORTRAS : 
				component.equals(Components.SwitchActuator.toString()) ? PORTSwitchActuator : 
				component.equals(Components.SwitchSensor1.toString()) ? PORTSwitchSensor1 : 
				component.equals(Components.SwitchSensor2.toString()) ? PORTSwitchSensor2 : 
				component.equals(Components.TestWAN.toString()) ? PORTTestWAN : -1;
		
		if(port<0){
			System.out.println(ras.interfaces.Utilities.separator3+"HeartbeatClient NotBoundException err: Port used is not allowed for "+component);
			throw new NotBoundException("HeartbeatClient err: Port used is not allowed for "+component);
		}
		setRegistry(LocateRegistry.getRegistry(new ConfigurationMachine().getIpAddress(), port));
		if(component.equals(Components.CTS.toString()))
			setStubCTS((CTSInterface)getRegistry().lookup(Components.CTS.toString()+"Interface"));
		else {
			if(component.equals(Components.LTSActuator.toString()))
				setStubLTSActuator((LTSActuatorInterface)getRegistry().lookup(Components.LTSActuator.toString()+"Interface"));
			else {
				if(component.equals(Components.LTSSensor.toString()))
					setStubLTSSensor((LTSSensorInterface)getRegistry().lookup(Components.LTSSensor.toString()+"Interface"));
				else {
					if(component.equals(Components.OpWAN.toString()))
						setStubOPWAN((OPWANInterface)getRegistry().lookup(Components.OpWAN.toString()+"Interface"));
					else {
						if(component.equals(Components.RAS.toString()))
							setStubRAS((RASInterface)getRegistry().lookup(Components.RAS.toString()+"Interface"));
						else {
							if(component.equals(Components.SwitchActuator.toString()))
								setStubSwitchRAS((SwitchRASInterface)getRegistry().lookup(Components.SwitchActuator.toString()+"Interface"));
							else{
								if(component.equals(Components.SwitchSensor1.toString()))
									setStubSwitchRAS((SwitchRASInterface)getRegistry().lookup(Components.SwitchSensor1.toString()+"Interface"));
								else{
									if(component.equals(Components.SwitchSensor2.toString()))
										setStubSwitchRAS((SwitchRASInterface)getRegistry().lookup(Components.SwitchSensor2.toString()+"Interface"));
									else
										if(component.equals(Components.TestWAN.toString()))
											setStubTestWAN((TestWANInterface)getRegistry().lookup(Components.TestWAN.toString()+"Interface"));
										else
											throw new RemoteException("Registry of "+component+" interface was not allowed");
								}
							}
						}
					}
				}
			}	
		}
	}
}
