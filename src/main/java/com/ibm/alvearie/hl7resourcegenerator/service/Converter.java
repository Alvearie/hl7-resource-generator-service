/*
 * (C) Copyright IBM Corp. 2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.alvearie.hl7resourcegenerator.service;

/**
 * Converts the data from input format to FHIR resource The generated output is json representation
 * of the FHIR bundle resource
 * 
 * @param <T>
 *
 * @author pbhallam
 */
@FunctionalInterface
public interface Converter<T> {
  String convertToFHIR(T data);
}
