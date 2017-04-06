package ras.interfaces;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OPWANInterface extends Remote {
	public final int PORTOPWan = 2010;
	
	public void sendMessageActuator(DigitalSignal digSignal) throws RemoteException, NotBoundException, Exception;
	public void receiveMessageCA(DigitalSignal digSignal) throws RemoteException;
}
