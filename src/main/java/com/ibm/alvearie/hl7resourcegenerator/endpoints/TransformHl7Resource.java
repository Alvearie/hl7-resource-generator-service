/*
 * (C) Copyright IBM Corp. 2020
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.ibm.alvearie.hl7resourcegenerator.endpoints;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ibm.alvearie.hl7resourcegenerator.exception.DataConversionException;
import com.ibm.alvearie.hl7resourcegenerator.service.HL7ConverterService;

@Component
@Path("/hl7")
public class TransformHl7Resource {

  @Autowired
  private HL7ConverterService converter;




  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Path("/transformation")
  @Produces(MediaType.APPLICATION_JSON)
  public Response convertFile(@Valid @FormDataParam("file") File file,
      @FormDataParam("file") FormDataContentDisposition fileDetails) {
    if (file != null) {
      try {
        String data = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String result = converter.convertToFHIR(data);
        return Response.ok(result).build();
      } catch (IOException e) {
        throw new DataConversionException("IOException encountered. ", e);

      }
    } else {
      return Response.status(Status.BAD_REQUEST.getStatusCode(), "No input File to process")
          .build();
    }
  }
  
  
  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  @Path("/transformation")
  @Produces(MediaType.APPLICATION_JSON)
  public Response convertContent(@NotEmpty String hl7Content) {
    if (hl7Content != null) {
      String result = converter.convertToFHIR(hl7Content);
        return Response.ok(result).build();

    } else {
      return Response
          .status(Status.BAD_REQUEST.getStatusCode(), "No input message data to process.")
          .build();
    }
  }



}
