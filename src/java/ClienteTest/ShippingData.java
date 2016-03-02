
package ClienteTest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ShippingData complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ShippingData">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.alignet.com/VPOSWS20/}Person">
 *       &lt;sequence>
 *         &lt;element name="receptionMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiverName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiverLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiverIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shippingCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shippingCharges" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShippingData", propOrder = {
    "receptionMethod",
    "receiverName",
    "receiverLastName",
    "receiverIdentifier",
    "shippingCode",
    "shippingCharges"
})
public class ShippingData
    extends Person
{

    protected String receptionMethod;
    protected String receiverName;
    protected String receiverLastName;
    protected String receiverIdentifier;
    protected String shippingCode;
    protected String shippingCharges;

    /**
     * Obtiene el valor de la propiedad receptionMethod.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceptionMethod() {
        return receptionMethod;
    }

    /**
     * Define el valor de la propiedad receptionMethod.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceptionMethod(String value) {
        this.receptionMethod = value;
    }

    /**
     * Obtiene el valor de la propiedad receiverName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * Define el valor de la propiedad receiverName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverName(String value) {
        this.receiverName = value;
    }

    /**
     * Obtiene el valor de la propiedad receiverLastName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverLastName() {
        return receiverLastName;
    }

    /**
     * Define el valor de la propiedad receiverLastName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverLastName(String value) {
        this.receiverLastName = value;
    }

    /**
     * Obtiene el valor de la propiedad receiverIdentifier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverIdentifier() {
        return receiverIdentifier;
    }

    /**
     * Define el valor de la propiedad receiverIdentifier.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverIdentifier(String value) {
        this.receiverIdentifier = value;
    }

    /**
     * Obtiene el valor de la propiedad shippingCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingCode() {
        return shippingCode;
    }

    /**
     * Define el valor de la propiedad shippingCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingCode(String value) {
        this.shippingCode = value;
    }

    /**
     * Obtiene el valor de la propiedad shippingCharges.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingCharges() {
        return shippingCharges;
    }

    /**
     * Define el valor de la propiedad shippingCharges.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingCharges(String value) {
        this.shippingCharges = value;
    }

}
