package com.pivaral.facturas.service;

import java.io.InputStream;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.pivaral.facturas.model.sat.GTDocumento;
import com.pivaral.facturas.utils.FileHelper;

@RequestScoped
public class UploadService {
	
	@Inject
	private FileHelper fileHelper;
	
	private Logger logger = Logger.getLogger( UploadService.class.getName()); 
	
	public Response uploadXml(
			InputStream uploadedInputStream,
			FormDataContentDisposition fileDetails) {
		
		var opExtension = fileHelper.getExtensionByStringHandling(fileDetails.getFileName());
		if(opExtension.isPresent()) {
			if(opExtension.get().equalsIgnoreCase("XML")) {
				try {
				    var jaxbContext = JAXBContext.newInstance(GTDocumento.class);            
				    var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				    var document = (GTDocumento) jaxbUnmarshaller.unmarshal(uploadedInputStream);
				    return Response.ok(document).build();
				}
				catch (JAXBException e) 
				{
					e.printStackTrace();
					return Response.status(500).entity(e.getMessage() + " --- " + e.getLocalizedMessage()).build();
				}				 
				
				/*String text = new BufferedReader(
					      new InputStreamReader(uploadedInputStream, StandardCharsets.UTF_8))
					        .lines()
					        .collect(Collectors.joining("\n"));*/
			} else {
				return Response.status(400).build();	
			}
		}else {
			return Response.status(400).build(); 
		}
	}
}