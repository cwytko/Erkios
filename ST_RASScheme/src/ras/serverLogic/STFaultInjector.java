package ras.serverLogic;

//import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ras.data.ST_RASTestSTCode;
import ras.data.ST_RASTestSTCodeContingency;
import ras.interfaces.Utilities.ParametersTest;
import ras.interfaces.Utilities.SchemeRAS;

public class STFaultInjector {

	private ST_RASTestSTCode testCode;
	private List<ST_RASTestSTCodeContingency> listTestCodeContingency;
	private enum Parameter{Power,Current,Voltage,Frequency};
	
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
	
	STFaultInjector(){
		super();
	}
	
	STFaultInjector(ST_RASTestSTCode testCode, List<ST_RASTestSTCodeContingency> listTestCodeContingency) throws Exception{
		setTestCode(testCode);
		setListTestCodeContingency(listTestCodeContingency);
	}
	
	public ST_RASTestSTCode injectErrorSTCode(){
		ST_RASTestSTCode testCodeError = null;
		String idRAS = getTestCode().getIdRASTest().getIdRAS().getIdRAS();
		String errorBit = null;
		HashMap<String, String> parametersTest = new HashMap<String, String>();
		if(idRAS.compareTo(SchemeRAS.ios_I.toString()) == 0 ||
				idRAS.compareTo(SchemeRAS.ios_III.toString()) == 0){
			errorBit = injectErrorBit(getTestCode().getCodeWordPower());
			parametersTest.put(ParametersTest.Power.toString(), errorBit);
			String scheme = idRAS.compareTo(SchemeRAS.ios_I.toString()) == 0 ? SchemeRAS.ios_I.toString() : SchemeRAS.ios_III.toString();
			System.out.println(ras.interfaces.Utilities.separator3+"====="+scheme +" WAS INJECTED WITH ERROR BIT IN PARAMETER "+ParametersTest.Power.toString());
		} else {
			if(idRAS.compareTo(SchemeRAS.siv_R.toString()) == 0){
				int position = new Random().nextInt(Parameter.values().length);
				String valueParameter = getTestCode().getCodeWordPower();
				errorBit = Parameter.Power.ordinal() == position ? injectErrorBit(valueParameter) : valueParameter;
				parametersTest.put(Parameter.Power.toString(), errorBit);
				String scheme = SchemeRAS.siv_R.toString();
				if(Parameter.Power.ordinal() == position)
					System.out.println(ras.interfaces.Utilities.separator3+"====="+scheme +" WAS INJECTED WITH ERROR BIT IN PARAMETER "+Parameter.Power.toString());

				valueParameter = getTestCode().getCodeWordCurrent();
				errorBit = Parameter.Current.ordinal() == position ? injectErrorBit(valueParameter) : valueParameter;
				parametersTest.put(Parameter.Current.toString(), errorBit);
				if(Parameter.Current.ordinal() == position)
					System.out.println(ras.interfaces.Utilities.separator3+"====="+scheme +" WAS INJECTED WITH ERROR BIT IN PARAMETER "+Parameter.Current.toString());
				
				valueParameter = getTestCode().getCodeWordVoltage();
				errorBit = Parameter.Voltage.ordinal() == position ? injectErrorBit(valueParameter) : valueParameter;
				parametersTest.put(Parameter.Voltage.toString(), errorBit);
				if(Parameter.Voltage.ordinal() == position)
					System.out.println(ras.interfaces.Utilities.separator3+"====="+scheme +" WAS INJECTED WITH ERROR BIT IN PARAMETER "+Parameter.Voltage.toString());
				
				valueParameter = getTestCode().getCodeWordFrequency();
				errorBit = Parameter.Frequency.ordinal() == position ? injectErrorBit(valueParameter) : valueParameter;
				parametersTest.put(Parameter.Frequency.toString(), errorBit);
				if(Parameter.Frequency.ordinal() == position)	
					System.out.println(ras.interfaces.Utilities.separator3+"====="+scheme +" WAS INJECTED WITH ERROR BIT IN PARAMETER "+Parameter.Frequency.toString());
			} else {
				if(idRAS.compareTo(SchemeRAS.ios_II.toString()) == 0 || idRAS.compareTo(SchemeRAS.siv_E.toString()) == 0){
					errorBit = injectErrorBit(getTestCode().getCodeWordReactivePower());
					parametersTest.put(ParametersTest.ReactivePower.toString(), errorBit);
					String scheme = idRAS.compareTo(SchemeRAS.ios_II.toString()) == 0 ? SchemeRAS.ios_II.toString() : SchemeRAS.siv_E.toString();
					System.out.println(ras.interfaces.Utilities.separator3+"====="+scheme +" WAS INJECTED WITH ERROR BIT IN PARAMETER "+ParametersTest.ReactivePower.toString());
				} else {
					if(idRAS.compareTo(SchemeRAS.siv_S.toString()) == 0){
						errorBit = injectErrorBit(getTestCode().getCodeWordPhaseVoltage());
						parametersTest.put(ParametersTest.PhaseVoltage.toString(), errorBit);
						String scheme = SchemeRAS.siv_S.toString();
						System.out.println(ras.interfaces.Utilities.separator3+"====="+scheme +" WAS INJECTED WITH ERROR BIT IN PARAMETER "+ParametersTest.PhaseVoltage.toString());
					}
				}
			}
		}
		
		testCodeError = new ST_RASTestSTCode(getTestCode(),
				parametersTest.get(ParametersTest.Current.toString()),
				parametersTest.get(ParametersTest.Frequency.toString()),
				parametersTest.get(ParametersTest.PhaseVoltage.toString()),
				parametersTest.get(ParametersTest.Power.toString()),
				parametersTest.get(ParametersTest.ReactivePower.toString()),
				parametersTest.get(ParametersTest.Voltage.toString()),
				ParametersTest.None.ordinal()+1);
		
		return testCodeError;
	}
	
	public List<ST_RASTestSTCodeContingency> injectListErrorSTCodeContingency(){
		List<ST_RASTestSTCodeContingency> listTestCodeContingencyError = getListTestCodeContingency();
		int position = new Random().nextInt(getListTestCodeContingency().size());
		ST_RASTestSTCodeContingency testCodeContingencyError = getListTestCodeContingency().get(position); 
		String valueContingency = testCodeContingencyError.getCodeWordContingecy();
		valueContingency = injectErrorBit(valueContingency);
		testCodeContingencyError = new ST_RASTestSTCodeContingency(
				testCodeContingencyError.getIdComponent(), 
				testCodeContingencyError.getIdRASTest(), 
				testCodeContingencyError.getIdContingency(), 
				testCodeContingencyError.getIdSTCode(), 
				testCodeContingencyError.getIdTypeAction(), 
				null, 
				valueContingency);
		listTestCodeContingencyError.set(position, testCodeContingencyError);
		String scheme = testCodeContingencyError.getIdRASTest().getIdRAS().getIdRAS();
		int idContingency = testCodeContingencyError.getIdContingency().getIdContingency().getIdContingency();
		System.out.println(ras.interfaces.Utilities.separator3+"====="+scheme +" WAS INJECTED WITH ERROR BIT IN CONTINGENCY ID "+idContingency);
		return listTestCodeContingencyError;
	}
	
	private String injectErrorBit(String codeWord){
		String errorBit = null;
		int position = 0, min = 0, max = codeWord.length() ;
		char [] characters;
		Random rand = new Random();
		position = rand.nextInt((max - min)) + min;
		characters = codeWord.toCharArray();
		characters[position] = (characters[position] == '1' ? '0' : '1');
		errorBit = String.valueOf(characters);
		return errorBit;
	}
}
