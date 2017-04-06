package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="sra_classRAS")
public class ST_classRAS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cra_idClassification")
	private int idClassification;
	@Column(name="cra_nameClass")
	private String nameClass;
	
	public ST_classRAS() {
		this.setIdClassification(0);
		this.setNameClass("");
	}
	
	public int getIdClassification() {
		return idClassification;
	}
	private void setIdClassification(int idClassification) {
		this.idClassification = idClassification;
	}
	public String getNameClass() {
		return nameClass;
	}
	private void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}
}
