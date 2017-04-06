package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="sra_directionLink")
public class ST_directionLink implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="dil_idDirLink")
	private int idDirectionLink;

	@Column(name="dil_directionLink")
	private String directionLink;

	public ST_directionLink(){
		this.setDirectionLink("");
		this.setIdDirectionLink(0);
	}
	
	public int getIdDirectionLink() {
		return idDirectionLink;
	}

	private void setIdDirectionLink(int idDirectionLink) {
		this.idDirectionLink = idDirectionLink;
	}

	public String getDirectionLink() {
		return directionLink;
	}

	private void setDirectionLink(String directionLink) {
		this.directionLink = directionLink;
	}
}
