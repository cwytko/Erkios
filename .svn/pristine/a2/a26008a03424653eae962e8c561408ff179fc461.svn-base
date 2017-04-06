package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_remedialActions")
public class ST_remedialActions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="rac_idRemedialAction")
	private int idRemedialAction;
	
	@ManyToOne
	@JoinColumn(name="rac_idRAS")
	private ST_rasSchemes idRAS;
	
	@Column(name="rac_remedialAction")
	private String remedialAction;

	public ST_remedialActions(){
		setIdRemedialAction(0);
		setIdRAS(null);
		setRemedialAction("");
	}
	
	public ST_remedialActions(ST_rasSchemes idRAS, String remedialAction) {
		setIdRemedialAction(0);
		setIdRAS(idRAS);
		setRemedialAction(remedialAction);
	}
	
	public int getIdRemedialAction() {
		return idRemedialAction;
	}

	private void setIdRemedialAction(int idRemedialAction) {
		this.idRemedialAction = idRemedialAction;
	}

	public ST_rasSchemes getIdRAS() {
		return idRAS;
	}

	private void setIdRAS(ST_rasSchemes idRAS) {
		this.idRAS = idRAS;
	}

	public String getRemedialAction() {
		return remedialAction;
	}

	private void setRemedialAction(String remedialAction) {
		this.remedialAction = remedialAction;
	}
	
	
}
