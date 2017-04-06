package ras.interfaces;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface LTSSensorInterface extends Remote{
	
	public static int PORT = 1200;
	//Message is sent from CTS to LTS Sensor to disable Sensor
	//This action will allow to perform the test with the digital signal provided by CTS
	public TestCtrlSignal disableEnableSensor(TestCtrlSignal testSignal) throws RemoteException, InterruptedException, NotBoundException, Exception;
	
	public void resultDisableEnableSensor(DigitalSignal digitalSig) throws RemoteException;
	
	//Test data to be sent to Op. WAN which initiates the test, it receives a test control signal
	//After a message (test control signal) is received by LTS Sensor, it has to be redirected to Switch
	//to convert it to digital Signal 
	public TestCtrlSignal sendMessageSensor(TestCtrlSignal signalParemeters) throws RemoteException, Exception;
	
	public void resultTestSensor(DigitalSignal digitalSig) throws RemoteException;
	
	//LTS receives a digital signal which in turn will used to send to actuator
	//This digital signal is converted from a test control signal which was created on CTS
	public boolean receivesDigitalSignalTest(DigitalSignal signalTest) throws RemoteException;
	
	public String verifyHeartbeat(String component) throws RemoteException, NotBoundException;
}
