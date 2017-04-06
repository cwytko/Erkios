package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="sra_typeSignal")
public class ST_typeSignal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="tsi_idSignal")
	private int idSignal;
	
	@Column(name="tsi_nameSignal")
	private String nameSignal;

	public ST_typeSignal(){
		this.setIdSignal(0);
		this.setNameSignal("");
	}
	
	public int getIdSignal() {
		return idSignal;
	}

	private void setIdSignal(int idSignal) {
		this.idSignal = idSignal;
	}

	public String getNameSignal() {
		return nameSignal;
	}

	private void setNameSignal(String nameSignal) {
		this.nameSignal = nameSignal;
	}
}
