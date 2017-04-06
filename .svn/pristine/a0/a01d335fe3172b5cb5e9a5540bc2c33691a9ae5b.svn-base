package ras.interfaces;

import java.io.Serializable;
import java.util.List;

import ras.data.ST_RASTest;
import ras.data.ST_RASTestDisableEnableComponent;
import ras.data.ST_RASTestSTCode;
import ras.data.ST_RASTestSTCodeContingency;

public class TestCtrlSignal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean disableEnableComponents;
	private Boolean resultTestSensor;
	private Boolean resultTestActuator;
	private DigitalSignal digSignal;
	private AnalogSignal anaSignal;
	private ST_RASTest rasTest;
	private String message;
	private String typeAction;
	private ST_RASTestDisableEnableComponent rasTestDisableEnable;
	private ST_RASTestSTCode rasTestSTCode;
	private List<ST_RASTestSTCodeContingency> listRASTestSTCodeContingency;
	
	public TestCtrlSignal(Boolean disableEnableComponents, Boolean resultTestSensor, DigitalSignal digSignal, ST_RASTestSTCode rasTestSTCode){
		setDisableEnableComponents(disableEnableComponents);
		setResultTestSensor(resultTestSensor);
		setDigSignal(digSignal);
		if(rasTestSTCode != null)
			setRasTestSTCode(rasTestSTCode);
	}
	
	public TestCtrlSignal(Boolean disableEnableComponents, Boolean resultTestSensor, DigitalSignal digSignal, 
			ST_RASTest rasTest, ST_RASTestSTCode rasTestSTCode, List<ST_RASTestSTCodeContingency> listRASTestSTCodeContingency){
		setDisableEnableComponents(disableEnableComponents);
		setResultTestSensor(resultTestSensor);
		setDigSignal(digSignal);
		setRasTest(rasTest);
		if(rasTestSTCode != null)
			setRasTestSTCode(rasTestSTCode);
		if(listRASTestSTCodeContingency != null)
			setListRASTestSTCodeContingency(listRASTestSTCodeContingency);
	}
	
	public TestCtrlSignal(DigitalSignal digSignal, AnalogSignal anaSignal, ST_RASTest rasTest, String message, String typeAction, 
			ST_RASTestSTCode rasTestSTCode, List<ST_RASTestSTCodeContingency> listRASTestSTCodeContingency){
		setRasTest(rasTest);
		setDigSignal(digSignal);
		setAnaSignal(anaSignal);
		setMessage(message);
		setTypeAction(typeAction);
		if(rasTestSTCode != null)
			setRasTestSTCode(rasTestSTCode);
		if(listRASTestSTCodeContingency != null)
			setListRASTestSTCodeContingency(listRASTestSTCodeContingency);
	}
	
	public TestCtrlSignal(Boolean disableEnableComponents, DigitalSignal digSignal, AnalogSignal anaSignal, ST_RASTest rasTest, 
			String message, String typeAction, ST_RASTestDisableEnableComponent rasTestDisableEnable, ST_RASTestSTCode rasTestSTCode, 
			List<ST_RASTestSTCodeContingency> listRASTestSTCodeContingency){
		setDisableEnableComponents(disableEnableComponents);
		setRasTest(rasTest);
		setDigSignal(digSignal);
		setAnaSignal(anaSignal);
		setMessage(message);
		setTypeAction(typeAction);
		setRasTestDisableEnable(rasTestDisableEnable);
		if(digSignal != null){
			setResultTestActuator(digSignal.getResultTestActuator());
			setResultTestSensor(digSignal.getResultTestSensor());
		}
		if(rasTestSTCode != null)
			setRasTestSTCode(rasTestSTCode);
		if(listRASTestSTCodeContingency != null)
			setListRASTestSTCodeContingency(listRASTestSTCodeContingency);
	}
	
	public TestCtrlSignal(TestCtrlSignal testSignal, Boolean resultTestActuator, Boolean resultTestSensor, ST_RASTestSTCode rasTestSTCode, 
			List<ST_RASTestSTCodeContingency> listRASTestSTCodeContingency){
		setResultTestActuator(resultTestActuator);
		setResultTestSensor(resultTestSensor);
		setDigSignal(testSignal.getDigSignal());
		setRasTest(testSignal.getRasTest());
		setMessage(testSignal.getMessage());
		setAnaSignal(testSignal.getAnaSignal());
		setDisableEnableComponents(testSignal.getDisableEnableComponents());
		setRasTestDisableEnable(testSignal.getRasTestDisableEnable());
		setTypeAction(testSignal.getTypeAction());
		if(rasTestSTCode != null)
			setRasTestSTCode(rasTestSTCode);
		if(listRASTestSTCodeContingency != null)
			setListRASTestSTCodeContingency(listRASTestSTCodeContingency);
	}
	
	public TestCtrlSignal(Boolean disableEnableComponents, Boolean resultTestSensor, Boolean resultTestActuator, DigitalSignal digSignal, AnalogSignal anaSignal,
			 ST_RASTest rasTest, String message, String typeAction, ST_RASTestDisableEnableComponent rasTestDisableEnable, ST_RASTestSTCode rasTestSTCode,
			 List<ST_RASTestSTCodeContingency> listRASTestSTCodeContingency){
		setDisableEnableComponents(disableEnableComponents);
		setResultTestSensor(resultTestSensor);
		setResultTestActuator(resultTestActuator);
		setDigSignal(digSignal);
		setAnaSignal(anaSignal);
		setRasTest(rasTest);
		setMessage(message);
		setTypeAction(typeAction);
		setRasTestDisableEnable(rasTestDisableEnable);
		if(rasTestSTCode != null)
			setRasTestSTCode(rasTestSTCode);
		if(listRASTestSTCodeContingency != null)
			setListRASTestSTCodeContingency(listRASTestSTCodeContingency);
	}
	
	public Boolean getDisableEnableComponents() {
		return disableEnableComponents;
	}
	private void setDisableEnableComponents(Boolean disableEnableComponents) {
		this.disableEnableComponents = disableEnableComponents;
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

	public DigitalSignal getDigSignal() {
		return digSignal;
	}
	private void setDigSignal(DigitalSignal digSignal) {
		this.digSignal = digSignal;
	}

	public AnalogSignal getAnaSignal() {
		return anaSignal;
	}

	private void setAnaSignal(AnalogSignal anaSignal) {
		this.anaSignal = anaSignal;
	}

	public ST_RASTest getRasTest() {
		return rasTest;
	}

	private void setRasTest(ST_RASTest rasTest) {
		this.rasTest = rasTest;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}

	public String getTypeAction() {
		return typeAction;
	}

	private void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	public ST_RASTestDisableEnableComponent getRasTestDisableEnable() {
		return rasTestDisableEnable;
	}

	private void setRasTestDisableEnable(ST_RASTestDisableEnableComponent rasTestDisableEnable) {
		this.rasTestDisableEnable = rasTestDisableEnable;
	}

	public ST_RASTestSTCode getRasTestSTCode() {
		return rasTestSTCode;
	}

	private void setRasTestSTCode(ST_RASTestSTCode rasTestSTCode) {
		this.rasTestSTCode = rasTestSTCode;
	}

	public List<ST_RASTestSTCodeContingency> getListRASTestSTCodeContingency() {
		return listRASTestSTCodeContingency;
	}

	private void setListRASTestSTCodeContingency(
			List<ST_RASTestSTCodeContingency> listRASTestSTCodeContingency) {
		this.listRASTestSTCodeContingency = listRASTestSTCodeContingency;
	}
}
