package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_RASTestRemedialActions")
public class ST_RASTestRemedialActions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="rtr_idRASTestRemedialAction")
	private int idRASTestRemedialAction;
	
	@ManyToOne
	@JoinColumn(name="rtr_idRemedialAction")
	private ST_remedialActions idRemedialAction;
	
	@ManyToOne
	@JoinColumn(name="rtr_idRASTest")
	private ST_RASTest idRASTest;
	
	@Column(name="rtr_remedialAction")
	private String remedialAction;
	
	public ST_RASTestRemedialActions(){
		setIdRASTest(null);
		setIdRASTestRemedialAction(0);
		setIdRemedialAction(null);
		setRemedialAction("");
	}

	public ST_RASTestRemedialActions(ST_RASTest rasTest, ST_remedialActions remedialActions, String remedialAction){
		setIdRASTest(rasTest);
		setIdRemedialAction(remedialActions);
		setRemedialAction(remedialAction);
	}
	
	public int getIdRASTestRemedialAction() {
		return idRASTestRemedialAction;
	}

	private void setIdRASTestRemedialAction(int idRASTestRemedialAction) {
		this.idRASTestRemedialAction = idRASTestRemedialAction;
	}

	public ST_remedialActions getIdRemedialAction() {
		return idRemedialAction;
	}

	private void setIdRemedialAction(ST_remedialActions idRemedialAction) {
		this.idRemedialAction = idRemedialAction;
	}

	public ST_RASTest getIdRASTest() {
		return idRASTest;
	}

	private void setIdRASTest(ST_RASTest idRASTest) {
		this.idRASTest = idRASTest;
	}

	public String getRemedialAction() {
		return remedialAction;
	}

	private void setRemedialAction(String remedialAction) {
		this.remedialAction = remedialAction;
	}
}