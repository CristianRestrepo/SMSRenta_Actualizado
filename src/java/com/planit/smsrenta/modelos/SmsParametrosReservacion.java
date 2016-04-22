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
public class SmsParametrosReservacion {

    private Integer idParametro;
    private int parametroValorIncremento;
    private SmsMercado smsMercado;

    public SmsParametrosReservacion() {
        smsMercado = new SmsMercado();
    }

    public SmsParametrosReservacion(SmsMercado smsMercado) {
        this.smsMercado = smsMercado;
    }

    public SmsParametrosReservacion(int parametroValorIncremento, SmsMercado smsMercado) {
        this.parametroValorIncremento = parametroValorIncremento;
        this.smsMercado = smsMercado;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public int getParametroValorIncremento() {
        return parametroValorIncremento;
    }

    public void setParametroValorIncremento(int parametroValorIncremento) {
        this.parametroValorIncremento = parametroValorIncremento;
    }

    public SmsMercado getSmsMercado() {
        return smsMercado;
    }

    public void setSmsMercado(SmsMercado smsMercado) {
        this.smsMercado = smsMercado;
    }

}
