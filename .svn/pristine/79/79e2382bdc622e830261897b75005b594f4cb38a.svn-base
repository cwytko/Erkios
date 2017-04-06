package ras.serverLogic;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import ras.clientLogic.DBRASSchemeClient;
import ras.data.ST_RASTest;
import ras.data.ST_RASTestContingencies;
import ras.data.ST_RASTestSTCode;
import ras.data.ST_RASTestSTCodeContingency;
import ras.interfaces.Utilities.Components;
import ras.interfaces.Utilities.ComponentsTest;
import ras.interfaces.Utilities.ParametersTest;
import ras.interfaces.Utilities.SchemeRAS;
import ras.interfaces.Utilities.TypeActions;
import ras.interfaces.Utilities.TypeCodeAction;

/*
	LEGACY SYSTEM TO CHECK		COMPONENT THAT CHECKS
	----------------------		---------------------
	SWITCHSENSOR1				LTSSENSOR
	SENSOR						SWITCHSENSOR1
	SWITCHSENSOR2				SWITCHSENSOR1
	OPWAN						LTSSENSOR
	OPWAN						LTSACTUATOR
	RAS							LTSACTUATOR
	RAS							SWITCHACTUATOR
	ACTUATOR					SWITCHACTUATOR
	SWITCHACTUATOR  			LTSACTUATOR
	ALL 
*/

public class STControl {

	private ST_RASTestSTCode testCode;
	private List<ST_RASTestSTCodeContingency> listTestCodeContingency;
	private DBRASSchemeClient stubDBRAS;
	
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

	STControl(){
		super();
	}
	
	STControl(ST_RASTestSTCode testCode, List<ST_RASTestSTCodeContingency> listTestCodeContingency) throws Exception{
		setTestCode(testCode);
		setListTestCodeContingency(listTestCodeContingency);
		try {
			stubDBRAS = new DBRASSchemeClient();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println(ras.interfaces.Utilities.separator3+"STControl err: stubDBRAS"+e.getMessage());
			throw new Exception("STControl err: stubDBRAS"+e.getMessage());
		}
	}
	
	public Boolean controlSTCode(int source, int destination, String component) throws Exception{
		Boolean flag = true;
		STCodes stCode = new STCodes();
		String idRAS = getTestCode().getIdRASTest().getIdRAS().getIdRAS();
		String typeCode = getTestCode().getIdSTCode().getShortCodeName();
		String errorBit = null, typeParameter = null;
		int positionParameter = 0;
		Boolean checkParameters = new Random().nextBoolean();
		
		if(component.equals(ComponentsTest.None.toString()))
			return flag;

		if(component.equals(getTestCode().getIdComponent().getShortNameComponent()) ||
				(getTestCode().getIdComponent().getShortNameComponent().equals(ComponentsTest.All.toString()) && 
				checkParameters)){
			try {
				if(idRAS.compareTo(SchemeRAS.ios_I.toString()) == 0 || idRAS.compareTo(SchemeRAS.ios_III.toString()) == 0){
					typeParameter = ParametersTest.Power.toString();
					positionParameter = ParametersTest.Power.ordinal() + 1;
					errorBit = getTestCode().getCodeWordPower();
					stCode.codingTechnique(errorBit, typeCode, TypeCodeAction.Decoding.toString());
				} else {
					if(idRAS.compareTo(SchemeRAS.siv_R.toString()) == 0){
						typeParameter = ParametersTest.Power.toString();
						positionParameter = ParametersTest.Power.ordinal() + 1;
						errorBit = getTestCode().getCodeWordPower();
						stCode.codingTechnique(errorBit, typeCode, TypeCodeAction.Decoding.toString());
						
						typeParameter = ParametersTest.Current.toString();
						positionParameter = ParametersTest.Current.ordinal() + 1;
						errorBit = getTestCode().getCodeWordCurrent();
						stCode.codingTechnique(errorBit, typeCode, TypeCodeAction.Decoding.toString());
						
						typeParameter = ParametersTest.Voltage.toString();
						positionParameter = ParametersTest.Voltage.ordinal() + 1;
						errorBit = getTestCode().getCodeWordVoltage();
						stCode.codingTechnique(errorBit, typeCode, TypeCodeAction.Decoding.toString());
						
						typeParameter = ParametersTest.Frequency.toString();
						positionParameter = ParametersTest.Frequency.ordinal() + 1;
						errorBit = getTestCode().getCodeWordFrequency();
						stCode.codingTechnique(errorBit, typeCode, TypeCodeAction.Decoding.toString());
					} else {
						if(idRAS.compareTo(SchemeRAS.ios_II.toString()) == 0 || idRAS.compareTo(SchemeRAS.siv_E.toString()) == 0){
							typeParameter = ParametersTest.ReactivePower.toString();
							positionParameter = ParametersTest.ReactivePower.ordinal() + 1;
							errorBit = getTestCode().getCodeWordReactivePower();
							stCode.codingTechnique(errorBit, typeCode, TypeCodeAction.Decoding.toString());
						} else {
							if(idRAS.compareTo(SchemeRAS.siv_S.toString()) == 0){
								typeParameter = ParametersTest.PhaseVoltage.toString();
								positionParameter = ParametersTest.PhaseVoltage.ordinal() + 1;
								errorBit = getTestCode().getCodeWordPhaseVoltage();
								stCode.codingTechnique(errorBit, typeCode, TypeCodeAction.Decoding.toString());
							}
						}
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				ST_RASTest rasTest = null;
				ST_RASTestSTCode testErrorSTCode = null;
				
				System.out.println(ras.interfaces.Utilities.separator3+ "##### STControlSTCode err:"+e.getMessage());
				System.out.println(stubDBRAS.getStubDBRAS().logLink(getTestCode().getIdRASTest(), source, destination, 
						TypeActions.SendMessageTest.ordinal()+1, false, true, Components.values()[destination-1].toString(), 
						"Wrong "+typeParameter+" Parameter value:"+errorBit));
				//System.out.println("******************************************* value"+getTestCode().getIdRASTestSTCode());
				if(getTestCode().getIdRASTestSTCode() == 0 )
					setTestCode((ST_RASTestSTCode)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTestSTCode where rts_idRASTest =:id", getTestCode().getIdRASTest().getIdRASTest()).get(0));
				
				testErrorSTCode = new ST_RASTestSTCode(getTestCode(), errorBit, positionParameter);
				
				//System.out.println("******************************************* value"+testErrorSTCode.getIdRASTestSTCode());
				stubDBRAS.getStubDBRAS().updateGenericQuery(testErrorSTCode, testErrorSTCode.getIdRASTestSTCode());
				
				rasTest = (ST_RASTest)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTest where tra_idRASTest =:id", getTestCode().getIdRASTest().getIdRASTest()).get(0);
				rasTest = new ST_RASTest(rasTest.getIdRASTest(), rasTest.getIdRAS(), rasTest.getIdClassification(), rasTest.getDateTimeBeginTest(), 
						false, Calendar.getInstance().getTime(), "ERROR ON PARAMETER");
				stubDBRAS.getStubDBRAS().updateGenericQuery(rasTest, positionParameter);
				throw new Exception(Components.values()[source-1].toString()+"Impl \nSTControlSTCode err: Wrong "+typeParameter+" Parameter value:"+errorBit);
			}
		}
		return flag;
	}
	
	public Boolean controlSTCodeContingency(int source, int destination, String component) throws Exception{
		Boolean flag = true;
		ST_RASTestSTCodeContingency testErrorSTCodeContingency = null;
		String errorBit = null;
		STCodes stCode = new STCodes();
		Boolean checkParameters = new Random().nextBoolean();
		
		if(component.equals(ComponentsTest.None.toString()))
			return flag;
			
		if(component.equals(getTestCode().getIdComponent().getShortNameComponent()) ||
				(getTestCode().getIdComponent().getShortNameComponent().equals(ComponentsTest.All.toString()) && 
				checkParameters))
		try {
			for(ST_RASTestSTCodeContingency testErrorSTContingency : getListTestCodeContingency()){
				testErrorSTCodeContingency = testErrorSTContingency;
				errorBit = testErrorSTContingency.getCodeWordContingecy();
				stCode.codingTechnique(errorBit, testErrorSTContingency.getIdSTCode().getShortCodeName(), TypeCodeAction.Decoding.toString());
			}	
		} catch (Exception e) {
			// TODO: handle exception
			ST_RASTest rasTest = null;
			
			System.out.println(ras.interfaces.Utilities.separator3+"##### STControlSTCode err:"+e.getMessage());
			System.out.println(stubDBRAS.getStubDBRAS().logLink(getTestCode().getIdRASTest(), source, destination, 
					TypeActions.SendMessageTest.ordinal()+1, true, true, Components.values()[destination-1].toString(), 
					"Wrong Contingency with id "+testErrorSTCodeContingency.getIdContingency().getIdContingency().getIdContingency()+" value:"+errorBit));
			//System.out.println("******************************************* value"+testErrorSTCodeContingency.getIdRASTestSTCodeContingency());
			int idContingency = testErrorSTCodeContingency.getIdContingency().getIdContingency().getIdContingency();
			if(testErrorSTCodeContingency.getIdRASTestSTCodeContingency() == 0){
				String query = "from sra_RASTestContingencies where rtc_idContingency =:id and rtc_idRASTest = "+testErrorSTCodeContingency.getIdRASTest().getIdRASTest();
				ST_RASTestContingencies contingency = (ST_RASTestContingencies)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(query, idContingency).get(0);
				query = "from sra_RASTestSTCodeContingency where rsc_idRASTest =:id and rsc_idContingency ="+contingency.getIdRASTestContingency();
				testErrorSTCodeContingency = (ST_RASTestSTCodeContingency)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery(query,testErrorSTCodeContingency.getIdRASTest().getIdRASTest()).get(0);
			}
			
			testErrorSTCodeContingency = new ST_RASTestSTCodeContingency(errorBit, testErrorSTCodeContingency);
			//System.out.println("******************************************* value"+testErrorSTCodeContingency.getIdRASTestSTCodeContingency());
			stubDBRAS.getStubDBRAS().updateGenericQuery(testErrorSTCodeContingency, testErrorSTCodeContingency.getIdRASTestSTCodeContingency());
			
			rasTest = (ST_RASTest)(Object)stubDBRAS.getStubDBRAS().selectGenericQuery("from sra_RASTest where tra_idRASTest =:id", getTestCode().getIdRASTest().getIdRASTest()).get(0);
			rasTest = new ST_RASTest(rasTest.getIdRASTest(), rasTest.getIdRAS(), rasTest.getIdClassification(), rasTest.getDateTimeBeginTest(), 
					false, Calendar.getInstance().getTime(), "ERROR ON CONTINGENCY");
			stubDBRAS.getStubDBRAS().updateGenericQuery(rasTest, 0);
			System.out.println(ras.interfaces.Utilities.separator3+"Wrong Contingency with id "+idContingency+" value:"+errorBit);
			throw new Exception(Components.values()[source-1].toString()+"\nWrong Contingency with id "+idContingency+" value:"+errorBit.substring(0, 10));
		}
		
		return flag;
	}
}
