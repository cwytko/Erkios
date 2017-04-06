package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_RASTestLink")
public class ST_RASTestLink implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="rtl_idRASTestLink")
	private int idRASTestLink;
	
	@ManyToOne
	@JoinColumn(name="rtl_idLinkEES")
	private ST_linkEES idLinkEES;
	
	@ManyToOne
	@JoinColumn(name="rtl_idRASTest")
	private ST_RASTest idRASTest;
	
	@Column(name="rtl_idTypeAction")
	private int idTypeAction;
	
	@Column(name="rtl_resultLink")
	private Boolean resultLink;
	
	@Column(name="rtl_stateComponent")
	private Boolean stateComponent;
	
	@Column(name="rtl_comment")
	private String comment;
	
	public ST_RASTestLink(){
		setIdLinkEES(null);
		setIdRASTestLink(0);
		setIdRASTest(null);
		setIdTypeAction(0);
		setResultLink(null);
		setStateComponent(null);
	}
	
	public ST_RASTestLink(ST_linkEES linkEES, ST_RASTest rasTest, int idTypeAction, Boolean resultLink, Boolean stateComponent, String Comment){
		setIdLinkEES(linkEES);
		setIdRASTest(rasTest);
		setIdTypeAction(idTypeAction);
		setResultLink(resultLink);
		setStateComponent(stateComponent);
		setComment(Comment);
	}

	public int getIdRASTestLink() {
		return idRASTestLink;
	}

	private void setIdRASTestLink(int idRASTestLink) {
		this.idRASTestLink = idRASTestLink;
	}

	public ST_linkEES getIdLinkEES() {
		return idLinkEES;
	}

	private void setIdLinkEES(ST_linkEES idLinkEES) {
		this.idLinkEES = idLinkEES;
	}

	public ST_RASTest getIdRASTest() {
		return idRASTest;
	}

	private void setIdRASTest(ST_RASTest idRASTest) {
		this.idRASTest = idRASTest;
	}

	public int getIdTypeAction() {
		return idTypeAction;
	}

	private void setIdTypeAction(int idTypeAction) {
		this.idTypeAction = idTypeAction;
	}

	public Boolean getResultLink() {
		return resultLink;
	}

	private void setResultLink(Boolean resultLink) {
		this.resultLink = resultLink;
	}

	public Boolean getStateComponent() {
		return stateComponent;
	}

	private void setStateComponent(Boolean stateComponent) {
		this.stateComponent = stateComponent;
	}

	public String getComment() {
		return comment;
	}

	private void setComment(String comment) {
		this.comment = comment;
	}
}