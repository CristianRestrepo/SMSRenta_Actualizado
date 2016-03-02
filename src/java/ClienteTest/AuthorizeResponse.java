
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
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="additionalMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authorizationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authorizedAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fraudScore" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fraudFactors" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resultAR" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="errorCodeAR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorMessageAR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorizationCodeAR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="authorizedAmountAR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fraudScoreAR" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
    "result",
    "errorCode",
    "errorMessage",
    "additionalMessage",
    "authorizationCode",
    "authorizedAmount",
    "fraudScore",
    "fraudFactors",
    "resultAR",
    "errorCodeAR",
    "errorMessageAR",
    "authorizationCodeAR",
    "authorizedAmountAR",
    "fraudScoreAR"
})
@XmlRootElement(name = "authorizeResponse")
public class AuthorizeResponse {

    protected int result;
    @XmlElement(required = true)
    protected String errorCode;
    @XmlElement(required = true)
    protected String errorMessage;
    @XmlElement(required = true)
    protected String additionalMessage;
    @XmlElement(required = true)
    protected String authorizationCode;
    @XmlElement(required = true)
    protected String authorizedAmount;
    protected int fraudScore;
    @XmlElement(required = true)
    protected String fraudFactors;
    protected Integer resultAR;
    protected String errorCodeAR;
    protected String errorMessageAR;
    protected String authorizationCodeAR;
    protected String authorizedAmountAR;
    protected Integer fraudScoreAR;

    /**
     * Obtiene el valor de la propiedad result.
     * 
     */
    public int getResult() {
        return result;
    }

    /**
     * Define el valor de la propiedad result.
     * 
     */
    public void setResult(int value) {
        this.result = value;
    }

    /**
     * Obtiene el valor de la propiedad errorCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Define el valor de la propiedad errorCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Obtiene el valor de la propiedad errorMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Define el valor de la propiedad errorMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Obtiene el valor de la propiedad additionalMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalMessage() {
        return additionalMessage;
    }

    /**
     * Define el valor de la propiedad additionalMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalMessage(String value) {
        this.additionalMessage = value;
    }

    /**
     * Obtiene el valor de la propiedad authorizationCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * Define el valor de la propiedad authorizationCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationCode(String value) {
        this.authorizationCode = value;
    }

    /**
     * Obtiene el valor de la propiedad authorizedAmount.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizedAmount() {
        return authorizedAmount;
    }

    /**
     * Define el valor de la propiedad authorizedAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizedAmount(String value) {
        this.authorizedAmount = value;
    }

    /**
     * Obtiene el valor de la propiedad fraudScore.
     * 
     */
    public int getFraudScore() {
        return fraudScore;
    }

    /**
     * Define el valor de la propiedad fraudScore.
     * 
     */
    public void setFraudScore(int value) {
        this.fraudScore = value;
    }

    /**
     * Obtiene el valor de la propiedad fraudFactors.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFraudFactors() {
        return fraudFactors;
    }

    /**
     * Define el valor de la propiedad fraudFactors.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFraudFactors(String value) {
        this.fraudFactors = value;
    }

    /**
     * Obtiene el valor de la propiedad resultAR.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getResultAR() {
        return resultAR;
    }

    /**
     * Define el valor de la propiedad resultAR.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setResultAR(Integer value) {
        this.resultAR = value;
    }

    /**
     * Obtiene el valor de la propiedad errorCodeAR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCodeAR() {
        return errorCodeAR;
    }

    /**
     * Define el valor de la propiedad errorCodeAR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCodeAR(String value) {
        this.errorCodeAR = value;
    }

    /**
     * Obtiene el valor de la propiedad errorMessageAR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessageAR() {
        return errorMessageAR;
    }

    /**
     * Define el valor de la propiedad errorMessageAR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessageAR(String value) {
        this.errorMessageAR = value;
    }

    /**
     * Obtiene el valor de la propiedad authorizationCodeAR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationCodeAR() {
        return authorizationCodeAR;
    }

    /**
     * Define el valor de la propiedad authorizationCodeAR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationCodeAR(String value) {
        this.authorizationCodeAR = value;
    }

    /**
     * Obtiene el valor de la propiedad authorizedAmountAR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizedAmountAR() {
        return authorizedAmountAR;
    }

    /**
     * Define el valor de la propiedad authorizedAmountAR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizedAmountAR(String value) {
        this.authorizedAmountAR = value;
    }

    /**
     * Obtiene el valor de la propiedad fraudScoreAR.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFraudScoreAR() {
        return fraudScoreAR;
    }

    /**
     * Define el valor de la propiedad fraudScoreAR.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFraudScoreAR(Integer value) {
        this.fraudScoreAR = value;
    }

}
