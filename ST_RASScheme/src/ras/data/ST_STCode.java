package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name="sra_STCode")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ST_STCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="stc_idSTCode")
	private int idSTCode;
	
	@Column(name = "stc_codeName")
	private String codeName;
	
	@Column(name = "stc_shortCodeName")
	private String shortCodeName;
	
	@Column(name = "stc_comment")
	private String comment;
	
	public int getIdSTCode() {
		return idSTCode;
	}

	private void setIdSTCode(int idSTCode) {
		this.idSTCode = idSTCode;
	}

	public String getCodeName() {
		return codeName;
	}

	private void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getShortCodeName() {
		return shortCodeName;
	}

	private void setShortCodeName(String shortCodeName) {
		this.shortCodeName = shortCodeName;
	}

	public String getComment() {
		return comment;
	}

	private void setComment(String comment) {
		this.comment = comment;
	}

	public ST_STCode(){
		setIdSTCode(0);
		setCodeName(null);
		setShortCodeName(null);
		setComment(null);
	}
}
