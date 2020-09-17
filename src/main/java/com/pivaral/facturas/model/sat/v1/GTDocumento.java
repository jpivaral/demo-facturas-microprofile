package com.pivaral.facturas.model.sat.v1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.w3c.dom.Element;

import com.pivaral.facturas.model.Factura;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "GTDocumento", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
public class GTDocumento {
	
	public Factura convertToFactura() {
		var factura = new Factura();
		this.getContent()
				.stream()
				.filter(c -> c.getValue() instanceof GTDocumento.SAT)
				.map(c -> (GTDocumento.SAT)c.getValue() )
				.findFirst()
				.ifPresent(s -> {
					factura.setNit(s.getDTE().getDatosEmision().getEmisor().getNITEmisor());
					factura.setProveedor(s.getDTE().getDatosEmision().getEmisor().getNombreComercial());
					factura.setSerie(s.getDTE().getCertificacion().getNumeroAutorizacion().getSerie());
					factura.setFactura(String.valueOf(s.getDTE().getCertificacion().getNumeroAutorizacion().getNumero()));
					factura.setFechaFactura(
							LocalDate.of(
									s.getDTE().getCertificacion().getFechaHoraCertificacion().getYear(), 
									s.getDTE().getCertificacion().getFechaHoraCertificacion().getMonth(), 
									s.getDTE().getCertificacion().getFechaHoraCertificacion().getDay())
							);
					factura.setValorServicios(s.getDTE().getDatosEmision().getTotales().getGranTotal());;
				});
		return factura;
	}

    @XmlElementRefs({
        @XmlElementRef(name = "SAT", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> content;
    @XmlAttribute(name = "Version", required = true)
    protected BigDecimal version;

    public List<JAXBElement<?>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<?>>();
        }
        return this.content;
    }

    public BigDecimal getVersion() {
        if (version == null) {
            return new BigDecimal("0.4");
        } else {
            return version;
        }
    }

    public void setVersion(BigDecimal value) {
        this.version = value;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dte",
        "adenda"
    })
    public static class SAT {

        @XmlElement(name = "DTE", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
        protected GTDocumento.SAT.DTE dte;
        @XmlElement(name = "Adenda", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
        protected GTDocumento.SAT.Adenda adenda;
        @XmlAttribute(name = "ClaseDocumento", required = true)
        protected String claseDocumento;

        public GTDocumento.SAT.DTE getDTE() {
            return dte;
        }

        public void setDTE(GTDocumento.SAT.DTE value) {
            this.dte = value;
        }

        public GTDocumento.SAT.Adenda getAdenda() {
            return adenda;
        }

        public void setAdenda(GTDocumento.SAT.Adenda value) {
            this.adenda = value;
        }

        public String getClaseDocumento() {
            return claseDocumento;
        }

        /**
         * Sets the value of the claseDocumento property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setClaseDocumento(String value) {
            this.claseDocumento = value;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "any"
        })
        public static class Adenda {

            @XmlAnyElement(lax = true)
            protected List<Object> any;

            /**
             * Gets the value of the any property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the any property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAny().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Object }
             * {@link Element }
             * 
             * 
             */
            public List<Object> getAny() {
                if (any == null) {
                    any = new ArrayList<Object>();
                }
                return this.any;
            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "datosEmision",
            "certificacion"
        })
        public static class DTE {

            @XmlElement(name = "DatosEmision", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
            protected GTDocumento.SAT.DTE.DatosEmision datosEmision;
            @XmlElement(name = "Certificacion", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
            protected GTDocumento.SAT.DTE.Certificacion certificacion;
            @XmlAttribute(name = "ID", required = true)
            @XmlSchemaType(name = "anySimpleType")
            protected String id;

            /**
             * Gets the value of the datosEmision property.
             * 
             * @return
             *     possible object is
             *     {@link GTDocumento.SAT.DTE.DatosEmision }
             *     
             */
            public GTDocumento.SAT.DTE.DatosEmision getDatosEmision() {
                return datosEmision;
            }

            /**
             * Sets the value of the datosEmision property.
             * 
             * @param value
             *     allowed object is
             *     {@link GTDocumento.SAT.DTE.DatosEmision }
             *     
             */
            public void setDatosEmision(GTDocumento.SAT.DTE.DatosEmision value) {
                this.datosEmision = value;
            }

            /**
             * Gets the value of the certificacion property.
             * 
             * @return
             *     possible object is
             *     {@link GTDocumento.SAT.DTE.Certificacion }
             *     
             */
            public GTDocumento.SAT.DTE.Certificacion getCertificacion() {
                return certificacion;
            }

            /**
             * Sets the value of the certificacion property.
             * 
             * @param value
             *     allowed object is
             *     {@link GTDocumento.SAT.DTE.Certificacion }
             *     
             */
            public void setCertificacion(GTDocumento.SAT.DTE.Certificacion value) {
                this.certificacion = value;
            }

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getID() {
                if (id == null) {
                    return "DatosCertificados";
                } else {
                    return id;
                }
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setID(String value) {
                this.id = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="NITCertificador">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="13"/>
             *               &lt;whiteSpace value="collapse"/>
             *               &lt;pattern value="([1-9])+([0-9])*([0-9]|K)"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="NombreCertificador">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="255"/>
             *               &lt;whiteSpace value="collapse"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="NumeroAutorizacion">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>tipoUUID">
             *                 &lt;attribute name="Serie" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;whiteSpace value="collapse"/>
             *                       &lt;minLength value="1"/>
             *                       &lt;maxLength value="20"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="Numero" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
             *                       &lt;minInclusive value="1"/>
             *                       &lt;maxInclusive value="999999999999999"/>
             *                       &lt;totalDigits value="15"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="FechaHoraCertificacion">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime">
             *               &lt;pattern value="((\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})(.(\d{3}))?(-06:00)?)"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "nitCertificador",
                "nombreCertificador",
                "numeroAutorizacion",
                "fechaHoraCertificacion"
            })
            public static class Certificacion {

                @XmlElement(name = "NITCertificador", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected String nitCertificador;
                @XmlElement(name = "NombreCertificador", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected String nombreCertificador;
                @XmlElement(name = "NumeroAutorizacion", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected GTDocumento.SAT.DTE.Certificacion.NumeroAutorizacion numeroAutorizacion;
                @XmlElement(name = "FechaHoraCertificacion", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected XMLGregorianCalendar fechaHoraCertificacion;

                /**
                 * Gets the value of the nitCertificador property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNITCertificador() {
                    return nitCertificador;
                }

                /**
                 * Sets the value of the nitCertificador property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNITCertificador(String value) {
                    this.nitCertificador = value;
                }

                /**
                 * Gets the value of the nombreCertificador property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getNombreCertificador() {
                    return nombreCertificador;
                }

                /**
                 * Sets the value of the nombreCertificador property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setNombreCertificador(String value) {
                    this.nombreCertificador = value;
                }

                /**
                 * Gets the value of the numeroAutorizacion property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link GTDocumento.SAT.DTE.Certificacion.NumeroAutorizacion }
                 *     
                 */
                public GTDocumento.SAT.DTE.Certificacion.NumeroAutorizacion getNumeroAutorizacion() {
                    return numeroAutorizacion;
                }

                /**
                 * Sets the value of the numeroAutorizacion property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link GTDocumento.SAT.DTE.Certificacion.NumeroAutorizacion }
                 *     
                 */
                public void setNumeroAutorizacion(GTDocumento.SAT.DTE.Certificacion.NumeroAutorizacion value) {
                    this.numeroAutorizacion = value;
                }

                /**
                 * Gets the value of the fechaHoraCertificacion property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getFechaHoraCertificacion() {
                    return fechaHoraCertificacion;
                }

                /**
                 * Sets the value of the fechaHoraCertificacion property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setFechaHoraCertificacion(XMLGregorianCalendar value) {
                    this.fechaHoraCertificacion = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>tipoUUID">
                 *       &lt;attribute name="Serie" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;whiteSpace value="collapse"/>
                 *             &lt;minLength value="1"/>
                 *             &lt;maxLength value="20"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="Numero" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
                 *             &lt;minInclusive value="1"/>
                 *             &lt;maxInclusive value="999999999999999"/>
                 *             &lt;totalDigits value="15"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *     &lt;/extension>
                 *   &lt;/simpleContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class NumeroAutorizacion {

                    @XmlValue
                    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                    protected String value;
                    @XmlAttribute(name = "Serie", required = true)
                    protected String serie;
                    @XmlAttribute(name = "Numero", required = true)
                    protected long numero;

                    /**
                     * Gets the value of the value property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Sets the value of the value property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                    /**
                     * Gets the value of the serie property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSerie() {
                        return serie;
                    }

                    /**
                     * Sets the value of the serie property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSerie(String value) {
                        this.serie = value;
                    }

                    /**
                     * Gets the value of the numero property.
                     * 
                     */
                    public long getNumero() {
                        return numero;
                    }

                    /**
                     * Sets the value of the numero property.
                     * 
                     */
                    public void setNumero(long value) {
                        this.numero = value;
                    }

                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="DatosGenerales">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attribute name="Tipo" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;whiteSpace value="collapse"/>
             *                       &lt;enumeration value="FACT"/>
             *                       &lt;enumeration value="FCAM"/>
             *                       &lt;enumeration value="FPEQ"/>
             *                       &lt;enumeration value="FCAP"/>
             *                       &lt;enumeration value="FESP"/>
             *                       &lt;enumeration value="NABN"/>
             *                       &lt;enumeration value="RDON"/>
             *                       &lt;enumeration value="RECI"/>
             *                       &lt;enumeration value="NDEB"/>
             *                       &lt;enumeration value="NCRE"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="Exp">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;enumeration value="SI"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="FechaHoraEmision" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime">
             *                       &lt;pattern value="((\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})(.(\d{3}))?(-06:00)?)"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="CodigoMoneda" use="required" type="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoMoneda" />
             *                 &lt;attribute name="NumeroAcceso">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
             *                       &lt;whiteSpace value="collapse"/>
             *                       &lt;minInclusive value="100000000"/>
             *                       &lt;maxInclusive value="999999999"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Emisor">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="DireccionEmisor" type="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoDireccion"/>
             *                 &lt;/sequence>
             *                 &lt;attribute name="NITEmisor" use="required" type="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoNITDelEFACE" />
             *                 &lt;attribute name="NombreEmisor" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;maxLength value="255"/>
             *                       &lt;minLength value="1"/>
             *                       &lt;whiteSpace value="collapse"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="CodigoEstablecimiento" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
             *                       &lt;minInclusive value="1"/>
             *                       &lt;maxInclusive value="9999"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="NombreComercial" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;minLength value="1"/>
             *                       &lt;maxLength value="255"/>
             *                       &lt;whiteSpace value="collapse"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="CorreoEmisor">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoCorreoElectronico">
             *                       &lt;whiteSpace value="collapse"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="AfiliacionIVA" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;enumeration value="GEN"/>
             *                       &lt;enumeration value="EXE"/>
             *                       &lt;enumeration value="PEQ"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Receptor">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="DireccionReceptor" type="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoDireccion" minOccurs="0"/>
             *                 &lt;/sequence>
             *                 &lt;attribute name="IDReceptor" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoNITReceptor">
             *                       &lt;whiteSpace value="collapse"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="TipoEspecial">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;whiteSpace value="collapse"/>
             *                       &lt;enumeration value="CUI"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="NombreReceptor" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;minLength value="1"/>
             *                       &lt;maxLength value="255"/>
             *                       &lt;whiteSpace value="collapse"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="CorreoReceptor">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoCorreoElectronico">
             *                       &lt;whiteSpace value="collapse"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Frases" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Frase" maxOccurs="100">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;attribute name="TipoFrase" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
             *                                 &lt;whiteSpace value="collapse"/>
             *                                 &lt;minInclusive value="1"/>
             *                                 &lt;maxInclusive value="4"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                           &lt;attribute name="CodigoEscenario" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
             *                                 &lt;minInclusive value="1"/>
             *                                 &lt;maxInclusive value="99"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Items">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="Item" maxOccurs="9999">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="Cantidad">
             *                               &lt;complexType>
             *                                 &lt;simpleContent>
             *                                   &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
             *                                   &lt;/extension>
             *                                 &lt;/simpleContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                             &lt;element name="UnidadMedida" minOccurs="0">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                   &lt;minLength value="1"/>
             *                                   &lt;maxLength value="3"/>
             *                                   &lt;whiteSpace value="collapse"/>
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="Descripcion">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                   &lt;minLength value="1"/>
             *                                   &lt;maxLength value="10000"/>
             *                                   &lt;whiteSpace value="collapse"/>
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="PrecioUnitario">
             *                               &lt;simpleType>
             *                                 &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales">
             *                                 &lt;/restriction>
             *                               &lt;/simpleType>
             *                             &lt;/element>
             *                             &lt;element name="Precio" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales"/>
             *                             &lt;element name="Descuento" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
             *                             &lt;element name="Impuestos" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="Impuesto" maxOccurs="20">
             *                                         &lt;complexType>
             *                                           &lt;complexContent>
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                               &lt;sequence>
             *                                                 &lt;element name="NombreCorto">
             *                                                   &lt;simpleType>
             *                                                     &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
             *                                                       &lt;whiteSpace value="collapse"/>
             *                                                     &lt;/restriction>
             *                                                   &lt;/simpleType>
             *                                                 &lt;/element>
             *                                                 &lt;element name="CodigoUnidadGravable">
             *                                                   &lt;simpleType>
             *                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
             *                                                       &lt;minInclusive value="1"/>
             *                                                       &lt;totalDigits value="7"/>
             *                                                     &lt;/restriction>
             *                                                   &lt;/simpleType>
             *                                                 &lt;/element>
             *                                                 &lt;element name="MontoGravable" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
             *                                                 &lt;element name="CantidadUnidadesGravables" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
             *                                                 &lt;element name="MontoImpuesto">
             *                                                   &lt;complexType>
             *                                                     &lt;simpleContent>
             *                                                       &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
             *                                                       &lt;/extension>
             *                                                     &lt;/simpleContent>
             *                                                   &lt;/complexType>
             *                                                 &lt;/element>
             *                                               &lt;/sequence>
             *                                             &lt;/restriction>
             *                                           &lt;/complexContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                             &lt;element name="Total" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales"/>
             *                             &lt;element name="ComplementosItem" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence maxOccurs="unbounded">
             *                                       &lt;element name="ComplementoItem">
             *                                         &lt;complexType>
             *                                           &lt;complexContent>
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                               &lt;sequence>
             *                                                 &lt;any/>
             *                                               &lt;/sequence>
             *                                               &lt;attribute name="IDComplementoItem" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
             *                                               &lt;attribute name="NombreComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
             *                                               &lt;attribute name="URIComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
             *                                             &lt;/restriction>
             *                                           &lt;/complexContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="NumeroLinea" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
             *                                 &lt;minInclusive value="1"/>
             *                                 &lt;maxInclusive value="9999"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                           &lt;attribute name="BienOServicio" use="required">
             *                             &lt;simpleType>
             *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                                 &lt;length value="1"/>
             *                                 &lt;whiteSpace value="collapse"/>
             *                                 &lt;enumeration value="B"/>
             *                                 &lt;enumeration value="S"/>
             *                               &lt;/restriction>
             *                             &lt;/simpleType>
             *                           &lt;/attribute>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Totales">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="TotalImpuestos" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="TotalImpuesto" maxOccurs="20">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;attribute name="NombreCorto" use="required">
             *                                       &lt;simpleType>
             *                                         &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
             *                                           &lt;whiteSpace value="collapse"/>
             *                                         &lt;/restriction>
             *                                       &lt;/simpleType>
             *                                     &lt;/attribute>
             *                                     &lt;attribute name="TotalMontoImpuesto" use="required" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" />
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="GranTotal" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Complementos" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence maxOccurs="unbounded">
             *                   &lt;element name="Complemento">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;any/>
             *                           &lt;/sequence>
             *                           &lt;attribute name="IDComplemento" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
             *                           &lt;attribute name="NombreComplemento" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
             *                           &lt;attribute name="URIComplemento" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="ID" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="DatosEmision" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "datosGenerales",
                "emisor",
                "receptor",
                "frases",
                "items",
                "totales",
                "complementos"
            })
            public static class DatosEmision {

                @XmlElement(name = "DatosGenerales", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected GTDocumento.SAT.DTE.DatosEmision.DatosGenerales datosGenerales;
                @XmlElement(name = "Emisor", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected GTDocumento.SAT.DTE.DatosEmision.Emisor emisor;
                @XmlElement(name = "Receptor", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected GTDocumento.SAT.DTE.DatosEmision.Receptor receptor;
                @XmlElement(name = "Frases", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                protected GTDocumento.SAT.DTE.DatosEmision.Frases frases;
                @XmlElement(name = "Items", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected GTDocumento.SAT.DTE.DatosEmision.Items items;
                @XmlElement(name = "Totales", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                protected GTDocumento.SAT.DTE.DatosEmision.Totales totales;
                @XmlElement(name = "Complementos", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                protected GTDocumento.SAT.DTE.DatosEmision.Complementos complementos;
                @XmlAttribute(name = "ID", required = true)
                @XmlSchemaType(name = "anySimpleType")
                protected String id;

                /**
                 * Gets the value of the datosGenerales property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.DatosGenerales }
                 *     
                 */
                public GTDocumento.SAT.DTE.DatosEmision.DatosGenerales getDatosGenerales() {
                    return datosGenerales;
                }

                /**
                 * Sets the value of the datosGenerales property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.DatosGenerales }
                 *     
                 */
                public void setDatosGenerales(GTDocumento.SAT.DTE.DatosEmision.DatosGenerales value) {
                    this.datosGenerales = value;
                }

                /**
                 * Gets the value of the emisor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Emisor }
                 *     
                 */
                public GTDocumento.SAT.DTE.DatosEmision.Emisor getEmisor() {
                    return emisor;
                }

                /**
                 * Sets the value of the emisor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Emisor }
                 *     
                 */
                public void setEmisor(GTDocumento.SAT.DTE.DatosEmision.Emisor value) {
                    this.emisor = value;
                }

                /**
                 * Gets the value of the receptor property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Receptor }
                 *     
                 */
                public GTDocumento.SAT.DTE.DatosEmision.Receptor getReceptor() {
                    return receptor;
                }

                /**
                 * Sets the value of the receptor property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Receptor }
                 *     
                 */
                public void setReceptor(GTDocumento.SAT.DTE.DatosEmision.Receptor value) {
                    this.receptor = value;
                }

                /**
                 * Gets the value of the frases property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Frases }
                 *     
                 */
                public GTDocumento.SAT.DTE.DatosEmision.Frases getFrases() {
                    return frases;
                }

                /**
                 * Sets the value of the frases property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Frases }
                 *     
                 */
                public void setFrases(GTDocumento.SAT.DTE.DatosEmision.Frases value) {
                    this.frases = value;
                }

                /**
                 * Gets the value of the items property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Items }
                 *     
                 */
                public GTDocumento.SAT.DTE.DatosEmision.Items getItems() {
                    return items;
                }

                /**
                 * Sets the value of the items property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Items }
                 *     
                 */
                public void setItems(GTDocumento.SAT.DTE.DatosEmision.Items value) {
                    this.items = value;
                }

                /**
                 * Gets the value of the totales property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Totales }
                 *     
                 */
                public GTDocumento.SAT.DTE.DatosEmision.Totales getTotales() {
                    return totales;
                }

                /**
                 * Sets the value of the totales property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Totales }
                 *     
                 */
                public void setTotales(GTDocumento.SAT.DTE.DatosEmision.Totales value) {
                    this.totales = value;
                }

                /**
                 * Gets the value of the complementos property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Complementos }
                 *     
                 */
                public GTDocumento.SAT.DTE.DatosEmision.Complementos getComplementos() {
                    return complementos;
                }

                /**
                 * Sets the value of the complementos property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Complementos }
                 *     
                 */
                public void setComplementos(GTDocumento.SAT.DTE.DatosEmision.Complementos value) {
                    this.complementos = value;
                }

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getID() {
                    if (id == null) {
                        return "DatosEmision";
                    } else {
                        return id;
                    }
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setID(String value) {
                    this.id = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence maxOccurs="unbounded">
                 *         &lt;element name="Complemento">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;any/>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="IDComplemento" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                 *                 &lt;attribute name="NombreComplemento" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                 *                 &lt;attribute name="URIComplemento" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "complemento"
                })
                public static class Complementos {

                    @XmlElement(name = "Complemento", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                    protected List<GTDocumento.SAT.DTE.DatosEmision.Complementos.Complemento> complemento;

                    /**
                     * Gets the value of the complemento property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the complemento property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getComplemento().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link GTDocumento.SAT.DTE.DatosEmision.Complementos.Complemento }
                     * 
                     * 
                     */
                    public List<GTDocumento.SAT.DTE.DatosEmision.Complementos.Complemento> getComplemento() {
                        if (complemento == null) {
                            complemento = new ArrayList<GTDocumento.SAT.DTE.DatosEmision.Complementos.Complemento>();
                        }
                        return this.complemento;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;any/>
                     *       &lt;/sequence>
                     *       &lt;attribute name="IDComplemento" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                     *       &lt;attribute name="NombreComplemento" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                     *       &lt;attribute name="URIComplemento" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "any"
                    })
                    public static class Complemento {

                        @XmlAnyElement(lax = true)
                        protected Object any;
                        @XmlAttribute(name = "IDComplemento")
                        @XmlSchemaType(name = "anySimpleType")
                        protected String idComplemento;
                        @XmlAttribute(name = "NombreComplemento", required = true)
                        @XmlSchemaType(name = "anySimpleType")
                        protected String nombreComplemento;
                        @XmlAttribute(name = "URIComplemento", required = true)
                        @XmlSchemaType(name = "anySimpleType")
                        protected String uriComplemento;

                        /**
                         * Gets the value of the any property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Object }
                         *     
                         */
                        public Object getAny() {
                            return any;
                        }

                        /**
                         * Sets the value of the any property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Object }
                         *     
                         */
                        public void setAny(Object value) {
                            this.any = value;
                        }

                        /**
                         * Gets the value of the idComplemento property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getIDComplemento() {
                            return idComplemento;
                        }

                        /**
                         * Sets the value of the idComplemento property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setIDComplemento(String value) {
                            this.idComplemento = value;
                        }

                        /**
                         * Gets the value of the nombreComplemento property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getNombreComplemento() {
                            return nombreComplemento;
                        }

                        /**
                         * Sets the value of the nombreComplemento property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setNombreComplemento(String value) {
                            this.nombreComplemento = value;
                        }

                        /**
                         * Gets the value of the uriComplemento property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getURIComplemento() {
                            return uriComplemento;
                        }

                        /**
                         * Sets the value of the uriComplemento property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setURIComplemento(String value) {
                            this.uriComplemento = value;
                        }

                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;attribute name="Tipo" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;whiteSpace value="collapse"/>
                 *             &lt;enumeration value="FACT"/>
                 *             &lt;enumeration value="FCAM"/>
                 *             &lt;enumeration value="FPEQ"/>
                 *             &lt;enumeration value="FCAP"/>
                 *             &lt;enumeration value="FESP"/>
                 *             &lt;enumeration value="NABN"/>
                 *             &lt;enumeration value="RDON"/>
                 *             &lt;enumeration value="RECI"/>
                 *             &lt;enumeration value="NDEB"/>
                 *             &lt;enumeration value="NCRE"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="Exp">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;enumeration value="SI"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="FechaHoraEmision" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime">
                 *             &lt;pattern value="((\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})(.(\d{3}))?(-06:00)?)"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="CodigoMoneda" use="required" type="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoMoneda" />
                 *       &lt;attribute name="NumeroAcceso">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
                 *             &lt;whiteSpace value="collapse"/>
                 *             &lt;minInclusive value="100000000"/>
                 *             &lt;maxInclusive value="999999999"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class DatosGenerales {

                    @XmlAttribute(name = "Tipo", required = true)
                    protected String tipo;
                    @XmlAttribute(name = "Exp")
                    protected String exp;
                    @XmlAttribute(name = "FechaHoraEmision", required = true)
                    protected XMLGregorianCalendar fechaHoraEmision;
                    @XmlAttribute(name = "CodigoMoneda", required = true)
                    protected TipoMoneda codigoMoneda;
                    @XmlAttribute(name = "NumeroAcceso")
                    protected Integer numeroAcceso;

                    /**
                     * Gets the value of the tipo property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipo() {
                        return tipo;
                    }

                    /**
                     * Sets the value of the tipo property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipo(String value) {
                        this.tipo = value;
                    }

                    /**
                     * Gets the value of the exp property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getExp() {
                        return exp;
                    }

                    /**
                     * Sets the value of the exp property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setExp(String value) {
                        this.exp = value;
                    }

                    /**
                     * Gets the value of the fechaHoraEmision property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public XMLGregorianCalendar getFechaHoraEmision() {
                        return fechaHoraEmision;
                    }

                    /**
                     * Sets the value of the fechaHoraEmision property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public void setFechaHoraEmision(XMLGregorianCalendar value) {
                        this.fechaHoraEmision = value;
                    }

                    /**
                     * Gets the value of the codigoMoneda property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TipoMoneda }
                     *     
                     */
                    public TipoMoneda getCodigoMoneda() {
                        return codigoMoneda;
                    }

                    /**
                     * Sets the value of the codigoMoneda property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TipoMoneda }
                     *     
                     */
                    public void setCodigoMoneda(TipoMoneda value) {
                        this.codigoMoneda = value;
                    }

                    /**
                     * Gets the value of the numeroAcceso property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Integer }
                     *     
                     */
                    public Integer getNumeroAcceso() {
                        return numeroAcceso;
                    }

                    /**
                     * Sets the value of the numeroAcceso property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Integer }
                     *     
                     */
                    public void setNumeroAcceso(Integer value) {
                        this.numeroAcceso = value;
                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="DireccionEmisor" type="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoDireccion"/>
                 *       &lt;/sequence>
                 *       &lt;attribute name="NITEmisor" use="required" type="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoNITDelEFACE" />
                 *       &lt;attribute name="NombreEmisor" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;maxLength value="255"/>
                 *             &lt;minLength value="1"/>
                 *             &lt;whiteSpace value="collapse"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="CodigoEstablecimiento" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
                 *             &lt;minInclusive value="1"/>
                 *             &lt;maxInclusive value="9999"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="NombreComercial" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;minLength value="1"/>
                 *             &lt;maxLength value="255"/>
                 *             &lt;whiteSpace value="collapse"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="CorreoEmisor">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoCorreoElectronico">
                 *             &lt;whiteSpace value="collapse"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="AfiliacionIVA" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;enumeration value="GEN"/>
                 *             &lt;enumeration value="EXE"/>
                 *             &lt;enumeration value="PEQ"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "direccionEmisor"
                })
                public static class Emisor {

                    @XmlElement(name = "DireccionEmisor", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                    protected TipoDireccion direccionEmisor;
                    @XmlAttribute(name = "NITEmisor", required = true)
                    protected String nitEmisor;
                    @XmlAttribute(name = "NombreEmisor", required = true)
                    protected String nombreEmisor;
                    @XmlAttribute(name = "CodigoEstablecimiento", required = true)
                    protected int codigoEstablecimiento;
                    @XmlAttribute(name = "NombreComercial", required = true)
                    protected String nombreComercial;
                    @XmlAttribute(name = "CorreoEmisor")
                    protected String correoEmisor;
                    @XmlAttribute(name = "AfiliacionIVA", required = true)
                    protected String afiliacionIVA;

                    /**
                     * Gets the value of the direccionEmisor property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TipoDireccion }
                     *     
                     */
                    public TipoDireccion getDireccionEmisor() {
                        return direccionEmisor;
                    }

                    /**
                     * Sets the value of the direccionEmisor property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TipoDireccion }
                     *     
                     */
                    public void setDireccionEmisor(TipoDireccion value) {
                        this.direccionEmisor = value;
                    }

                    /**
                     * Gets the value of the nitEmisor property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNITEmisor() {
                        return nitEmisor;
                    }

                    /**
                     * Sets the value of the nitEmisor property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNITEmisor(String value) {
                        this.nitEmisor = value;
                    }

                    /**
                     * Gets the value of the nombreEmisor property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNombreEmisor() {
                        return nombreEmisor;
                    }

                    /**
                     * Sets the value of the nombreEmisor property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNombreEmisor(String value) {
                        this.nombreEmisor = value;
                    }

                    /**
                     * Gets the value of the codigoEstablecimiento property.
                     * 
                     */
                    public int getCodigoEstablecimiento() {
                        return codigoEstablecimiento;
                    }

                    /**
                     * Sets the value of the codigoEstablecimiento property.
                     * 
                     */
                    public void setCodigoEstablecimiento(int value) {
                        this.codigoEstablecimiento = value;
                    }

                    /**
                     * Gets the value of the nombreComercial property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNombreComercial() {
                        return nombreComercial;
                    }

                    /**
                     * Sets the value of the nombreComercial property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNombreComercial(String value) {
                        this.nombreComercial = value;
                    }

                    /**
                     * Gets the value of the correoEmisor property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCorreoEmisor() {
                        return correoEmisor;
                    }

                    /**
                     * Sets the value of the correoEmisor property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCorreoEmisor(String value) {
                        this.correoEmisor = value;
                    }

                    /**
                     * Gets the value of the afiliacionIVA property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAfiliacionIVA() {
                        return afiliacionIVA;
                    }

                    /**
                     * Sets the value of the afiliacionIVA property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAfiliacionIVA(String value) {
                        this.afiliacionIVA = value;
                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="Frase" maxOccurs="100">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;attribute name="TipoFrase" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
                 *                       &lt;whiteSpace value="collapse"/>
                 *                       &lt;minInclusive value="1"/>
                 *                       &lt;maxInclusive value="4"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *                 &lt;attribute name="CodigoEscenario" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                 *                       &lt;minInclusive value="1"/>
                 *                       &lt;maxInclusive value="99"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "frase"
                })
                public static class Frases {

                    @XmlElement(name = "Frase", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                    protected List<GTDocumento.SAT.DTE.DatosEmision.Frases.Frase> frase;

                    /**
                     * Gets the value of the frase property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the frase property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getFrase().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link GTDocumento.SAT.DTE.DatosEmision.Frases.Frase }
                     * 
                     * 
                     */
                    public List<GTDocumento.SAT.DTE.DatosEmision.Frases.Frase> getFrase() {
                        if (frase == null) {
                            frase = new ArrayList<GTDocumento.SAT.DTE.DatosEmision.Frases.Frase>();
                        }
                        return this.frase;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;attribute name="TipoFrase" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
                     *             &lt;whiteSpace value="collapse"/>
                     *             &lt;minInclusive value="1"/>
                     *             &lt;maxInclusive value="4"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *       &lt;attribute name="CodigoEscenario" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
                     *             &lt;minInclusive value="1"/>
                     *             &lt;maxInclusive value="99"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "")
                    public static class Frase {

                        @XmlAttribute(name = "TipoFrase", required = true)
                        protected int tipoFrase;
                        @XmlAttribute(name = "CodigoEscenario", required = true)
                        protected int codigoEscenario;

                        /**
                         * Gets the value of the tipoFrase property.
                         * 
                         */
                        public int getTipoFrase() {
                            return tipoFrase;
                        }

                        /**
                         * Sets the value of the tipoFrase property.
                         * 
                         */
                        public void setTipoFrase(int value) {
                            this.tipoFrase = value;
                        }

                        /**
                         * Gets the value of the codigoEscenario property.
                         * 
                         */
                        public int getCodigoEscenario() {
                            return codigoEscenario;
                        }

                        /**
                         * Sets the value of the codigoEscenario property.
                         * 
                         */
                        public void setCodigoEscenario(int value) {
                            this.codigoEscenario = value;
                        }

                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="Item" maxOccurs="9999">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="Cantidad">
                 *                     &lt;complexType>
                 *                       &lt;simpleContent>
                 *                         &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
                 *                         &lt;/extension>
                 *                       &lt;/simpleContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                   &lt;element name="UnidadMedida" minOccurs="0">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                         &lt;minLength value="1"/>
                 *                         &lt;maxLength value="3"/>
                 *                         &lt;whiteSpace value="collapse"/>
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="Descripcion">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                         &lt;minLength value="1"/>
                 *                         &lt;maxLength value="10000"/>
                 *                         &lt;whiteSpace value="collapse"/>
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="PrecioUnitario">
                 *                     &lt;simpleType>
                 *                       &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales">
                 *                       &lt;/restriction>
                 *                     &lt;/simpleType>
                 *                   &lt;/element>
                 *                   &lt;element name="Precio" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales"/>
                 *                   &lt;element name="Descuento" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                 *                   &lt;element name="Impuestos" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="Impuesto" maxOccurs="20">
                 *                               &lt;complexType>
                 *                                 &lt;complexContent>
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                                     &lt;sequence>
                 *                                       &lt;element name="NombreCorto">
                 *                                         &lt;simpleType>
                 *                                           &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
                 *                                             &lt;whiteSpace value="collapse"/>
                 *                                           &lt;/restriction>
                 *                                         &lt;/simpleType>
                 *                                       &lt;/element>
                 *                                       &lt;element name="CodigoUnidadGravable">
                 *                                         &lt;simpleType>
                 *                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                 *                                             &lt;minInclusive value="1"/>
                 *                                             &lt;totalDigits value="7"/>
                 *                                           &lt;/restriction>
                 *                                         &lt;/simpleType>
                 *                                       &lt;/element>
                 *                                       &lt;element name="MontoGravable" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                 *                                       &lt;element name="CantidadUnidadesGravables" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                 *                                       &lt;element name="MontoImpuesto">
                 *                                         &lt;complexType>
                 *                                           &lt;simpleContent>
                 *                                             &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
                 *                                             &lt;/extension>
                 *                                           &lt;/simpleContent>
                 *                                         &lt;/complexType>
                 *                                       &lt;/element>
                 *                                     &lt;/sequence>
                 *                                   &lt;/restriction>
                 *                                 &lt;/complexContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                   &lt;element name="Total" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales"/>
                 *                   &lt;element name="ComplementosItem" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence maxOccurs="unbounded">
                 *                             &lt;element name="ComplementoItem">
                 *                               &lt;complexType>
                 *                                 &lt;complexContent>
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                                     &lt;sequence>
                 *                                       &lt;any/>
                 *                                     &lt;/sequence>
                 *                                     &lt;attribute name="IDComplementoItem" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                 *                                     &lt;attribute name="NombreComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                 *                                     &lt;attribute name="URIComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                 *                                   &lt;/restriction>
                 *                                 &lt;/complexContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="NumeroLinea" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
                 *                       &lt;minInclusive value="1"/>
                 *                       &lt;maxInclusive value="9999"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *                 &lt;attribute name="BienOServicio" use="required">
                 *                   &lt;simpleType>
                 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *                       &lt;length value="1"/>
                 *                       &lt;whiteSpace value="collapse"/>
                 *                       &lt;enumeration value="B"/>
                 *                       &lt;enumeration value="S"/>
                 *                     &lt;/restriction>
                 *                   &lt;/simpleType>
                 *                 &lt;/attribute>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "item"
                })
                public static class Items {

                    @XmlElement(name = "Item", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                    protected List<GTDocumento.SAT.DTE.DatosEmision.Items.Item> item;

                    /**
                     * Gets the value of the item property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the item property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getItem().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item }
                     * 
                     * 
                     */
                    public List<GTDocumento.SAT.DTE.DatosEmision.Items.Item> getItem() {
                        if (item == null) {
                            item = new ArrayList<GTDocumento.SAT.DTE.DatosEmision.Items.Item>();
                        }
                        return this.item;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="Cantidad">
                     *           &lt;complexType>
                     *             &lt;simpleContent>
                     *               &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
                     *               &lt;/extension>
                     *             &lt;/simpleContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *         &lt;element name="UnidadMedida" minOccurs="0">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *               &lt;minLength value="1"/>
                     *               &lt;maxLength value="3"/>
                     *               &lt;whiteSpace value="collapse"/>
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="Descripcion">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *               &lt;minLength value="1"/>
                     *               &lt;maxLength value="10000"/>
                     *               &lt;whiteSpace value="collapse"/>
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="PrecioUnitario">
                     *           &lt;simpleType>
                     *             &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales">
                     *             &lt;/restriction>
                     *           &lt;/simpleType>
                     *         &lt;/element>
                     *         &lt;element name="Precio" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales"/>
                     *         &lt;element name="Descuento" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                     *         &lt;element name="Impuestos" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="Impuesto" maxOccurs="20">
                     *                     &lt;complexType>
                     *                       &lt;complexContent>
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                           &lt;sequence>
                     *                             &lt;element name="NombreCorto">
                     *                               &lt;simpleType>
                     *                                 &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
                     *                                   &lt;whiteSpace value="collapse"/>
                     *                                 &lt;/restriction>
                     *                               &lt;/simpleType>
                     *                             &lt;/element>
                     *                             &lt;element name="CodigoUnidadGravable">
                     *                               &lt;simpleType>
                     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                     *                                   &lt;minInclusive value="1"/>
                     *                                   &lt;totalDigits value="7"/>
                     *                                 &lt;/restriction>
                     *                               &lt;/simpleType>
                     *                             &lt;/element>
                     *                             &lt;element name="MontoGravable" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                     *                             &lt;element name="CantidadUnidadesGravables" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                     *                             &lt;element name="MontoImpuesto">
                     *                               &lt;complexType>
                     *                                 &lt;simpleContent>
                     *                                   &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
                     *                                   &lt;/extension>
                     *                                 &lt;/simpleContent>
                     *                               &lt;/complexType>
                     *                             &lt;/element>
                     *                           &lt;/sequence>
                     *                         &lt;/restriction>
                     *                       &lt;/complexContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *         &lt;element name="Total" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales"/>
                     *         &lt;element name="ComplementosItem" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence maxOccurs="unbounded">
                     *                   &lt;element name="ComplementoItem">
                     *                     &lt;complexType>
                     *                       &lt;complexContent>
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                           &lt;sequence>
                     *                             &lt;any/>
                     *                           &lt;/sequence>
                     *                           &lt;attribute name="IDComplementoItem" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                     *                           &lt;attribute name="NombreComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                     *                           &lt;attribute name="URIComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                     *                         &lt;/restriction>
                     *                       &lt;/complexContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="NumeroLinea" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
                     *             &lt;minInclusive value="1"/>
                     *             &lt;maxInclusive value="9999"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *       &lt;attribute name="BienOServicio" use="required">
                     *         &lt;simpleType>
                     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                     *             &lt;length value="1"/>
                     *             &lt;whiteSpace value="collapse"/>
                     *             &lt;enumeration value="B"/>
                     *             &lt;enumeration value="S"/>
                     *           &lt;/restriction>
                     *         &lt;/simpleType>
                     *       &lt;/attribute>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "cantidad",
                        "unidadMedida",
                        "descripcion",
                        "precioUnitario",
                        "precio",
                        "descuento",
                        "impuestos",
                        "total",
                        "complementosItem"
                    })
                    public static class Item {

                        @XmlElement(name = "Cantidad", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                        protected GTDocumento.SAT.DTE.DatosEmision.Items.Item.Cantidad cantidad;
                        @XmlElement(name = "UnidadMedida", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                        protected String unidadMedida;
                        @XmlElement(name = "Descripcion", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                        protected String descripcion;
                        @XmlElement(name = "PrecioUnitario", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                        protected BigDecimal precioUnitario;
                        @XmlElement(name = "Precio", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                        protected BigDecimal precio;
                        @XmlElement(name = "Descuento", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                        protected BigDecimal descuento;
                        @XmlElement(name = "Impuestos", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                        protected GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos impuestos;
                        @XmlElement(name = "Total", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                        protected BigDecimal total;
                        @XmlElement(name = "ComplementosItem", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                        protected GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem complementosItem;
                        @XmlAttribute(name = "NumeroLinea", required = true)
                        protected int numeroLinea;
                        @XmlAttribute(name = "BienOServicio", required = true)
                        protected String bienOServicio;

                        /**
                         * Gets the value of the cantidad property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.Cantidad }
                         *     
                         */
                        public GTDocumento.SAT.DTE.DatosEmision.Items.Item.Cantidad getCantidad() {
                            return cantidad;
                        }

                        /**
                         * Sets the value of the cantidad property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.Cantidad }
                         *     
                         */
                        public void setCantidad(GTDocumento.SAT.DTE.DatosEmision.Items.Item.Cantidad value) {
                            this.cantidad = value;
                        }

                        /**
                         * Gets the value of the unidadMedida property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getUnidadMedida() {
                            return unidadMedida;
                        }

                        /**
                         * Sets the value of the unidadMedida property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setUnidadMedida(String value) {
                            this.unidadMedida = value;
                        }

                        /**
                         * Gets the value of the descripcion property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getDescripcion() {
                            return descripcion;
                        }

                        /**
                         * Sets the value of the descripcion property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setDescripcion(String value) {
                            this.descripcion = value;
                        }

                        /**
                         * Gets the value of the precioUnitario property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public BigDecimal getPrecioUnitario() {
                            return precioUnitario;
                        }

                        /**
                         * Sets the value of the precioUnitario property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public void setPrecioUnitario(BigDecimal value) {
                            this.precioUnitario = value;
                        }

                        /**
                         * Gets the value of the precio property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public BigDecimal getPrecio() {
                            return precio;
                        }

                        /**
                         * Sets the value of the precio property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public void setPrecio(BigDecimal value) {
                            this.precio = value;
                        }

                        /**
                         * Gets the value of the descuento property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public BigDecimal getDescuento() {
                            return descuento;
                        }

                        /**
                         * Sets the value of the descuento property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public void setDescuento(BigDecimal value) {
                            this.descuento = value;
                        }

                        /**
                         * Gets the value of the impuestos property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos }
                         *     
                         */
                        public GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos getImpuestos() {
                            return impuestos;
                        }

                        /**
                         * Sets the value of the impuestos property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos }
                         *     
                         */
                        public void setImpuestos(GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos value) {
                            this.impuestos = value;
                        }

                        /**
                         * Gets the value of the total property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public BigDecimal getTotal() {
                            return total;
                        }

                        /**
                         * Sets the value of the total property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link BigDecimal }
                         *     
                         */
                        public void setTotal(BigDecimal value) {
                            this.total = value;
                        }

                        /**
                         * Gets the value of the complementosItem property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem }
                         *     
                         */
                        public GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem getComplementosItem() {
                            return complementosItem;
                        }

                        /**
                         * Sets the value of the complementosItem property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem }
                         *     
                         */
                        public void setComplementosItem(GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem value) {
                            this.complementosItem = value;
                        }

                        /**
                         * Gets the value of the numeroLinea property.
                         * 
                         */
                        public int getNumeroLinea() {
                            return numeroLinea;
                        }

                        /**
                         * Sets the value of the numeroLinea property.
                         * 
                         */
                        public void setNumeroLinea(int value) {
                            this.numeroLinea = value;
                        }

                        /**
                         * Gets the value of the bienOServicio property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getBienOServicio() {
                            return bienOServicio;
                        }

                        /**
                         * Sets the value of the bienOServicio property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setBienOServicio(String value) {
                            this.bienOServicio = value;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;simpleContent>
                         *     &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
                         *     &lt;/extension>
                         *   &lt;/simpleContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "value"
                        })
                        public static class Cantidad {

                            @XmlValue
                            protected BigDecimal value;

                            /**
                             * Acepta cantidades mayores a 0
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            public BigDecimal getValue() {
                                return value;
                            }

                            /**
                             * Sets the value of the value property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            public void setValue(BigDecimal value) {
                                this.value = value;
                            }

                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence maxOccurs="unbounded">
                         *         &lt;element name="ComplementoItem">
                         *           &lt;complexType>
                         *             &lt;complexContent>
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *                 &lt;sequence>
                         *                   &lt;any/>
                         *                 &lt;/sequence>
                         *                 &lt;attribute name="IDComplementoItem" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                         *                 &lt;attribute name="NombreComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                         *                 &lt;attribute name="URIComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                         *               &lt;/restriction>
                         *             &lt;/complexContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "complementoItem"
                        })
                        public static class ComplementosItem {

                            @XmlElement(name = "ComplementoItem", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                            protected List<GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem.ComplementoItem> complementoItem;

                            /**
                             * Gets the value of the complementoItem property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the complementoItem property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getComplementoItem().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem.ComplementoItem }
                             * 
                             * 
                             */
                            public List<GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem.ComplementoItem> getComplementoItem() {
                                if (complementoItem == null) {
                                    complementoItem = new ArrayList<GTDocumento.SAT.DTE.DatosEmision.Items.Item.ComplementosItem.ComplementoItem>();
                                }
                                return this.complementoItem;
                            }


                            /**
                             * <p>Java class for anonymous complex type.
                             * 
                             * <p>The following schema fragment specifies the expected content contained within this class.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;complexContent>
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                             *       &lt;sequence>
                             *         &lt;any/>
                             *       &lt;/sequence>
                             *       &lt;attribute name="IDComplementoItem" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                             *       &lt;attribute name="NombreComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                             *       &lt;attribute name="URIComplementoItem" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
                             *     &lt;/restriction>
                             *   &lt;/complexContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "any"
                            })
                            public static class ComplementoItem {

                                @XmlAnyElement(lax = true)
                                protected Object any;
                                @XmlAttribute(name = "IDComplementoItem")
                                @XmlSchemaType(name = "anySimpleType")
                                protected String idComplementoItem;
                                @XmlAttribute(name = "NombreComplementoItem", required = true)
                                @XmlSchemaType(name = "anySimpleType")
                                protected String nombreComplementoItem;
                                @XmlAttribute(name = "URIComplementoItem", required = true)
                                @XmlSchemaType(name = "anySimpleType")
                                protected String uriComplementoItem;

                                /**
                                 * Gets the value of the any property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link Object }
                                 *     
                                 */
                                public Object getAny() {
                                    return any;
                                }

                                /**
                                 * Sets the value of the any property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link Object }
                                 *     
                                 */
                                public void setAny(Object value) {
                                    this.any = value;
                                }

                                /**
                                 * Gets the value of the idComplementoItem property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                public String getIDComplementoItem() {
                                    return idComplementoItem;
                                }

                                /**
                                 * Sets the value of the idComplementoItem property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                public void setIDComplementoItem(String value) {
                                    this.idComplementoItem = value;
                                }

                                /**
                                 * Gets the value of the nombreComplementoItem property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                public String getNombreComplementoItem() {
                                    return nombreComplementoItem;
                                }

                                /**
                                 * Sets the value of the nombreComplementoItem property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                public void setNombreComplementoItem(String value) {
                                    this.nombreComplementoItem = value;
                                }

                                /**
                                 * Gets the value of the uriComplementoItem property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                public String getURIComplementoItem() {
                                    return uriComplementoItem;
                                }

                                /**
                                 * Sets the value of the uriComplementoItem property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                public void setURIComplementoItem(String value) {
                                    this.uriComplementoItem = value;
                                }

                            }

                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="Impuesto" maxOccurs="20">
                         *           &lt;complexType>
                         *             &lt;complexContent>
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *                 &lt;sequence>
                         *                   &lt;element name="NombreCorto">
                         *                     &lt;simpleType>
                         *                       &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
                         *                         &lt;whiteSpace value="collapse"/>
                         *                       &lt;/restriction>
                         *                     &lt;/simpleType>
                         *                   &lt;/element>
                         *                   &lt;element name="CodigoUnidadGravable">
                         *                     &lt;simpleType>
                         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                         *                         &lt;minInclusive value="1"/>
                         *                         &lt;totalDigits value="7"/>
                         *                       &lt;/restriction>
                         *                     &lt;/simpleType>
                         *                   &lt;/element>
                         *                   &lt;element name="MontoGravable" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                         *                   &lt;element name="CantidadUnidadesGravables" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                         *                   &lt;element name="MontoImpuesto">
                         *                     &lt;complexType>
                         *                       &lt;simpleContent>
                         *                         &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
                         *                         &lt;/extension>
                         *                       &lt;/simpleContent>
                         *                     &lt;/complexType>
                         *                   &lt;/element>
                         *                 &lt;/sequence>
                         *               &lt;/restriction>
                         *             &lt;/complexContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "impuesto"
                        })
                        public static class Impuestos {

                            @XmlElement(name = "Impuesto", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                            protected List<GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto> impuesto;

                            /**
                             * Gets the value of the impuesto property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the impuesto property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getImpuesto().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto }
                             * 
                             * 
                             */
                            public List<GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto> getImpuesto() {
                                if (impuesto == null) {
                                    impuesto = new ArrayList<GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto>();
                                }
                                return this.impuesto;
                            }


                            /**
                             * <p>Java class for anonymous complex type.
                             * 
                             * <p>The following schema fragment specifies the expected content contained within this class.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;complexContent>
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                             *       &lt;sequence>
                             *         &lt;element name="NombreCorto">
                             *           &lt;simpleType>
                             *             &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
                             *               &lt;whiteSpace value="collapse"/>
                             *             &lt;/restriction>
                             *           &lt;/simpleType>
                             *         &lt;/element>
                             *         &lt;element name="CodigoUnidadGravable">
                             *           &lt;simpleType>
                             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
                             *               &lt;minInclusive value="1"/>
                             *               &lt;totalDigits value="7"/>
                             *             &lt;/restriction>
                             *           &lt;/simpleType>
                             *         &lt;/element>
                             *         &lt;element name="MontoGravable" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                             *         &lt;element name="CantidadUnidadesGravables" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" minOccurs="0"/>
                             *         &lt;element name="MontoImpuesto">
                             *           &lt;complexType>
                             *             &lt;simpleContent>
                             *               &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
                             *               &lt;/extension>
                             *             &lt;/simpleContent>
                             *           &lt;/complexType>
                             *         &lt;/element>
                             *       &lt;/sequence>
                             *     &lt;/restriction>
                             *   &lt;/complexContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "nombreCorto",
                                "codigoUnidadGravable",
                                "montoGravable",
                                "cantidadUnidadesGravables",
                                "montoImpuesto"
                            })
                            public static class Impuesto {

                                @XmlElement(name = "NombreCorto", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                                protected TipoImpuesto nombreCorto;
                                @XmlElement(name = "CodigoUnidadGravable", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                                protected BigInteger codigoUnidadGravable;
                                @XmlElement(name = "MontoGravable", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                                protected BigDecimal montoGravable;
                                @XmlElement(name = "CantidadUnidadesGravables", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                                protected BigDecimal cantidadUnidadesGravables;
                                @XmlElement(name = "MontoImpuesto", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                                protected GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto.MontoImpuesto montoImpuesto;

                                /**
                                 * Gets the value of the nombreCorto property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link TipoImpuesto }
                                 *     
                                 */
                                public TipoImpuesto getNombreCorto() {
                                    return nombreCorto;
                                }

                                /**
                                 * Sets the value of the nombreCorto property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link TipoImpuesto }
                                 *     
                                 */
                                public void setNombreCorto(TipoImpuesto value) {
                                    this.nombreCorto = value;
                                }

                                /**
                                 * Gets the value of the codigoUnidadGravable property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link BigInteger }
                                 *     
                                 */
                                public BigInteger getCodigoUnidadGravable() {
                                    return codigoUnidadGravable;
                                }

                                /**
                                 * Sets the value of the codigoUnidadGravable property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link BigInteger }
                                 *     
                                 */
                                public void setCodigoUnidadGravable(BigInteger value) {
                                    this.codigoUnidadGravable = value;
                                }

                                /**
                                 * Gets the value of the montoGravable property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                public BigDecimal getMontoGravable() {
                                    return montoGravable;
                                }

                                /**
                                 * Sets the value of the montoGravable property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                public void setMontoGravable(BigDecimal value) {
                                    this.montoGravable = value;
                                }

                                /**
                                 * Gets the value of the cantidadUnidadesGravables property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                public BigDecimal getCantidadUnidadesGravables() {
                                    return cantidadUnidadesGravables;
                                }

                                /**
                                 * Sets the value of the cantidadUnidadesGravables property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link BigDecimal }
                                 *     
                                 */
                                public void setCantidadUnidadesGravables(BigDecimal value) {
                                    this.cantidadUnidadesGravables = value;
                                }

                                /**
                                 * Gets the value of the montoImpuesto property.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto.MontoImpuesto }
                                 *     
                                 */
                                public GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto.MontoImpuesto getMontoImpuesto() {
                                    return montoImpuesto;
                                }

                                /**
                                 * Sets the value of the montoImpuesto property.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto.MontoImpuesto }
                                 *     
                                 */
                                public void setMontoImpuesto(GTDocumento.SAT.DTE.DatosEmision.Items.Item.Impuestos.Impuesto.MontoImpuesto value) {
                                    this.montoImpuesto = value;
                                }


                                /**
                                 * <p>Java class for anonymous complex type.
                                 * 
                                 * <p>The following schema fragment specifies the expected content contained within this class.
                                 * 
                                 * <pre>
                                 * &lt;complexType>
                                 *   &lt;simpleContent>
                                 *     &lt;extension base="&lt;http://www.sat.gob.gt/dte/fel/0.1.0>NumeroNDecimales">
                                 *     &lt;/extension>
                                 *   &lt;/simpleContent>
                                 * &lt;/complexType>
                                 * </pre>
                                 * 
                                 * 
                                 */
                                @XmlAccessorType(XmlAccessType.FIELD)
                                @XmlType(name = "", propOrder = {
                                    "value"
                                })
                                public static class MontoImpuesto {

                                    @XmlValue
                                    protected BigDecimal value;

                                    /**
                                     * Acepta cantidades mayores a 0
                                     * 
                                     * @return
                                     *     possible object is
                                     *     {@link BigDecimal }
                                     *     
                                     */
                                    public BigDecimal getValue() {
                                        return value;
                                    }

                                    /**
                                     * Sets the value of the value property.
                                     * 
                                     * @param value
                                     *     allowed object is
                                     *     {@link BigDecimal }
                                     *     
                                     */
                                    public void setValue(BigDecimal value) {
                                        this.value = value;
                                    }

                                }

                            }

                        }

                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="DireccionReceptor" type="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoDireccion" minOccurs="0"/>
                 *       &lt;/sequence>
                 *       &lt;attribute name="IDReceptor" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}tipoNITReceptor">
                 *             &lt;whiteSpace value="collapse"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="TipoEspecial">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;whiteSpace value="collapse"/>
                 *             &lt;enumeration value="CUI"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="NombreReceptor" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;minLength value="1"/>
                 *             &lt;maxLength value="255"/>
                 *             &lt;whiteSpace value="collapse"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="CorreoReceptor">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoCorreoElectronico">
                 *             &lt;whiteSpace value="collapse"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "direccionReceptor"
                })
                public static class Receptor {

                    @XmlElement(name = "DireccionReceptor", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                    protected TipoDireccion direccionReceptor;
                    @XmlAttribute(name = "IDReceptor", required = true)
                    protected String idReceptor;
                    @XmlAttribute(name = "TipoEspecial")
                    protected String tipoEspecial;
                    @XmlAttribute(name = "NombreReceptor", required = true)
                    protected String nombreReceptor;
                    @XmlAttribute(name = "CorreoReceptor")
                    protected String correoReceptor;

                    /**
                     * Gets the value of the direccionReceptor property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TipoDireccion }
                     *     
                     */
                    public TipoDireccion getDireccionReceptor() {
                        return direccionReceptor;
                    }

                    /**
                     * Sets the value of the direccionReceptor property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TipoDireccion }
                     *     
                     */
                    public void setDireccionReceptor(TipoDireccion value) {
                        this.direccionReceptor = value;
                    }

                    /**
                     * Gets the value of the idReceptor property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getIDReceptor() {
                        return idReceptor;
                    }

                    /**
                     * Sets the value of the idReceptor property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setIDReceptor(String value) {
                        this.idReceptor = value;
                    }

                    /**
                     * Gets the value of the tipoEspecial property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoEspecial() {
                        return tipoEspecial;
                    }

                    /**
                     * Sets the value of the tipoEspecial property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoEspecial(String value) {
                        this.tipoEspecial = value;
                    }

                    /**
                     * Gets the value of the nombreReceptor property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNombreReceptor() {
                        return nombreReceptor;
                    }

                    /**
                     * Sets the value of the nombreReceptor property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNombreReceptor(String value) {
                        this.nombreReceptor = value;
                    }

                    /**
                     * Gets the value of the correoReceptor property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCorreoReceptor() {
                        return correoReceptor;
                    }

                    /**
                     * Sets the value of the correoReceptor property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCorreoReceptor(String value) {
                        this.correoReceptor = value;
                    }

                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="TotalImpuestos" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="TotalImpuesto" maxOccurs="20">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;attribute name="NombreCorto" use="required">
                 *                             &lt;simpleType>
                 *                               &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
                 *                                 &lt;whiteSpace value="collapse"/>
                 *                               &lt;/restriction>
                 *                             &lt;/simpleType>
                 *                           &lt;/attribute>
                 *                           &lt;attribute name="TotalMontoImpuesto" use="required" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" />
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="GranTotal" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales"/>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "totalImpuestos",
                    "granTotal"
                })
                public static class Totales {

                    @XmlElement(name = "TotalImpuestos", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
                    protected GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos totalImpuestos;
                    @XmlElement(name = "GranTotal", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                    protected BigDecimal granTotal;

                    /**
                     * Gets the value of the totalImpuestos property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos }
                     *     
                     */
                    public GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos getTotalImpuestos() {
                        return totalImpuestos;
                    }

                    /**
                     * Sets the value of the totalImpuestos property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos }
                     *     
                     */
                    public void setTotalImpuestos(GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos value) {
                        this.totalImpuestos = value;
                    }

                    /**
                     * Gets the value of the granTotal property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getGranTotal() {
                        return granTotal;
                    }

                    /**
                     * Sets the value of the granTotal property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setGranTotal(BigDecimal value) {
                        this.granTotal = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="TotalImpuesto" maxOccurs="20">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;attribute name="NombreCorto" use="required">
                     *                   &lt;simpleType>
                     *                     &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
                     *                       &lt;whiteSpace value="collapse"/>
                     *                     &lt;/restriction>
                     *                   &lt;/simpleType>
                     *                 &lt;/attribute>
                     *                 &lt;attribute name="TotalMontoImpuesto" use="required" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" />
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "totalImpuesto"
                    })
                    public static class TotalImpuestos {

                        @XmlElement(name = "TotalImpuesto", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0", required = true)
                        protected List<GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos.TotalImpuesto> totalImpuesto;

                        /**
                         * Gets the value of the totalImpuesto property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the totalImpuesto property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getTotalImpuesto().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos.TotalImpuesto }
                         * 
                         * 
                         */
                        public List<GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos.TotalImpuesto> getTotalImpuesto() {
                            if (totalImpuesto == null) {
                                totalImpuesto = new ArrayList<GTDocumento.SAT.DTE.DatosEmision.Totales.TotalImpuestos.TotalImpuesto>();
                            }
                            return this.totalImpuesto;
                        }


                        /**
                         * <p>Java class for anonymous complex type.
                         * 
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;attribute name="NombreCorto" use="required">
                         *         &lt;simpleType>
                         *           &lt;restriction base="{http://www.sat.gob.gt/dte/fel/0.1.0}TipoImpuesto">
                         *             &lt;whiteSpace value="collapse"/>
                         *           &lt;/restriction>
                         *         &lt;/simpleType>
                         *       &lt;/attribute>
                         *       &lt;attribute name="TotalMontoImpuesto" use="required" type="{http://www.sat.gob.gt/dte/fel/0.1.0}NumeroNDecimales" />
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "")
                        public static class TotalImpuesto {

                            @XmlAttribute(name = "NombreCorto", required = true)
                            protected TipoImpuesto nombreCorto;
                            @XmlAttribute(name = "TotalMontoImpuesto", required = true)
                            protected BigDecimal totalMontoImpuesto;

                            /**
                             * Gets the value of the nombreCorto property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link TipoImpuesto }
                             *     
                             */
                            public TipoImpuesto getNombreCorto() {
                                return nombreCorto;
                            }

                            /**
                             * Sets the value of the nombreCorto property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link TipoImpuesto }
                             *     
                             */
                            public void setNombreCorto(TipoImpuesto value) {
                                this.nombreCorto = value;
                            }

                            /**
                             * Gets the value of the totalMontoImpuesto property.
                             * 
                             * @return
                             *     possible object is
                             *     {@link BigDecimal }
                             *     
                             */
                            public BigDecimal getTotalMontoImpuesto() {
                                return totalMontoImpuesto;
                            }

                            /**
                             * Sets the value of the totalMontoImpuesto property.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link BigDecimal }
                             *     
                             */
                            public void setTotalMontoImpuesto(BigDecimal value) {
                                this.totalMontoImpuesto = value;
                            }
                        }
                    }
                }
            }
        }
    }
}
