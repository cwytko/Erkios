package ras.interfaces;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface LTSActuatorInterface extends Remote{
	
	public final int PORT = 1205;
	//Message sent from Test WAN to switch to disable actuator
	//This action will allow to perform the test with the digital signal provided by CTS
	public TestCtrlSignal disableEnableActuator(TestCtrlSignal testSignal) 
			throws RemoteException, InterruptedException, NotBoundException, Exception;
	
	public void resultDisableEnableActuator(DigitalSignal digSignal) throws RemoteException;
	
	//Digital signal will be received from Op. WAN to trip RAS scheme
	//The digital signal received by LTS Actuator will be on charge of trip command RAS Scheme
	//This signal belong to type of RAS selected as first instance on the CTS
	public void sendMesssageActuator(DigitalSignal digSignal) throws RemoteException, NotBoundException, Exception;
	
	//Digital signal received as result of the test
	public void receiveResultsRAS(DigitalSignal digSignal) throws RemoteException, NotBoundException, Exception;
	
	public String verifyHeartbeat(String component) throws RemoteException, NotBoundException;
}
