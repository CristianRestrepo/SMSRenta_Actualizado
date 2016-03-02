
package ClienteTest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CardData complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CardData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="brand" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expiryMonth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="expiryYear" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="financialId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="accountTypeId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="securityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardData", propOrder = {
    "brand",
    "number",
    "expiryMonth",
    "expiryYear",
    "financialId",
    "accountTypeId",
    "securityCode"
})
public class CardData {

    @XmlElement(required = true)
    protected String brand;
    @XmlElement(required = true)
    protected String number;
    protected int expiryMonth;
    protected int expiryYear;
    protected Integer financialId;
    protected Integer accountTypeId;
    protected String securityCode;

    /**
     * Obtiene el valor de la propiedad brand.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Define el valor de la propiedad brand.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * Obtiene el valor de la propiedad number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Define el valor de la propiedad number.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Obtiene el valor de la propiedad expiryMonth.
     * 
     */
    public int getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * Define el valor de la propiedad expiryMonth.
     * 
     */
    public void setExpiryMonth(int value) {
        this.expiryMonth = value;
    }

    /**
     * Obtiene el valor de la propiedad expiryYear.
     * 
     */
    public int getExpiryYear() {
        return expiryYear;
    }

    /**
     * Define el valor de la propiedad expiryYear.
     * 
     */
    public void setExpiryYear(int value) {
        this.expiryYear = value;
    }

    /**
     * Obtiene el valor de la propiedad financialId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFinancialId() {
        return financialId;
    }

    /**
     * Define el valor de la propiedad financialId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFinancialId(Integer value) {
        this.financialId = value;
    }

    /**
     * Obtiene el valor de la propiedad accountTypeId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    /**
     * Define el valor de la propiedad accountTypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAccountTypeId(Integer value) {
        this.accountTypeId = value;
    }

    /**
     * Obtiene el valor de la propiedad securityCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * Define el valor de la propiedad securityCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityCode(String value) {
        this.securityCode = value;
    }

}
