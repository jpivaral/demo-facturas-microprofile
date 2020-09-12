package com.pivaral.facturas.rest;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.pivaral.facturas.service.UploadService;

@Path("/facturas")
@RequestScoped
public class FacturaEndpoint {
	
	@Inject
	private UploadService uploadService;
	
	 @POST
	 @Path("/uploadfile")
	 @Consumes(MediaType.MULTIPART_FORM_DATA)
	 public Response uploadFile(
			 @FormDataParam("file") InputStream uploadedInputStream,
			 @FormDataParam("file") FormDataContentDisposition fileDetails ) throws IOException {
		 return uploadService.uploadXml(uploadedInputStream, fileDetails);		 
	 }
}
