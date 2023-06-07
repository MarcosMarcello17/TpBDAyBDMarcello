package com.uca.TpBDAvanzadaMarcello.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Region {
	
	@Getter
	@Setter
	@Column
	@Id
	private int id;
	
	@Getter
	@Setter
	private int idElemento;
	
	@Getter
	@Setter
	@Column
	private String desde;
	@Getter
	@Setter
	@Column
	private String hasta;
	@Getter
	@Setter
	@Column
	private int idOrigen;
	@Getter
	@Setter
	@Column
	private int idPadre;
	@Getter
	@Setter
	@Column
	private int idRge;
	@Getter
	@Setter
	@Column
	private String info;
	@Getter
	@Setter
	@Column
	private int maxEscala;
	@Getter
	@Setter
	@Column
	private int minEscala;
	@Getter
	@Setter
	@Column
	private String modificado;
	@Getter
	@Setter
	@Column
	private String nombre;
	@Getter
	@Setter
	@Column
	private String subTipo;
	
	public Region() {
		
	}

	public int getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(int idElemento) {
		this.idElemento = idElemento;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(int idOrigen) {
		this.idOrigen = idOrigen;
	}

	public int getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

	public int getIdRge() {
		return idRge;
	}

	public void setIdRge(int idRge) {
		this.idRge = idRge;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getMaxEscala() {
		return maxEscala;
	}

	public void setMaxEscala(int maxEscala) {
		this.maxEscala = maxEscala;
	}

	public int getMinEscala() {
		return minEscala;
	}

	public void setMinEscala(int minEscala) {
		this.minEscala = minEscala;
	}

	public String getModificado() {
		return modificado;
	}

	public void setModificado(String modificado) {
		this.modificado = modificado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSubTipo() {
		return subTipo;
	}

	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
	}
	
}
