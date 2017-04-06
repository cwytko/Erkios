package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="sra_componentsEES")
public class ST_componentsEES implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cee_idComponent")
	private int idComponent;
	
	@Column(name="cee_shortNameComponent")
	private String shortNameComponent;
	
	@Column(name="cee_nameComponent")
	private String nameComponent;

	public ST_componentsEES(){
		this.setIdComponent(0);
		this.setNameComponent("");
		this.setShortNameComponent("");
	}
	
	public int getIdComponent() {
		return idComponent;
	}

	private void setIdComponent(int idComponent) {
		this.idComponent = idComponent;
	}

	public String getShortNameComponent() {
		return shortNameComponent;
	}

	private void setShortNameComponent(String shortNameComponent) {
		this.shortNameComponent = shortNameComponent;
	}

	public String getNameComponent() {
		return nameComponent;
	}

	private void setNameComponent(String nameComponent) {
		this.nameComponent = nameComponent;
	}
}
