
package ClienteTest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Flight complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Flight">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.alignet.com/VPOSWS20/}Product">
 *       &lt;sequence>
 *         &lt;element name="airlineCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departureAirport" type="{http://www.alignet.com/VPOSWS20/}Airport" minOccurs="0"/>
 *         &lt;element name="arriveAirport" type="{http://www.alignet.com/VPOSWS20/}Airport" minOccurs="0"/>
 *         &lt;element name="departureDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departureTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arriveDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arriveTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="passengerList" type="{http://www.alignet.com/VPOSWS20/}Passenger" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reservation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Flight", propOrder = {
    "airlineCode",
    "departureAirport",
    "arriveAirport",
    "departureDate",
    "departureTime",
    "arriveDate",
    "arriveTime",
    "passengerList",
    "reservation"
})
public class Flight
    extends Product
{

    protected String airlineCode;
    protected Airport departureAirport;
    protected Airport arriveAirport;
    protected String departureDate;
    protected String departureTime;
    protected String arriveDate;
    protected String arriveTime;
    protected List<Passenger> passengerList;
    protected String reservation;

    /**
     * Obtiene el valor de la propiedad airlineCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirlineCode() {
        return airlineCode;
    }

    /**
     * Define el valor de la propiedad airlineCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirlineCode(String value) {
        this.airlineCode = value;
    }

    /**
     * Obtiene el valor de la propiedad departureAirport.
     * 
     * @return
     *     possible object is
     *     {@link Airport }
     *     
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /**
     * Define el valor de la propiedad departureAirport.
     * 
     * @param value
     *     allowed object is
     *     {@link Airport }
     *     
     */
    public void setDepartureAirport(Airport value) {
        this.departureAirport = value;
    }

    /**
     * Obtiene el valor de la propiedad arriveAirport.
     * 
     * @return
     *     possible object is
     *     {@link Airport }
     *     
     */
    public Airport getArriveAirport() {
        return arriveAirport;
    }

    /**
     * Define el valor de la propiedad arriveAirport.
     * 
     * @param value
     *     allowed object is
     *     {@link Airport }
     *     
     */
    public void setArriveAirport(Airport value) {
        this.arriveAirport = value;
    }

    /**
     * Obtiene el valor de la propiedad departureDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * Define el valor de la propiedad departureDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDate(String value) {
        this.departureDate = value;
    }

    /**
     * Obtiene el valor de la propiedad departureTime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Define el valor de la propiedad departureTime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureTime(String value) {
        this.departureTime = value;
    }

    /**
     * Obtiene el valor de la propiedad arriveDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArriveDate() {
        return arriveDate;
    }

    /**
     * Define el valor de la propiedad arriveDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArriveDate(String value) {
        this.arriveDate = value;
    }

    /**
     * Obtiene el valor de la propiedad arriveTime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArriveTime() {
        return arriveTime;
    }

    /**
     * Define el valor de la propiedad arriveTime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArriveTime(String value) {
        this.arriveTime = value;
    }

    /**
     * Gets the value of the passengerList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the passengerList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPassengerList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Passenger }
     * 
     * 
     */
    public List<Passenger> getPassengerList() {
        if (passengerList == null) {
            passengerList = new ArrayList<Passenger>();
        }
        return this.passengerList;
    }

    /**
     * Obtiene el valor de la propiedad reservation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservation() {
        return reservation;
    }

    /**
     * Define el valor de la propiedad reservation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservation(String value) {
        this.reservation = value;
    }

}
