
package ClienteTest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="acquirerId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="commerceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="purchaseCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "acquirerId",
    "commerceId",
    "purchaseCode"
})
@XmlRootElement(name = "reverse")
public class Reverse {

    protected int acquirerId;
    protected int commerceId;
    @XmlElement(required = true)
    protected String purchaseCode;

    /**
     * Obtiene el valor de la propiedad acquirerId.
     * 
     */
    public int getAcquirerId() {
        return acquirerId;
    }

    /**
     * Define el valor de la propiedad acquirerId.
     * 
     */
    public void setAcquirerId(int value) {
        this.acquirerId = value;
    }

    /**
     * Obtiene el valor de la propiedad commerceId.
     * 
     */
    public int getCommerceId() {
        return commerceId;
    }

    /**
     * Define el valor de la propiedad commerceId.
     * 
     */
    public void setCommerceId(int value) {
        this.commerceId = value;
    }

    /**
     * Obtiene el valor de la propiedad purchaseCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurchaseCode() {
        return purchaseCode;
    }

    /**
     * Define el valor de la propiedad purchaseCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurchaseCode(String value) {
        this.purchaseCode = value;
    }

}
