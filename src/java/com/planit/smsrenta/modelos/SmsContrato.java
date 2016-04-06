/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.modelos;

/**
 *
 * @author Desarrollo_Planit
 */
public class SmsContrato {
    
    private Integer idContrato;
    private SmsReservacion smsReservacion;
    private String contratoObjeto;

    public SmsContrato() {
    }

    public SmsContrato(SmsReservacion smsReservacion) {
        this.smsReservacion = smsReservacion;
    }

    public SmsContrato(SmsReservacion smsReservacion, String contratoObjeto) {
        this.smsReservacion = smsReservacion;
        this.contratoObjeto = contratoObjeto;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public SmsReservacion getSmsReservacion() {
        return smsReservacion;
    }

    public void setSmsReservacion(SmsReservacion smsReservacion) {
        this.smsReservacion = smsReservacion;
    }

    public String getContratoObjeto() {
        return contratoObjeto;
    }

    public void setContratoObjeto(String contratoObjeto) {
        this.contratoObjeto = contratoObjeto;
    }
    
    
    
    
}
