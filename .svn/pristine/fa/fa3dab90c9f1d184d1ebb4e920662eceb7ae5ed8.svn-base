package ras.serverLogic;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Random;

import ras.clientLogic.DBRASSchemeClient;
import ras.clientLogic.LTSClient;
import ras.conexion.ConfigurationMachine;
import ras.conexion.SensorActuatorClient;
import ras.data.ST_RASTest;
import ras.data.ST_RASTestSTCode;
import ras.data.ST_RASTestSTCodeContingency;
import ras.data.ST_parameters;
import ras.data.ST_remedialActions;
import ras.interfaces.AnalogSignal;
import ras.interfaces.DigitalSignal;
import ras.interfaces.SwitchRASInterface;
import ras.interfaces.TestCtrlSignal;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.ComponentsTest;
import ras.interfaces.Utilities.DisableEnableComponents;
import ras.interfaces.Utilities.ParametersTest;
import ras.interfaces.Utilities.TypeActions;
import ras.interfaces.Utilities.TypeCodeAction;
import ras.security.EncryptDecrypt;

public class SwitchImpl extends UnicastRemoteObject implements SwitchRASInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int PORT;
	private String interfaceRAS;
	private static final int PORTSwitchActuator = SwitchRASInterface.PORTSwitchActuator;
	private static final int PORTSwitchSensor1 = SwitchRASInterface.PORTSwitchSensor1;
	private static final int PORTSwitchSensor2 = SwitchRASInterface.PORTSwitchSensor2;
	private static SwitchImpl switchRAS;
	private Registry registry;
	private ConfigurationMachine config = new ConfigurationMachine();

	public int getPORT() {
		return PORT;
	}

	private void setPORT(int pORT) {
		PORT = pORT;
	}

	public String getInterfaceRAS() {
		return interfaceRAS;
	}

	private void setInterfaceRAS(String interfaceRAS) {
		this.interfaceRAS = interfaceRAS;
	}

	protected SwitchImpl(String[] args) throws RemoteException, Exception {
		super();
		// TODO Auto-generated constructor stub
		if(args.length != 2)
			throw new Exception("Wrong number of parameters for SWITCH component");
		if(args[0].equals("-o")){
			if(args[1].equals(Components.SwitchActuator.toString())){
				setPORT(PORTSwitchActuator);
				setInterfaceRAS(Components.SwitchActuator.toString());
			}else{
				if(args[1].equals(Components.SwitchSensor1.toString())){
					setPORT(PORTSwitchSensor1);
					setInterfaceRAS(Components.SwitchSensor1.toString());
				}else{
					if(args[1].equals(Components.SwitchSensor2.toString())){
						setPORT(PORTSwitchSensor2);
						setInterfaceRAS(Components.SwitchSensor2.toString());
					}
				}
			}
			setInterfaceRAS(getInterfaceRAS()+"Interface");
		}
		registry = LocateRegistry.createRegistry(getPORT());
		registry.bind(getInterfaceRAS(), this);
	}
	
	public static void main(String[] args) throws RemoteException, Exception{
		switchRAS = new SwitchImpl(args);
		switchRAS.message(switchRAS.getPORT(), switchRAS.getInterfaceRAS());
	}
	
	private void message(int port, String interfaceRAS){
		System.out.println(interfaceRAS+" bound in registry on port "+port);
	}

	@Override
	public boolean disableEnableSensor(TestCtrlSignal testSignal) throws RemoteException, Exception {
		// TODO Auto-generated method stub
		ST_RASTest rasTest = testSignal.getRasTest();
		String message = testSignal.getMessage();
		AnalogSignal analogSig = testSignal.getAnaSignal();
		
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		SensorActuatorClient sac = new SensorActuatorClient();
		STCodes stCode = new STCodes();
		
		System.out.println(message);
		int ordinalDisableEnable = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
		try {
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSSensor.ordinal()+1,
					Components.SwitchSensor1.ordinal()+1, ordinalDisableEnable, true, true, "Switch Sensor1", ""));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"SwitchImpl err:"+e.getMessage());
			throw new Exception("SwitchImpl err:"+e.getMessage());
		}
		try {
			
			String valueDecoded = "";
			Boolean disableEnableDecoded = null;
			try {
				valueDecoded = (String) stCode.codingTechnique(testSignal.getRasTestSTCode().getCodeWordDisableEnableSensor(), 
						testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), TypeCodeAction.Decoding.toString());
				disableEnableDecoded = Boolean.valueOf(valueDecoded);
				if(disableEnableDecoded.compareTo(analogSig.getDisableEnableComponent()) != 0)
					throw new Exception("DISABLE/ENABLE: values are different after decoding value");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor1.ordinal()+1,
						Components.Sensor.ordinal()+1, ordinalDisableEnable, false, true, "SwitchSensor1", "SwitchImpl err: Error decoding value"));
				
				int typeAction = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal(): TypeActions.DisableComponent.ordinal())+1;
				ST_RASTestSTCode testSTCode = (ST_RASTestSTCode)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(
					"from sra_RASTestSTCode where rts_idRASTest = :id and rts_idTypeAction ="+typeAction, rasTest.getIdRASTest()).get(0);
				
				String nameParameter = analogSig.getDisableEnableComponent() ? ParametersTest.EnableComponentSensor.toString() : ParametersTest.DisableComponentSensor.toString();
				ST_parameters parameter = (ST_parameters)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(
						"from sra_parameters where spa_nameParameter :=", nameParameter).get(0);
				
				ST_RASTestSTCode stErrorCode = new ST_RASTestSTCode(testSTCode, testSignal.getRasTestSTCode().getCodeWordDisableEnableSensor(), parameter.getIdParameter());
				stubDBRAS.getStubDBRAS().updateGenericQuery(stErrorCode, stErrorCode.getIdRASTestSTCode());
				System.out.println(ras.interfaces.Utilities.separator3+"SwitchImpl err: "+e.getMessage());
				throw new Exception("SwitchImpl err: "+e.getMessage());
				
			}
			String action = (analogSig.getDisableEnableComponent() ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString());
			boolean resultLink = sac.connectSensorActuatorClient(config.hostSensor, analogSig, config.PORTSensor, Components.Sensor.toString(), action);
			if(resultLink)
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor1.ordinal()+1,
					Components.Sensor.ordinal()+1, ordinalDisableEnable, true, true, "SENSOR", ""));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor1.ordinal()+1,
					Components.Sensor.ordinal()+1, ordinalDisableEnable, false, true, "SENSOR", "SwitchImpl err: connection to Sensor"));
			System.out.println(ras.interfaces.Utilities.separator3+"SwitchImplSensor err:"+e.getMessage());
			throw new RemoteException("SwitchImplSensor err:"+e.getMessage());
		}
		return false;
	}

	@Override
	public boolean disableEnableActuator(TestCtrlSignal testSignal) throws RemoteException, Exception {
		// TODO Auto-generated method stub
		ST_RASTest rasTest = testSignal.getRasTest();
		String message = testSignal.getMessage();
		DigitalSignal digSignal = testSignal.getDigSignal();
		
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		SensorActuatorClient sac = new SensorActuatorClient();
		STCodes stCode = new STCodes();
		
		System.out.println(message);
		int ordinalDisableEnable = (digSignal.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
		try {
			
			System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSActuator.ordinal()+1, 
					Components.SwitchActuator.ordinal()+1, ordinalDisableEnable, true, true, "Switch Actuator",""));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(ras.interfaces.Utilities.separator3+"SwitchImplActuator err:"+e1.getMessage());
			throw new RemoteException("SwitchImplActuator err:"+e1.getMessage());
		}
		try {
			String valueDecoded = "";
			Boolean disableEnableDecoded = null;
			try {
				valueDecoded = (String) stCode.codingTechnique(testSignal.getRasTestSTCode().getCodeWordDisableEnableActuator(), 
						testSignal.getRasTestSTCode().getIdSTCode().getShortCodeName(), TypeCodeAction.Decoding.toString());
				disableEnableDecoded = Boolean.valueOf(valueDecoded);
				if(disableEnableDecoded.compareTo(digSignal.getDisableEnableComponent()) != 0)
					throw new Exception("DISABLE/ENABLE: values are different after decoding value");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchActuator.ordinal()+1,
						Components.Actuator.ordinal()+1, ordinalDisableEnable, false, true, "SwitchActuator", "SwitchImplActuator err: Error decoding value"));
				
				int typeAction = (digSignal.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal(): TypeActions.DisableComponent.ordinal())+1;
				ST_RASTestSTCode testSTCode = (ST_RASTestSTCode)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(
					"from sra_RASTestSTCode where rts_idRASTest = :id and rts_idTypeAction ="+typeAction, rasTest.getIdRASTest()).get(0);
				
				String nameParameter = digSignal.getDisableEnableComponent() ? ParametersTest.EnableComponentActuator.toString() : ParametersTest.DisableComponentActuator.toString();
				ST_parameters parameter = (ST_parameters)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(
						"from sra_parameters where spa_nameParameter :=", nameParameter).get(0);
				
				ST_RASTestSTCode stErrorCode = new ST_RASTestSTCode(testSTCode, testSignal.getRasTestSTCode().getCodeWordDisableEnableSensor(), parameter.getIdParameter());
				stubDBRAS.getStubDBRAS().updateGenericQuery(stErrorCode, stErrorCode.getIdRASTestSTCode());
				System.out.println(ras.interfaces.Utilities.separator3+"SwitchImplActuator err: "+e.getMessage());
				throw new RemoteException("SwitchImplActuator err: "+e.getMessage());		
			}
			
			String action =(digSignal.getDisableEnableComponent() ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString());
			boolean resultLink = sac.connectSensorActuatorClient(config.hostActuator, digSignal, config.PORTActuator,Components.Actuator.toString(),action);
			//System.out.println("----"+resultLink);
			if(resultLink)
				System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchActuator.ordinal()+1, 
						Components.Actuator.ordinal()+1, ordinalDisableEnable, true, true, "ACTUATOR",""));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"SwitchImplActuator err:"+e.getMessage());
			throw new RemoteException("SwitchImplActuator err:"+e.getMessage());
		}
		return false;
	}

	@Override
	public boolean resultDisableEnableSensor(DigitalSignal digitalSig)
			throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		String idSession = new EncryptDecrypt().decryptMsg(digitalSig.getIdSession());
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		LTSClient stubLTS = new LTSClient(Components.LTSSensor.toString());
		
		ST_RASTest rasTest = (ST_RASTest)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTest where tra_idRASTest = :id", idSession.split("#")[2]).get(0);
		boolean flagSensor = digitalSig.getDisableEnableComponent() != digitalSig.getRealStatusDisableEnableComponent() ? true : false;
		
		System.out.println(ras.interfaces.Utilities.separator2+"***** CTS created a message -"
				+" idRASTest: "+idSession.split("#")[2]
				+" RAS Classification: "+idSession.split("#")[0]
				+" RAS Scheme: "+idSession.split("#")[1]
				+". SENSOR was "
				+(digitalSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
				+(flagSensor ? "successfully" : "unsuccessfully")
				);
		
		int ordinalDisableEnable = (digitalSig.getDisableEnableComponent() ? TypeActions.EnableComponent.ordinal() : TypeActions.DisableComponent.ordinal()) + 1;
		System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.Sensor.ordinal()+1, 
				Components.Sensor.ordinal()+1,ordinalDisableEnable, true, digitalSig.getDisableEnableComponent(), "Sensor Status",
				(flagSensor ? "successful" : "unsuccessful")));
		
		System.out.println(stubDBRAS.getStubDBRAS().logLink(rasTest, Components.Sensor.ordinal()+1, 
				Components.SwitchSensor2.ordinal()+1, ordinalDisableEnable, true, true, "SENSOR", ""));
		System.out.println(ras.interfaces.Utilities.separator1+""+Components.SwitchSensor2.toString()+" sends result of "
				+(digitalSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
				+" to "+Components.LTSSensor.toString());
		stubLTS.getStubLTSSensor().resultDisableEnableSensor(digitalSig);
		return false;
	}

	@Override
	public boolean sendMessageSensor(TestCtrlSignal testSignal)
			throws RemoteException, Exception {
		// TODO Auto-generated method stub
		ST_RASTest rasTest = testSignal.getRasTest();
		String message = testSignal.getMessage();
		AnalogSignal analogSig = testSignal.getAnaSignal();
		STControl stControl = new STControl(testSignal.getRasTestSTCode(), testSignal.getListRASTestSTCodeContingency());
		
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		SensorActuatorClient sac = new SensorActuatorClient();
		
		System.out.println(message);
		
		/*SELF-TESTING CONTROL SENSOR*/
		try{
		
			stControl.controlSTCode(Components.SwitchSensor1.ordinal()+1, Components.Sensor.ordinal()+1, Components.Sensor.toString());
			System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL SENSOR PASSED");
			stControl.controlSTCodeContingency(Components.SwitchSensor1.ordinal()+1, Components.Sensor.ordinal()+1, Components.Sensor.toString());
			System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL SENSOR PASSED");
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(ras.interfaces.Utilities.separator3+"SwitchImpl sendMessageSensor err:"+e.getMessage());;
			throw new RemoteException("SwitchImpl sendMessageSensor err:"+e.getMessage());
		}
		
		System.out.println(ras.interfaces.Utilities.separator5+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSSensor.ordinal()+1, 
				Components.SwitchSensor1.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, true, true, "Switch Sensor1", ""));
		boolean resultLink = sac.connectSensorActuatorClient(config.hostSensor, analogSig, config.PORTSensor, Components.Sensor.toString(), TypeActions.SendMessageTest.toString());
		if(!resultLink)
			System.out.println(ras.interfaces.Utilities.separator5+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor1.ordinal()+1, 
					Components.Sensor.ordinal()+1, TypeActions.SendMessageTest.ordinal()+1, true, true, "SENSOR", "SENSOR is still enabled"));
		return resultLink;
	}

	@Override
	public boolean resultSendMessageSensor(DigitalSignal digitalSig)
			throws RemoteException, Exception {
		// TODO Auto-generated method stub
		String idSession = new EncryptDecrypt().decryptMsg(digitalSig.getIdSession());
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		LTSClient stubLTS = new LTSClient(Components.LTSSensor.toString());
		
		ST_RASTest rasTest = (ST_RASTest)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTest where tra_idRASTest = :id", 
				idSession.split("#")[2]).get(0);
		Boolean flagTestSensor = digitalSig.getResultTestSensor();
		System.out.println(ras.interfaces.Utilities.separator1+"+++++ CTS sent a message to SENSOR with data"
				+" idRASTest: "+idSession.split("#")[2]
				+" RAS Classification: "+idSession.split("#")[0]
				+" RAS Scheme: "+idSession.split("#")[1]
				+"SENSOR performed the Test "
				+(flagTestSensor ? "successfully" : "unsuccessfully")
				);
		
		System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchSensor1.ordinal()+1, Components.Sensor.ordinal()+1,
				TypeActions.SendMessageTest.ordinal()+1, true, true, "Switch Sensor 1", ""));
		
		System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.Sensor.ordinal()+1, Components.Sensor.ordinal()+1, 
				TypeActions.SendMessageTest.ordinal()+1, true, digitalSig.getDisableEnableComponent(), "Sensor Status", 
				"Command to RAS Scheme was processed"+(flagTestSensor ? "successfully" : "unsuccessfully")));
		
		System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.Sensor.ordinal()+1, Components.SwitchSensor2.ordinal()+1,
				TypeActions.SendMessageTest.ordinal()+1, true, true, "Switch Sensor2", ""));
		
		System.out.println(ras.interfaces.Utilities.separator3+""+Components.SwitchSensor2.toString()+" sends result of "+(flagTestSensor ? "success":"unsuccess")
				+" to "+Components.LTSSensor.toString());
		stubLTS.getStubLTSSensor().resultTestSensor(digitalSig);
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean sendMessageActuator(DigitalSignal digitalSig)
			throws RemoteException, Exception {
		// TODO Auto-generated method stub
		String idSession = new EncryptDecrypt().decryptMsg(digitalSig.getIdSession());
		DBRASSchemeClient stubDBRAS = new DBRASSchemeClient();
		SensorActuatorClient sac = new SensorActuatorClient();
		LTSClient stubLTS = new LTSClient(Components.LTSActuator.toString());
		ST_RASTestSTCode testCode = null;
		List<ST_RASTestSTCodeContingency> listTestCodeContingency = null;
		
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
			listTestCodeContingency = injector.injectListErrorSTCodeContingency();
		}
		
		System.out.println(ras.interfaces.Utilities.separator1+"+++++ CTS sent a message to ACTUATOR with data"
				+" idRASTest: "+idSession.split("#")[2]
				+" RAS Classification: "+idSession.split("#")[0]
				+" RAS Scheme: "+idSession.split("#")[1]);
		
		System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.LTSActuator.ordinal()+1, Components.RAS.ordinal()+1,
				digitalSig.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ? TypeActions.SendMessageTest.ordinal()+1
				: TypeActions.SendMessageCA.ordinal()+1, true, true, "RAS", digitalSig.getTypeAction().equals(TypeActions.SendMessageTest.toString()) 
				? TypeActions.SendMessageTest.toString() : TypeActions.SendMessageCA.toString()));
		
		boolean resultLink = true;
		/*As stated on the proposal:
		  In Substation Actuator, the LTS causes the control data from both the local RL1 and the CL1 to be reported to the LTS 
		  rather than passed onto the actuator (which of course would trip the SIPS and cause problems)
		 */
		String idRASTest = new EncryptDecrypt().decryptMsg(digitalSig.getIdSession());
		System.out.println(ras.interfaces.Utilities.separator1+"+++++ RAS Scheme sent Remedial Actions -"
					+" idRASTest: "+idRASTest.split("#")[2]
					+" RAS Classification: "+idSession.split("#")[0]
					+" RAS Scheme: "+idSession.split("#")[1]);
		
		if(!digitalSig.getDisableEnableComponent()){
			System.out.println(ras.interfaces.Utilities.separator1+"+++++ Switch Actuator reports Remedial Actions to LTS Actuator instead of Actuator");
			Boolean flagTestActuator = new Random().nextBoolean();
			flagTestActuator = !digitalSig.getDisableEnableComponent() && digitalSig.getResultTestSensor() ? true : false;
			
			if(digitalSig.getResultTestSensor() && flagTestActuator){
				System.out.println(ras.interfaces.Utilities.separator3+""+Components.SwitchActuator.toString()+" processed the message sent to RAS Scheme"
						+(flagTestActuator ? " successfully" : " unsuccessfully"));
					
				if(digitalSig.getRemedialActions() != null && !digitalSig.getRemedialActions().isEmpty()){
					
					/*SELF-TESTING CONTROL RAS*/
					STControl stControl = new STControl(testCode, listTestCodeContingency);
					stControl.controlSTCode(Components.RAS.ordinal()+1, Components.SwitchActuator.ordinal()+1, Components.RAS.toString());
					System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL RAS PASSED");
					stControl.controlSTCodeContingency(Components.RAS.ordinal()+1, Components.SwitchActuator.ordinal()+1, Components.RAS.toString());
					System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL RAS PASSED");
					
					System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.RAS.ordinal()+1, Components.SwitchActuator.ordinal()+1,
							digitalSig.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ? TypeActions.SendMessageTest.ordinal()+1
							: TypeActions.SendMessageCA.ordinal()+1, true, true, "Switch Actuator", "REMEDIAL ACTIONS WHERE RECEIVED SUCCESSFULLY"));
					
					System.out.println(ras.interfaces.Utilities.separator3+"LIST OF REMEDIAL ACTIONS");
					for(ST_remedialActions remedial : digitalSig.getRemedialActions())
						System.out.println(ras.interfaces.Utilities.separator3+"- "+remedial.getRemedialAction());
				} else {
					System.out.println(ras.interfaces.Utilities.separator3+"LIST OF REMEDIAL ACTIONS WAS NOT RECEIVED");
					System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.RAS.ordinal()+1, Components.SwitchActuator.ordinal()+1,
							digitalSig.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ? TypeActions.SendMessageTest.ordinal()+1
							: TypeActions.SendMessageCA.ordinal()+1, false, true, "Switch Actuator", "REMEDIAL ACTIONS WHERE NOT RECEIVED"));
				}
				
				DigitalSignal digitalAuxSig = new DigitalSignal(digitalSig, idRASTest.split("#")[2], digitalSig.getDisableEnableComponent(), digitalSig.getTypeAction());
				digitalAuxSig = new DigitalSignal(digitalAuxSig, digitalAuxSig.getResultTestSensor(), flagTestActuator);
				
				/*SELF-TESTING CONTROL ACTUATOR*/
				STControl stControl = new STControl(testCode, listTestCodeContingency);
				stControl.controlSTCode(Components.SwitchActuator.ordinal()+1, Components.Actuator.ordinal()+1, Components.Actuator.toString());
				System.out.println(ras.interfaces.Utilities.separator1+"##### PARAMETERS SELF-TESTING CONTROL ACTUATOR PASSED");
				stControl.controlSTCodeContingency(Components.SwitchActuator.ordinal()+1, Components.Actuator.ordinal()+1, Components.Actuator.toString());
				System.out.println(ras.interfaces.Utilities.separator1+"##### CONTINGENCIES SELF-TESTING CONTROL ACTUATOR PASSED");
				
				try {
					stubLTS.getStubLTSActuator().receiveResultsRAS(digitalAuxSig);
				} catch (Exception e) {
					// TODO: handle exception
					throw new RemoteException(e.getMessage());
				}
				
			} else {
				resultLink = sac.connectSensorActuatorClient(config.hostActuator, digitalSig, config.PORTActuator, Components.Actuator.toString(), digitalSig.getTypeAction());
				String comment = "";
				if(!resultLink)
					comment = "ACTUATOR is still enabled";
				System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.SwitchActuator.ordinal()+1,
						Components.Actuator.ordinal()+1, digitalSig.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ? TypeActions.SendMessageTest.ordinal()+1
								: TypeActions.SendMessageCA.ordinal()+1, true, digitalSig.getDisableEnableComponent(), "ACTUATOR", comment));
			}
		} else {
			System.out.println(ras.interfaces.Utilities.separator6+stubDBRAS.getStubDBRAS().logLink(rasTest, Components.RAS.ordinal()+1, Components.SwitchActuator.ordinal()+1,
					digitalSig.getTypeAction().equals(TypeActions.SendMessageTest.toString()) ? TypeActions.SendMessageTest.ordinal()+1
					: TypeActions.SendMessageCA.ordinal()+1, false, true, "Switch Actuator", digitalSig.getTypeAction().equals(TypeActions.SendMessageTest.toString()) 
					? TypeActions.SendMessageTest.toString() : TypeActions.SendMessageCA.toString()));
		}
		return resultLink;
	}
}