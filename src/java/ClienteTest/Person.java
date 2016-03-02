
package ClienteTest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Person complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Person">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastNames" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numberIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="address" type="{http://www.alignet.com/VPOSWS20/}AddressData" minOccurs="0"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outIdentifierCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateIdentifierDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person", propOrder = {
    "names",
    "lastNames",
    "numberIdentifier",
    "gender",
    "address",
    "birthday",
    "outIdentifierCity",
    "dateIdentifierDate",
    "nationality"
})
@XmlSeeAlso({
    ShippingData.class
})
public class Person {

    @XmlElement(required = true)
    protected String names;
    @XmlElement(required = true)
    protected String lastNames;
    protected String numberIdentifier;
    protected String gender;
    protected AddressData address;
    protected String birthday;
    protected String outIdentifierCity;
    protected String dateIdentifierDate;
    protected String nationality;

    /**
     * Obtiene el valor de la propiedad names.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNames() {
        return names;
    }

    /**
     * Define el valor de la propiedad names.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNames(String value) {
        this.names = value;
    }

    /**
     * Obtiene el valor de la propiedad lastNames.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastNames() {
        return lastNames;
    }

    /**
     * Define el valor de la propiedad lastNames.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastNames(String value) {
        this.lastNames = value;
    }

    /**
     * Obtiene el valor de la propiedad numberIdentifier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberIdentifier() {
        return numberIdentifier;
    }

    /**
     * Define el valor de la propiedad numberIdentifier.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberIdentifier(String value) {
        this.numberIdentifier = value;
    }

    /**
     * Obtiene el valor de la propiedad gender.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Define el valor de la propiedad gender.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Obtiene el valor de la propiedad address.
     * 
     * @return
     *     possible object is
     *     {@link AddressData }
     *     
     */
    public AddressData getAddress() {
        return address;
    }

    /**
     * Define el valor de la propiedad address.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressData }
     *     
     */
    public void setAddress(AddressData value) {
        this.address = value;
    }

    /**
     * Obtiene el valor de la propiedad birthday.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Define el valor de la propiedad birthday.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthday(String value) {
        this.birthday = value;
    }

    /**
     * Obtiene el valor de la propiedad outIdentifierCity.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutIdentifierCity() {
        return outIdentifierCity;
    }

    /**
     * Define el valor de la propiedad outIdentifierCity.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutIdentifierCity(String value) {
        this.outIdentifierCity = value;
    }

    /**
     * Obtiene el valor de la propiedad dateIdentifierDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateIdentifierDate() {
        return dateIdentifierDate;
    }

    /**
     * Define el valor de la propiedad dateIdentifierDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateIdentifierDate(String value) {
        this.dateIdentifierDate = value;
    }

    /**
     * Obtiene el valor de la propiedad nationality.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Define el valor de la propiedad nationality.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

}
