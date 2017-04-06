package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_contingencies")

public class ST_contingencies implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="con_idContingency")
	private int idContingency;
	
	@ManyToOne
	@JoinColumn(name="con_idRAS")
	private ST_rasSchemes idRAS;
	
	@Column(name="con_checkContingency")
	private Boolean checkContingency;
	
	@Column(name="con_contingency")
	private String contingency;

	public ST_contingencies (){
		this.setContingency("");
		this.setIdContingency(0);
		this.setCheckContingency(false);
		this.setRas(null);
	}
	
	public int getIdContingency() {
		return idContingency;
	}

	private void setIdContingency(int idContingency) {
		this.idContingency = idContingency;
	}

	public ST_rasSchemes getRas() {
		return idRAS;
	}

	private void setRas(ST_rasSchemes idRAS) {
		this.idRAS = idRAS;
	}

	public String getContingency() {
		return contingency;
	}

	private void setContingency(String contingency) {
		this.contingency = contingency;
		setCheckContingency(false);
	}
	
	public Boolean getCheckContingency() {
		return checkContingency;
	}
	
	public void setCheckContingency(Boolean checkContingency) {
		this.checkContingency = checkContingency;
	}
}
