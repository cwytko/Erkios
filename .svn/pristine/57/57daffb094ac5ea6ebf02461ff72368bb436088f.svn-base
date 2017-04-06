package ras.serverLogic;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ras.clientLogic.LTSClient;
import ras.data.ST_contingencies;
import ras.interfaces.DigitalSignal;
import ras.interfaces.OPWANInterface;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.SchemeRAS;
import ras.interfaces.Utilities.TypeActions;
import ras.security.EncryptDecrypt;

public class OPWANImpl  extends UnicastRemoteObject implements OPWANInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PORT = OPWANInterface.PORTOPWan;
	private Registry registry;
	private static OPWANImpl opWAN;

	protected OPWANImpl() throws RemoteException, AlreadyBoundException {
		registry = LocateRegistry.createRegistry(PORT);
		registry.bind("OPWANInterface", this);
	}

	public static void main(String[] args){
		try {
			opWAN = new OPWANImpl();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"OPWANImpl err: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		opWAN.message();
	}
	
	private void message(){
		System.out.println("OPWANInterface bound in registry on port "+PORT);
	}
	
	@Override
	public void sendMessageActuator(DigitalSignal digSignal)
			throws RemoteException, NotBoundException, Exception {
		// TODO Auto-generated method stub
		LTSClient stubLTS = new LTSClient(Components.LTSActuator.toString());
		
		if(digSignal.getResultTestSensor() && !digSignal.getDisableEnableComponent() && digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString())){
			String idSession = new EncryptDecrypt().decryptMsg(digSignal.getIdSession());
			System.out.println(ras.interfaces.Utilities.separator2+"***** CTS has created a command to trip RAS Scheme "+digSignal.getSchemeRAS().getNameRAS()
					+" ("+digSignal.getClassRAS().getNameClass()+"Category) with idRASTest: "+idSession.split("#")[2]);
			
			String idRAS = idSession.split("#")[1];
	
			
			System.out.println(ras.interfaces.Utilities.separator1+"LIST OF CONTINGENCIES");
			for(ST_contingencies contingency : digSignal.getContingenciesList())
				System.out.println(ras.interfaces.Utilities.separator1+"- "+contingency.getContingency());
		} else {
			if(digSignal.getTypeAction().equals(TypeActions.SendMessageCA.toString())){
				System.out.println(ras.interfaces.Utilities.separator2+"***** CA has created a command to trip RAS Scheme "+digSignal.getSchemeRAS().getNameRAS()
						+" ("+digSignal.getClassRAS().getNameClass()+"Category): ");
				
				String idRAS = digSignal.getSchemeRAS().getIdRAS();
				
				System.out.println(ras.interfaces.Utilities.separator1+"LIST OF CONTINGENCIES");
				for(ST_contingencies contingency : digSignal.getContingenciesList())
					System.out.println(ras.interfaces.Utilities.separator1+"- "+contingency.getContingency());
			}
		}
		try {
			stubLTS.getStubLTSActuator().sendMesssageActuator(digSignal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RemoteException(e.getMessage());
		}
		
	}

	@Override
	public void receiveMessageCA(DigitalSignal digSignal)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
