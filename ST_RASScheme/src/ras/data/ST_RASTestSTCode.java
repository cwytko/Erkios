package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name="sra_RASTestSTCode")
@Inheritance(strategy=InheritanceType.JOINED)
public class ST_RASTestSTCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="rts_idRASTestSTCode")
	private int idRASTestSTCode;
	
	@ManyToOne
	@JoinColumn(name = "rts_idSTCode")
	private ST_STCode idSTCode;
	
	@ManyToOne
	@JoinColumn(name = "rts_idComponent")
	private ST_componentsEES idComponent;
	
	@ManyToOne
	@JoinColumn(name = "rts_idRASTest")
	private ST_RASTest idRASTest;
	
	@Column(name = "rts_idTypeAction")
	private int idTypeAction;
	
	@Column(name = "rts_codeWordCreateMessage")
	private String codeWordCreateMessage;
	
	@Column(name = "rts_codeWordDisableEnableSensor")
	private String codeWordDisableEnableSensor;
	
	@Column(name = "rts_codeWordDisableEnableActuator")
	private String codeWordDisableEnableActuator;
	
	@Column(name = "rts_idCodeWordParamError")
	private int idCodeWordParamError;
	
	@Column(name = "rts_codeWordError")
	private String codeWordError;

	public int getIdRASTestSTCode() {
		return idRASTestSTCode;
	}

	private void setIdRASTestSTCode(int idRASTestSTCode) {
		this.idRASTestSTCode = idRASTestSTCode;
	}

	public ST_STCode getIdSTCode() {
		return idSTCode;
	}

	private void setIdSTCode(ST_STCode idSTCode) {
		this.idSTCode = idSTCode;
	}

	public ST_componentsEES getIdComponent() {
		return idComponent;
	}

	private void setIdComponent(ST_componentsEES idComponent) {
		this.idComponent = idComponent;
	}

	public ST_RASTest getIdRASTest() {
		return idRASTest;
	}

	private void setIdRASTest(ST_RASTest idRASTest) {
		this.idRASTest = idRASTest;
	}

	public int getIdTypeAction() {
		return idTypeAction;
	}

	private void setIdTypeAction(int idTypeAction) {
		this.idTypeAction = idTypeAction;
	}

	public String getCodeWordCreateMessage() {
		return codeWordCreateMessage;
	}

	private void setCodeWordCreateMessage(String codeWordCreateMessage) {
		this.codeWordCreateMessage = codeWordCreateMessage;
	}

	public String getCodeWordDisableEnableSensor() {
		return codeWordDisableEnableSensor;
	}

	private void setCodeWordDisableEnableSensor(String codeWordDisableEnableSensor) {
		this.codeWordDisableEnableSensor = codeWordDisableEnableSensor;
	}

	public String getCodeWordDisableEnableActuator() {
		return codeWordDisableEnableActuator;
	}

	private void setCodeWordDisableEnableActuator(
			String codeWordDisableEnableActuator) {
		this.codeWordDisableEnableActuator = codeWordDisableEnableActuator;
	}


	public int getIdCodeWordParamError() {
		return idCodeWordParamError;
	}

	private void setIdCodeWordParamError(int idCodeWordParamError) {
		this.idCodeWordParamError = idCodeWordParamError;
	}

	public String getCodeWordError() {
		return codeWordError;
	}

	private void setCodeWordError(String codeWordError) {
		this.codeWordError = codeWordError;
	}

	public ST_RASTestSTCode(){
		setCodeWordDisableEnableSensor(null);
		setCodeWordDisableEnableActuator(null);
		
		setIdCodeWordParamError(0);
		
		setCodeWordError(null);
		setIdComponent(null);
		setIdRASTest(null);
		setIdRASTestSTCode(0);
		setIdTypeAction(0);
		setIdSTCode(null);
	}
	
	public ST_RASTestSTCode(ST_RASTestSTCode idRASTestSTCode, ST_componentsEES idComponent, ST_RASTest idRASTest, ST_STCode idSTCode, int idTypeAction){
		setCodeWordDisableEnableSensor(idRASTestSTCode.getCodeWordDisableEnableSensor());
		setCodeWordDisableEnableActuator(idRASTestSTCode.getCodeWordDisableEnableActuator());
		setCodeWordCreateMessage(idRASTestSTCode.getCodeWordCreateMessage());
		setIdCodeWordParamError(idRASTestSTCode.getIdCodeWordParamError());
		setIdRASTestSTCode(idRASTestSTCode.getIdRASTestSTCode());
		setCodeWordError(null);
		setIdComponent(idComponent);
		setIdRASTest(idRASTest);
		setIdSTCode(idSTCode);
		setIdTypeAction(idTypeAction);
	}
	
	public ST_RASTestSTCode(ST_componentsEES idComponent, ST_RASTest idRASTest, ST_STCode idSTCode, int idTypeAction){
		setCodeWordDisableEnableSensor(null);
		setCodeWordDisableEnableActuator(null);
		setIdCodeWordParamError(0);
		setCodeWordError(null);
		setIdComponent(idComponent);
		setIdRASTest(idRASTest);
		setIdSTCode(idSTCode);
		setIdTypeAction(idTypeAction);
	}
	
	public ST_RASTestSTCode(ST_RASTestSTCode idRASTestSTCode, int idTypeAction){
		setCodeWordDisableEnableSensor(idRASTestSTCode.codeWordDisableEnableSensor);
		setCodeWordDisableEnableActuator(idRASTestSTCode.codeWordDisableEnableActuator);
		setCodeWordCreateMessage(idRASTestSTCode.getCodeWordCreateMessage());
		setIdCodeWordParamError(0);
		setCodeWordError(null);
		setIdComponent(idRASTestSTCode.getIdComponent());
		setIdRASTest(idRASTestSTCode.getIdRASTest());
		setIdSTCode(idRASTestSTCode.getIdSTCode());
		setIdRASTestSTCode(idRASTestSTCode.getIdRASTestSTCode());
		setIdTypeAction(idTypeAction);
	}
	
	public ST_RASTestSTCode(ST_RASTestSTCode idRASTestSTCode, int idCodeWordParamError, String codeWordCreateMessage){
		setIdCodeWordParamError(idCodeWordParamError);
		setCodeWordCreateMessage(codeWordCreateMessage);
		
		setIdComponent(idRASTestSTCode.getIdComponent());
		setIdRASTest(idRASTestSTCode.getIdRASTest());
		setIdSTCode(idRASTestSTCode.getIdSTCode());
		setIdTypeAction(idRASTestSTCode.getIdTypeAction());
		setIdRASTestSTCode(idRASTestSTCode.getIdRASTestSTCode());
	}
	
	public ST_RASTestSTCode(ST_RASTestSTCode idRASTestSTCode, String codeWordDisableEnableSensor, String codeWordDisableEnableActuator){
		setCodeWordDisableEnableSensor(codeWordDisableEnableSensor);
		setCodeWordDisableEnableActuator(codeWordDisableEnableActuator);
		setCodeWordCreateMessage(idRASTestSTCode.getCodeWordCreateMessage());
		setIdComponent(idRASTestSTCode.getIdComponent());
		setIdRASTest(idRASTestSTCode.getIdRASTest());
		setIdSTCode(idRASTestSTCode.getIdSTCode());
		setIdTypeAction(idRASTestSTCode.getIdTypeAction());
		setIdRASTestSTCode(idRASTestSTCode.getIdRASTestSTCode());
	}
	
	public ST_RASTestSTCode(ST_RASTestSTCode idRASTestSTCode, int idCodeWordParamError){
		setCodeWordCreateMessage(idRASTestSTCode.getCodeWordCreateMessage());
		setCodeWordDisableEnableActuator(idRASTestSTCode.getCodeWordDisableEnableActuator());
		setCodeWordDisableEnableSensor(idRASTestSTCode.getCodeWordDisableEnableSensor());
		setIdComponent(idRASTestSTCode.getIdComponent());
		setIdRASTest(idRASTestSTCode.getIdRASTest());
		setIdSTCode(idRASTestSTCode.getIdSTCode());
		setIdTypeAction(idRASTestSTCode.getIdTypeAction());
		setIdCodeWordParamError(idCodeWordParamError);
		setIdRASTestSTCode(idRASTestSTCode.getIdRASTestSTCode());
		
		
	}
	
	public ST_RASTestSTCode(ST_RASTestSTCode idRASTestSTCode, String codeWordError, int idCodeWordParamError){
		setIdCodeWordParamError(idCodeWordParamError);
		setCodeWordError(codeWordError);
		
		setCodeWordDisableEnableSensor(idRASTestSTCode.getCodeWordDisableEnableSensor());
		setCodeWordDisableEnableActuator(idRASTestSTCode.getCodeWordDisableEnableActuator());
		setCodeWordCreateMessage(idRASTestSTCode.getCodeWordCreateMessage());
		setIdComponent(idRASTestSTCode.getIdComponent());
		setIdRASTest(idRASTestSTCode.getIdRASTest());
		setIdSTCode(idRASTestSTCode.getIdSTCode());
		setIdTypeAction(idRASTestSTCode.getIdTypeAction());
		setIdRASTestSTCode(idRASTestSTCode.getIdRASTestSTCode());
	}
}
