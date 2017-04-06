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

@Entity(name="sra_RASTestSTCodeContingency")
@Inheritance(strategy=InheritanceType.JOINED)
public class ST_RASTestSTCodeContingency implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="rsc_idRASTestSTCodeContingency")
	private int idRASTestSTCodeContingency;
	
	@ManyToOne
	@JoinColumn(name = "rsc_idContingency")
	private ST_RASTestContingencies idContingency;
	
	@ManyToOne
	@JoinColumn(name = "rsc_idSTCode")
	private ST_STCode idSTCode;
	
	@ManyToOne
	@JoinColumn(name = "rsc_idComponent")
	private ST_componentsEES idComponent;
	
	@ManyToOne
	@JoinColumn(name = "rsc_idRASTest")
	private ST_RASTest idRASTest;
	
	@Column(name = "rsc_idTypeAction")
	private int idTypeAction;
	
	@Column(name = "rsc_codeWordContingecy")
	private String codeWordContingecy;
	
	@Column(name = "rsc_codeWordError")
	private String codeWordError;

	public int getIdRASTestSTCodeContingency() {
		return idRASTestSTCodeContingency;
	}

	private void setIdRASTestSTCodeContingency(int idRASTestSTCodeContingency) {
		this.idRASTestSTCodeContingency = idRASTestSTCodeContingency;
	}

	public ST_RASTestContingencies getIdContingency() {
		return idContingency;
	}

	private void setIdContingency(ST_RASTestContingencies idContingency) {
		this.idContingency = idContingency;
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

	public String getCodeWordContingecy() {
		return codeWordContingecy;
	}

	private void setCodeWordContingecy(String codeWordContingecy) {
		this.codeWordContingecy = codeWordContingecy;
	}

	public String getCodeWordError() {
		return codeWordError;
	}

	private void setCodeWordError(String codeWordError) {
		this.codeWordError = codeWordError;
	}
	
	public ST_RASTestSTCodeContingency(){
		setCodeWordContingecy(null);
		setCodeWordError(null);
		setIdComponent(null);
		setIdRASTest(null);
		setIdRASTestSTCodeContingency(0);
		setIdContingency(null);
		setIdTypeAction(0);
		setIdSTCode(null);
	}
	
	public ST_RASTestSTCodeContingency(ST_componentsEES idComponent, ST_RASTest idRASTest, ST_RASTestContingencies idContingency, 
			ST_STCode idSTCode, int idTypeAction){
		setCodeWordContingecy(null);
		setCodeWordError(null);
		setIdComponent(idComponent);
		setIdRASTest(idRASTest);
		setIdTypeAction(idTypeAction);
		setIdSTCode(idSTCode);
		setIdContingency(idContingency);
	}
	
	public ST_RASTestSTCodeContingency(ST_RASTestSTCodeContingency idRASTestSTCodeContingency, String codeWordContingency){
		setCodeWordContingecy(codeWordContingency);
		
		setIdComponent(idRASTestSTCodeContingency.getIdComponent());
		setIdRASTest(idRASTestSTCodeContingency.getIdRASTest());
		setIdSTCode(idRASTestSTCodeContingency.getIdSTCode());
		setIdTypeAction(idRASTestSTCodeContingency.getIdTypeAction());
		setIdContingency(idRASTestSTCodeContingency.getIdContingency());
		setIdRASTestSTCodeContingency(idRASTestSTCodeContingency.getIdRASTestSTCodeContingency());
	}
	
	public ST_RASTestSTCodeContingency(String codeWordError, ST_RASTestSTCodeContingency idRASTestSTCodeContingency){
		setCodeWordError(codeWordError);
		
		setCodeWordContingecy(idRASTestSTCodeContingency.getCodeWordContingecy());
		setIdComponent(idRASTestSTCodeContingency.getIdComponent());
		setIdRASTest(idRASTestSTCodeContingency.getIdRASTest());
		setIdSTCode(idRASTestSTCodeContingency.getIdSTCode());
		setIdTypeAction(idRASTestSTCodeContingency.getIdTypeAction());
		setIdContingency(idRASTestSTCodeContingency.getIdContingency());
		setIdRASTestSTCodeContingency(idRASTestSTCodeContingency.getIdRASTestSTCodeContingency());
	}
	
	public ST_RASTestSTCodeContingency(ST_componentsEES idComponent, ST_RASTest idRASTest, ST_RASTestContingencies idContingency, 
			ST_STCode idSTCode, int idTypeAction, String codeWordError, String codeWordContingency){
		setCodeWordContingecy(codeWordContingency);
		setCodeWordError(codeWordError);
		setIdComponent(idComponent);
		setIdRASTest(idRASTest);
		setIdTypeAction(idTypeAction);
		setIdSTCode(idSTCode);
		setIdContingency(idContingency);
	}
}