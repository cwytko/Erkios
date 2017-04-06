package ras.interfaces;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CTSInterface extends Remote {
	
	public final int PORT = 1215;

	//CTS creates a message to be passed from CTS to the field,
	//the message will be created according to the RAS scheme selected 
	//Type I, II, III, Event-based, Response-based or Synchrophasor-based
	public TestCtrlSignal createMessage(TestCtrlSignal testSignal) throws RemoteException, Exception;
	
	//Once a message has been created, the sensor and actuator must be disabled
	//until the test is completed
	public TestCtrlSignal diseableEnableActuatorSensor(TestCtrlSignal testSignal) throws RemoteException, NotBoundException, InterruptedException, Exception;
	
	//After both sensor and actuator has been disabled
	//CTS will send a message to LTS Sensor and Actuator to perform the test
	public TestCtrlSignal sendMessage(TestCtrlSignal testSignal) throws RemoteException, NotBoundException, Exception;
	
	//Once test is completed, CTS will received a notification that the test was successful 
	//This action will allow to re-enable actuator and sensor which were disable previously 
	//to a test
	public void receiveResultTest(TestCtrlSignal resultTest) throws RemoteException;
	
	//Once a test has been performed, the results will be stored in a database to future analysis
	public boolean logTest(TestCtrlSignal testSignal) throws RemoteException;
	
	public String verifyHeartbeat(String component) throws RemoteException, NotBoundException;
}