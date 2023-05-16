package com.uca.TpBDAvanzadaMarcello.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uca.TpBDAvanzadaMarcello.service.CammesaService;

@RestController
@RequestMapping("api/")
public class APIController {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@Autowired
	private CammesaService cammesaService;
	
	
	APIController(CammesaService cammesaService){
		this.cammesaService = cammesaService;
	}
	
	@GetMapping("/health")
	public ResponseEntity<?> getHealth(){
		return new ResponseEntity<>("Service working",HttpStatus.OK);
	}
	
	@GetMapping("/diaConMayorDemanda")
	public ResponseEntity<?> obtenerDiaMayorDemandaPorRegion(){
		return new ResponseEntity<>(cammesaService.obtenerDiasMayorDemanda(), HttpStatus.OK);
	}
	
	@GetMapping("/demandaFeriadoMasCercano")
	public ResponseEntity<?> obtenerDemFeriado(@RequestParam(value="fecha", defaultValue="2023-01-01") String fecha) {
		return new ResponseEntity<>(cammesaService.demandaFeriadoCercano(fecha), HttpStatus.OK);
	}
	
	@DeleteMapping("/borrarRegion")
	public ResponseEntity<?> borrarRegion(@RequestParam(value = "idRegion", defaultValue="0") String idRegion) {
		return new ResponseEntity<>(cammesaService.borrarRegion(idRegion), HttpStatus.OK)	;
	}
	
	@PostMapping("/demandaYTemperaturaDiario")
	public ResponseEntity<?> descargarDemandaYTemp(@RequestParam(value = "idRegion", defaultValue="1002") String idRegion, @RequestParam(value= "fecha", defaultValue="2023-01-01") String fecha) {
		return new ResponseEntity<>(cammesaService.anadirDemandaYTemperatura(idRegion, fecha),HttpStatus.OK);
	}
	
	@PostMapping("/actualizarRegiones")
	public ResponseEntity<?> actualizarInfoRegiones(){
		return new ResponseEntity<>(this.cammesaService.anadirRegiones(), HttpStatus.OK);
	}
	
	@PostMapping("/demandaYTemperaturaMensual")
	public ResponseEntity<?> descargarDemandaMensual(@RequestParam(value = "mes", defaultValue = "01") String mes, @RequestParam(value="idRegion", defaultValue="1002") String idRegion) throws Exception {
		JobParameters Parameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis())
				.toJobParameters();
		try {
			jobLauncher.run(job, Parameters);
		}catch(JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e){
			e.printStackTrace();
		}
		return new ResponseEntity<>("Batch Process started!!", HttpStatus.OK);
	}
}