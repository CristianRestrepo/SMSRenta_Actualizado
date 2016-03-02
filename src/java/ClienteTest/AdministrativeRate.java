
package ClienteTest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para AdministrativeRate complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="AdministrativeRate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="administrativeRateAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="administrativeRateIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="administrativeRateIvaReturn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="administrativeRateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdministrativeRate", propOrder = {
    "administrativeRateAmount",
    "administrativeRateIva",
    "administrativeRateIvaReturn",
    "administrativeRateCode"
})
public class AdministrativeRate {

    @XmlElement(required = true)
    protected String administrativeRateAmount;
    protected String administrativeRateIva;
    protected String administrativeRateIvaReturn;
    protected String administrativeRateCode;

    /**
     * Obtiene el valor de la propiedad administrativeRateAmount.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrativeRateAmount() {
        return administrativeRateAmount;
    }

    /**
     * Define el valor de la propiedad administrativeRateAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrativeRateAmount(String value) {
        this.administrativeRateAmount = value;
    }

    /**
     * Obtiene el valor de la propiedad administrativeRateIva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrativeRateIva() {
        return administrativeRateIva;
    }

    /**
     * Define el valor de la propiedad administrativeRateIva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrativeRateIva(String value) {
        this.administrativeRateIva = value;
    }

    /**
     * Obtiene el valor de la propiedad administrativeRateIvaReturn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrativeRateIvaReturn() {
        return administrativeRateIvaReturn;
    }

    /**
     * Define el valor de la propiedad administrativeRateIvaReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrativeRateIvaReturn(String value) {
        this.administrativeRateIvaReturn = value;
    }

    /**
     * Obtiene el valor de la propiedad administrativeRateCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministrativeRateCode() {
        return administrativeRateCode;
    }

    /**
     * Define el valor de la propiedad administrativeRateCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministrativeRateCode(String value) {
        this.administrativeRateCode = value;
    }

}
