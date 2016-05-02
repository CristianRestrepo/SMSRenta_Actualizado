/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.modelos;

public class SmsCorreo {
    
    private String correoDestinatario;
    private String asusto;
    private String mensaje;

    public SmsCorreo() {
    }

    public SmsCorreo(String correoDestinatario, String asusto, String mensaje) {
        this.correoDestinatario = correoDestinatario;
        this.asusto = asusto;
        this.mensaje = mensaje;
    }

    public String getCorreoDestinatario() {
        return correoDestinatario;
    }

    public void setCorreoDestinatario(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }

    public String getAsusto() {
        return asusto;
    }

    public void setAsusto(String asusto) {
        this.asusto = asusto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
