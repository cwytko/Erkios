package ras.conexion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.util.Random;

import ras.clientLogic.LTSClient;
import ras.clientLogic.SwitchClient;
import ras.data.ST_remedialActions;
import ras.interfaces.AnalogSignal;
import ras.interfaces.DigitalSignal;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.DisableEnableComponents;
import ras.interfaces.Utilities.TypeActions;
import ras.security.EncryptDecrypt;

public class SensorActuatorServer {
	
	private Boolean disableEnableActuator;
	private Boolean disableEnableSensor; 
	
	public Boolean getDisableEnableActuator() {
		return disableEnableActuator;
	}

	private void setDisableEnableActuator(Boolean disableEnableActuator) {
		this.disableEnableActuator = disableEnableActuator;
	}

	public Boolean getDisableEnableSensor() {
		return disableEnableSensor;
	}

	private void setDisableEnableSensor(Boolean disableEnableSensor) {
		this.disableEnableSensor = disableEnableSensor;
	}

	public static void main(String[] args) throws Exception{
		int serverPort = 0;
		ServerSocket listenSocket = null;
		if(args.length != 2)
			throw new Exception("Wrong number of parameters for component SENSOR/ACTUATOR");
		if(args[0].equals("-o")){
			if(args[1].equals(Components.Actuator.toString()))
				serverPort = new ConfigurationMachine().PORTActuator;
			else
				if(args[1].equals(Components.Sensor.toString()))
					serverPort = new ConfigurationMachine().PORTSensor;
		}
		try {
			SensorActuatorServer sas = new SensorActuatorServer();
			sas.setDisableEnableActuator(true);
			sas.setDisableEnableSensor(true);
			listenSocket = new ServerSocket(serverPort);
			System.out.println(args[1]+" is running on port "+serverPort);
			
			while(true){
				Socket clientSocket = listenSocket.accept();
				Connection con = new Connection(clientSocket, args[1],sas.getDisableEnableActuator(),sas.getDisableEnableSensor());
				con.start();
				try {
					con.join();
				} catch (Exception e) {
					// TODO: handle exception
				}
				//System.out.println(sas.getDisableEnableActuator());
				if(args[1].equals(Components.Actuator.toString()))
					sas.setDisableEnableActuator(con.getDisableEnableActuator());
				if(args[1].equals(Components.Sensor.toString()))
					sas.setDisableEnableSensor(con.getDisableEnableSensor());
				
				//System.out.println(con.getDisableEnableActuator());
			}	
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Listen: "+e.getMessage());
		}
		
	}
}


class Connection extends Thread{
	private DataInputStream in;
	private DataOutputStream out;
	private Socket clientSocket;
	private String componentRAS;
	private SwitchClient stubSwitchClient = null;
	private LTSClient stubLTSClient = null;
	private Boolean disableEnableActuator;
	private Boolean disableEnableSensor;
	private byte[] message = new byte[1000];
	private ByteArrayInputStream bais = new ByteArrayInputStream(message);
	private ObjectInputStream ois;
	private ByteArrayOutputStream baos;
	private ObjectOutputStream oos;
	
	public Boolean getDisableEnableActuator() {
		return disableEnableActuator;
	}

	private void setDisableEnableActuator(Boolean disableEnableActuator) {
		this.disableEnableActuator = disableEnableActuator;
	}

	public Boolean getDisableEnableSensor() {
		return disableEnableSensor;
	}

	private void setDisableEnableSensor(Boolean disableEnableSensor) {
		this.disableEnableSensor = disableEnableSensor;
	}

	public Connection(){
		//setDisableEnableActuator(true);  //BY DEFAULT THE ACTUATOR MUST BE ENABLED
		//setDisableEnableSensor(true); //BY DEFAULT THE SENSOR MUST BE ENABLED
	}
	
	public Connection(Socket auxClientSocket, String component, boolean disableEnableActuator, boolean disableEnableSensor){
		try {
			clientSocket = auxClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			setDisableEnableActuator(disableEnableActuator);
			setDisableEnableSensor(disableEnableSensor);
			componentRAS = component;
			//this.start();
			//this.join();
		} catch (IOException e) {
			// TODO: handle exception
			
			System.out.println(ras.interfaces.Utilities.separator3+component+"err Connection: "+e.getMessage());
		}
	}
	
	public void run(){
		//byte[] message = null;
		AnalogSignal analogSig = null;
		DigitalSignal digitalSig = null;
		boolean replyLogLink = false;
		String idRASTest = null;
		String idSession = null;
		try {
			
			in.read(message);
			ois = new ObjectInputStream(bais);
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			
			if(componentRAS.equals(Components.Sensor.toString())){
				analogSig = (AnalogSignal)ois.readObject();
				idSession = new EncryptDecrypt().decryptMsg(analogSig.getIdSession());
				idRASTest =	idSession.split("#")[2];
				
				if((analogSig.getTypeAction().equals(TypeActions.EnableComponent.toString())) || 
						analogSig.getTypeAction().equals(TypeActions.DisableComponent.toString())){
					disableEnableSensor(analogSig, idSession, idRASTest, replyLogLink);
				} else {
					if((analogSig.getTypeAction().equals(TypeActions.SendMessageTest.toString())))
						SendMessageSensor(analogSig, idSession, idRASTest, replyLogLink);
				}
			}
			else{
				if(componentRAS.equals(Components.Actuator.toString())){
					digitalSig = (DigitalSignal)ois.readObject();
					idSession = new EncryptDecrypt().decryptMsg(digitalSig.getIdSession());
					idRASTest = idSession.split("#")[2];
					//String action = digitalSig.getDisableEnableComponent() ? TypeActions.EnableComponent.toString() : TypeActions.DisableComponent.toString();
					if((digitalSig.getTypeAction().equals(TypeActions.EnableComponent.toString()) 
							|| digitalSig.getTypeAction().equals(TypeActions.DisableComponent.toString()))){
						
						disableEnableActuator(digitalSig, idRASTest, replyLogLink);
					} else {
						if((digitalSig.getTypeAction().equals(TypeActions.SendMessageTest.toString())) ||
								digitalSig.getTypeAction().equals(TypeActions.SendMessageCA.toString()))
							
							SendMessageActuator(digitalSig, idSession, idRASTest, replyLogLink);
					}
				}
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	private void disableEnableSensor(AnalogSignal analogSig, String idSession, String idRASTest, Boolean replyLogLink) 
			throws NumberFormatException, Exception{
		replyLogLink = true;
		oos.writeObject(replyLogLink);
		oos.flush();
		message = baos.toByteArray();
		out.write(message);
		
		System.out.println(ras.interfaces.Utilities.separator2+"***** CTS created a message -"
					+" idRASTest: "+idRASTest
					+" RAS Classification: "+idSession.split("#")[0]
					+" RAS Scheme: "+idSession.split("#")[1]
					+". SENSOR will be "
					+(analogSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString()));
		//CONTROLS OF DISABLE/ENABLE SENSOR
		boolean flagSensor = getDisableEnableSensor();
		if(analogSig.getDisableEnableComponent().compareTo( getDisableEnableSensor()) != 0){
			System.out.println(ras.interfaces.Utilities.separator1+"Attempt to change status of SENSOR from "
					+(getDisableEnableSensor() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
					+" to "
					+(analogSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString()));
			
			setDisableEnableSensor(new Random().nextBoolean());
			setDisableEnableSensor(analogSig.getDisableEnableComponent() ? true : false);
//setDisableEnableSensor(analogSig.getDisableEnableComponent() ? false : true);
		} else {
			System.out.println(ras.interfaces.Utilities.separator1+" SENSOR has been already"
					+(getDisableEnableSensor() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString()));
		}
		System.out.println(ras.interfaces.Utilities.separator1+""+Components.Sensor.toString()+" was "
				+(analogSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
				+(getDisableEnableSensor() != flagSensor ? " successfully" : " unsuccessfully"));
		
		DigitalSignal digitalSig = new DigitalSignal(analogSig.getIdSession(),getDisableEnableSensor(), flagSensor, analogSig.getTypeAction());
		stubSwitchClient = new SwitchClient(Components.SwitchSensor2.toString());
		stubSwitchClient.getStubSwitch().resultDisableEnableSensor(digitalSig);
	}
	
	private void disableEnableActuator(DigitalSignal digitalSig, String idRASTest, Boolean replyLogLink) throws IOException, NotBoundException{
		replyLogLink = true;
		oos.writeObject(replyLogLink);
		oos.flush();
		message = baos.toByteArray();
		out.write(message);
		
		System.out.println(ras.interfaces.Utilities.separator2+"***** CTS created a message -"
				+" idRASTest: "+idRASTest
				+" RAS Classification: "+digitalSig.getClassRAS().getIdClassification()
				+" RAS Scheme: "+digitalSig.getSchemeRAS().getIdRAS()
				+". ACTUATOR will be "
				+(digitalSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString()));
		
		//CONTROLS OF DISABLE/ENABLE ACTUATOR
		boolean flagActuator = getDisableEnableActuator();
		//System.out.println(digitalSig.getDisableEnableComponent()+ "!=" +getDisableEnableActuator());
		if(digitalSig.getDisableEnableComponent().compareTo(getDisableEnableActuator()) != 0){
			System.out.println(ras.interfaces.Utilities.separator1+"Attempt to change status of ACTUATOR from "
					+(getDisableEnableActuator() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
					+" to "
					+(digitalSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString()));
			
			setDisableEnableActuator(new Random().nextBoolean());
			setDisableEnableActuator(digitalSig.getDisableEnableComponent() ? true : false);
			//setDisableEnableActuator(digitalSig.getDisableEnableComponent() ? false : true);
			//System.out.println("/////"+getDisableEnableActuator());
			
		} else {
			System.out.println(ras.interfaces.Utilities.separator1+"ACTUATOR has been already "
					+(getDisableEnableActuator() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString()));
		}
		
		System.out.println(ras.interfaces.Utilities.separator1+""+Components.Actuator.toString()+" was "
				+(digitalSig.getDisableEnableComponent() ? DisableEnableComponents.ENABLED.toString() : DisableEnableComponents.DISABLED.toString())
				+(getDisableEnableActuator().compareTo(flagActuator) != 0 ? " successfully" : " unsuccessfully"));
		
		digitalSig = new DigitalSignal(digitalSig, idRASTest, getDisableEnableActuator(), flagActuator, digitalSig.getTypeAction());
		stubLTSClient = new LTSClient(Components.LTSActuator.toString());
		stubLTSClient.getStubLTSActuator().resultDisableEnableActuator(digitalSig);
	}
	
	private void SendMessageSensor(AnalogSignal analogSig, String idSession, String idRASTest, Boolean replyLogLink) 
			throws NumberFormatException, Exception{
		//replyLogLink = true;
		
		System.out.println(ras.interfaces.Utilities.separator1+"+++++ CTS sent a command to trip RAS Scheme -"
					+" idRASTest: "+idRASTest
					+" RAS Classification: "+idSession.split("#")[0]
					+" RAS Scheme: "+idSession.split("#")[1]
					+" Type Action:"+analogSig.getTypeAction()
					+". SENSOR will send trip command to OP WAN");

		//CONTROLS TO SEND MESSAGE TO OP WAN
		Boolean flagTestSensor = getDisableEnableSensor();
		if(analogSig.getDisableEnableComponent().compareTo(getDisableEnableSensor()) == 0){
			System.out.println(ras.interfaces.Utilities.separator1+"----- SENSOR will previously verify the command before sending to RAS Scheme through OP WAN");
			
			//flagTestSensor = new Random().nextBoolean();
			flagTestSensor = (!getDisableEnableSensor() && !analogSig.getDisableEnableComponent()) ? true : false;
			//System.out.println(flagTestSensor+"---"+"---"+analogSig.getDisableEnableComponent());
			System.out.println(ras.interfaces.Utilities.separator3+""+Components.Sensor.toString()+" processed the message to be sent to RAS Scheme"
					+(flagTestSensor ? " successfully" : " unsuccessfully"));
			
			replyLogLink = flagTestSensor;
			oos.writeObject(replyLogLink);
			oos.flush();
			message = baos.toByteArray();
			out.write(message);
		} else {
			System.out.println(ras.interfaces.Utilities.separator3+"SENSOR has not processed successfuly the command since SENSOR state was not determined");
		}
		
		DigitalSignal digitalSig = new DigitalSignal(analogSig.getIdSession(), analogSig.getDisableEnableComponent(), getDisableEnableSensor(), analogSig.getTypeAction());
		digitalSig = new DigitalSignal(digitalSig, flagTestSensor, null);
		stubSwitchClient = new SwitchClient(Components.SwitchSensor2.toString());
		stubSwitchClient.getStubSwitch().resultSendMessageSensor(digitalSig);
	}
	
	private void SendMessageActuator(DigitalSignal digSignal, String idSession, String idRASTest, Boolean replyLogLink) 
			throws NumberFormatException, Exception{
		//replyLogLink = true;
		
		System.out.println(ras.interfaces.Utilities.separator1+"+++++ CTS sent a command to trip RAS Scheme -"
					+" idRASTest: "+idRASTest
					+" RAS Classification: "+idSession.split("#")[0]
					+" RAS Scheme: "+idSession.split("#")[1]
					+". ACTUATOR received control decisions to be executed");

		//CONTROLS TO SEND MESSAGE TO OP WAN
		Boolean flagTestActuator = getDisableEnableSensor();
		if(digSignal.getDisableEnableComponent().compareTo(getDisableEnableSensor()) == 0){
			System.out.println(ras.interfaces.Utilities.separator1+"----- ACTUATOR will previously verify the command before applying Remedial Actions");
			
			//flagTestSensor = new Random().nextBoolean();
			flagTestActuator = (!getDisableEnableSensor() && !digSignal.getDisableEnableComponent() && 
					digSignal.getResultTestSensor()) ? true : false;
			//System.out.println(flagTestSensor+"---"+"---"+analogSig.getDisableEnableComponent());
			System.out.println(ras.interfaces.Utilities.separator3+""+Components.Actuator.toString()+" processed the message sent to RAS Scheme"
					+(flagTestActuator ? " successfully" : " unsuccessfully"));
			
			replyLogLink = flagTestActuator;
			oos.writeObject(replyLogLink);
			oos.flush();
			message = baos.toByteArray();
			out.write(message);
		} else {
			System.out.println(ras.interfaces.Utilities.separator3+"ACTUATOR has not processed successfuly the command since ACTUATOR state was not determined");
		}
		
		if(digSignal.getRemedialActions() != null && !digSignal.getRemedialActions().isEmpty()){
			System.out.print(ras.interfaces.Utilities.separator3+"LIST OF REMEDIAL ACTIONS");
			for(ST_remedialActions remedial: digSignal.getRemedialActions())
				System.out.println(ras.interfaces.Utilities.separator3+"- "+remedial.getRemedialAction());
		} else {
			System.out.println(ras.interfaces.Utilities.separator3+"LIST OF REMEDIAL ACTIONS WAS NOT RECEIVED");
		}
		
		DigitalSignal digitalAuxSig = new DigitalSignal(digSignal,idSession.split("#")[2],digSignal.getDisableEnableComponent(), digSignal.getTypeAction());

		digitalAuxSig = new DigitalSignal(digitalAuxSig, digSignal.getResultTestSensor(), flagTestActuator);
		stubLTSClient = new LTSClient(Components.LTSActuator.toString());
		stubLTSClient.getStubLTSActuator().receiveResultsRAS(digitalAuxSig);
		
	}
}
