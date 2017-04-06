package ras.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="sra_RASTest")
@Inheritance(strategy=InheritanceType.JOINED)
public class ST_RASTest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="tra_idRASTest")
	private int idRASTest;
	
	@ManyToOne
	@JoinColumn(name="tra_idRAS")
	private ST_rasSchemes idRAS;
	
	@ManyToOne
	@JoinColumn(name="tra_idClassification")
	private ST_classRAS idClassification;
	
	@Column(name="tra_dateTimeBeginTest")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeBeginTest;
	
	@Column(name="tra_resultTest")
	private Boolean resultTest;
	
	@Column(name="tra_dateTimeEndTest")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeEndTest;
	
	@Column(name="tra_comment")
	private String comment;

	public ST_RASTest(){
		setIdRASTest(0);
		setIdRAS(null);
		setIdClassification(null);
		setResultTest(false);
		setDateTimeBeginTest(null);
		setDateTimeEndTest(null);
		setComment(null);
	}
	
	public ST_RASTest(int idRASTest, ST_rasSchemes idRAS, ST_classRAS idClassification, Date dateTimeBeginTest){
		setIdRASTest(idRASTest);
		setIdRAS(idRAS);
		setIdClassification(idClassification);
		setResultTest(null);
		setDateTimeEndTest(null);
		setDateTimeBeginTest(dateTimeBeginTest);
	}
	
	public ST_RASTest(ST_rasSchemes idRAS, ST_classRAS idClassification, Date dateTimeBeginTest){
		setIdRAS(idRAS);
		setIdClassification(idClassification);
		setResultTest(null);
		setDateTimeEndTest(null);
		setDateTimeBeginTest(dateTimeBeginTest);
	}
	
	public ST_RASTest(int idRASTest, ST_rasSchemes idRAS, ST_classRAS idClassification, Date dateTimeBeginTest, 
			Boolean resultTest, Date dateTimeEndTest, String comment){
		
		setIdRASTest(idRASTest);
		setIdRAS(idRAS);
		setIdClassification(idClassification);
		setResultTest(resultTest);
		setDateTimeEndTest(dateTimeEndTest);
		setDateTimeBeginTest(dateTimeBeginTest);
		setComment(comment);
	}
	
	public int getIdRASTest() {
		return idRASTest;
	}

	private void setIdRASTest(int idRASTest) {
		this.idRASTest = idRASTest;
	}

	public ST_rasSchemes getIdRAS() {
		return idRAS;
	}

	private void setIdRAS(ST_rasSchemes idRAS) {
		this.idRAS = idRAS;
	}

	public ST_classRAS getIdClassification() {
		return idClassification;
	}

	private void setIdClassification(ST_classRAS idClassification) {
		this.idClassification = idClassification;
	}

	private void setResultTest(Boolean resultTest) {
		this.resultTest = resultTest;
	}

	public Date getDateTimeBeginTest() {
		return dateTimeBeginTest;
	}

	private void setDateTimeBeginTest(Date dateTimeTest) {
		this.dateTimeBeginTest = dateTimeTest;
	}
	
	public Boolean getResultTest() {
		return resultTest;
	}
	
	public Date getDateTimeEndTest() {
		return dateTimeEndTest;
	}

	private void setDateTimeEndTest(Date dateTimeEndTest) {
		this.dateTimeEndTest = dateTimeEndTest;
	}

	public String getComment() {
		return comment;
	}

	private void setComment(String comment) {
		this.comment = comment;
	}
	
}
