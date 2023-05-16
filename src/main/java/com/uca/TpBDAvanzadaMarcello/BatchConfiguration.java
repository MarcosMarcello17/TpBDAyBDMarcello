package com.uca.TpBDAvanzadaMarcello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.uca.TpBDAvanzadaMarcello.entity.DemandaIntervalo;
import com.uca.TpBDAvanzadaMarcello.listener.JobCompletionNotificationListener;
import com.uca.TpBDAvanzadaMarcello.processor.DemandaIntervaloItemProcessor;
import com.uca.TpBDAvanzadaMarcello.repository.DemandaIntervaloRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfiguration{
	
	private String apiUrl = "https://api.cammesa.com/demanda-svc/demanda/ObtieneDemandaYTemperaturaRegionByFecha?fecha=2023-01-01&id_region=1002";
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	
	@Autowired
	private DemandaIntervaloRepository demIntRepo;
	
	@Bean
	public ItemReader<DemandaIntervalo> reader(){
		return new RESTDemandaReader(apiUrl, restTemplateBuilder);
	}

	@Bean
	public DemandaIntervaloItemProcessor processor() {
	  return new DemandaIntervaloItemProcessor();
	}

	@Bean
	public RepositoryItemWriter<DemandaIntervalo> writer() {
		RepositoryItemWriter<DemandaIntervalo> wr = new RepositoryItemWriter<>();
		wr.setMethodName("save");
		wr.setRepository(demIntRepo);
		return wr;
	}
	
	@Bean
	public Job importUserJob(JobRepository jobRepository,
	    JobCompletionNotificationListener listener, Step step1) {
	  return new JobBuilder("importUserJob", jobRepository)
	    .incrementer(new RunIdIncrementer())
	    .listener(listener)
	    .flow(step1)
	    .end()
	    .build();
	}

	@Bean
	public Step step1(JobRepository jobRepository,
	    PlatformTransactionManager transactionManager) {
	  return new StepBuilder("step1", jobRepository)
	    .<DemandaIntervalo, DemandaIntervalo> chunk(12, transactionManager)
	    .reader(reader())
	    .processor(processor())
	    .writer(writer())
	    .build();
	}
	
}
