package ras.serverLogic;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ras.clientLogic.SwitchClient;
import ras.data.ST_contingencies;
import ras.data.ST_remedialActions;
import ras.interfaces.DigitalSignal;
import ras.interfaces.RASInterface;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.SchemeRAS;
import ras.interfaces.Utilities.TypeActions;

public class RASImpl extends UnicastRemoteObject implements RASInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PORT = RASInterface.PORTRAS;
	private Registry registry;
	private static RASImpl RAS;

	protected RASImpl() throws RemoteException, AlreadyBoundException {
		super();
		// TODO Auto-generated constructor stub
		registry = LocateRegistry.createRegistry(PORT);
		registry.bind("RASInterface", this);
	}

	public static void main(String[] args){
		try {
			RAS = new RASImpl();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"RASImpl err: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		RAS.message();
	}
	
	private void message(){
		System.out.println("RASInterface bound in registry on port "+PORT);
	}
	
	@Override
	public void sendMessageActuator(DigitalSignal digSignal)
			throws RemoteException, NotBoundException, Exception {
		// TODO Auto-generated method stub
		SwitchClient stubSwitch = new SwitchClient(Components.SwitchActuator.toString());
		String message = "", idRAS = "";
		String newline = System.getProperty("line.separator");
		
		if(digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()))
			message = ras.interfaces.Utilities.separator3+"CTS has sent the command to trip RAS Scheme";
		else
			if(digSignal.getTypeAction().equals(TypeActions.SendMessageCA.toString()))
				message = ras.interfaces.Utilities.separator1+"***** CA has sent the command to trip RAS Scheme";
		idRAS = digSignal.getSchemeRAS().getIdRAS();
		
		message += newline+ras.interfaces.Utilities.separator3+"LIST OF CONTINGENCIES";
		for(ST_contingencies contingency : digSignal.getContingenciesList())
			message += newline+ras.interfaces.Utilities.separator3+"- "+contingency.getContingency();
		message += newline+ras.interfaces.Utilities.separator3+"LIST OF REMEDIAL ACTIONS";
		for(ST_remedialActions remedial : digSignal.getRemedialActions())
			message += newline+ras.interfaces.Utilities.separator3+""+remedial.getIdRemedialAction()+". "+remedial.getRemedialAction();
		System.out.println(message);
		try {
			stubSwitch.getStubSwitch().sendMessageActuator(digSignal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RemoteException(e.getMessage());
		}
		
	}

}
