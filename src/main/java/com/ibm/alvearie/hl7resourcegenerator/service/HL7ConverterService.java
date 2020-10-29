/*
 * (C) Copyright IBM Corp. 2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.alvearie.hl7resourcegenerator.service;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import com.google.common.base.Preconditions;
import com.ibm.alvearie.hl7resourcegenerator.exception.DataConversionException;
import io.github.linuxforhealth.hl7.HL7ToFHIRConverter;


public class HL7ConverterService implements Converter<String> {
  private HL7ToFHIRConverter converter;

  public HL7ConverterService() {
    try {
      converter = new HL7ToFHIRConverter(false, BundleType.COLLECTION);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to create HL7ToFHIRConverter instance", e);
    }
  }


  @Override
  public String convertToFHIR(String data) {
    Preconditions.checkArgument(StringUtils.isNotBlank(data), "input data cannot be blank or null");
    try {
      return converter.convert(data);
    } catch (IOException e) {
      throw new DataConversionException("Failure encountered during conversion", e);
    }
  }



}
