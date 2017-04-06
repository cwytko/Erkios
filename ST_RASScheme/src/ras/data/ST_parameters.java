package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="sra_parameters")
public class ST_parameters implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="spa_idParameter")
	private int idParameter;
	
	@Column(name="spa_nameParameter")
	private String nameParameter;

	public int getIdParameter() {
		return idParameter;
	}

	private void setIdParameter(int idParameter) {
		this.idParameter = idParameter;
	}

	public String getNameParameter() {
		return nameParameter;
	}

	private void setNameParameter(String nameParameter) {
		this.nameParameter = nameParameter;
	}
	
	public ST_parameters(){
		setIdParameter(0);
		setNameParameter(null);
	}
}
