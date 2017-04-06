package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_parametersRAS")
public class ST_parametersRAS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pra_idParameter")
	private int idParameter;
	
	@ManyToOne
	@JoinColumn(name="pra_idRAS")
	private ST_rasSchemes idRAS;
	
	@Column(name="pra_paramContingencies")
	private boolean paramContingencies;

	public ST_parametersRAS(){
		this.setparamContingencies(false);
		this.setIdParameter(0);
		this.setIdRAS(null);
		
	}
	
	public int getIdParameter() {
		return idParameter;
	}

	private void setIdParameter(int idParameter) {
		this.idParameter = idParameter;
	}

	public ST_rasSchemes getIdRAS() {
		return idRAS;
	}

	private void setIdRAS(ST_rasSchemes idRAS) {
		this.idRAS = idRAS;
	}

	public boolean isparamContingencies() {
		return paramContingencies;
	}

	private void setparamContingencies(boolean paramContingencies) {
		this.paramContingencies = paramContingencies;
	}
	
	
}
