package com.pivaral.facturas;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@ApplicationPath("/api")
@ApplicationScoped
public class FacturasRestApplication extends Application {
	
	@Override
	public Map<String, Object> getProperties() {
	    Map<String, Object> props = new HashMap<>();
	    props.put("jersey.config.server.provider.classnames", 
	            "org.glassfish.jersey.media.multipart.MultiPartFeature");
	    return props;
	}	
}