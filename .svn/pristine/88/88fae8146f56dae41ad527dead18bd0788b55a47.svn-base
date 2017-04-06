package ras.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_RASTestDisableEnableComponents")
public class ST_RASTestDisableEnableComponent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="dec_idDisableEnableComponent")
	private int idDisableEnableComponent;
	
	@ManyToOne
	@JoinColumn(name="dec_idRAS")
	private ST_rasSchemes idRAS;
	
	@Column(name="dec_idAction")
	private int idAction;
	
	@ManyToOne
	@JoinColumn(name="dec_idRASTest")
	private ST_RASTest idRASTest;
	
	
	@Column(name="dec_disableEnableSensor")
	private Boolean disableEnableSensor;
	
	@Column(name="dec_datetimeSensor")
	private Date datetimeSensor;
	
	@Column(name="dec_disableEnableActuator")
	private Boolean disableEnableActuator;
	
	@Column(name="dec_datetimeActuator")
	private Date datetimeActuator;
	
	@Column(name="dec_resultDisableEnableComponent")
	private Boolean resultDisableEnableComponent;
	
	@Column(name="dec_comment")
	private String comment;
	
	public ST_RASTestDisableEnableComponent(){
		setIdDisableEnableComponent(0);
		setIdRAS(null);
		setIdAction(0);
		setIdRASTest(null);
		setDisableEnableSensor(false);
		setDatetimeSensor(null);
		setDisableEnableActuator(false);
		setDatetimeActuator(null);
		setResultDisableEnableComponent(false);
		setComment("");
	}
	
	public ST_RASTestDisableEnableComponent(ST_rasSchemes rasScheme, int idAction, ST_RASTest rasTest, Boolean disableEnableSensor,
			Date dateTimeSensor, Boolean disableEnableActuator, Date dateTimeActuator, Boolean resultDisableEnableComponent, String comment){
		setIdRAS(rasScheme);
		setIdAction(idAction);
		setIdRASTest(rasTest);
		setDisableEnableSensor(disableEnableSensor);
		setDatetimeSensor(dateTimeSensor);
		setDisableEnableActuator(disableEnableActuator);
		setDatetimeActuator(dateTimeActuator);
		setResultDisableEnableComponent(resultDisableEnableComponent);
		setComment(comment);
	}
	
	public ST_RASTestDisableEnableComponent(Boolean disableEnableSensor, Date dateTimeSensor, Boolean disableEnableActuator, 
			Date dateTimeActuator){
		setDisableEnableSensor(disableEnableSensor);
		setDatetimeSensor(dateTimeSensor);
		setDisableEnableActuator(disableEnableActuator);
		setDatetimeActuator(dateTimeActuator);
	}
	
	public int getIdDisableEnableComponent() {
		return idDisableEnableComponent;
	}

	private void setIdDisableEnableComponent(int idDisableEnableComponent) {
		this.idDisableEnableComponent = idDisableEnableComponent;
	}

	public ST_rasSchemes getIdRAS() {
		return idRAS;
	}

	private void setIdRAS(ST_rasSchemes idRAS) {
		this.idRAS = idRAS;
	}

	public int getIdAction() {
		return idAction;
	}

	private void setIdAction(int idAction) {
		this.idAction = idAction;
	}

	public ST_RASTest getIdRASTest() {
		return idRASTest;
	}

	private void setIdRASTest(ST_RASTest idRASTest) {
		this.idRASTest = idRASTest;
	}

	public Boolean getDisableEnableSensor() {
		return disableEnableSensor;
	}

	private void setDisableEnableSensor(Boolean disableEnableSensor) {
		this.disableEnableSensor = disableEnableSensor;
	}

	public Date getDatetimeSensor() {
		return datetimeSensor;
	}

	private void setDatetimeSensor(Date datetimeSensor) {
		this.datetimeSensor = datetimeSensor;
	}
	
	public Boolean getDisableEnableActuator() {
		return disableEnableActuator;
	}

	private void setDisableEnableActuator(Boolean disableEnableActuator) {
		this.disableEnableActuator = disableEnableActuator;
	}

	public Date getDatetimeActuator() {
		return datetimeActuator;
	}

	private void setDatetimeActuator(Date datetimeActuator) {
		this.datetimeActuator = datetimeActuator;
	}

	public Boolean getResultDisableEnableComponent() {
		return resultDisableEnableComponent;
	}

	private void setResultDisableEnableComponent(Boolean resultDisableEnableComponent) {
		this.resultDisableEnableComponent = resultDisableEnableComponent;
	}

	public String getComment() {
		return comment;
	}

	private void setComment(String comment) {
		this.comment = comment;
	}	
}
