package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_RASTestContingencies")
public class ST_RASTestContingencies implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="rtc_idRASTestContingency")
	private int idRASTestContingency;
	
	@ManyToOne
	@JoinColumn(name="rtc_idContingency")
	private ST_contingencies idContingency;
	
	@ManyToOne
	@JoinColumn(name="rtc_idRASTest")
	private ST_RASTest idRASTest;
	
	@Column(name="rtc_contingency")
	private String contingency;

	public ST_RASTestContingencies(){
		setContingency("");
		setIdContingency(null);
		setIdRASTest(null);
		setIdRASTestContingency(0);
	}
	
	public ST_RASTestContingencies(ST_contingencies contingencies, ST_RASTest rasTest, String contingency){
		setIdContingency(contingencies);
		setIdRASTest(rasTest);
		setContingency(contingency);
	}
	
	public int getIdRASTestContingency() {
		return idRASTestContingency;
	}

	private void setIdRASTestContingency(int idRASTestContingency) {
		this.idRASTestContingency = idRASTestContingency;
	}

	public ST_contingencies getIdContingency() {
		return idContingency;
	}

	private void setIdContingency(ST_contingencies idContingency) {
		this.idContingency = idContingency;
	}

	public ST_RASTest getIdRASTest() {
		return idRASTest;
	}

	private void setIdRASTest(ST_RASTest idRASTest) {
		this.idRASTest = idRASTest;
	}

	public String getContingency() {
		return contingency;
	}

	private void setContingency(String contingency) {
		this.contingency = contingency;
	}
	
	
}