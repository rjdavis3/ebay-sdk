//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.07 at 10:29:05 AM EST 
//

package com.ebay.shopping.categories.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * A variable that contains specific information about the context of this
 * error. For example, if you pass in an attribute set ID that does not match
 * the specified category, the attribute set ID might be returned as an error
 * parameter. Use error parameters to flag fields that users need to correct.
 * Also use error parameters to distinguish between errors when multiple errors
 * are returned.
 * 
 * 
 * <p>
 * Java class for ErrorParameterType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorParameterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ParamID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorParameterType", propOrder = { "value" })
public class ErrorParameterType {

	@XmlElement(name = "Value")
	protected String value;
	@XmlAttribute(name = "ParamID")
	protected String paramID;

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the value property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the value of the paramID property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getParamID() {
		return paramID;
	}

	/**
	 * Sets the value of the paramID property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setParamID(String value) {
		this.paramID = value;
	}

}
