package ras.interfaces;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TestWANInterface extends Remote{
	//***** PROXY, message is delivered to LTS actuator, LTS sensor or CTS
	public final int PORT = 1245;

	//Action received from CTS to LTS actuator/sensor to disable/enable actuator and sensor
	public TestCtrlSignal diseableEnableActuatorSensor(TestCtrlSignal testSignal) throws RemoteException, NotBoundException, InterruptedException, Exception;
	//Test WAN receives message to be used for testing purposes, it will be sent to LTS Sensor
	public TestCtrlSignal sendMessage(TestCtrlSignal testSignal) throws RemoteException, NotBoundException, Exception;
	//Once test is completed, CTS will received a notification that the test was successful 
	//to re-enable actuator and sensor
	public void receivesResultsRAS(TestCtrlSignal resultTest) throws RemoteException, NotBoundException, Exception;
	//Test WAN will send the result of the test to CTS
	public void sendLogCTS(TestCtrlSignal logDataTest) throws RemoteException;
	
	public String verifyHeartbeat(String component) throws RemoteException, NotBoundException;
}
