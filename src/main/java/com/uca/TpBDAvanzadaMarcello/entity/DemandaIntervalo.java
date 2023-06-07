package com.uca.TpBDAvanzadaMarcello.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public class DemandaIntervalo {

	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name= "region_id")
	@OneToMany(mappedBy= "DemandaIntervalo", fetch = FetchType.EAGER)
	private Region region;
	@Getter
	@Setter
	private int dem;
	@Getter
	@Setter
	private int demAyer;
	@Getter
	@Setter
	private int demHoy;
	@Getter
	@Setter
	private int demPrevista;
	@Getter
	@Setter
	private int demSemanaAnt;
	@Getter
	@Setter
	private Date fecha;
	@Getter
	@Setter
	private int temp;
	@Getter
	@Setter
	private int tempAyer;
	@Getter
	@Setter
	private int tempHoy;
	@Getter
	@Setter
	private int tempPrevista;
	@Getter
	@Setter
	private int tempSemanaAnt;

	public DemandaIntervalo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public int getDem() {
		return dem;
	}

	public void setDem(int dem) {
		this.dem = dem;
	}

	public int getDemAyer() {
		return demAyer;
	}

	public void setDemAyer(int demAyer) {
		this.demAyer = demAyer;
	}

	public int getDemHoy() {
		return demHoy;
	}

	public void setDemHoy(int demHoy) {
		this.demHoy = demHoy;
	}

	public int getDemPrevista() {
		return demPrevista;
	}

	public void setDemPrevista(int demPrevista) {
		this.demPrevista = demPrevista;
	}

	public int getDemSemanaAnt() {
		return demSemanaAnt;
	}

	public void setDemSemanaAnt(int demSemanaAnt) {
		this.demSemanaAnt = demSemanaAnt;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getTempAyer() {
		return tempAyer;
	}

	public void setTempAyer(int tempAyer) {
		this.tempAyer = tempAyer;
	}

	public int getTempHoy() {
		return tempHoy;
	}

	public void setTempHoy(int tempHoy) {
		this.tempHoy = tempHoy;
	}

	public int getTempPrevista() {
		return tempPrevista;
	}

	public void setTempPrevista(int tempPrevista) {
		this.tempPrevista = tempPrevista;
	}

	public int getTempSemanaAnt() {
		return tempSemanaAnt;
	}

	public void setTempSemanaAnt(int tempSemanaAnt) {
		this.tempSemanaAnt = tempSemanaAnt;
	}
	
	
}
