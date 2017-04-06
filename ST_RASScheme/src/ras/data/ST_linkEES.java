package ras.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="sra_linkEES")
public class ST_linkEES implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cee_idLink")
	private int idLink;
	
	@ManyToOne
	@JoinColumn(name="cee_source")
	private ST_componentsEES linkSource;

	@ManyToOne
	@JoinColumn(name="cee_destination")
	private ST_componentsEES linkDestination;

	@ManyToOne
	@JoinColumn(name="cee_directionLink")
	private ST_directionLink idDirectionLink;
	
	@ManyToOne
	@JoinColumn(name="cee_idSignal")
	private ST_typeSignal idSignal;

	public ST_linkEES(){
		this.setIdDirectionLink(null);
		this.setIdLink(0);
		this.setIdSignal(null);
		this.setlinkSource(null);
		this.setlinkDestination(null);
	}
	
	public int getIdLink() {
		return idLink;
	}

	private void setIdLink(int idLink) {
		this.idLink = idLink;
	}

	public ST_componentsEES getlinkSource() {
		return linkSource;
	}

	private void setlinkSource(ST_componentsEES linkSource) {
		this.linkSource = linkSource;
	}

	public ST_componentsEES getlinkDestination() {
		return linkDestination;
	}

	private void setlinkDestination(ST_componentsEES linkDestination) {
		this.linkDestination = linkDestination;
	}

	public ST_directionLink getIdDirectionLink() {
		return idDirectionLink;
	}

	private void setIdDirectionLink(ST_directionLink idDirectionLink) {
		this.idDirectionLink = idDirectionLink;
	}

	public ST_typeSignal getIdSignal() {
		return idSignal;
	}

	private void setIdSignal(ST_typeSignal idSignal) {
		this.idSignal = idSignal;
	}
}
