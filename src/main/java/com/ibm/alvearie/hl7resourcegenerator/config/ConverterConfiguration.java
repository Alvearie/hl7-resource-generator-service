/*
 * (C) Copyright IBM Corp. 2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.alvearie.hl7resourcegenerator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.ibm.alvearie.hl7resourcegenerator.service.HL7ConverterService;


@Configuration
@PropertySource("classpath:application.properties")
public class ConverterConfiguration {
  private static final Logger LOGGER = LoggerFactory.getLogger(ConverterConfiguration.class);


  @Bean
  public HL7ConverterService getHL7Converter() {
    LOGGER.info("Creating instance of HL7Converter");
    return new HL7ConverterService();

  }



}
