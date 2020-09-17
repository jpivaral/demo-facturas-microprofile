package com.pivaral.facturas.rest;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.pivaral.facturas.model.Factura;
import com.pivaral.facturas.service.FacturaService;
import com.pivaral.facturas.service.UploadService;

@Path("/facturas")
@RequestScoped
public class FacturaEndpoint {
	
	@Inject
	private UploadService uploadService;
	
	@Inject
	private FacturaService facturaService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllFacturas() {
		return facturaService.findAllFacturas();
	}	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createFactura(Factura factura, @Context UriInfo uriInfo) {
		return facturaService.createFactura(factura, uriInfo);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findFacturaById(@PathParam("id") Long id) {
		return facturaService.findFacturaById(id);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateFacturaById(@PathParam("id") Long id, Factura factura) {
		return facturaService.updateFacturaById(id, factura);
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteFacturaById(@PathParam("id") Long id) {
		return facturaService.deleteFacturaById(id);
	}	
	
	@POST
	@Path("/uploadfile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetails ) throws IOException {
		return uploadService.uploadXml(uploadedInputStream, fileDetails);		 
	}
}
