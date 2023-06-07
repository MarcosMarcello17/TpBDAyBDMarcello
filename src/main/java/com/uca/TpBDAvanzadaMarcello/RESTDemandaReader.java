package com.uca.TpBDAvanzadaMarcello;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import com.uca.TpBDAvanzadaMarcello.entity.DemandaIntervalo;
import com.uca.TpBDAvanzadaMarcello.entity.Region;
import com.uca.TpBDAvanzadaMarcello.repository.RegionRepository;

public class RESTDemandaReader implements ItemReader<DemandaIntervalo> {
	private final String apiUrl;
	private final RestTemplateBuilder restTemplateBuilder;
	private Integer nextDemandaIndex;
	private String regionID;
	@Autowired
	private RegionRepository regionRepo;
	private List<DemandaIntervalo> demandaData;
	
	RESTDemandaReader(String regionID, RestTemplateBuilder restTemplateBuilder){
		this.apiUrl = "https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?fecha=2023-01-01&id_region=" + regionID;
		this.restTemplateBuilder = restTemplateBuilder;
		this.nextDemandaIndex = 0;
	}

	@Override
	@Nullable
	public DemandaIntervalo read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (demandaDataIsNotInitialized()){
            demandaData = fetchDemandaDataFromAPI();
            Optional<Region> reg = regionRepo.findById(Long.parseLong(regionID));
            for(DemandaIntervalo intervalo: demandaData) {
            	intervalo.setRegion(reg.get());
            	reg.get().addDem(intervalo);
            }
        }
 
        DemandaIntervalo nextDemanda = null;
 
        if (nextDemandaIndex < demandaData.size()) {
            nextDemanda = demandaData.get(nextDemandaIndex);
            nextDemandaIndex++;
        }
        else {
            nextDemandaIndex = 0;
            demandaData = null;
        }
 
        return nextDemanda;
    }
 
    private boolean demandaDataIsNotInitialized() {
        return this.demandaData == null;
    }
 
    private List<DemandaIntervalo> fetchDemandaDataFromAPI() {
        ResponseEntity<DemandaIntervalo[]> response = restTemplateBuilder.build().getForEntity(apiUrl,DemandaIntervalo[].class);
        return Arrays.asList(response.getBody());
    }

}
