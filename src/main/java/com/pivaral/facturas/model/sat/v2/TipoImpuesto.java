//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.14 at 10:22:06 AM CST 
//


package com.pivaral.facturas.model.sat.v2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoImpuesto.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoImpuesto">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IVA"/>
 *     &lt;enumeration value="PETROLEO"/>
 *     &lt;enumeration value="TURISMO HOSPEDAJE"/>
 *     &lt;enumeration value="TURISMO PASAJES"/>
 *     &lt;enumeration value="TIMBRE DE PRENSA"/>
 *     &lt;enumeration value="BOMBEROS"/>
 *     &lt;enumeration value="TASA MUNICIPAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoImpuesto", namespace = "http://www.sat.gob.gt/dte/fel/0.2.0")
@XmlEnum
public enum TipoImpuesto {

    IVA("IVA"),
    PETROLEO("PETROLEO"),
    @XmlEnumValue("TURISMO HOSPEDAJE")
    TURISMO_HOSPEDAJE("TURISMO HOSPEDAJE"),
    @XmlEnumValue("TURISMO PASAJES")
    TURISMO_PASAJES("TURISMO PASAJES"),
    @XmlEnumValue("TIMBRE DE PRENSA")
    TIMBRE_DE_PRENSA("TIMBRE DE PRENSA"),
    BOMBEROS("BOMBEROS"),
    @XmlEnumValue("TASA MUNICIPAL")
    TASA_MUNICIPAL("TASA MUNICIPAL");
    private final String value;

    TipoImpuesto(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoImpuesto fromValue(String v) {
        for (TipoImpuesto c: TipoImpuesto.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
