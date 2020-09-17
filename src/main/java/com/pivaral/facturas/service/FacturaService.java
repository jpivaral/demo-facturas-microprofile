package com.pivaral.facturas.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.pivaral.facturas.model.Factura;
import com.pivaral.facturas.repository.FacturaRepository;

@RequestScoped
public class FacturaService {
	
	@Inject
	private FacturaRepository facturaRepository;
	
	public Response findAllFacturas() {
		return Response.ok(facturaRepository.findAll()).build();
	}
	
	public Response findFacturaById(Long id) {
		var opFactura = facturaRepository.findById(id);
		if(opFactura.isPresent()) {
			return Response.ok(opFactura.get()).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	public Response updateFacturaById(Long id, Factura factura) {
		factura.setId(id);
		return Response.accepted().entity(facturaRepository.save(factura)).build();
	}
	
	public Response deleteFacturaById(Long id) {
		var factura = new Factura();
		factura.setId(id);
		facturaRepository.remove(factura);
		return Response.noContent().build();
	}
	
	public Response createFactura(Factura factura, UriInfo uriInfo) {
		factura = facturaRepository.save(factura);
		var builder = uriInfo.getAbsolutePathBuilder();
		builder.path(factura.getId().toString());
		return Response.created(builder.build()).entity(factura).build();
	}

}
