package ras.interfaces;

public interface Utilities {
	public enum Components{CA, //Central Applications
		CTS, //Control Test System
		TestWAN, //Test WAN
		OpWAN, //Operation WAN
		LTSSensor, //Local Test System Sensor
		DAC, //Data Acquisition and Control
		PTCT, //Power Transformer/Current Transformer
		SwitchSensor1, //Switch Sensor 1
		Sensor, //Sensor
		SwitchSensor2, //Switch Sensor 2
		LTSActuator, //Local Test System Actuator
		RAS, //Remedial Action Scheme
		SwitchActuator, //Switch Actuator
		Actuator //Actuator
	};
	
	public enum DisableEnableComponents{ENABLED,DISABLED};
	public enum TypeSignals{DigitalSignal,TestControlSignal,AnalogSignal};
	public enum TypeActions{CreateMessage,EnableComponent,DisableComponent,SendMessageTest,Error,SendMessagePTCT,SendMessageDAC,SendMessageRAS,SendMessageCA,ReportSuccess,LogTest};
	public enum SchemeRAS{10-bus};
	public enum TypeCodeAction{Encoding,Decoding};
	public enum TypeCode{Berger,Duplication,Residue};
	public enum ComponentsTest{OpWAN,Sensor,Actuator,SwitchSensor1,SwitchSensor2,All,SwitchActuator,None};
	public enum ParametersTest{None,CreateMessage,EnableComponentSensor,DisableComponentSensor,EnableComponentActuator,DisableComponentActuator,
								Contingency};
	
	public static final int MAX_HEARTBEATS = 10;
	public static final int extendHeartbeat = 5000;
	public static final int TIMEOUT = 15;
	public static final int SLEEP = 500;
	
	final String char1 = new String(new char[1]).replace('\0', ' ');
	final String char2 = new String(new char[2]).replace('\0', ' ');
	final String char3 = new String(new char[3]).replace('\0', ' ');
	final String separator1 = char3;					//	"\t\t      |
	//public String separator6 = char3 + "+";			//	"\t\t      +|
	//public String separator7 = char3 + ">";			//	"\t\t      >|
	//public String separator8 = char3 + "<";			//	"\t\t      <|
	//public String separator9 = char3 + "!";			//	"\t\t      !|
	
	final String separator2 = char2;					//	"\t\t|
	//public String separator10= char2 + "*";			//	"\t\t*|
	//public String separator11= char2 + "!";			//	"\t\t!|
	
	final String separator3 = char3 + char1;			//	"\t\t            |
	//public String separator12= char3 + char1 + "-";	//	"\t\t            -|
	
	final String separator4 = char2;				//	"\t      <|
	final String separator5 = char1;				//	"\t|
	final String separator6 = char1;				//	"      "|
}
