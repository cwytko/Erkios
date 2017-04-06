package ras.serverLogic;

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

import ras.clientLogic.DBRASSchemeClient;
import ras.clientLogic.HeartbeatClient;
import ras.clientLogic.OPWANClient;
import ras.clientLogic.SwitchClient;
import ras.data.ST_RASTest;
import ras.data.ST_RASTestDisableEnableComponent;
import ras.interfaces.AnalogSignal;
import ras.interfaces.DigitalSignal;
import ras.interfaces.LTSSensorInterface;
import ras.interfaces.TestCtrlSignal;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.DisableEnableComponents;
import ras.interfaces.Utilities.TypeActions;
import ras.security.EncryptDecrypt;

public class LTSSensorImpl extends UnicastRemoteObject implements LTSSensorInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PORT = LTSSensorInterface.PORT;
	private Boolean disableEnableSensor;
	private String idSessionDisableEnable;
	private String idSessionSendMessage;
	private Boolean resultTestSensor;
	private Registry registry;
	private static LTSSensorImpl ltsSensor;
	
	private static final int MAX_HEARBEATS = ras.interfaces.Utilities.MAX_HEARTBEATS;
	private static int countHeartbeats = 0;
	private boolean flagHeartbeat  = false;
	private int extendHeartbeat = ras.interfaces.Utilities.extendHeartbeat;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private Future<Object> future = null;
	
	public Boolean getDisableEnableSensor() {
		return disableEnableSensor;
	}

	private void setDisableEnableSensor(Boolean disableEnableSensor) {
		this.disableEnableSensor = disableEnableSensor;
	}

	public String getIdSessionDisableEnable() {
		return idSessionDisableEnable;
	}

	private void setIdSessionDisableEnable(String idSessionDisableEnable) {
		this.idSessionDisableEnable = idSessionDisableEnable;
	}

	public String getIdSessionSendMessage() {
		return idSessionSendMessage;
	}

	private void setIdSessionSendMessage(String idSessionSendMessage) {
		this.idSessionSendMessage = idSessionSendMessage;
	}

	public Boolean getResultTestSensor() {
		return resultTestSensor;
	}

	private void setResultTestSensor(Boolean resultTestSensor) {
		this.resultTestSensor = resultTestSensor;
	}

	protected LTSSensorImpl() throws RemoteException{
		registry = LocateRegistry.createRegistry(PORT);
		registry.rebind("LTSSensorInterface", this);
	}
	
	public static void main(String[] args) throws RemoteException{
		ltsSensor = new LTSSensorImpl();
		try {
			ltsSensor.message();
			ltsSensor.checkHeartBeat();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"LTSSensorInterface err: "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private  void message(){
		setResultTestSensor(false);
		System.out.println("LTSSensorInterface bound in registry on port "+PORT);
	}

	@Override
	public TestCtrlSignal disableEnableSensor(TestCtrlSignal testSignal)
			throws RemoteException, InterruptedException, NotBoundException, Exception {
		// TODO Auto-generated method stub
		AnalogSignal analogSig = testSignal.getAnaSignal();
		String message = testSignal.getMessage();
		ST_RASTest rasTest = testSignal.getRasTest();
		
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		SwitchClient stubSwitch = new SwitchClient(Components.SwitchSensor1.toString());
		//Boolean resultSensor = null;
		Date dateTimeSensor = null;
		ST_RASTestDisableEnableComponent decAux = null;
		
		try {
			System.out.println(message);
			try {
				int ordinalDisableEnable = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.TestWAN.ordinal()+1,
						Components.LTSSensor.ordinal()+1, ordinalDisableEnable, true, true, "LTS Sensor", ""));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw e;
			}
			
			//********** THIS PROCESS MUST SEND A SIGNAL TO SWITCH SENSOR1 THAT SENDS A COMMAND TO SENSOR TO DISABLE/ENABLE IT
			//To disable an actuator  disableEnableComponent must be FALSE, otherwise it will enable actuator
			String idSession = new EncryptDecrypt().encryptMsg(rasTest.getIdClassification().getIdClassification()+"#"
					+rasTest.getIdRAS().getIdRAS()+"#"+Integer.toString(rasTest.getIdRASTest()));
			TestCtrlSignal testAuxCtrlSignal = new TestCtrlSignal(null, analogSig, rasTest, message, 
					(analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString()),
					testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
			stubSwitch.getStubSwitch().disableEnableSensor(testAuxCtrlSignal);
			
			while(!idSession.equals(getIdSessionDisableEnable()))
				Thread.sleep(ras.interfaces.Utilities.SLEEP);

			//resultSensor = getDisableEnableSensor();
			if(getDisableEnableSensor().booleanValue() == analogSig.getDisableEnableComponent().booleanValue()){ 
				if(getIdSessionDisableEnable().equals(analogSig.getIdSession())){
					int ordinalDisableEnable = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
					System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor2.ordinal()+1,
							Components.LTSSensor.ordinal()+1, ordinalDisableEnable, true, true, "LTS Sensor", ""));
					
					dateTimeSensor = Calendar.getInstance().getTime();
					decAux = new ST_RASTestDisableEnableComponent(getDisableEnableSensor(), dateTimeSensor, null, null);
					setDisableEnableSensor(!getDisableEnableSensor().booleanValue());
					
					System.out.println(ras.interfaces.Utilities.separator1+""+(analogSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
								+" of SENSOR was "+(analogSig.getDisableEnableComponent().equals(getDisableEnableSensor()) ? "successful" : "unsuccessful"));
				} else {
					int ordinalDisableEnable = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
					System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor2.ordinal()+1,
							Components.LTSSensor.ordinal()+1, ordinalDisableEnable, true, true, "LTS Sensor", "wrong Id Session2"));
					System.out.println(ras.interfaces.Utilities.separator3+"LTSSensorImplDisableEnableSensor err: Id Session does not belong to idRASTest:"+rasTest.getIdRASTest());
					throw new RemoteException("LTSSensorImplDisableEnableSensor err: Id Session does not belong to idRASTest:"+rasTest.getIdRASTest());
				}
			} else{
				int ordinalDisableEnable = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
				String action =  (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString());
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor2.ordinal()+1,
						Components.LTSSensor.ordinal()+1, ordinalDisableEnable, true, true, "LTS Sensor", "wrong "+action));
				System.out.println(ras.interfaces.Utilities.separator3+"LTSSensorImplDisableEnableSensor err: different values: "+action);
				throw new RemoteException("LTSSensorImplDisableEnableSensor err: different values: "+action);
			}
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			System.out.println(ras.interfaces.Utilities.separator2+""+e.getMessage());
			throw e;
		}
		TestCtrlSignal testAuxCtrl = new TestCtrlSignal(analogSig.getDisableEnableComponent(), null, analogSig, rasTest, 
				null,"", decAux,testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
		try {
			monitorHeartbeat();
		} catch (Exception e) {
			// TODO: handle exception
			int ordinalDisableEnable = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
			System.out.println(stubDBRAS.getStubDBRAS().logLink(testSignal.getRasTest(), Components.LTSSensor.ordinal()+1,
					Components.TestWAN.ordinal()+1, ordinalDisableEnable, false, false, "LTSSensor-TestWAN", e.getMessage().substring(0, 99)));
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return testAuxCtrl;
		//return false;
	}

	@Override
	public TestCtrlSignal sendMessageSensor(TestCtrlSignal testSignal)
			throws RemoteException, Exception {
		// TODO Auto-generated method stub
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		//SwitchClient stubSwitch = new SwitchClient(Components.SwitchSensor1.toString());
		OPWANClient stubOPWAN = null;
		DigitalSignal digAuxSignal = null;
		TestCtrlSignal testAuxSignal = new TestCtrlSignal(null,testSignal.getAnaSignal(),testSignal.getRasTest(),
				testSignal.getMessage(),testSignal.getTypeAction(),testSignal.getRasTestSTCode(),testSignal.getListRASTestSTCodeContingency());
		
		STControl stControl = new STControl(testSignal.getRasTestSTCode(), testSignal.getListRASTestSTCodeContingency());		
		
		System.out.println(testSignal.getMessage());
		
		System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(testSignal.getRasTest(), Components.TestWAN.ordinal()+1,
				Components.LTSSensor.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, true, true, "LTS SENSOR", 
				"LTS Sensor receives trip command"));
		
		/*SELF-TESTING CONTROL SWITCHSENSOR1*/
		try {
			stControl.controlSTCode(Components.LTSSensor.ordinal()+1, Components.SwitchSensor1.ordinal()+1, Components.SwitchSensor1.toString());
			System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL SWITCHSENSOR1 PASSED");
			stControl.controlSTCodeContingency(Components.LTSSensor.ordinal()+1, Components.SwitchSensor1.ordinal()+1, Components.SwitchSensor1.toString());
			System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL SWITCHSENSOR1 PASSED");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"LTSSensorImpl sendMessageSensor err:"+e.getMessage());;
			throw new RemoteException("LTSSensorImpl sendMessageSensor err:"+e.getMessage());
		}
		
		boolean resultSensor = false;
		try {
			final TestSignalTimeout testSignalTimeOut = new TestSignalTimeout(testAuxSignal);
			future = executor.submit(new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					// TODO Auto-generated method stub
					SwitchClient stubSwitch = new SwitchClient(Components.SwitchSensor1.toString());
					testSignalTimeOut.setResultTest(stubSwitch.getStubSwitch().sendMessageSensor(testSignalTimeOut.getTestSignal()));
					return null;
				}
			});
			
			try {
				future.get(ras.interfaces.Utilities.TIMEOUT, TimeUnit.SECONDS);
			} catch (InterruptedException | TimeoutException | ExecutionException e) {
				// TODO: handle exception
				System.out.println(ras.interfaces.Utilities.separator3+"sendMessageSensor err:"+e.getMessage());
				throw new Exception("sendMessageSensor err:"+e.getMessage());
			}
			
			resultSensor = testSignalTimeOut.isResultTest();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"LTSSensorImpl err:\n"+e.getMessage());
			throw new RemoteException("LTSSensorImpl err:\n"+e.getMessage());
		}
		
		
		
		//boolean resultSensor = stubSwitch.getStubSwitch().sendMessageSensor(testAuxSignal);
		
		String idSession = new EncryptDecrypt().encryptMsg(Integer.toString(testSignal.getDigSignal().getClassRAS().getIdClassification())+"#"
				+testSignal.getDigSignal().getSchemeRAS().getIdRAS()+"#"+Integer.toString(testSignal.getRasTest().getIdRASTest()));
		
		while(!idSession.equals(getIdSessionSendMessage()))
			Thread.sleep(ras.interfaces.Utilities.SLEEP);
		//System.out.println(getResultTestSensor()+"---"+resultSensor);
		
		/*SELF-TESTING CONTROL SWITCHSENSOR2*/
		stControl.controlSTCode(Components.SwitchSensor2.ordinal()+1, Components.LTSSensor.ordinal()+1, Components.SwitchSensor2.toString());
		System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL SWITCHSENSOR2 PASSED");
		stControl.controlSTCodeContingency(Components.SwitchSensor2.ordinal()+1, Components.LTSSensor.ordinal()+1, Components.SwitchSensor2.toString());
		System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL SWITCHSENSOR2 PASSED");
		
		if(getResultTestSensor() && resultSensor && getIdSessionSendMessage().equals(testSignal.getAnaSignal().getIdSession())){
			//THIS IS DONE TO PREPEAR FOR NEXT TEST
			testAuxSignal = new TestCtrlSignal(testSignal,null,getResultTestSensor(),testSignal.getRasTestSTCode(), testSignal.getListRASTestSTCodeContingency());
			stubOPWAN = new OPWANClient();
			
			System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(testSignal.getRasTest(), Components.SwitchSensor2.ordinal()+1, 
					Components.LTSSensor.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, true, true, "LTS SENSOR", "Sensor received trip command"));
			
			digAuxSignal = new DigitalSignal(testSignal.getDigSignal(), getResultTestSensor(), null);
			
			/*SELF-TESTING CONTROL OPWAN*/
			stControl.controlSTCode(Components.LTSSensor.ordinal()+1, Components.OpWAN.ordinal()+1, Components.OpWAN.toString());
			System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL OPWAN PASSED");
			stControl.controlSTCodeContingency(Components.LTSSensor.ordinal()+1, Components.OpWAN.ordinal()+1, Components.OpWAN.toString());
			System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL OPWAN PASSED");
			stubOPWAN.getStubOPWAN().sendMessageActuator(digAuxSignal);
			
			setResultTestSensor(!getResultTestSensor());
			setResultTestSensor(!getDisableEnableSensor());
		}
		
		
		return testAuxSignal;
	}

	@Override
	public boolean receivesDigitalSignalTest(DigitalSignal signalTest)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resultDisableEnableSensor(DigitalSignal digitalSig)
			throws RemoteException {
		// TODO Auto-generated method stub
		setIdSessionDisableEnable(digitalSig.getIdSession());
		setDisableEnableSensor(digitalSig.getDisableEnableComponent());
	}

	@Override
	public void resultTestSensor(DigitalSignal digitalSig)
			throws RemoteException {
		// TODO Auto-generated method stub
		setResultTestSensor(digitalSig.getResultTestSensor());
		setIdSessionSendMessage(digitalSig.getIdSession());
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
						heartbeatTestWAN = stubHeartbeat.getStubTestWAN().verifyHeartbeat(Components.LTSSensor.toString());
						if(heartbeatTestWAN.equals(Components.LTSSensor.toString()))
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
		private boolean resultTest;
		
		public TestSignalTimeout(TestCtrlSignal testSignal){
			setTestSignal(testSignal);
		}

		public TestCtrlSignal getTestSignal() {
			return testSignal;
		}

		private void setTestSignal(TestCtrlSignal testSignal) {
			this.testSignal = testSignal;
		}

		public boolean isResultTest() {
			return resultTest;
		}

		private void setResultTest(boolean resultTest) {
			this.resultTest = resultTest;
		}
		
	}
}
