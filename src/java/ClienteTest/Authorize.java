
package ClienteTest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="commerceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="acquirerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ipAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionTrace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fingerprint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="additionalObservations" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="purchaseData" type="{http://www.alignet.com/VPOSWS20/}PurchaseData" minOccurs="0"/>
 *         &lt;element name="cardData" type="{http://www.alignet.com/VPOSWS20/}CardData" minOccurs="0"/>
 *         &lt;element name="billingData" type="{http://www.alignet.com/VPOSWS20/}Person" minOccurs="0"/>
 *         &lt;element name="shippingData" type="{http://www.alignet.com/VPOSWS20/}ShippingData" minOccurs="0"/>
 *         &lt;element name="orderWeb" type="{http://www.alignet.com/VPOSWS20/}OrderWeb" minOccurs="0"/>
 *         &lt;element name="flight" type="{http://www.alignet.com/VPOSWS20/}Flight" minOccurs="0"/>
 *         &lt;element name="good" type="{http://www.alignet.com/VPOSWS20/}Good" minOccurs="0"/>
 *         &lt;element name="administrativeRate" type="{http://www.alignet.com/VPOSWS20/}AdministrativeRate" minOccurs="0"/>
 *         &lt;element name="taxes" type="{http://www.alignet.com/VPOSWS20/}Tax" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reservedFields" type="{http://www.alignet.com/VPOSWS20/}ReservedField" maxOccurs="unbounded" minOccurs="0"/>
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
    "commerceId",
    "acquirerId",
    "language",
    "ipAddress",
    "transactionTrace",
    "fingerprint",
    "additionalObservations",
    "purchaseData",
    "cardData",
    "billingData",
    "shippingData",
    "orderWeb",
    "flight",
    "good",
    "administrativeRate",
    "taxes",
    "reservedFields"
})
@XmlRootElement(name = "authorize")
public class Authorize {

    protected Integer commerceId;
    protected Integer acquirerId;
    protected String language;
    protected String ipAddress;
    protected String transactionTrace;
    protected String fingerprint;
    protected String additionalObservations;
    protected PurchaseData purchaseData;
    protected CardData cardData;
    protected Person billingData;
    protected ShippingData shippingData;
    protected OrderWeb orderWeb;
    protected Flight flight;
    protected Good good;
    protected AdministrativeRate administrativeRate;
    protected List<Tax> taxes;
    protected List<ReservedField> reservedFields;

    /**
     * Obtiene el valor de la propiedad commerceId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCommerceId() {
        return commerceId;
    }

    /**
     * Define el valor de la propiedad commerceId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCommerceId(Integer value) {
        this.commerceId = value;
    }

    /**
     * Obtiene el valor de la propiedad acquirerId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAcquirerId() {
        return acquirerId;
    }

    /**
     * Define el valor de la propiedad acquirerId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAcquirerId(Integer value) {
        this.acquirerId = value;
    }

    /**
     * Obtiene el valor de la propiedad language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Define el valor de la propiedad language.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Obtiene el valor de la propiedad ipAddress.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Define el valor de la propiedad ipAddress.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpAddress(String value) {
        this.ipAddress = value;
    }

    /**
     * Obtiene el valor de la propiedad transactionTrace.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionTrace() {
        return transactionTrace;
    }

    /**
     * Define el valor de la propiedad transactionTrace.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionTrace(String value) {
        this.transactionTrace = value;
    }

    /**
     * Obtiene el valor de la propiedad fingerprint.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     * Define el valor de la propiedad fingerprint.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFingerprint(String value) {
        this.fingerprint = value;
    }

    /**
     * Obtiene el valor de la propiedad additionalObservations.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalObservations() {
        return additionalObservations;
    }

    /**
     * Define el valor de la propiedad additionalObservations.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalObservations(String value) {
        this.additionalObservations = value;
    }

    /**
     * Obtiene el valor de la propiedad purchaseData.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseData }
     *     
     */
    public PurchaseData getPurchaseData() {
        return purchaseData;
    }

    /**
     * Define el valor de la propiedad purchaseData.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseData }
     *     
     */
    public void setPurchaseData(PurchaseData value) {
        this.purchaseData = value;
    }

    /**
     * Obtiene el valor de la propiedad cardData.
     * 
     * @return
     *     possible object is
     *     {@link CardData }
     *     
     */
    public CardData getCardData() {
        return cardData;
    }

    /**
     * Define el valor de la propiedad cardData.
     * 
     * @param value
     *     allowed object is
     *     {@link CardData }
     *     
     */
    public void setCardData(CardData value) {
        this.cardData = value;
    }

    /**
     * Obtiene el valor de la propiedad billingData.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getBillingData() {
        return billingData;
    }

    /**
     * Define el valor de la propiedad billingData.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setBillingData(Person value) {
        this.billingData = value;
    }

    /**
     * Obtiene el valor de la propiedad shippingData.
     * 
     * @return
     *     possible object is
     *     {@link ShippingData }
     *     
     */
    public ShippingData getShippingData() {
        return shippingData;
    }

    /**
     * Define el valor de la propiedad shippingData.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingData }
     *     
     */
    public void setShippingData(ShippingData value) {
        this.shippingData = value;
    }

    /**
     * Obtiene el valor de la propiedad orderWeb.
     * 
     * @return
     *     possible object is
     *     {@link OrderWeb }
     *     
     */
    public OrderWeb getOrderWeb() {
        return orderWeb;
    }

    /**
     * Define el valor de la propiedad orderWeb.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderWeb }
     *     
     */
    public void setOrderWeb(OrderWeb value) {
        this.orderWeb = value;
    }

    /**
     * Obtiene el valor de la propiedad flight.
     * 
     * @return
     *     possible object is
     *     {@link Flight }
     *     
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Define el valor de la propiedad flight.
     * 
     * @param value
     *     allowed object is
     *     {@link Flight }
     *     
     */
    public void setFlight(Flight value) {
        this.flight = value;
    }

    /**
     * Obtiene el valor de la propiedad good.
     * 
     * @return
     *     possible object is
     *     {@link Good }
     *     
     */
    public Good getGood() {
        return good;
    }

    /**
     * Define el valor de la propiedad good.
     * 
     * @param value
     *     allowed object is
     *     {@link Good }
     *     
     */
    public void setGood(Good value) {
        this.good = value;
    }

    /**
     * Obtiene el valor de la propiedad administrativeRate.
     * 
     * @return
     *     possible object is
     *     {@link AdministrativeRate }
     *     
     */
    public AdministrativeRate getAdministrativeRate() {
        return administrativeRate;
    }

    /**
     * Define el valor de la propiedad administrativeRate.
     * 
     * @param value
     *     allowed object is
     *     {@link AdministrativeRate }
     *     
     */
    public void setAdministrativeRate(AdministrativeRate value) {
        this.administrativeRate = value;
    }

    /**
     * Gets the value of the taxes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaxes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tax }
     * 
     * 
     */
    public List<Tax> getTaxes() {
        if (taxes == null) {
            taxes = new ArrayList<Tax>();
        }
        return this.taxes;
    }

    /**
     * Gets the value of the reservedFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reservedFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReservedFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReservedField }
     * 
     * 
     */
    public List<ReservedField> getReservedFields() {
        if (reservedFields == null) {
            reservedFields = new ArrayList<ReservedField>();
        }
        return this.reservedFields;
    }

}
