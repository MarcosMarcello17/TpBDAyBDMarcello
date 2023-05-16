package com.uca.TpBDAvanzadaMarcello.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
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
	
	public ResponseEntity<?> anadirRegiones() {
		regionRepo.deleteAll();
        ResponseEntity<Region[]> response = restTemplateBuilder.build().getForEntity("https://api.cammesa.com/demanda-svc/demanda/RegionesDemanda", Region[].class);
        List<Region> regiones =  Arrays.asList(response.getBody());
        for(Region region: regiones) {
        	regionRepo.save(region);
        }
        return new ResponseEntity<>("Regiones Actualizadas", HttpStatus.OK);
	}

	@SuppressWarnings("null")
	public ResponseEntity<?> demandaFeriadoCercano(String fecha) {
		LocalDate fechaIng = LocalDate.parse(fecha);
		if(fechaIng.compareTo(LocalDate.now()) >= 0) {
			return new ResponseEntity<>("Dia Invalido", HttpStatus.BAD_REQUEST);
		}
		while(!restTemplateBuilder.build().getForObject("https://api.cammesa.com/demanda-svc/demanda/EsDiaFeriado?fecha=" + fechaIng, Boolean.class)) {
			fechaIng = fechaIng.minusDays(1);
		}
		return new ResponseEntity<>(Integer.toString(this.demandaPromedioPaisByFecha(fechaIng.toString())),HttpStatus.OK);
	}
	
	private int demandaPromedioPaisByFecha(String fecha) {
        List<DemandaIntervalo> demFecha= demandaIntervaloRepo.selectAvgDemandaOfDate(fecha);
        Integer demTot = 0;
        Integer tot = 0;
        for(DemandaIntervalo intervalo: demFecha) {
        	demTot += intervalo.getDem();
        	tot++;
        }
        if(tot == 0) {
        	return 0;
        }else {
        	return (demTot / tot);
        }
    }
	
	public ResponseEntity<?> anadirDemandaYTemperatura(String idRegion, String fecha) {
		ResponseEntity<DemandaIntervalo[]> response = restTemplateBuilder.build().getForEntity("https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?id_region="+idRegion+"&fecha="+ fecha, DemandaIntervalo[].class);
        List<DemandaIntervalo> demandaPorIntervalos =  Arrays.asList(response.getBody());
        if(regionRepo.findAll().isEmpty()) {
        	return new ResponseEntity<>("Regiones no añadidas", HttpStatus.BAD_REQUEST);
        }
        Optional<Region> reg = regionRepo.findById(Long.parseLong(idRegion));
        if (reg.isEmpty()) {
        	return new ResponseEntity<>("Region ID no encontrado", HttpStatus.NOT_FOUND);
        }
        for(DemandaIntervalo intervalo: demandaPorIntervalos) {
        	intervalo.setRegion(reg.get());
        	demandaIntervaloRepo.save(intervalo);
        }
        return new ResponseEntity<>("Añadido", HttpStatus.OK);
	}

	public ResponseEntity<?> borrarRegion(String idRegion) {
		regionRepo.deleteRegionWithID(idRegion);
		return new ResponseEntity<>("Region Eliminada", HttpStatus.OK);
	}

	public ResponseEntity<?> obtenerDiasMayorDemanda() {
		return new ResponseEntity<>(demandaIntervaloRepo.obtenerDiaMayorDemandaPorRegion(), HttpStatus.OK);
	}

}
