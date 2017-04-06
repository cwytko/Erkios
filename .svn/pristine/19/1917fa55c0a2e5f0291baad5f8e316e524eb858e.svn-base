package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_RASSchemes")
public class ST_rasSchemes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ras_idRAS")
	private String idRAS;
	
	@ManyToOne
	@JoinColumn(name="ras_idClassification")
	private ST_classRAS idClassification;
	
	@Column(name="ras_nameRAS")
	private String nameRAS;
	
	public ST_rasSchemes(){
		this.setClassification(null);
		this.setIdRAS("");
		this.setNameRAS("");
	}
	
	public String getIdRAS() {
		return idRAS;
	}
	private void setIdRAS(String idRAS) {
		this.idRAS = idRAS;
	}
	public ST_classRAS getClassification() {
		return idClassification;
	}
	private void setClassification(ST_classRAS idClassification) {
		this.idClassification = idClassification;
	}
	public String getNameRAS() {
		return nameRAS;
	}
	private void setNameRAS(String nameRAS) {
		this.nameRAS = nameRAS;
	}
	
	
}
