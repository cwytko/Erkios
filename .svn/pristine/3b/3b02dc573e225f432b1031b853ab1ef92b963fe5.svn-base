package ras.serverLogic;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ras.data.ConnectionDB;
import ras.data.ST_RASTest;
import ras.data.ST_RASTestContingencies;
import ras.data.ST_RASTestLink;
import ras.data.ST_RASTestTripCommand;
import ras.data.ST_linkEES;
import ras.interfaces.DBRASSchemeInterface;

public class DBRASSchemeImpl extends UnicastRemoteObject implements DBRASSchemeInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PORT = DBRASSchemeInterface.PORT;//(new ConfigurationMachine().getRealPort());
	private Registry registry;
	private static DBRASSchemeImpl dbRAS;
	
	protected DBRASSchemeImpl() throws RemoteException{
		try{
			registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("DBRASSchemeInterface", this);
		} catch (RemoteException e){
			throw e;
		}
		/*super(0, 
				new SslRMIClientSocketFactory() //RMISSLClientSocketFactory() 
				new SslRMIServerSocketFactory(	null,
                        						null, 
                        						true) //RMISSLServerSocketFactory());*/
	}
	
	public static void main(String[] args){
		try{
			/*Registry registry = LocateRegistry.createRegistry(PORT, 
					new SslRMIClientSocketFactory(),	//RMISSLClientSocketFactory() 
					new SslRMIServerSocketFactory() //RMISSLServerSocketFactory());
			RMIRegistry rmi = new RMIRegistry(PORT);
			rmi.initiateRMI();
			Registry registry = LocateRegistry.getRegistry(null, PORT, new SslRMIClientSocketFactory());*/
			
			dbRAS = new DBRASSchemeImpl();
			dbRAS.message();
		} catch (Exception e){
			System.out.println("DBRASSchemeInterface err: "+ e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void message(){
		System.out.println("DBRASSchemeInterface bound in registry on port "+PORT);
	}
	
	@Override
	public List<Object> selectGenericQuery(String queryDB, Object id) throws RemoteException {
		// TODO Auto-generated method stub
		ConnectionDB db = new ConnectionDB();
		return db.selectGenericQuery(queryDB, id);
		
	}

	@Override
	public int insertGenericQuery(Object data) {
		// TODO Auto-generated method stub
		ConnectionDB db = new ConnectionDB();
		return db.insertGenericQuery(data);
		
	}

	@Override
	public int insertGenericQuery(ST_RASTest rasTest, ST_RASTestTripCommand tripCommand,
			List<ST_RASTestContingencies> contingencies) throws RemoteException {
		// TODO Auto-generated method stub
		ConnectionDB db = new ConnectionDB();
		return db.insertGenericQuery(rasTest, tripCommand, contingencies);
	}
	
	@Override
	public void insertParametersComponents(int port, String component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logTest(String queryDB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateGenericQuery(ST_RASTest data, int id) throws RemoteException{
		// TODO Auto-generated method stub
		ConnectionDB db = new ConnectionDB();
		return db.updateGenericQuery(data,id);
	}

	@Override
	public String logLink(ST_RASTest rasTest, int Source, int Destination,
			int idTypeAction, Boolean resultLink, Boolean stateComponent,
			String message, String comment) throws Exception {
		// TODO Auto-generated method stub
		ST_linkEES link = new ST_linkEES();
		ST_RASTestLink testLink = null;
		String queryDB = null;
		int idLink = 0;
		
		try {
			queryDB = "from sra_linkEES where cee_source = "+Source+" and cee_destination = "+Destination;
			link = (ST_linkEES)(Object)selectGenericQuery(queryDB, null).get(0);
			testLink = new ST_RASTestLink(link, rasTest, idTypeAction, resultLink, stateComponent, comment);
			idLink = insertGenericQuery(testLink);
			
			if(idLink > 0)
				message = ras.interfaces.Utilities.separator1+"Data received by "+message+"- idLink:"+idLink+" idRASTest:"+rasTest.getIdRASTest()+" idTypeAction:"+idTypeAction;
			else
				message = ras.interfaces.Utilities.separator1+"Error to store log of data received by "+message+"- idLink:"+idLink+" idRASTest:"+rasTest.getIdRASTest()+" idTypeAction:"+idTypeAction;
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("DBRASSChemeImpl err:"+e.getMessage());
		}
		return message;
	}

	@Override
	public boolean updateGenericQuery(Object data, int id)
			throws RemoteException {
		ConnectionDB db = new ConnectionDB();
		return db.updateGenericQuery(data, id);
		// TODO Auto-generated method stub
	}
}