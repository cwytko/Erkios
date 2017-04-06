package ras.serverLogic;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ras.clientLogic.DBRASSchemeClient;
import ras.clientLogic.HeartbeatClient;
import ras.clientLogic.RASClient;
import ras.clientLogic.SwitchClient;
import ras.clientLogic.TestWANClient;
import ras.data.ST_RASTest;
import ras.data.ST_RASTestDisableEnableComponent;
import ras.data.ST_RASTestSTCode;
import ras.data.ST_RASTestSTCodeContingency;
import ras.data.ST_contingencies;
import ras.data.ST_remedialActions;
import ras.interfaces.DigitalSignal;
import ras.interfaces.LTSActuatorInterface;
import ras.interfaces.TestCtrlSignal;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.ComponentsTest;
import ras.interfaces.Utilities.DisableEnableComponents;
import ras.interfaces.Utilities.SchemeRAS;
import ras.interfaces.Utilities.TypeActions;
import ras.security.EncryptDecrypt;

public class LTSActuatorImpl extends UnicastRemoteObject implements LTSActuatorInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int PORT = LTSActuatorInterface.PORT;
	private Boolean disableEnableActuator;
	private String idSession;
	private Registry registry;
	private static LTSActuatorImpl ltsActuator;
	
	private static final int MAX_HEARBEATS = ras.interfaces.Utilities.MAX_HEARTBEATS;
	private static int countHeartbeats = 0;
	private boolean flagHeartbeat  = false;
	private int extendHeartbeat = ras.interfaces.Utilities.extendHeartbeat;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Future<Object> future = null;
	
	public Boolean getDisableEnableActuator() {
		return disableEnableActuator;
	}

	private void setDisableEnableActuator(Boolean disableEnableActuator) {
		this.disableEnableActuator = disableEnableActuator;
	}

	public String getIdSession() {
		return idSession;
	}

	private void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	protected LTSActuatorImpl() throws RemoteException, AlreadyBoundException {
		registry = LocateRegistry.createRegistry(PORT);
		registry.bind("LTSActuatorInterface", this);
	}
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		ltsActuator = new LTSActuatorImpl();
		try {
			ltsActuator.message();
			ltsActuator.checkHeartBeat();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("LTSActuatorInterface err: "+e.getMessage());
			throw new RemoteException("LTSActuatorInterface err:"+e.getMessage());
		}
		
	}
	
	private void message(){
		System.out.println("LTSActuatorInterface bound in registry on port "+PORT);
	}

	@Override
	public TestCtrlSignal disableEnableActuator(TestCtrlSignal testSignal) throws RemoteException, InterruptedException, NotBoundException, Exception {
		// TODO Auto-generated method stub
		
		String message = testSignal.getMessage();
		ST_RASTest rasTest = testSignal.getRasTest();
		DigitalSignal digSignal = testSignal.getDigSignal();
		
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		SwitchClient stubSwitch = new SwitchClient(Components.SwitchActuator.toString());
		boolean resultActuator = false;
		Date dateTimeActuator = null;
		ST_RASTestDisableEnableComponent decAux = null;
		
		try {
			try {
				System.out.println(message);
				int ordinalDisableEnable = (digSignal.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.TestWAN.ordinal()+1,
						Components.LTSActuator.ordinal()+1, ordinalDisableEnable, true, true, "LTS Actuator",""));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
			//********* THIS PROCESS MUST SEND A SIGNAL TO SWITCH ACTUATOR THAT SENDS A COMMAND TO ACTUATOR TO DISABLE/ENABLE IT
			//To disable an actuator  disableEnableComponent must be FALSE, otherwise it will enable actuator
			String idSession = new EncryptDecrypt().encryptMsg(rasTest.getIdClassification().getIdClassification()+"#"
					+rasTest.getIdRAS().getIdRAS()+"#"+Integer.toString(rasTest.getIdRASTest()));
			
			TestCtrlSignal testAuxCtrlSignal = new TestCtrlSignal(digSignal, null, rasTest, message, 
					(digSignal.getDisableEnableComponent() ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString()),
					testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
			stubSwitch.getStubSwitch().disableEnableActuator(testAuxCtrlSignal);
			
			while(!idSession.equals(getIdSession()))
				Thread.sleep(ras.interfaces.Utilities.SLEEP);
			
			/*int ordinalDisableEnable = (digSignal.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.ACTUATOR.ordinal()+1,
					Components.LTSActuator.ordinal()+1, ordinalDisableEnable, true, true, "LTS Actuator",""));*/
			resultActuator = getDisableEnableActuator(); //new Random().nextBoolean();
			if(getDisableEnableActuator().equals(digSignal.getDisableEnableComponent()) && getIdSession().equals(digSignal.getIdSession())){
				dateTimeActuator = Calendar.getInstance().getTime();
				decAux = new ST_RASTestDisableEnableComponent(null, null, resultActuator, dateTimeActuator);
				//setDisableEnableActuator(resultActuator);
				
				System.out.println(ras.interfaces.Utilities.separator1+""+(digSignal.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
							+" of ACTUATOR was "+(digSignal.getDisableEnableComponent() == resultActuator ? "successful" : "unsuccessful"));	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(ras.interfaces.Utilities.separator3+"LTSActuatorImpl err: disableEnableActuator \n"+e.getMessage());
			throw new RemoteException("LTSActuatorImpl err: disableEnableActuator \n"+e.getMessage());
		}
		return new TestCtrlSignal(digSignal.getDisableEnableComponent(),digSignal, null, rasTest, null,"", decAux,
				testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
		//return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sendMesssageActuator(DigitalSignal digSignal)
			throws RemoteException, NotBoundException, Exception {
		// TODO Auto-generated method stub
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		RASClient stubRAS = new RASClient();
		ST_RASTest rasTest = null;
		ST_RASTestSTCode testCode = null;
		List<ST_RASTestSTCodeContingency> listTestCodeContingency = null;
		
		
		int ordinalTypeAction;
		String message = "", idRAS = "",idSession = new EncryptDecrypt().decryptMsg(digSignal.getIdSession());
		String newline = System.getProperty("line.separator");
		
		try {
			final TestSignalTimeout testSignalTimeout = new TestSignalTimeout(idSession.split("#")[2]);
			future = executor.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					// TODO Auto-generated method stub
					DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
					testSignalTimeout.setRasTest((ST_RASTest)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(
							"from sra_RASTest where tra_idRASTest =:id", testSignalTimeout.getIdRASTest()).get(0));
					
					String queryDB = "from sra_RASTestSTCode where rts_idTypeAction = "+
							(TypeActions.SendMessageTest.ordinal()+1)+" and rts_idRASTest =:id";
					testSignalTimeout.setTestCode((ST_RASTestSTCode)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(
							queryDB, testSignalTimeout.getIdRASTest()).get(0));
					testSignalTimeout.setListTestCodeContingency((List<ST_RASTestSTCodeContingency>)(Object)
							stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTestSTCodeContingency "+
							"where rsc_idRASTest =:id", testSignalTimeout.getIdRASTest()));
					
					return null;
				}
			});
			
			try {
				future.get(ras.interfaces.Utilities.TIMEOUT*2,TimeUnit.SECONDS);
			} catch (InterruptedException | TimeoutException | ExecutionException e) {
				// TODO: handle exception
				System.out.println(ras.interfaces.Utilities.separator3+"sendMessageActuator err:\n"+e.getMessage());
				throw new Exception("sendMessageActuator err:\n"+e.getMessage());
			}
			
			
			rasTest = testSignalTimeout.getRasTest();
			//rasTest = (ST_RASTest)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTest where tra_idRASTest =:id", idSession.split("#")[2]).get(0);
			//String queryDB = "from sra_RASTestSTCode where rts_idTypeAction = "+ (TypeActions.SendMessageTest.ordinal()+1)+" and rts_idRASTest =:id";
			testCode = testSignalTimeout.getTestCode();
			//testCode = (ST_RASTestSTCode)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDB, idSession.split("#")[2]).get(0);				
			listTestCodeContingency = testSignalTimeout.getListTestCodeContingency();
			//listTestCodeContingency = (List<ST_RASTestSTCodeContingency>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTestSTCodeContingency "+
			//				"where rsc_idRASTest =:id", idSession.split("#")[2]); 
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"LTSActuatorImpl err:\n"+e.getMessage());
			throw new RemoteException("LTSActuatorImpl err:\n"+e.getMessage());
		}
		
		
		/*FAULT INJECTION */
		STFaultInjector injector = new STFaultInjector(testCode, listTestCodeContingency);
		if(new Random().nextBoolean() && !testCode.getIdComponent().getShortNameComponent().equals(ComponentsTest.None.toString())){
			testCode = injector.injectErrorSTCode();
		} else {
			if(new Random().nextBoolean() && !testCode.getIdComponent().getShortNameComponent().equals(ComponentsTest.None.toString()))
				listTestCodeContingency = injector.injectListErrorSTCodeContingency();
		}
				
		ordinalTypeAction = digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ? TypeActions.SendMessageTest.ordinal()+1 :
			TypeActions.SendMessageCA.ordinal()+1;
		
		System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSSensor.ordinal()+1 , Components.OpWAN.ordinal()+1, 
				ordinalTypeAction, true, true, "Op WAN", digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()) 
				? TypeActions.SendMessageTest.toString() : TypeActions.SendMessageCA.toString()));
		
		/*SELF-TESTING CONTROL OPWAN*/
		STControl stControl = new STControl(testCode, listTestCodeContingency);
		stControl.controlSTCode(Components.OpWAN.ordinal()+1, Components.LTSActuator.ordinal()+1, Components.OpWAN.toString());
		System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL OPWAN PASSED");
		stControl.controlSTCodeContingency(Components.OpWAN.ordinal()+1, Components.LTSActuator.ordinal()+1, Components.OpWAN.toString());
		System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL OPWAN PASSED");
		
		System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.OpWAN.ordinal()+1 , Components.LTSActuator.ordinal()+1, 
				ordinalTypeAction, true, true, "LTS Actuator", digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()) 
				? TypeActions.SendMessageTest.toString() : TypeActions.SendMessageCA.toString()));
		
		if(digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()))
			message = ras.interfaces.Utilities.separator3+"CTS has sent the command to trip RAS Scheme";
		else
			if(digSignal.getTypeAction().equals(TypeActions.SendMessageCA.toString()))
				message = ras.interfaces.Utilities.separator1+"***** CA has sent the command to trip RAS Scheme";
		idRAS = digSignal.getSchemeRAS().getIdRAS();
		
		if(idRAS.equals(SchemeRAS.ios_I.toString()) || idRAS.equals(SchemeRAS.ios_III.toString()))
			message += newline+ras.interfaces.Utilities.separator3+"VARIABLE - Power:"+digSignal.getPower();
		else{
			if(idRAS.equals(SchemeRAS.siv_R.toString()))
				message += newline+ras.interfaces.Utilities.separator3+"VARIABLE - Power:"+digSignal.getPower()+ "(MW) Current: "+digSignal.getCurrent()
				+"(A) Voltage: "+digSignal.getVoltage()+"(V) Frequency: "+digSignal.getFrequency()+"(Hz)";
			else{
				if(idRAS.equals(SchemeRAS.ios_II.toString()) || idRAS.equals(SchemeRAS.siv_E.toString()))
					message += newline+ras.interfaces.Utilities.separator3+"VARIABLE - Reactive Power:"+digSignal.getReactivePower()+"(MVAR)";
				else
					if(idRAS.equals(SchemeRAS.siv_S.toString()))
						message += newline+ras.interfaces.Utilities.separator3+"VARIABLE - Phase of Voltage:"+digSignal.getPhaseVoltage()+"(φ)";
			}
		}
		
		message += newline+ras.interfaces.Utilities.separator3+"LIST OF CONTINGENCIES";
		for(ST_contingencies contingency : digSignal.getContingenciesList())
			message += newline+ras.interfaces.Utilities.separator3+"- "+contingency.getContingency();
		
		System.out.println(message);
		
		stControl.controlSTCode(Components.LTSActuator.ordinal()+1, Components.RAS.ordinal()+1, Components.RAS.toString());
		System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL RAS PASSED");
		stControl.controlSTCodeContingency(Components.LTSActuator.ordinal()+1, Components.RAS.ordinal()+1, Components.RAS.toString());
		System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL RAS PASSED");
		
		try {
			stubRAS.getStubRAS().sendMessageActuator(digSignal);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RemoteException(e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void receiveResultsRAS(DigitalSignal digSignal)
			throws RemoteException, NotBoundException, Exception {
		// TODO Auto-generated method stub
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		TestWANClient stubTestWAN = null;
		TestCtrlSignal resultTest = null;
		ST_RASTestSTCode testCode = null;
		List<ST_RASTestSTCodeContingency> listTestCodeContingency = null;
		String idSession = new EncryptDecrypt().decryptMsg(digSignal.getIdSession());
		
		ST_RASTest rasTest = (ST_RASTest)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTest where tra_idRASTest =:id",
				idSession.split("#")[2]).get(0);
		
		String queryDB = "from sra_RASTestSTCode where rts_idTypeAction = "+
				(TypeActions.SendMessageTest.ordinal()+1)+" and rts_idRASTest =:id";
		testCode = (ST_RASTestSTCode)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDB, idSession.split("#")[2]).get(0);				
		listTestCodeContingency = (List<ST_RASTestSTCodeContingency>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTestSTCodeContingency "+
				"where rsc_idRASTest =:id", idSession.split("#")[2]);
		
		/*FAULT INJECTION */
		STFaultInjector injector = new STFaultInjector(testCode, listTestCodeContingency);
		if(new Random().nextBoolean() && !testCode.getIdComponent().getShortNameComponent().equals(ComponentsTest.None.toString())){
			testCode = injector.injectErrorSTCode();
		} else {
			if(new Random().nextBoolean() && !testCode.getIdComponent().getShortNameComponent().equals(ComponentsTest.None.toString()))
				listTestCodeContingency = injector.injectListErrorSTCodeContingency();
		}
		
		String comment = digSignal.getResultTestActuator().equals(digSignal.getResultTestSensor()) ? "TEST RAS WAS SUCCESSFUL" : 
			"TEST RAS WAS UNSUCCESSFUL";
		
		if(digSignal.getRemedialActions() != null && !digSignal.getRemedialActions().isEmpty()){
			System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchActuator.ordinal()+1,
					Components.LTSActuator.ordinal()+1, digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ?
							TypeActions.SendMessageTest.ordinal()+1 : TypeActions.SendMessageCA.ordinal()+1, true, true, "LTS Actuator",
							comment));
			
			System.out.println(ras.interfaces.Utilities.separator3+"LIST OF REMEDIAL ACTIONS");
			for(ST_remedialActions remedial:digSignal.getRemedialActions())
				System.out.println(ras.interfaces.Utilities.separator3+"- "+remedial.getRemedialAction());
		} else {
			System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.RAS.ordinal()+1, Components.SwitchActuator.ordinal()+1,
					digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ? TypeActions.SendMessageTest.ordinal()+1
					: TypeActions.SendMessageCA.ordinal()+1, false, true, "Switch Actuator", comment + " REMEDIAL ACTIONS WHERE NOT RECEIVED SUCCESSFULLY"));
		}
		resultTest = new TestCtrlSignal(digSignal.getDisableEnableComponent(), digSignal, null, rasTest, comment, 
				digSignal.getTypeAction(),null,testCode,listTestCodeContingency);
		try {
			monitorHeartbeat();
			stubTestWAN = new TestWANClient();
			
			/*SELF-TESTING CONTROL RAS*/
			STControl stControl = new STControl(testCode, listTestCodeContingency);
			stControl.controlSTCode(Components.SwitchActuator.ordinal()+1, Components.LTSActuator.ordinal()+1, Components.SwitchActuator.toString());
			System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL SwitchActuator PASSED");
			stControl.controlSTCodeContingency(Components.SwitchActuator.ordinal()+1, Components.LTSActuator.ordinal()+1, Components.SwitchActuator.toString());
			System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL SwitchActuator PASSED");
			
			stubTestWAN.getStubTestWAN().receivesResultsRAS(resultTest);	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.TestWAN.ordinal()+1,
					Components.LTSActuator.ordinal()+1, digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ? TypeActions.SendMessageTest.ordinal()+1
							: TypeActions.SendMessageCA.ordinal()+1, false, false, "LTSActuator-TestWAN", e.getMessage().substring(0, 99)));
			System.out.println(ras.interfaces.Utilities.separator3+"receiveResultsRAS err:"+e.getMessage());
			throw new RemoteException("LTSActuatorImpl receiveresultsRAS err:"+e.getMessage());
		}
	}

	@Override
	public void resultDisableEnableActuator(DigitalSignal digSignal)
			throws RemoteException {
		// TODO Auto-generated method stub
		setIdSession(digSignal.getIdSession());
		setDisableEnableActuator(digSignal.getDisableEnableComponent());
	}

	@Override
	public String verifyHeartbeat(String component) throws RemoteException,
			NotBoundException {
		// TODO Auto-generated method stub
		String comp = null;
		if(component.equals(Components.TestWAN.toString()))
			comp = Components.TestWAN.toString();
		
		return comp;
	}
	
	private void checkHeartBeat(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
					try {
						Thread.sleep(extendHeartbeat);
						String heartbeatTestWAN = null; 
						HeartbeatClient stubHeartbeat = new HeartbeatClient(Components.TestWAN.toString());
						heartbeatTestWAN = stubHeartbeat.getStubTestWAN().verifyHeartbeat(Components.LTSActuator.toString());
						if(heartbeatTestWAN.equals(Components.LTSActuator.toString()))
							flagHeartbeat = true;
						if(countHeartbeats>0 && flagHeartbeat == true){
							countHeartbeats = 0;
							extendHeartbeat = ras.interfaces.Utilities.extendHeartbeat;
						}
					} catch (InterruptedException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						countHeartbeats++;
						System.out.println(ras.interfaces.Utilities.separator1+">>>>> HEARTBEATS WITHOUT RESPONSE: "+countHeartbeats+" FROM "+Components.TestWAN.toString()+" <<<<<");
						flagHeartbeat = false;
						if(countHeartbeats == MAX_HEARBEATS){
							extendHeartbeat += ras.interfaces.Utilities.extendHeartbeat/2;
							//countHeartbeats = 0;
							System.out.println(ras.interfaces.Utilities.separator4+"<<<<< Server "+Components.TestWAN.toString()+" was down for "+ MAX_HEARBEATS +" Heartbeats");
						}
					}
			}
		}).start();
	}
	
	private void monitorHeartbeat() throws Exception{
		if(!flagHeartbeat){
			while(!flagHeartbeat){//System.out.print("here1");
				Thread.sleep(extendHeartbeat/2);
				if(flagHeartbeat){
					System.out.println(ras.interfaces.Utilities.separator1+"<<<<< SERVER "+Components.TestWAN.toString()+" IS UP AGAIN >>>>>");
					Thread.sleep(extendHeartbeat/2);
					break;
				}
				if(countHeartbeats >= MAX_HEARBEATS){
					extendHeartbeat += extendHeartbeat/2;
					int temp = countHeartbeats;
					countHeartbeats = 0;
					throw new Exception(ras.interfaces.Utilities.separator1+"!!!!! Server "+Components.TestWAN.toString()+" was down for "+ temp +" Heartbeats");
				}
			}
			//System.out.println("here2");
			
			System.out.println(ras.interfaces.Utilities.separator1+"<<<<< SYSTEM WILL KEEP WORKING >>>>>");
		}
	}
	
	static class TestSignalTimeout{
		private ST_RASTestSTCode testCode;
		private List<ST_RASTestSTCodeContingency> listTestCodeContingency;
		private String idRASTest;
		private ST_RASTest rasTest;
		
		public TestSignalTimeout(String idRASTest){
			setIdRASTest(idRASTest);
		}

		public String getIdRASTest() {
			return idRASTest;
		}

		private void setIdRASTest(String idRASTest) {
			this.idRASTest = idRASTest;
		}

		public ST_RASTestSTCode getTestCode() {
			return testCode;
		}

		private void setTestCode(ST_RASTestSTCode testCode) {
			this.testCode = testCode;
		}

		public List<ST_RASTestSTCodeContingency> getListTestCodeContingency() {
			return listTestCodeContingency;
		}

		private void setListTestCodeContingency(
				List<ST_RASTestSTCodeContingency> listTestCodeContingency) {
			this.listTestCodeContingency = listTestCodeContingency;
		}

		public ST_RASTest getRasTest() {
			return rasTest;
		}

		private void setRasTest(ST_RASTest rasTest) {
			this.rasTest = rasTest;
		}
		
	}
}
