package com.uca.TpBDAvanzadaMarcello.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.uca.TpBDAvanzadaMarcello.entity.Region;
import com.uca.TpBDAvanzadaMarcello.repository.DemandaIntervaloRepository;
import com.uca.TpBDAvanzadaMarcello.repository.RegionRepository;
import com.uca.TpBDAvanzadaMarcello.entity.DemandaEnDia;
import com.uca.TpBDAvanzadaMarcello.entity.DemandaIntervalo;

@Service
public class CammesaService {
	
	private final RestTemplateBuilder restTemplateBuilder;
	private final RegionRepository regionRepo;
	private final DemandaIntervaloRepository demandaIntervaloRepo;
	
	CammesaService(RestTemplateBuilder restTemplateBuilder, RegionRepository regionRepo, DemandaIntervaloRepository demandaIntervaloRepo){
		this.restTemplateBuilder = restTemplateBuilder;
		this.regionRepo = regionRepo;
		this.demandaIntervaloRepo = demandaIntervaloRepo;
	}
	
	public String anadirRegiones() {
		regionRepo.deleteAll();
        ResponseEntity<Region[]> response = restTemplateBuilder.build().getForEntity("https://api.cammesa.com/demanda-svc/demanda/RegionesDemanda", Region[].class);
        List<Region> regiones =  Arrays.asList(response.getBody());
        for(Region region: regiones) {
        	regionRepo.save(region);
        }
        return "Regiones Actualizadas";
	}

	@SuppressWarnings("null")
	public String demandaFeriadoCercano(String fecha) {
		LocalDate fechaIng = LocalDate.parse(fecha);
		if(fechaIng.compareTo(LocalDate.now()) >= 0) {
			return "Dia Invalido";
		}
		while(!restTemplateBuilder.build().getForObject("https://api.cammesa.com/demanda-svc/demanda/EsDiaFeriado?fecha=" + fechaIng, Boolean.class)) {
			fechaIng = fechaIng.minusDays(1);
		}
		return Integer.toString(this.demandaPromedioPaisByFecha(fechaIng.toString()));
	}
	
	private int demandaPromedioPaisByFecha(String fecha) {
        List<DemandaIntervalo> demFecha= demandaIntervaloRepo.selectAvgDemandaOfDate(fecha);
        Integer demTot = 0;
        Integer tot = 0;
        for(DemandaIntervalo intervalo: demFecha) {
        	demTot += intervalo.getDem();
        	tot++;
        }
        return (demTot / tot);
    }
	
	public String anadirDemandaYTemperatura(String idRegion, String fecha) {
		ResponseEntity<DemandaIntervalo[]> response = restTemplateBuilder.build().getForEntity("https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?id_region="+idRegion+"&fecha="+ fecha, DemandaIntervalo[].class);
        List<DemandaIntervalo> demandaPorIntervalos =  Arrays.asList(response.getBody());
        for(DemandaIntervalo intervalo: demandaPorIntervalos) {
        	intervalo.setRegion(regionRepo.findById(Long.parseLong(idRegion)).get());
        	demandaIntervaloRepo.save(intervalo);
        }
        return "Añadido";
	}

	public String borrarRegion(String idRegion) {
		regionRepo.deleteRegionWithID(idRegion);
		return "Region Eliminada";
	}

	public List<DemandaEnDia> obtenerDiasMayorDemanda() {
		return demandaIntervaloRepo.obtenerDiaMayorDemandaPorRegion();
	}

}
