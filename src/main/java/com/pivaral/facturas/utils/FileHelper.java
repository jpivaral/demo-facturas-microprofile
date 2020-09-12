package com.pivaral.facturas.utils;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileHelper {
	 
	public Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
}
