package ras.interfaces;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RASInterface extends Remote {
	public final int PORTRAS = 2025;
	
	public void sendMessageActuator(DigitalSignal digSignal) throws RemoteException, NotBoundException, Exception;
}
