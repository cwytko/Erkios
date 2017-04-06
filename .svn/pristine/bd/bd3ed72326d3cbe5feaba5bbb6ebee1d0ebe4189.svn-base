package ras.interfaces;

import java.io.Serializable;
import java.util.List;

import ras.data.ST_classRAS;
import ras.data.ST_contingencies;
import ras.data.ST_rasSchemes;
import ras.data.ST_remedialActions;
import ras.data.ST_tripCommand;
import ras.security.EncryptDecrypt;

public class DigitalSignal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ST_contingencies> contingenciesList;
	
	//Remedial actions required to solve a failure
	private List<ST_remedialActions> remedialActions;
	
	//Type of RAS Scheme either Impacts on the System or Scheme Input Variables
	private ST_classRAS classRAS;
	
	//RAS scheme used to perform a Test: Type I, II, III, Event-based, Response-based or Synchrophasor-based
	private ST_rasSchemes schemeRAS;
	
	private String idSession;
	
	private Boolean disableEnableComponent;
	
	private Boolean realStatusDisableEnableComponent;
	
	private String typeAction;
	
	private Boolean resultTestSensor;
	
	private Boolean resultTestActuator;
	
	//private String[] parameters;
	
	public DigitalSignal(ST_classRAS classRAS, List<ST_contingencies> contingenciesList,  
							ST_rasSchemes schemeRAS, List<ST_remedialActions> remedialActions){
		
		setClassRAS(classRAS);
		setContingenciesList(contingenciesList);
		setSchemeRAS(schemeRAS);
		setRemedialActions(remedialActions);
	}
	
	public DigitalSignal(DigitalSignal digSignal, String idRasTest, Boolean enableDisableComponent, String typeAction){
		String idSession = "";
		EncryptDecrypt edec = new EncryptDecrypt();
		
		setClassRAS(digSignal.getClassRAS());
		setContingenciesList(digSignal.getContingenciesList());
		setSchemeRAS(digSignal.getSchemeRAS());
		setRemedialActions(digSignal.getRemedialActions());
		setResultTestActuator(digSignal.getResultTestActuator());
		setResultTestSensor(digSignal.getResultTestSensor());
		
		idSession = String.valueOf(getClassRAS().getIdClassification())+"#"+getSchemeRAS().getIdRAS()+"#"+idRasTest;
		idSession = edec.encryptMsg(idSession);
		setIdSession(idSession);
		setDisableEnableComponent(enableDisableComponent);
		setTypeAction(typeAction);
	}
	
	public DigitalSignal(String idSession, Boolean enableDisableComponent, Boolean realStatusDisableEnableComponent, String typeAction){
		setIdSession(idSession);
		setDisableEnableComponent(enableDisableComponent);
		setTypeAction(typeAction);
		setRealStatusDisableEnableComponent(realStatusDisableEnableComponent);
	}
	
	public DigitalSignal(DigitalSignal digSignal, String idRasTest, Boolean enableDisableComponent, Boolean realStatusDisableEnableComponent, String typeAction){
		String idSession = "";
		EncryptDecrypt edec = new EncryptDecrypt();
		
		setClassRAS(digSignal.getClassRAS());
		setContingenciesList(digSignal.getContingenciesList());
		setSchemeRAS(digSignal.getSchemeRAS());
		setRemedialActions(digSignal.getRemedialActions());
		
		idSession = String.valueOf(getClassRAS().getIdClassification())+"#"+getSchemeRAS().getIdRAS()+"#"+idRasTest;
		idSession = edec.encryptMsg(idSession);
		setIdSession(idSession);
		setDisableEnableComponent(enableDisableComponent);
		setRealStatusDisableEnableComponent(realStatusDisableEnableComponent);
		setTypeAction(typeAction);
	}
	
	public DigitalSignal(DigitalSignal digSignal, Boolean resultTestSensor, Boolean resultTestActuator){
		setClassRAS(digSignal.getClassRAS());
		setContingenciesList(digSignal.getContingenciesList());
		setSchemeRAS(digSignal.getSchemeRAS());
		setRemedialActions(digSignal.getRemedialActions());
		setIdSession(digSignal.getIdSession());
		setDisableEnableComponent(digSignal.getDisableEnableComponent());
		setRealStatusDisableEnableComponent(digSignal.getRealStatusDisableEnableComponent());
		setTypeAction(digSignal.getTypeAction());
		
		setResultTestActuator(resultTestActuator);
		setResultTestSensor(resultTestSensor);
	}
	
	public DigitalSignal(DigitalSignal digSignal, String typeAction){
		setClassRAS(digSignal.getClassRAS());
		setContingenciesList(digSignal.getContingenciesList());
		setSchemeRAS(digSignal.getSchemeRAS());
		setRemedialActions(digSignal.getRemedialActions());
		setIdSession(digSignal.getIdSession());
		setDisableEnableComponent(digSignal.getDisableEnableComponent());
		setRealStatusDisableEnableComponent(digSignal.getRealStatusDisableEnableComponent());
		setResultTestActuator(digSignal.getResultTestActuator());
		setResultTestSensor(digSignal.getResultTestSensor());
		setTypeAction(typeAction);
	}

	

	public List<ST_contingencies> getContingenciesList() {
		return contingenciesList;
	}

	private void setContingenciesList(List<ST_contingencies> contingenciesList) {
		this.contingenciesList = contingenciesList;
	}

	public List<ST_remedialActions> getRemedialActions() {
		return remedialActions;
	}

	private void setRemedialActions(List<ST_remedialActions> remedialActions) {
		this.remedialActions = remedialActions;
	}

	public ST_classRAS getClassRAS() {
		return classRAS;
	}

	private void setClassRAS(ST_classRAS classRAS) {
		this.classRAS = classRAS;
	}

	public ST_rasSchemes getSchemeRAS() {
		return schemeRAS;
	}

	private void setSchemeRAS(ST_rasSchemes schemeRAS) {
		this.schemeRAS = schemeRAS;
	}

	public String getIdSession() {
		return idSession;
	}

	private void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public Boolean getDisableEnableComponent() {
		return disableEnableComponent;
	}

	private void setDisableEnableComponent(Boolean disableEnaableComponent) {
		this.disableEnableComponent = disableEnaableComponent;
	}

	public Boolean getRealStatusDisableEnableComponent() {
		return realStatusDisableEnableComponent;
	}

	private void setRealStatusDisableEnableComponent(
			Boolean realStatusDisableEnableComponent) {
		this.realStatusDisableEnableComponent = realStatusDisableEnableComponent;
	}

	public String getTypeAction() {
		return typeAction;
	}

	private void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	public Boolean getResultTestSensor() {
		return resultTestSensor;
	}

	private void setResultTestSensor(Boolean resultTestSensor) {
		this.resultTestSensor = resultTestSensor;
	}

	public Boolean getResultTestActuator() {
		return resultTestActuator;
	}

	private void setResultTestActuator(Boolean resultTestActuator) {
		this.resultTestActuator = resultTestActuator;
	}
/*
	public String[] getParameters() {
		return parameters;
	}

	private void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
*/
}
