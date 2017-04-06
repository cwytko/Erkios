package ras.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SwitchRASInterface extends Remote{
	public final int PORTSwitchSensor1 = 1300;
	public final int PORTSwitchSensor2 = 1305;
	public final int PORTSwitchActuator = 1310;
	
	public boolean disableEnableSensor(TestCtrlSignal testSignal) throws RemoteException, Exception;
	public boolean resultDisableEnableSensor(DigitalSignal digitalSig) throws RemoteException, NumberFormatException, Exception;
	public boolean sendMessageSensor(TestCtrlSignal testSignal) throws RemoteException, Exception;
	public boolean resultSendMessageSensor(DigitalSignal digitalSig) throws RemoteException, Exception;
	
	public boolean disableEnableActuator(TestCtrlSignal testSignal) throws RemoteException, Exception;	
	public boolean sendMessageActuator(DigitalSignal digitalSig) throws RemoteException, Exception;
	
	//public boolean receiveMessagePTCT(AnalogSignal anaSignal);
}
