//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.12 at 04:48:56 PM CST 
//


package com.pivaral.facturas.model.sat.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoMoneda.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipoMoneda">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;length value="3"/>
 *     &lt;enumeration value="GTQ"/>
 *     &lt;enumeration value="USD"/>
 *     &lt;enumeration value="VES"/>
 *     &lt;enumeration value="CRC"/>
 *     &lt;enumeration value="SVC"/>
 *     &lt;enumeration value="NIO"/>
 *     &lt;enumeration value="DKK"/>
 *     &lt;enumeration value="NOK"/>
 *     &lt;enumeration value="SEK"/>
 *     &lt;enumeration value="CAD"/>
 *     &lt;enumeration value="HKD"/>
 *     &lt;enumeration value="TWD"/>
 *     &lt;enumeration value="PTE"/>
 *     &lt;enumeration value="EUR"/>
 *     &lt;enumeration value="CHF"/>
 *     &lt;enumeration value="HNL"/>
 *     &lt;enumeration value="GBP"/>
 *     &lt;enumeration value="ARS"/>
 *     &lt;enumeration value="DOP"/>
 *     &lt;enumeration value="COP"/>
 *     &lt;enumeration value="MXN"/>
 *     &lt;enumeration value="BRL"/>
 *     &lt;enumeration value="MYR"/>
 *     &lt;enumeration value="INR"/>
 *     &lt;enumeration value="PKR"/>
 *     &lt;enumeration value="KPW"/>
 *     &lt;enumeration value="JPY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "tipoMoneda", namespace = "http://www.sat.gob.gt/dte/fel/0.1.0")
@XmlEnum
public enum TipoMoneda {

    GTQ,
    USD,
    VES,
    CRC,
    SVC,
    NIO,
    DKK,
    NOK,
    SEK,
    CAD,
    HKD,
    TWD,
    PTE,
    EUR,
    CHF,
    HNL,
    GBP,
    ARS,
    DOP,
    COP,
    MXN,
    BRL,
    MYR,
    INR,
    PKR,
    KPW,
    JPY;

    public String value() {
        return name();
    }

    public static TipoMoneda fromValue(String v) {
        return valueOf(v);
    }

}