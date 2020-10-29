/*
 * (C) Copyright IBM Corp. 2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.alvearie.hl7resourcegenerator.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;
import com.ibm.alvearie.hl7resourcegenerator.endpoints.TransformHl7Resource;
import com.ibm.alvearie.hl7resourcegenerator.exceptionmappers.RuntimeExceptionMapper;



@Component
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    // Register the Feature for Multipart Uploads (File Upload):
    register(MultiPartFeature.class);
    register(TransformHl7Resource.class);

    register(RuntimeExceptionMapper.class);


    // set properties
    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
  }
}
