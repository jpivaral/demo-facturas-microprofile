package com.pivaral.facturas.repository;

import java.util.Optional;

import org.apache.deltaspike.data.api.AbstractEntityRepository;

import com.pivaral.facturas.model.Factura;

public abstract class FacturaRepository extends AbstractEntityRepository<Factura, Long> {
	
	public abstract Optional<Factura> findById(Long id);
}
