package ras.serverLogic;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import ras.clientLogic.TestWANClient;
import ras.data.ST_RASTest;
import ras.data.ST_RASTestContingencies;
import ras.data.ST_RASTestRemedialActions;
import ras.data.ST_RASTestSTCode;
import ras.data.ST_RASTestSTCodeContingency;
import ras.data.ST_RASTestTripCommand;
import ras.data.ST_contingencies;
import ras.data.ST_RASTestDisableEnableComponent;
import ras.data.ST_remedialActions;
import ras.data.ST_tripCommand;
import ras.interfaces.AnalogSignal;
import ras.interfaces.CTSInterface;
import ras.interfaces.DigitalSignal;
import ras.interfaces.TestCtrlSignal;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.ComponentsTest;
import ras.interfaces.Utilities.ParametersTest;
import ras.interfaces.Utilities.SchemeRAS;
import ras.interfaces.Utilities.TypeActions;
import ras.interfaces.Utilities.TypeCodeAction;
import ras.security.EncryptDecrypt;


public class CTSImpl extends UnicastRemoteObject implements CTSInterface { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PORT = CTSInterface.PORT;
	
	private static final int MAX_HEARBEATS = ras.interfaces.Utilities.MAX_HEARTBEATS;
	private static int countHeartbeats = 0;
	private boolean flagHeartbeat  = false;
	private int extendHeartbeat = ras.interfaces.Utilities.extendHeartbeat;
	
	private Registry registry;
	private static CTSImpl cts;
	private TestCtrlSignal testSignal;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Future<Object> future = null;
	
	private TestCtrlSignal getTestSignal() {
		return testSignal;
	}

	private void setTestSignal(TestCtrlSignal testSignal) {
		this.testSignal = testSignal;
	}

	protected CTSImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
		try{
			registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("CTSInterface", this);
		} catch (RemoteException e){
			System.out.println(ras.interfaces.Utilities.separator3+"CTSImpl err:" + e.getMessage());
			throw new RemoteException("CTSImpl err:" + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws RemoteException{
		cts = new CTSImpl();
		try{
			
			cts.message();
			cts.checkHeartBeat();
		} catch(Exception e){
			System.out.println(ras.interfaces.Utilities.separator3+"CTSInterface err: "+ e.getMessage());
		}
	}
	
	private void message(){
		System.out.println("CTSInterface bound in registry on port "+PORT);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TestCtrlSignal createMessage(TestCtrlSignal testSignal) throws Exception {
		// TODO Auto-generated method stub
		Integer idTransaction = 0;
		Calendar cal = Calendar.getInstance();
		//Integer idAuxTransaction = 0;
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		List<ST_RASTest> rasTestFailed = new ArrayList<ST_RASTest>();
		
		rasTestFailed = (List<ST_RASTest>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTest where tra_resultTest is null", null);
		if(!rasTestFailed.isEmpty()){
			boolean flagUpdate = false;
			ST_RASTest rasTestUpdate = null;
			for(ST_RASTest rasTestAux:rasTestFailed){
				//System.out.println("HERE"+rasTestAux.getIdRASTest());
				rasTestUpdate = new ST_RASTest(rasTestAux.getIdRASTest(), rasTestAux.getIdRAS(),rasTestAux.getIdClassification(),
						rasTestAux.getDateTimeBeginTest(),false,cal.getTime(),"RAS Test has not been completed");
				flagUpdate = stubDBRAS.getStubDBRAS().updateGenericQuery(rasTestUpdate,rasTestUpdate.getIdRASTest());
				if(flagUpdate)
					System.out.println(ras.interfaces.Utilities.separator1+"RAS Test "+rasTestUpdate.getIdRAS().getNameRAS()
							+" Message with Id("+rasTestUpdate.getIdRASTest()+") has failed on "
							+rasTestUpdate.getDateTimeEndTest().toString());
			}
		}
		
		ST_RASTest rasTest = new ST_RASTest(testSignal.getDigSignal().getSchemeRAS(), 
				testSignal.getDigSignal().getClassRAS(), cal.getTime());
		
		List<ST_RASTestSTCodeContingency> listRASTestSTCodeContingencies = new ArrayList<ST_RASTestSTCodeContingency>();
		List<ST_RASTestContingencies> listRASTestContingencies = new ArrayList<ST_RASTestContingencies>();
		ST_RASTestSTCodeContingency testCodeContingency = null;
		ST_RASTestContingencies testContingencies = null;
		for(ST_contingencies contingency:testSignal.getDigSignal().getContingenciesList()){
			testContingencies = new ST_RASTestContingencies(contingency, rasTest, contingency.getContingency());
			testCodeContingency = new ST_RASTestSTCodeContingency(
					testSignal.getRasTestSTCode().getIdComponent(), 
					rasTest, 
					testContingencies,
					testSignal.getRasTestSTCode().getIdSTCode(), 
					//testSignal.getRasTestSTCode().getIdTypeAction());
					TypeActions.SendMessageTest.ordinal()+1);
			listRASTestContingencies.add(testContingencies);
			listRASTestSTCodeContingencies.add(testCodeContingency);
		}
		idTransaction = stubDBRAS.getStubDBRAS().insertGenericQuery(rasTest, rasTestTripCommand, 
				listRASTestContingencies);
		if(idTransaction > 0){
			System.out.println(ras.interfaces.Utilities.separator2+"***** Message created on :"+rasTest.getDateTimeBeginTest().toString()
					+" idRASTest: "+idTransaction
					+" RAS Classification: "+rasTest.getIdClassification().getIdClassification()
					+" RAS Scheme: "+rasTest.getIdRAS().getIdRAS());
		}else{
			System.out.println(ras.interfaces.Utilities.separator2+"***** Message was not created on :"+rasTest.getDateTimeBeginTest().toString()
					+" idRASTest: "+idTransaction
					+" RAS Classification: "+rasTest.getIdClassification().getIdClassification()
					+" RAS Scheme: "+rasTest.getIdRAS().getIdRAS());
			System.out.println(ras.interfaces.Utilities.separator3+"CTSImpl err: An error occurred when message was being created");
			throw new Exception("CTSImpl err: An error occurred when message was being created");
		}
		rasTest = new ST_RASTest(idTransaction, testSignal.getDigSignal().getSchemeRAS(), testSignal.getDigSignal().getClassRAS(), 
				rasTest.getDateTimeBeginTest(), null, null, "");

		ST_RASTestSTCode stCode = new ST_RASTestSTCode(testSignal.getRasTestSTCode().getIdComponent(), 
				rasTest, testSignal.getRasTestSTCode().getIdSTCode(), testSignal.getRasTestSTCode().getIdTypeAction());
		STCodes codeTest = new STCodes();
		String codeWordCreateMessage = (String) codeTest.codingTechnique(idTransaction, testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(),
				TypeCodeAction.Encoding.toString());
		stCode = new ST_RASTestSTCode(stCode, ParametersTest.None.ordinal()+1, codeWordCreateMessage);
		stubDBRAS.getStubDBRAS().insertGenericQuery(stCode);
		
		return new TestCtrlSignal(null, null, null, rasTest, stCode, listRASTestSTCodeContingencies);
	}
	

	@Override
	public TestCtrlSignal diseableEnableActuatorSensor(
			TestCtrlSignal testSignal) throws RemoteException, NotBoundException, InterruptedException, Exception {
		// TODO Auto-generated method stub
		Boolean disableEnableComponent;
		ST_RASTest rasTest = testSignal.getRasTest();
		
		Integer idTransaction = null;
		DBRASSchemeClient stubDBRAS = null;
		ST_RASTest rasTestAux = null;
		DigitalSignal digSignal = null;
		AnalogSignal analogSig = null;
		ST_RASTestSTCode testCode = null;
		TestCtrlSignal testAuxCtrlSignal = null;
		String typeAction = null;
		ST_RASTestDisableEnableComponent disableComponent = new ST_RASTestDisableEnableComponent();
		STCodes stCodes = new STCodes();
		String valueBinarySTCode = null; 
		
		disableEnableComponent =  testSignal.getDisableEnableComponents();
		stubDBRAS = new DBRASSchemeClient();
		
		rasTestAux = new ST_RASTest(rasTest.getIdRASTest(), rasTest.getIdRAS(), rasTest.getIdClassification(), rasTest.getDateTimeBeginTest());
		digSignal = new DigitalSignal(new DigitalSignal(rasTestAux.getIdClassification(), null, null, rasTestAux.getIdRAS(), null), 
				Integer.toString(rasTestAux.getIdRASTest()), disableEnableComponent, (disableEnableComponent ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString()));
		analogSig = new AnalogSignal(null,null, digSignal.getIdSession(), disableEnableComponent, 
				(disableEnableComponent ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString()));
		
		
		valueBinarySTCode = (String) stCodes.codingTechnique(disableEnableComponent.toString(), testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(),
				TypeCodeAction.Encoding.toString());
		testCode = new ST_RASTestSTCode(testSignal.getRasTestSTCode(), valueBinarySTCode,valueBinarySTCode);
		testCode = new ST_RASTestSTCode(testCode, "", ParametersTest.None.ordinal()+1);
		testAuxCtrlSignal = new TestCtrlSignal(digSignal, analogSig, rasTestAux,"",
				(disableEnableComponent ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString()),
				testCode,null);
		stubDBRAS.getStubDBRAS().insertGenericQuery(testCode);
		
		int ordinalDisableEnable = (disableEnableComponent ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
		try {
			monitorHeartbeat();
			
			final TestSignalTimeout testSignalTimeout = new TestSignalTimeout(testAuxCtrlSignal);
			future = executor.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					// TODO Auto-generated method stub
					TestWANClient stubTestWAN = new TestWANClient();
					testSignalTimeout.setTestSignal(stubTestWAN.getStubTestWAN().diseableEnableActuatorSensor(testSignalTimeout.getTestSignal()));
					return null;
				}
			});
			
			try {
				future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
			} catch (InterruptedException | TimeoutException | ExecutionException e) {
				// TODO: handle exception
				System.out.println(ras.interfaces.Utilities.separator3+"disableEnableActuatorSensor err: TIMEOUT");
				throw new Exception("disableEnableActuatorSensor err: \n "+e.getMessage());
			}
			
			testAuxCtrlSignal = testSignalTimeout.getTestSignal();
			//testAuxCtrlSignal = stubTestWAN.getStubTestWAN().diseableEnableActuatorSensor(testAuxCtrlSignal);
			disableComponent = testAuxCtrlSignal.getRasTestDisableEnable();
		} catch (Exception e) {
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTestAux, Components.TestWAN.ordinal()+1,
					Components.CTS.ordinal()+1, ordinalDisableEnable, false, false, "CTS-TestWAN", e.getMessage().substring(0, 99)));
			System.out.println(ras.interfaces.Utilities.separator3+"CTS err:"+e.getMessage());
			throw new RemoteException("CTS err:"+e.getMessage());
		}
		
		if(disableEnableComponent)
			while(disableComponent.getResultDisableEnableComponent().compareTo(true) != 0){
				try {
					monitorHeartbeat();
					
					final TestSignalTimeout testSignalTimeout = new TestSignalTimeout(testAuxCtrlSignal);
					future = executor.submit(new Callable<Object>() {

						@Override
						public Object call() throws Exception {
							// TODO Auto-generated method stub
							TestWANClient stubTestWAN = new TestWANClient();
							testSignalTimeout.setTestSignal(stubTestWAN.getStubTestWAN().diseableEnableActuatorSensor(testSignalTimeout.getTestSignal()));
							return null;
						}
					});
					
					try {
						future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
					} catch (InterruptedException | TimeoutException | ExecutionException e) {
						// TODO: handle exception
						System.out.println(ras.interfaces.Utilities.separator3+"Reverse disableEnableActuatorSensor err: TIMEOUT ");
						throw new Exception("Reverse disableEnableActuatorSensor err: \n "+e.getMessage());
					}
					disableComponent = testSignalTimeout.getTestSignal().getRasTestDisableEnable();
					//disableComponent = stubTestWAN.getStubTestWAN().diseableEnableActuatorSensor(testAuxCtrlSignal).getRasTestDisableEnable();	
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTestAux, Components.TestWAN.ordinal()+1,
							Components.CTS.ordinal()+1, ordinalDisableEnable, false, false, "CTS-TestWAN", e.getMessage().substring(0, 99)));
					System.out.println(ras.interfaces.Utilities.separator3+"CTSImpl err:"+e.getMessage());
					throw new Exception("CTSImpl err:"+e.getMessage());
				}
				
			}
		
		
		System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTestAux, Components.TestWAN.ordinal()+1, Components.CTS.ordinal()+1, 
				ordinalDisableEnable, true, true, "CTS", ""));
		
		//Store data of disable or enable of Sensor and Actuator
		idTransaction = stubDBRAS.getStubDBRAS().insertGenericQuery(disableComponent);
		if(idTransaction == null || idTransaction == 0)
			System.out.println(ras.interfaces.Utilities.separator2+"!!!!! A problem occurred when "+disableComponent.getClass().getName()+" was been stored");

		typeAction = testSignal.getDisableEnableComponents() ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString();
		digSignal = new DigitalSignal(new DigitalSignal(rasTestAux.getIdClassification(), testSignal.getDigSignal().getContingenciesList(), 
				 rasTestAux.getIdRAS(), testSignal.getDigSignal().getRemedialActions()), 
				Integer.toString(rasTestAux.getIdRASTest()), disableEnableComponent, (disableEnableComponent ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString()));
		
		//digSignal = new DigitalSignal(digSignal, String.valueOf(testSignal.getRasTest().getIdRASTest()), testSignal.getDisableEnableComponents(), disableComponent. .getRealStatusDisableEnableComponent(), typeAction);
		return new TestCtrlSignal(testSignal.getDisableEnableComponents(), digSignal, analogSig, rasTest, typeAction, typeAction, 
				disableComponent,testCode,testSignal.getListRASTestSTCodeContingency());
				//disableEnableComponent, null, null, null, null,null, disableComponent);	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TestCtrlSignal sendMessage(TestCtrlSignal testSignal) throws RemoteException, NotBoundException, Exception{

		String idRAS;
		String idSession;
		String newline = System.getProperty("line.separator");
		TestCtrlSignal testAuxSignal = testSignal;
		AnalogSignal anaSig = null;
		DigitalSignal digSignal = null;
		float[] parameters;
		idRAS = testSignal.getDigSignal().getSchemeRAS().getIdRAS();
		int sizeParameters = (idRAS.equals(SchemeRAS.ios_I.toString())
				|| idRAS.equals(SchemeRAS.ios_III.toString()) || idRAS.equals(SchemeRAS.ios_II.toString()) 
				|| idRAS.equals(SchemeRAS.siv_E.toString()) || idRAS.equals(SchemeRAS.siv_S.toString())) 
				? 1 : idRAS.equals(SchemeRAS.siv_R.toString()) ? 4 : 0;
		parameters = new float[sizeParameters];
		STCodes stCode = new STCodes();
		ST_RASTestSTCode testCode = null;
		List<ST_RASTestSTCodeContingency> listTestCodeContingency = null;
		HashMap<String, String> parametersTest = new HashMap<String, String>();
		//HashMap<ST_RASTestContingencies, String> contingencyTest = new HashMap<ST_RASTestContingencies, String>();

		//TestWANClient stubTestWAN = null;
		DBRASSchemeClient stubDBRAS = null;
		stubDBRAS = new DBRASSchemeClient();

		if(testSignal.getRasTestDisableEnable().getResultDisableEnableComponent()){
			System.out.println(ras.interfaces.Utilities.separator1+"CTS will send command to LTS Sensor to start Test");
			String message =ras.interfaces.Utilities.separator1+"+++++ CTS sends command to trip "+testSignal.getDigSignal().getSchemeRAS().getNameRAS()+" RAS Scheme ("
					+testSignal.getDigSignal().getClassRAS().getNameClass()+" Category) with idRASTest"+testSignal.getRasTest().getIdRASTest()+":";
			
			String codeParameter = "";
			if(idRAS.equals(SchemeRAS.ios_I.toString()) ||
					idRAS.equals(SchemeRAS.ios_III.toString())){
				message += newline+ras.interfaces.Utilities.separator3+"VARIABLE - Power:"+testSignal.getDigSignal().getPower();
				parameters[0] = testSignal.getDigSignal().getPower();
				codeParameter = (String)stCode.codingTechnique(parameters[0], testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), 
						TypeCodeAction.Encoding.toString());
				parametersTest.put(ParametersTest.Power.toString(), codeParameter);
			}
			else{
				if(idRAS.equals(SchemeRAS.siv_R.toString())){
					message += newline+ras.interfaces.Utilities.separator3+"VARIABLE - Power:"+testSignal.getDigSignal().getPower()+ "(MW) Current: "+testSignal.getDigSignal().getCurrent()
					+"(A) Voltage: "+testSignal.getDigSignal().getVoltage()+"(V) Frequency: "+testSignal.getDigSignal().getFrequency()+"(Hz)";
					parameters[0] = testSignal.getDigSignal().getPower();
					codeParameter = (String)stCode.codingTechnique(parameters[0], testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), 
							TypeCodeAction.Encoding.toString());
					parametersTest.put(ParametersTest.Power.toString(), codeParameter);
					parameters[1] = testSignal.getDigSignal().getCurrent();
					codeParameter = (String)stCode.codingTechnique(parameters[1], testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), 
							TypeCodeAction.Encoding.toString());
					parametersTest.put(ParametersTest.Current.toString(), codeParameter);
					parameters[2] = testSignal.getDigSignal().getVoltage();
					codeParameter = (String)stCode.codingTechnique(parameters[2], testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), 
							TypeCodeAction.Encoding.toString());
					parametersTest.put(ParametersTest.Voltage.toString(), codeParameter);
					parameters[3] = testSignal.getDigSignal().getFrequency();
					codeParameter = (String)stCode.codingTechnique(parameters[3], testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), 
							TypeCodeAction.Encoding.toString());
					parametersTest.put(ParametersTest.Frequency.toString(), codeParameter);
				}
				else{
					if(idRAS.equals(SchemeRAS.ios_II.toString()) || idRAS.equals(SchemeRAS.siv_E.toString())){
						message += newline+ras.interfaces.Utilities.separator3+"VARIABLE - Reactive Power:"+testSignal.getDigSignal().getReactivePower()+"(MVAR)";
						parameters[0] = testSignal.getDigSignal().getReactivePower();
						codeParameter = (String)stCode.codingTechnique(parameters[0], testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), 
								TypeCodeAction.Encoding.toString());
						parametersTest.put(ParametersTest.ReactivePower.toString(), codeParameter);
					}
					else
						if(idRAS.equals(SchemeRAS.siv_S.toString())){
							message += newline+ras.interfaces.Utilities.separator3+"VARIABLE - Phase of Voltage:"+testSignal.getDigSignal().getPhaseVoltage()+"(φ)";
							parameters[0] = testSignal.getDigSignal().getPhaseVoltage();
							codeParameter = (String)stCode.codingTechnique(parameters[0], testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), 
									TypeCodeAction.Encoding.toString());
							parametersTest.put(ParametersTest.PhaseVoltage.toString(), codeParameter);
						}
				}
			}
			
			message += newline+ras.interfaces.Utilities.separator3+"LIST OF CONTINGENCIES";
			listTestCodeContingency = new ArrayList<ST_RASTestSTCodeContingency>();
			if(testSignal.getDigSignal().getContingenciesList().size() != testSignal.getListRASTestSTCodeContingency().size()){
				System.out.println(ras.interfaces.Utilities.separator3+"CTSImplSendMessage err: different size of arrays ST_contingencies and ST_RASTestSTCodeContingency");
				throw new RemoteException("CTSImplSendMessage err: different size of arrays ST_contingencies and ST_RASTestSTCodeContingency");
			}
			
			//@SuppressWarnings("unchecked")
			List<ST_RASTestContingencies> listRASContingencies = (List<ST_RASTestContingencies>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTestContingencies where rtc_idRASTest = :id", testSignal.getRasTest().getIdRASTest());
			
			for(int i = 0; i<testSignal.getListRASTestSTCodeContingency().size();i++){
				ST_contingencies contingency = testSignal.getDigSignal().getContingenciesList().get(i);
				ST_RASTestSTCodeContingency testContingency = testSignal.getListRASTestSTCodeContingency().get(i);
				if(contingency.getIdContingency() == testContingency.getIdContingency().getIdContingency().getIdContingency()){
					codeParameter = (String)stCode.codingTechnique(contingency.getContingency(), testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(),
							TypeCodeAction.Encoding.toString());
					listTestCodeContingency.add(new ST_RASTestSTCodeContingency(testContingency.getIdComponent(), testSignal.getRasTest(), 
							listRASContingencies.get(i), testSignal.getRasTestSTCode().getIdSTCode(), testContingency.getIdTypeAction(),
							null, codeParameter));
					
					message += newline+ras.interfaces.Utilities.separator3+"- "+contingency.getContingency();
				} else {
					System.out.println(ras.interfaces.Utilities.separator3+"CTSImplSendMessage err: id contingency error"+contingency.getIdContingency());
					throw new RemoteException("CTSImplSendMessage err: id contingency error"+contingency.getIdContingency());
				}
			}
			System.out.println(message);
			
			idSession = String.valueOf(testSignal.getRasTest().getIdClassification().getIdClassification())+"#"
					+testSignal.getRasTest().getIdRAS().getIdRAS()+"#"
					+String.valueOf(testSignal.getRasTest().getIdRASTest());
			idSession = new EncryptDecrypt().encryptMsg(idSession);		
			anaSig = new AnalogSignal(parameters,null, idSession, testSignal.getDigSignal().getDisableEnableComponent(), 
					TypeActions.SendMessageTest.toString());
			digSignal = new DigitalSignal(testSignal.getDigSignal(), TypeActions.SendMessageTest.toString());
			
			testCode = new ST_RASTestSTCode(testSignal.getRasTestSTCode(),
					parametersTest.get(ParametersTest.Current.toString()),
					parametersTest.get(ParametersTest.Frequency.toString()),
					parametersTest.get(ParametersTest.PhaseVoltage.toString()),
					parametersTest.get(ParametersTest.Power.toString()),
					parametersTest.get(ParametersTest.ReactivePower.toString()),
					parametersTest.get(ParametersTest.Voltage.toString()),
					ParametersTest.None.ordinal()+1);
			
			stubDBRAS.getStubDBRAS().insertGenericQuery(testCode);
			
			//ST_RASTestSTCodeContingency testCodeContingency = null;
			for(ST_RASTestSTCodeContingency stCodeContingency : listTestCodeContingency){
				stubDBRAS.getStubDBRAS().insertGenericQuery(stCodeContingency);
			}
			
			String queryDB = "from sra_RASTestSTCode where rts_idTypeAction = "+
					(TypeActions.SendMessageTest.ordinal()+1)+" and rts_idRASTest =:id";
			testCode = (ST_RASTestSTCode)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(queryDB, testSignal.getRasTest().getIdRASTest()).get(0);				
			listTestCodeContingency = (List<ST_RASTestSTCodeContingency>)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTestSTCodeContingency "+
					"where rsc_idRASTest =:id", testSignal.getRasTest().getIdRASTest());
			
			/*FAULT INJECTION */
			STFaultInjector injector = new STFaultInjector(testCode, listTestCodeContingency);
			if(new Random().nextBoolean() && !testCode.getIdComponent().getShortNameComponent().equals(ComponentsTest.None.toString())){
				testCode = injector.injectErrorSTCode();
			} else {
				if(new Random().nextBoolean() && !testCode.getIdComponent().getShortNameComponent().equals(ComponentsTest.None.toString()))
					listTestCodeContingency = injector.injectListErrorSTCodeContingency();
			}
			
			testAuxSignal = new TestCtrlSignal(testSignal.getDisableEnableComponents(), digSignal,anaSig,
					testSignal.getRasTest(),message,TypeActions.SendMessageTest.toString(),
					testSignal.getRasTestDisableEnable(),testCode,listTestCodeContingency);
			
			try {
				monitorHeartbeat();
				//stubTestWAN = new TestWANClient();
				
				final TestSignalTimeout testSignalTimeout = new TestSignalTimeout(testAuxSignal);
				future = executor.submit(new Callable<Object>() {

					@Override
					public Object call() throws Exception {
						// TODO Auto-generated method stub
						TestWANClient stubTestWAN = new TestWANClient();
						testSignalTimeout.setTestSignal(stubTestWAN.getStubTestWAN().sendMessage(testSignalTimeout.getTestSignal()));
						return null;
					}
				});

				try {
					future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
				} catch (InterruptedException | TimeoutException | ExecutionException e) {
					// TODO: handle exception
					System.out.println(ras.interfaces.Utilities.separator3+"sendMessageActuatorSensor err: \n "+e.getMessage());
					throw new Exception("sendMessageActuatorSensor err: \n "+e.getMessage());
				}

				testAuxSignal = testSignalTimeout.getTestSignal();		
				//testAuxSignal = stubTestWAN.getStubTestWAN().sendMessage(testAuxSignal);	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(stubDBRAS.getStubDBRAS().logLink(testSignal.getRasTest(),
						Components.TestWAN.ordinal()+1, Components.CTS.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, 
						false, false, "CTS-TestWAN", e.getMessage().substring(0, 99)));
				System.out.println(ras.interfaces.Utilities.separator3+"CTSImpl err:"+e.getMessage());
				throw new RemoteException("CTSImpl err: "+e.getMessage());
			}
			
			
			while(!idSession.equals(getTestSignal().getDigSignal().getIdSession()))
				Thread.sleep(ras.interfaces.Utilities.SLEEP);
			
			System.out.println(stubDBRAS.getStubDBRAS().logLink(testAuxSignal.getRasTest(), Components.TestWAN.ordinal()+1, 
					Components.CTS.ordinal()+1, getTestSignal().getDigSignal().getTypeAction().equals(TypeActions.SendMessageTest.toString()) 
					? TypeActions.SendMessageTest.ordinal()+1 : TypeActions.SendMessageTest.ordinal()+1, true, true, "CTS", 
					getTestSignal().getDigSignal().getResultTestActuator().equals(getTestSignal().getDigSignal().getResultTestSensor())
					? "TEST RAS WAS SUCCESSFUL" : "TEST RAS WAS UNSUCCESSFUL"));
			
			if(getTestSignal().getDigSignal().getRemedialActions() != null){
				System.out.println(ras.interfaces.Utilities.separator3+"LIST OF REMEDIAL ACTIONS");
				for(ST_remedialActions remedial:getTestSignal().getDigSignal().getRemedialActions())
					System.out.println(ras.interfaces.Utilities.separator3+"- "+remedial.getRemedialAction());
			}
			
		} else {
			System.out.println(ras.interfaces.Utilities.separator3+"CTSImpl err (Disable Sensor & Actuators were not disabled): Message sent by CTS to trip "+testSignal.getDigSignal().getSchemeRAS().getNameRAS()
					+" RAS Scheme ("+testSignal.getDigSignal().getClassRAS().getNameClass()+" Category) with idRASTest"+testSignal.getRasTest().getIdRASTest());
			throw new RemoteException("CTSImpl err (Disable Sensor & Actuators were not disabled): Message sent by CTS to trip "+testSignal.getDigSignal().getSchemeRAS().getNameRAS()
					+" RAS Scheme ("+testSignal.getDigSignal().getClassRAS().getNameClass()+" Category) with idRASTest"+testSignal.getRasTest().getIdRASTest());
		}
		Date dateEndTest = Calendar.getInstance().getTime();
		ST_RASTest rasTestAux = new ST_RASTest(testAuxSignal.getRasTest().getIdRASTest(), testSignal.getDigSignal().getSchemeRAS(),
				testSignal.getDigSignal().getClassRAS(), testSignal.getRasTest().getDateTimeBeginTest(),
				getTestSignal().getResultTestActuator() && getTestSignal().getResultTestSensor() ? true : false, dateEndTest, 
				getTestSignal().getMessage());
				
		testAuxSignal = new TestCtrlSignal(getTestSignal().getDisableEnableComponents(), getTestSignal().getResultTestActuator(),
				getTestSignal().getResultTestActuator(), digSignal, anaSig, rasTestAux,
				getTestSignal().getMessage(), getTestSignal().getTypeAction(), testSignal.getRasTestDisableEnable(),
				testCode,listTestCodeContingency);
		
		stubDBRAS.getStubDBRAS().updateGenericQuery(rasTestAux,testAuxSignal.getRasTest().getIdRASTest());
		ST_RASTestRemedialActions remedialRAS = null;
		for(ST_remedialActions remedial : getTestSignal().getDigSignal().getRemedialActions()){
			remedialRAS = new ST_RASTestRemedialActions(testAuxSignal.getRasTest(), remedial, remedial.getRemedialAction());
			stubDBRAS.getStubDBRAS().insertGenericQuery(remedialRAS);
		}

		
		return testAuxSignal;
	}

	@Override
	public void receiveResultTest(TestCtrlSignal resultTest) throws RemoteException {
		// TODO Auto-generated method stub
		setTestSignal(resultTest);
	}

	@Override
	public boolean logTest(TestCtrlSignal testSignal) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String verifyHeartbeat(String component) throws RemoteException, NotBoundException {
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
						heartbeatTestWAN = stubHeartbeat.getStubTestWAN().verifyHeartbeat(Components.CTS.toString());
						if(heartbeatTestWAN.equals(Components.CTS.toString()))
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
