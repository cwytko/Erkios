package ras.serverLogic;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ras.clientLogic.CTSClient;
import ras.clientLogic.DBRASSchemeClient;
import ras.clientLogic.HeartbeatClient;
import ras.clientLogic.LTSClient;
import ras.data.ST_RASTest;
import ras.data.ST_RASTestDisableEnableComponent;
import ras.data.ST_remedialActions;
import ras.interfaces.AnalogSignal;
import ras.interfaces.DigitalSignal;
import ras.interfaces.TestCtrlSignal;
import ras.interfaces.TestWANInterface;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.DisableEnableComponents;
import ras.interfaces.Utilities.TypeActions;

public class TestWANImpl extends UnicastRemoteObject implements TestWANInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PORT = TestWANInterface.PORT;
	private Registry registry;
	private static TestWANImpl testWAN;
	
	private static final int MAX_HEARBEATS = ras.interfaces.Utilities.MAX_HEARTBEATS;
	private static int countHeartbeatsCTS = 0;
	private static int countHeartbeatsLTSActuator = 0;
	private static int countHeartbeatsLTSSensor = 0;
	private boolean flagHeartbeatCTS = false;
	private boolean flagHeartbeatLTSSensor = false;
	private boolean flagHeartbeatLTSActuator = false;
	private int extendHeartbeatCTS = ras.interfaces.Utilities.extendHeartbeat;
	private int extendHeartbeatLTSSensor = ras.interfaces.Utilities.extendHeartbeat;
	private int extendHeartbeatLTSActuator = ras.interfaces.Utilities.extendHeartbeat;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Future<Object> future = null;
	
	protected TestWANImpl() throws RemoteException, AlreadyBoundException{
		registry = LocateRegistry.createRegistry(PORT);
		registry.bind("TestWANInterface", this);
	}
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		testWAN = new TestWANImpl();
		try {
			testWAN.message();
			testWAN.checkHeartBeat();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(ras.interfaces.Utilities.separator3+"TestWANInterface err: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void message(){
		System.out.println("TestWANInterface bound in registry on port "+PORT);
	}
	/*
	public void logLink(ST_RASTest rasTest, int Source, int Destination, int idTypeAction, 
			Boolean resultLink, Boolean stateComponent, String message) throws RemoteException{
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		ST_linkEES link = new ST_linkEES();
		ST_RASTestLink testLink = null;
		String queryDB = "";
		int idLink = 0;
		try {
			queryDB = "from sra_linkEES where cee_source = "+Source+" and cee_destination = "+Destination;
			link = (ST_linkEES)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDB, null).get(0);
			testLink = new ST_RASTestLink(link, rasTest, idTypeAction, resultLink, stateComponent);
			idLink = stubDBRAS.getStubDBRAS().insertGenericQuery(testLink);
			if(idLink>0)
				System.out.println(ras.interfaces.Utilities.separator1+"Data received by "+message+"- idLink:"+idLink+" idRASTest:"+rasTest.getIdRASTest());
			else
				System.out.println(ras.interfaces.Utilities.separator1+"Error to store log of data received by "+message+"- idLink:"+idLink+" idRASTest:"+rasTest.getIdRASTest());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	*/
	
	@Override
	//disableEnableComponent -> false = DISABLED, true = ENABLED 
	public TestCtrlSignal diseableEnableActuatorSensor(TestCtrlSignal testSignal) throws RemoteException, NotBoundException, InterruptedException, Exception {
		
		ST_RASTest rasTest = testSignal.getRasTest();
		DigitalSignal digSignal = testSignal.getDigSignal();
		AnalogSignal analogSig = testSignal.getAnaSignal();
		
		Boolean flagResult, flagSensor, flagActuator, disableEnableComponent;
		int idAction = 0;
		String message = "";
		Date dateSensor, dateActuator;
		ST_RASTestDisableEnableComponent componentsDisableEnable = new ST_RASTestDisableEnableComponent();
		ST_RASTestDisableEnableComponent componentsDisableEnableActuator = new ST_RASTestDisableEnableComponent();
		ST_RASTestDisableEnableComponent componentsDisableEnableSensor = new ST_RASTestDisableEnableComponent();
		//LTSClient stubLTSClient = null;
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		
		dateSensor = dateActuator = null;
		flagResult = flagSensor = flagActuator = null;
		disableEnableComponent = digSignal.getDisableEnableComponent();
		
		try {
			System.out.println("\n"+ras.interfaces.Utilities.separator2+"***** CTS created a message on :"+rasTest.getDateTimeBeginTest().toString()
					+" idRASTest: "+rasTest.getIdRASTest()
					+" RAS Classification: "+rasTest.getIdClassification().getIdClassification()
					+" RAS Scheme: "+rasTest.getIdRAS().getIdRAS()
					+". SENSOR and ACTUATOR will be "
					+(disableEnableComponent ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString()));
			
			int ordinalDisableEnable = (disableEnableComponent ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal())+1;
			//System.out.println("-----"+disableEnableComponent+"*"+ordinalDisableEnable+"*"+TypeActions.CreateMessage.ordinal()+"*"+TypeActions.EnableComponent.ordinal()+"*"+
			//TypeActions.DisableComponent.ordinal()+"*"+TypeActions.SendMessage.ordinal()+"*"+TypeActions.LogTest.ordinal());
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.CTS.ordinal()+1,
					Components.TestWAN.ordinal()+1, ordinalDisableEnable, true, true, "Test WAN",""));
			
			message = ras.interfaces.Utilities.separator2+"***** CTS created a message on :"+rasTest.getDateTimeBeginTest().toString()
					+" idRASTest: "+rasTest.getIdRASTest()
					+" RAS Classification: "+rasTest.getIdClassification().getIdClassification()
					+" RAS Scheme: "+rasTest.getIdRAS().getIdRAS()
					+". ACTUATOR will be "
					+(disableEnableComponent ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString());

			TestCtrlSignal testAuxCtrlSignal = new TestCtrlSignal(digSignal, null, rasTest, message,
					(disableEnableComponent ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString()),
					testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
			try {
				monitorHeartbeat(Components.LTSActuator.toString());
				
				
				final TestSignalTimeout testSignalTimeout = new TestSignalTimeout(testAuxCtrlSignal);
				future = executor.submit(new Callable<Object>() {

					@Override
					public Object call() throws Exception {
						// TODO Auto-generated method stub
						LTSClient stubLTSClient = new LTSClient(Components.LTSActuator.toString());
						testSignalTimeout.setTestSignal(stubLTSClient.getStubLTSActuator().disableEnableActuator(testSignalTimeout.getTestSignal()));
						return null;
					}
				});
				
				try {
					future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
				} catch (InterruptedException | TimeoutException | ExecutionException e) {
					// TODO: handle exception
					System.out.println(ras.interfaces.Utilities.separator3+"disableEnableActuator err: \n"+e.getMessage());
					throw new Exception("disableEnableActuator err: \n"+e.getMessage());
				}
				
				componentsDisableEnableActuator = testSignalTimeout.getTestSignal().getRasTestDisableEnable();
				//LTSClient stubLTSClient = null;
				//stubLTSClient = new LTSClient(Components.LTSActuator.toString());
				//componentsDisableEnableActuator = stubLTSClient.getStubLTSActuator().disableEnableActuator(testAuxCtrlSignal).getRasTestDisableEnable();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSActuator.ordinal()+1,
						Components.TestWAN.ordinal()+1, ordinalDisableEnable, false, false, "TestWAN-LTSActuator", e.getMessage().substring(0, 99)));
				System.out.println(ras.interfaces.Utilities.separator3+e.getMessage());
				throw new RemoteException("TestWANImpl err:\n"+e.getMessage());
			}
			
			String comment = "";
			if(componentsDisableEnableActuator != null){
				flagActuator = componentsDisableEnableActuator.getDisableEnableActuator();
				dateActuator = componentsDisableEnableActuator.getDatetimeActuator();
			} else {
				comment = "wrong id Session1";
				flagActuator = disableEnableComponent ? false : true;
			}
				
			//componentsDisableEnable.setDatetimeActuator(dateTimeAction.getTime());
			//componentsDisableEnable.setDisableEnableSensor(flagActuator);
			
			/*
			logLink(rasTest, Components.TestWAN.ordinal()+1, Components.LTSActuator.ordinal()+1, 
					TypeActions.DisableEnableComponent.ordinal()+1, true, true, "LTS Actuator");
			logLink(rasTest, Components.LTSActuator.ordinal()+1, Components.SwitchActuator.ordinal()+1, 
					TypeActions.DisableEnableComponent.ordinal()+1, true, true, "Switch Actuator");
			logLink(rasTest, Components.SwitchActuator.ordinal()+1, Components.ACTUATOR.ordinal()+1, 
					TypeActions.DisableEnableComponent.ordinal()+1, true, true, "Actuator");
			*/
			
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.Actuator.ordinal()+1, 
					Components.Actuator.ordinal()+1,ordinalDisableEnable, true, flagActuator, "Actuator Status",
					(flagActuator.equals(disableEnableComponent) ? "successful" : "unsuccessful")));
			
			ordinalDisableEnable = (digSignal.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.Actuator.ordinal()+1,
					Components.LTSActuator.ordinal()+1, ordinalDisableEnable, true, true, "LTS Actuator",comment));
			
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSActuator.ordinal()+1,
					Components.TestWAN.ordinal()+1, ordinalDisableEnable, true, true, "Test WAN",""));
			
			System.out.println(ras.interfaces.Utilities.separator1+"Actuator has been "
					+(disableEnableComponent ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
					+(flagActuator.equals(digSignal.getDisableEnableComponent()) ? " successfully" : " unsuccessfully")
					+" on: "+dateActuator != null ? dateActuator.toString() : "");
			
			if(flagActuator.equals(disableEnableComponent)){		
				message = ras.interfaces.Utilities.separator2+"***** CTS created a message on :"+rasTest.getDateTimeBeginTest().toString()
						+" idRASTest: "+rasTest.getIdRASTest()
						+" RAS Classification: "+rasTest.getIdClassification().getIdClassification()
						+" RAS Scheme: "+rasTest.getIdRAS().getIdRAS()
						+". SENSOR will be "
						+(disableEnableComponent ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString());
				
				testAuxCtrlSignal = new TestCtrlSignal(null, analogSig, rasTest, message, 
						(disableEnableComponent ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString()),
						testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
				try {
					monitorHeartbeat(Components.LTSSensor.toString());
					
					final TestSignalTimeout testSignalTimeout = new TestSignalTimeout(testAuxCtrlSignal);
					future = executor.submit(new Callable<Object>() {

						@Override
						public Object call() throws Exception {
							// TODO Auto-generated method stub
							LTSClient stubLTSClient = new LTSClient(Components.LTSSensor.toString());
							testSignalTimeout.setTestSignal(stubLTSClient.getStubLTSSensor().disableEnableSensor(testSignalTimeout.getTestSignal()));
							return null;
						}
					});
					
					try {
						future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
					} catch (InterruptedException | TimeoutException | ExecutionException e) {
						// TODO: handle exception
						throw new Exception("disableEnableSensor err: \n"+e.getMessage());
					}
					
					componentsDisableEnableSensor = testSignalTimeout.getTestSignal().getRasTestDisableEnable();
					
					//stubLTSClient = new LTSClient(Components.LTSSensor.toString());
					//testAuxCtrlSignal = stubLTSClient.getStubLTSSensor().disableEnableSensor(testAuxCtrlSignal);
					//componentsDisableEnableSensor = testAuxCtrlSignal.getRasTestDisableEnable();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSSensor.ordinal()+1,
							Components.TestWAN.ordinal()+1, ordinalDisableEnable, false, false, "TestWAN-LTSSensor", e.getMessage().substring(0, 99)));
					System.out.println(e.getMessage());
					throw new RemoteException("TestWANImpl err:\n"+e.getMessage());
				}
				
				if(componentsDisableEnableSensor != null){
					flagSensor = componentsDisableEnableSensor.getDisableEnableSensor();
					dateSensor = componentsDisableEnableSensor.getDatetimeSensor();	
				} else
					flagSensor = disableEnableComponent ? false : true;
				
				//componentsDisableEnable.setDatetimeSensor(dateTimeAction.getTime());
				//componentsDisableEnable.setDisableEnableSensor(flagSensor);
				
				/*
				
				logLink(rasTest, Components.TestWAN.ordinal()+1, Components.LTSSensor.ordinal()+1, 
						TypeActions.DisableEnableComponent.ordinal()+1, true, true, "LTS Sensor");
				logLink(rasTest, Components.LTSSensor.ordinal()+1, Components.SwitchSensor1.ordinal()+1, 
						TypeActions.DisableEnableComponent.ordinal()+1, true, true, "Switch Sensor 1");
				logLink(rasTest, Components.SwitchSensor1.ordinal()+1, Components.SENSOR.ordinal()+1, 
						TypeActions.DisableEnableComponent.ordinal()+1, true, true, "Sensor");
				
				*/
				
				/*System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SENSOR.ordinal()+1, 
						Components.SENSOR.ordinal()+1,ordinalDisableEnable, true, flagSensor, "Sensor Status",
						(flagSensor == disableEnableComponent ? "successful" : "unsuccessful")));
				
				ordinalDisableEnable = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor2.ordinal()+1,
						Components.LTSSensor.ordinal()+1, ordinalDisableEnable, true, true, "LTS Sensor", ""));
				*/
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSSensor.ordinal()+1,
						Components.TestWAN.ordinal()+1, ordinalDisableEnable, true, true, "Test WAN", ""));
				
				System.out.println(ras.interfaces.Utilities.separator1+"Sensor has been "
						+(disableEnableComponent ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
						+(flagSensor.equals( digSignal.getDisableEnableComponent()) ? " successfully" : " unsuccessfully")
						+" on: "+dateSensor.toString());
				
				if(!flagSensor.equals(disableEnableComponent)){
					Boolean flagAuxActuator = !flagSensor;
		
					message = ras.interfaces.Utilities.separator1+"!!!!! State of ACTUATOR was rollbacked to its state "
							+(!disableEnableComponent ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString());
					
					String action =(flagSensor ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString());
					DigitalSignal digAuxSignal = new DigitalSignal(digSignal, Integer.toString(rasTest.getIdRASTest()), flagSensor,action);
					testAuxCtrlSignal = new TestCtrlSignal(digAuxSignal, null, rasTest, message, action,testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
					
					while(!flagAuxActuator.equals(flagSensor)){
						try {
							monitorHeartbeat(Components.LTSActuator.toString());
							
							
							final TestSignalTimeout testSignalTimeout = new TestSignalTimeout(testAuxCtrlSignal);
							future = executor.submit(new Callable<Object>() {

								@Override
								public Object call() throws Exception {
									// TODO Auto-generated method stub
									LTSClient stubLTSClient = new LTSClient(Components.LTSActuator.toString());
									testSignalTimeout.setTestSignal(stubLTSClient.getStubLTSActuator().disableEnableActuator(testSignalTimeout.getTestSignal()));
									return null;
								}
							});
							
							try {
								future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
							} catch (InterruptedException | TimeoutException | ExecutionException e) {
								// TODO: handle exception
								System.out.println(ras.interfaces.Utilities.separator3+"disableEnableActuator err: \n"+e.getMessage());
								throw new Exception("disableEnableActuator err: \n"+e.getMessage());
							}
							
							componentsDisableEnable = testSignalTimeout.getTestSignal().getRasTestDisableEnable();
							//stubLTSClient = new LTSClient(Components.LTSActuator.toString());
							// componentsDisableEnable = stubLTSClient.getStubLTSActuator().disableEnableActuator(testAuxCtrlSignal).getRasTestDisableEnable();
							flagAuxActuator = componentsDisableEnable.getDisableEnableActuator();
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSActuator.ordinal()+1,
									Components.TestWAN.ordinal()+1, ordinalDisableEnable, false, false, "TestWAN-LTSActuator", e.getMessage().substring(0, 99)));
							System.out.println(ras.interfaces.Utilities.separator3+e.getMessage());
							throw new RemoteException("TestWANImpl err:\n"+e.getMessage());
						}
						
					}
					dateActuator = Calendar.getInstance().getTime();
					
					System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.Actuator.ordinal()+1, 
							Components.Actuator.ordinal()+1,ordinalDisableEnable-1, true, flagAuxActuator, "Actuator Status (rollback)","rollback status"));
				}
			}
			else
				dateSensor = Calendar.getInstance().getTime();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		flagResult = (flagActuator.equals(flagSensor) && flagActuator.equals(disableEnableComponent)) ? true : false;
		message = "ACTUATOR and SENSOR have " + (flagResult ? "" : "not ") + "been " 
					+(disableEnableComponent ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString()); 
		//componentsDisableEnable.setResultDisableEnableComponent(flagResult);
		//componentsDisableEnable.setIdAction((diseableEnableComponent ? DisableEnableComponent.ENABLED.ordinal() : DisableEnableComponent.DISABLED.ordinal())+1);
		idAction = (disableEnableComponent ? DisableEnableComponents.ENABLED.ordinal() : DisableEnableComponents.DISABLED.ordinal())+1;
		componentsDisableEnable = new ST_RASTestDisableEnableComponent(rasTest.getIdRAS(), idAction, rasTest, flagSensor, dateSensor, 
				flagActuator, dateActuator, flagResult, message);
		
		return new TestCtrlSignal(disableEnableComponent,null, null, null, null,null, componentsDisableEnable,
				testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
	}

	@Override
	public TestCtrlSignal sendMessage(TestCtrlSignal testSignal)
			throws RemoteException, NotBoundException, Exception {
		// TODO Auto-generated method stub
		//LTSClient stubLTSClient = new LTSClient(Components.LTSSensor.toString());
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		//LTSClient stubLTS = null;
		TestCtrlSignal testAuxSignal = null;
		
		System.out.println(testSignal.getMessage());
		System.out.println(ras.interfaces.Utilities.separator5+""+stubDBRAS.getStubDBRAS().logLink(testSignal.getRasTest(), 
				Components.CTS.ordinal()+1, Components.TestWAN.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, true, 
				true, "Test WAN", ""));
		
		if(testSignal.getRasTestDisableEnable().getResultDisableEnableComponent() && !testSignal.getDigSignal().getDisableEnableComponent() &&
				testSignal.getAnaSignal() != null){
			testAuxSignal = new TestCtrlSignal(testSignal, null, null,testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
			
			try {
				monitorHeartbeat(Components.LTSSensor.toString());
				
				final TestSignalTimeout testSignalTimeout = new TestSignalTimeout(testAuxSignal);
				future = executor.submit(new Callable<Object>() {

					@Override
					public Object call() throws Exception {
						// TODO Auto-generated method stub
						LTSClient stubLTS = new LTSClient(Components.LTSSensor.toString());
						testSignalTimeout.setTestSignal(stubLTS.getStubLTSSensor().sendMessageSensor(testSignalTimeout.getTestSignal()));
						return null;
					}
				});
				
				try {
					future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
				} catch (InterruptedException | TimeoutException | ExecutionException e) {
					// TODO: handle exception
					System.out.println(ras.interfaces.Utilities.separator3+"sendMessage err: \n"+e.getMessage());
					throw new Exception("sendMessage err: \n"+e.getMessage());
				}
				
				testAuxSignal = testSignalTimeout.getTestSignal();
				//stubLTS = new LTSClient(Components.LTSSensor.toString());
				//testAuxSignal = stubLTS.getStubLTSSensor().sendMessageSensor(testAuxSignal);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(stubDBRAS.getStubDBRAS().logLink(testAuxSignal.getRasTest(), Components.LTSSensor.ordinal()+1,
						Components.TestWAN.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, false, false, "TestWAN-LTSSensor", e.getMessage().substring(0, 99)));
				System.out.println(ras.interfaces.Utilities.separator3+"TestWANImpl err:\n"+e.getMessage());
				throw new RemoteException("TestWANImpl err:\n"+e.getMessage());
			}
			
			/*
			if(testAuxSignal.getResultTestSensor()){
				testAuxSignal = new TestCtrlSignal(testSignal, null ,testAuxSignal.getResultTestSensor());
				System.out.println(ras.interfaces.Utilities.separator5+""+stubDBRAS.getStubDBRAS().logLink(testSignal.getRasTest(), 
						Components.LTSSensor.ordinal()+1, Components.TestWAN.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, true, 
						true, "TestWAN", ""));
			}*/
		}
		else{
			System.out.println(ras.interfaces.Utilities.separator3+"TestWANImpl err: Either Actuator and/or Sensor has not been disabled properly or an error on Analog Signal");
			throw new RemoteException("TestWANImpl err: Either Actuator and/or Sensor has not been disabled properly or an error on Analog Signal");
		}
		return testAuxSignal;
	}

	@Override
	public void receivesResultsRAS(TestCtrlSignal resultTest)
			throws RemoteException, NotBoundException, Exception {
		// TODO Auto-generated method stub
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		CTSClient stubCTS = null;
		DigitalSignal digSignal = resultTest.getDigSignal();
		
		
		String comment = digSignal.getResultTestActuator().equals(digSignal.getResultTestSensor()) ? "TEST RAS WAS SUCCESSFUL" : 
			"TEST RAS WAS UNSUCCESSFUL";
		
		if(digSignal.getRemedialActions() != null && !digSignal.getRemedialActions().isEmpty()){
			System.out.println(ras.interfaces.Utilities.separator3+"LIST OF REMEDIAL ACTIONS");
			for(ST_remedialActions remedial:digSignal.getRemedialActions())
				System.out.println(ras.interfaces.Utilities.separator3+"- "+remedial.getRemedialAction());
			System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(resultTest.getRasTest(), Components.LTSActuator.ordinal()+1,
					Components.TestWAN.ordinal()+1, digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ?
							TypeActions.SendMessageTest.ordinal()+1 : TypeActions.SendMessageCA.ordinal()+1, true, true, "Test WAN",
							comment));
		} else {
			System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(resultTest.getRasTest(), Components.LTSActuator.ordinal()+1,
					Components.TestWAN.ordinal()+1, digSignal.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ?
							TypeActions.SendMessageTest.ordinal()+1 : TypeActions.SendMessageCA.ordinal()+1, false, true, "Test WAN",
							comment+ "REMEDIAL ACTIONS WHERE NOT RECEIVED SUCCESSFULLY"));
		}
		try {
			monitorHeartbeat(Components.CTS.toString());
			stubCTS = new CTSClient();
			stubCTS.getStubCTS().receiveResultTest(resultTest);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(stubDBRAS.getStubDBRAS().logLink(resultTest.getRasTest(),
					Components.CTS.ordinal()+1, Components.TestWAN.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, 
					false, false, "TestWAN-CTS", e.getMessage().substring(0, 99)));
			System.out.println(ras.interfaces.Utilities.separator3+e.getMessage());
			throw new RemoteException("TestWANImpl err:\n"+e.getMessage());
		}
		
		
	}

	@Override
	public void sendLogCTS(TestCtrlSignal logDataTest) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String verifyHeartbeat(String component) throws RemoteException {
		// TODO Auto-generated method stub
		String comp = null;
		if(component.equals(Components.CTS.toString()))
			comp = Components.CTS.toString();
		else{
			if(component.equals(Components.LTSActuator.toString()))
				comp = Components.LTSActuator.toString();
			else
				if(component.equals(Components.LTSSensor.toString()))
					comp = Components.LTSSensor.toString();
		}
		return comp;
	}
	
	private void checkHeartBeat(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
					try {
						Thread.sleep(extendHeartbeatCTS);
						String heartbeatTestWAN = null; 
						HeartbeatClient stubHeartbeat = new HeartbeatClient(Components.CTS.toString());
						heartbeatTestWAN = stubHeartbeat.getStubCTS().verifyHeartbeat(Components.TestWAN.toString());
						if(heartbeatTestWAN.equals(Components.TestWAN.toString()))
							flagHeartbeatCTS = true;
						if(countHeartbeatsCTS>0 && flagHeartbeatCTS == true){
							countHeartbeatsCTS = 0;
							extendHeartbeatCTS = ras.interfaces.Utilities.extendHeartbeat;
						}
					} catch (InterruptedException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						countHeartbeatsCTS++;
						System.out.println(ras.interfaces.Utilities.separator1+">>>>> HEARTBEATS WITHOUT RESPONSE: "+countHeartbeatsCTS+" FROM "+ Components.CTS.toString() +"<<<<<");
						flagHeartbeatCTS = false;
						if(countHeartbeatsCTS == MAX_HEARBEATS){
							extendHeartbeatCTS += ras.interfaces.Utilities.extendHeartbeat/2;
							//countHeartbeatsCTS = 0;
							System.out.println(ras.interfaces.Utilities.separator4+"<<<<< Server "+Components.CTS.toString()+" was down for "+ MAX_HEARBEATS +" Heartbeats");
						}
					}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
					try {
						Thread.sleep(extendHeartbeatLTSActuator);
						String heartbeatTestWAN = null; 
						HeartbeatClient stubHeartbeat = new HeartbeatClient(Components.LTSActuator.toString());
						heartbeatTestWAN = stubHeartbeat.getStubLTSActuator().verifyHeartbeat(Components.TestWAN.toString());
						if(heartbeatTestWAN.equals(Components.TestWAN.toString()))
							flagHeartbeatLTSActuator = true;
						if(countHeartbeatsLTSActuator>0 && flagHeartbeatLTSActuator == true){
							countHeartbeatsLTSActuator = 0;
							extendHeartbeatLTSActuator = ras.interfaces.Utilities.extendHeartbeat;
						}
					} catch (InterruptedException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						countHeartbeatsLTSActuator++;
						System.out.println(ras.interfaces.Utilities.separator1+">>>>> HEARTBEATS WITHOUT RESPONSE: "+countHeartbeatsLTSActuator+" FROM "+ Components.LTSActuator.toString() +"<<<<<");
						flagHeartbeatLTSActuator = false;
						if(countHeartbeatsLTSActuator == MAX_HEARBEATS){
							extendHeartbeatLTSActuator += ras.interfaces.Utilities.extendHeartbeat/2;
							//countHeartbeatsLTSActuator = 0;
							System.out.println(ras.interfaces.Utilities.separator4+"<<<<< Server "+Components.LTSActuator.toString()+" was down for "+ MAX_HEARBEATS +" Heartbeats");
						}
					}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
					try {
						Thread.sleep(extendHeartbeatLTSSensor);
						String heartbeatTestWAN = null; 
						HeartbeatClient stubHeartbeat = new HeartbeatClient(Components.LTSSensor.toString());
						heartbeatTestWAN = stubHeartbeat.getStubLTSSensor().verifyHeartbeat(Components.TestWAN.toString());
						if(heartbeatTestWAN.equals(Components.TestWAN.toString()))
							flagHeartbeatLTSSensor = true;
						if(countHeartbeatsLTSSensor>0 && flagHeartbeatLTSSensor == true){
							countHeartbeatsLTSSensor = 0;
							extendHeartbeatLTSSensor = ras.interfaces.Utilities.extendHeartbeat;
						}
					} catch (InterruptedException | RemoteException | NotBoundException e) {
						// TODO Auto-generated catch block
						countHeartbeatsLTSSensor++;
						System.out.println(ras.interfaces.Utilities.separator1+">>>>> HEARTBEATS WITHOUT RESPONSE: "+countHeartbeatsLTSSensor+" FROM "+ Components.LTSSensor.toString() +"<<<<<");
						flagHeartbeatLTSSensor = false;
						if(countHeartbeatsLTSSensor == MAX_HEARBEATS){
							extendHeartbeatLTSSensor += ras.interfaces.Utilities.extendHeartbeat/2;
							//countHeartbeatsLTSSensor = 0;
							System.out.println(ras.interfaces.Utilities.separator4+"<<<<< Server "+Components.LTSSensor.toString()+" was down for "+ MAX_HEARBEATS +" Heartbeats");
						}
					}
			}
		}).start();
	}
	
	private void monitorHeartbeat(String component) throws Exception{
		boolean flagHeartbeat = true;
		int countHeartbeats = 0;
		int extendHeartbeat = 0;
		if(component.equals(Components.CTS.toString())){
			flagHeartbeat = flagHeartbeatCTS;
			countHeartbeats = countHeartbeatsCTS;
			extendHeartbeat = extendHeartbeatCTS;
		} else {
			if(component.equals(Components.LTSSensor.toString())){
				flagHeartbeat = flagHeartbeatLTSSensor;
				countHeartbeats = countHeartbeatsLTSSensor;
				extendHeartbeat = extendHeartbeatLTSSensor;
			} else {
				if(component.equals(Components.LTSActuator.toString())){
					flagHeartbeat = flagHeartbeatLTSActuator;
					countHeartbeats = countHeartbeatsLTSActuator;
					extendHeartbeat = extendHeartbeatLTSActuator;
				}
			}
		}
		
		if(!flagHeartbeat){
			while(!flagHeartbeat){//System.out.print("here1");
				Thread.sleep(extendHeartbeat/2);
				if(flagHeartbeat){
					System.out.println(ras.interfaces.Utilities.separator1+"<<<<< SERVER "+component+" IS UP AGAIN >>>>>");
					Thread.sleep(extendHeartbeat/2);
					break;
				}
				if(countHeartbeats >= MAX_HEARBEATS){
					extendHeartbeat += extendHeartbeat/2;
					int temp = countHeartbeats;
					countHeartbeats = 0;
					
					if(component.equals(Components.CTS.toString())){
						countHeartbeatsCTS = countHeartbeats;
						extendHeartbeatCTS = extendHeartbeat;
					} else {
						if(component.equals(Components.LTSSensor.toString())){
							countHeartbeatsLTSSensor = countHeartbeats ;
							extendHeartbeatLTSSensor = extendHeartbeat;
						} else {
							if(component.equals(Components.LTSActuator.toString())){
								countHeartbeatsLTSActuator = countHeartbeats;
								extendHeartbeatLTSActuator = extendHeartbeat;
							}
						}
					}
					throw new Exception(ras.interfaces.Utilities.separator1+"!!!!! Server "+component+" was down for "+ temp +" Heartbeats");
				}
			}
			//System.out.println("here2");
			System.out.println(ras.interfaces.Utilities.separator1+"<<<<< SYSTEM WILL KEEP WORKING >>>>>");
		}
	}
	
	static class TestSignalTimeout{
		private TestCtrlSignal testSignal;
		
		public TestSignalTimeout(TestCtrlSignal testSignal){
			setTestSignal(testSignal);
		}

		public TestCtrlSignal getTestSignal() {
			return testSignal;
		}

		private void setTestSignal(TestCtrlSignal testSignal) {
			this.testSignal = testSignal;
		}
		
	}
}