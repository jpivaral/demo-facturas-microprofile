package com.pivaral.facturas.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.pivaral.facturas.model.sat.v1.GTDocumento;
import com.pivaral.facturas.utils.FileHelper;

@RequestScoped
public class UploadService {
	
	@Inject
	private FileHelper fileHelper;
	
	public Response uploadXml(
			InputStream uploadedInputStream,
			FormDataContentDisposition fileDetails) {
		
		var opExtension = fileHelper.getExtensionByStringHandling(fileDetails.getFileName());
		if(opExtension.isPresent()) {
			if(opExtension.get().equalsIgnoreCase("XML")) {
				try {
					var linesList =  new BufferedReader(
							new InputStreamReader(uploadedInputStream, StandardCharsets.UTF_8))
							.lines()
							.collect(Collectors.toList());
					
					var isV2 = linesList
							.stream()
							.filter(l -> l.contains("http://www.sat.gob.gt/dte/fel/0.2.0"))
							.count() > 0;
					
				    String xml = linesList
				    		.stream()
				    		.collect(Collectors.joining("\n"));
				    
				    var reader = new StringReader(xml);
				    if(isV2) {
				    	var jaxbContext = JAXBContext.newInstance(com.pivaral.facturas.model.sat.v2.GTDocumento.class);
					    var unmarshaller = jaxbContext.createUnmarshaller();
					    var document = (com.pivaral.facturas.model.sat.v2.GTDocumento) unmarshaller.unmarshal(reader);
					    return Response.ok(document.convertToFactura()).build();
				    } else {
				    	var jaxbContext = JAXBContext.newInstance(GTDocumento.class);            
						var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
						var document = (GTDocumento) jaxbUnmarshaller.unmarshal(reader);
						return Response.ok(document.convertToFactura()).build();
				    }
				} catch (JAXBException ex) {
					ex.printStackTrace();
					return Response.status(500).entity(ex.getMessage() + " --- " + ex.getLocalizedMessage()).build();
				}					 				 
			} else {
				return Response.status(400).build();	
			}
		} else {
			return Response.status(400).build(); 
		}
	}
}