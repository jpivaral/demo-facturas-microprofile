package com.pivaral.facturas.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

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
				String text = new BufferedReader(
					      new InputStreamReader(uploadedInputStream, StandardCharsets.UTF_8))
					        .lines()
					        .collect(Collectors.joining("\n"));
				return Response.ok(text).build();
			} else {
				return Response.status(400).build();	
			}
		}else {
			return Response.status(400).build(); 
		}
	}
}