/*
 * (C) Copyright IBM Corp. 2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.alvearie.hl7resourcegenerator.endpoints;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.test.JerseyTest;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.ResourceType;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ibm.alvearie.hl7resourcegenerator.config.JerseyConfig;
import com.ibm.alvearie.hl7resourcegenerator.service.HL7ConverterService;
import io.github.linuxforhealth.fhir.FHIRContext;


@RunWith(SpringJUnit4ClassRunner.class)
@EnableConfigurationProperties
public class ConverterResourceTest extends JerseyTest {

  private static final String UPLOAD_FILE_NAME = "src/test/resources/sample.hl7";

  @Configuration
  static class ContextConfiguration {
    @Bean
    public HL7ConverterService getHL7Converter() {
      HL7ConverterService con = new HL7ConverterService();
      return con;
    }

  }


  @Override
  protected Application configure() {

    ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
    return new JerseyConfig().property("contextConfig", context)
        .register(TransformHl7Resource.class).register(MultiPartFeature.class);

  }

  @Override
  public void configureClient(ClientConfig config) {
    config.register(MultiPartFeature.class);
  }

  @Test
  public void valid_hl7_message_is_converted_to_fhir_bundle_resource()
      throws IOException, JSONException {

    MultiPart multiPart = new MultiPart();
    multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
    FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file", new File(UPLOAD_FILE_NAME),
        MediaType.APPLICATION_OCTET_STREAM_TYPE);
    multiPart.bodyPart(fileDataBodyPart);
    Response response = target("/hl7/transformation").request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA));
    assertThat(response.getStatus()).isEqualTo(200);
    String responseEntity = response.readEntity(String.class);
    FHIRContext context = new FHIRContext();
    IBaseResource bundleResource = context.getParser().parseResource(responseEntity);
    assertThat(bundleResource).isNotNull();
    Bundle b = (Bundle) bundleResource;
    assertThat(b.getId()).isNotNull();
    assertThat(b.getMeta().getLastUpdated()).isNotNull();

    List<BundleEntryComponent> e = b.getEntry();

    List<Resource> messageHeader =
        e.stream().filter(v -> ResourceType.MessageHeader == v.getResource().getResourceType())
            .map(BundleEntryComponent::getResource).collect(Collectors.toList());
    assertThat(messageHeader).hasSize(1);

    List<Resource> patientResource =
        e.stream().filter(v -> ResourceType.Patient == v.getResource().getResourceType())
            .map(BundleEntryComponent::getResource).collect(Collectors.toList());
    assertThat(patientResource).hasSize(1);

    List<Resource> encounterResource =
        e.stream().filter(v -> ResourceType.Encounter == v.getResource().getResourceType())
            .map(BundleEntryComponent::getResource).collect(Collectors.toList());
    assertThat(encounterResource).hasSize(1);
    List<Resource> obsResource =
        e.stream().filter(v -> ResourceType.Observation == v.getResource().getResourceType())
            .map(BundleEntryComponent::getResource).collect(Collectors.toList());
    assertThat(obsResource).hasSize(1);
    List<Resource> pracResource =
        e.stream().filter(v -> ResourceType.Practitioner == v.getResource().getResourceType())
            .map(BundleEntryComponent::getResource).collect(Collectors.toList());
    assertThat(pracResource).hasSize(4);

    List<Resource> allergyResources =
        e.stream().filter(v -> ResourceType.AllergyIntolerance == v.getResource().getResourceType())
            .map(BundleEntryComponent::getResource).collect(Collectors.toList());
    assertThat(allergyResources).hasSize(2);

  }


}
