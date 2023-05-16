package com.uca.TpBDAvanzadaMarcello.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

import com.uca.TpBDAvanzadaMarcello.entity.DemandaIntervalo;

public class DemandaIntervaloItemProcessor implements ItemProcessor<DemandaIntervalo, DemandaIntervalo> {

  private static final Logger log = LoggerFactory.getLogger(DemandaIntervaloItemProcessor.class);

  @Override
  public DemandaIntervalo process(final @NonNull DemandaIntervalo demInt) throws Exception {
	  log.info("Processor");
	  return demInt;
  }

}