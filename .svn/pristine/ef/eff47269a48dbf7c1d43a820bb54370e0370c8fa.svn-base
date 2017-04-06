package ras.conexion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/*
import ras.data.ST_rasSchemes;
import ras.interfaces.DigitalSignal;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.TypeActions;
*/

public class SensorActuatorClient {
	public boolean connectSensorActuatorClient(String host, Object objSignal, int port, String component, String typeAction) throws Exception{
		DataOutputStream out;
		DataInputStream in;
		Socket clientSocket = null;
		ByteArrayInputStream bais;
		ObjectInputStream ois;
		boolean replyActuatorSensor;
		try {
			clientSocket = new Socket(InetAddress.getByName(host), port);
			out = new DataOutputStream(clientSocket.getOutputStream());
			in = new DataInputStream(clientSocket.getInputStream());
			
			//SEND REQUEST TO SERVER
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(objSignal);
			oos.flush();
			byte[] message = baos.toByteArray();
			out.write(message);
			System.out.println(ras.interfaces.Utilities.separator1+"Signal "+typeAction+" was sent to "+component);
			
			bais = new ByteArrayInputStream(message);
			in.read(message);
			ois = new ObjectInputStream(bais);
			replyActuatorSensor = (boolean)ois.readObject();
			System.out.println(ras.interfaces.Utilities.separator1+"Signal was received by "+component+(replyActuatorSensor ? " and processed" : " but not processed"));
		} catch (Exception e) {
			System.out.println(ras.interfaces.Utilities.separator3+component+" err: "+e.getMessage());
			throw new Exception(ras.interfaces.Utilities.separator3+component+" err: "+e.getMessage());
		} finally {
			if(clientSocket != null)
				try {
					clientSocket.close();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(ras.interfaces.Utilities.separator3+component+" err: "+e.getMessage());
					throw new Exception(ras.interfaces.Utilities.separator3+component+" err: "+e.getMessage()); 
				}
		}
		return replyActuatorSensor;
	}
	
	/*
	public static void main(String[] args){
		SensorActuatorClient test = new SensorActuatorClient();
		ST_classRAS classras = new ST_classRAS();
		ST_rasSchemes rasschemes = new ST_rasSchemes();
		DigitalSignal tester = new DigitalSignal(classras, null, null, rasschemes, null);
		DigitalSignal testSignal = new DigitalSignal(tester, "33", false, TypeActions.DisableEnableComponent.toString());
		try {
			test.connectSensorActuatorClient("gridstat16", testSignal, new ConfigurationMachine().PORTActuator,
					Components.ACTUATOR.toString(),TypeActions.DisableEnableComponent.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}
