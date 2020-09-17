package com.pivaral.facturas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table
public class Factura implements Serializable {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	private String nit;
	private String nomenclatura;
	private String proveedor;
	private String serie;
	private String factura;
	private String cheque;
	@Column(columnDefinition = "DATE")
	private LocalDate fechaFactura;
	private String descripcion;
	private BigDecimal valorBien;
	private BigDecimal valorServicios;
	private BigDecimal valorCombustible;
	private BigDecimal valorActivos;

}
