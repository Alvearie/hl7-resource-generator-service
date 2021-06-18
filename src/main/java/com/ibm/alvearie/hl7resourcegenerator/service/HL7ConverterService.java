/*
 * (C) Copyright IBM Corp. 2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.alvearie.hl7resourcegenerator.service;

import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.hl7.ConverterOptions;
import io.github.linuxforhealth.hl7.HL7ToFHIRConverter;


public class HL7ConverterService implements Converter<String> {
  private HL7ToFHIRConverter converter;
  private static final ConverterOptions OPTIONS =
      new ConverterOptions.Builder().withBundleType(BundleType.COLLECTION).build();

  public HL7ConverterService() {
    converter = new HL7ToFHIRConverter();
  }


  @Override
  public String convertToFHIR(String data) {
    Preconditions.checkArgument(StringUtils.isNotBlank(data), "input data cannot be blank or null");

    return converter.convert(data, OPTIONS);

  }



}
