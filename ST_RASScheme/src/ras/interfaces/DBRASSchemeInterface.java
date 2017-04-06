package ras.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ras.data.ST_RASTest;
import ras.data.ST_RASTestContingencies;
import ras.data.ST_RASTestTripCommand;

public interface DBRASSchemeInterface extends Remote {
	
	public final int PORT = 1250;
	
	public List<Object> selectGenericQuery(String queryDB, Object id) throws RemoteException;
	public int insertGenericQuery(Object data) throws RemoteException, Exception;
	public int insertGenericQuery(ST_RASTest rasTest, ST_RASTestTripCommand tripCommand, List<ST_RASTestContingencies> list) throws RemoteException;
	public boolean updateGenericQuery(ST_RASTest data, int id) throws RemoteException;
	public boolean updateGenericQuery(Object data, int id) throws RemoteException;
	public void insertParametersComponents(int port, String component) throws RemoteException;
	public void logTest(String queryDB) throws RemoteException;
	public String logLink(ST_RASTest rasTest, int Source, int Destination, int idTypeAction, Boolean resultLink,
			Boolean stateComponent, String Message, String comment) throws RemoteException, Exception;
}