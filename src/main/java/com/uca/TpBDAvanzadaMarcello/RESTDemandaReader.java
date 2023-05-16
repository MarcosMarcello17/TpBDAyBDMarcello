package com.uca.TpBDAvanzadaMarcello;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import com.uca.TpBDAvanzadaMarcello.entity.DemandaIntervalo;

public class RESTDemandaReader implements ItemReader<DemandaIntervalo> {
	private final String apiUrl;
	private final RestTemplateBuilder restTemplateBuilder;
	private Integer nextDemandaIndex;
	private List<DemandaIntervalo> demandaData;
	
	RESTDemandaReader(String apiUrl, RestTemplateBuilder restTemplateBuilder){
		this.apiUrl = apiUrl;
		this.restTemplateBuilder = restTemplateBuilder;
		this.nextDemandaIndex = 0;
	}

	@Override
	@Nullable
	public DemandaIntervalo read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (demandaDataIsNotInitialized()){
            demandaData = fetchDemandaDataFromAPI();
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
