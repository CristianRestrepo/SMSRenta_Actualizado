/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.ICalificacionDao;
import com.planit.smsrenta.dao.ImpCalificacionDao;
import com.planit.smsrenta.metodos.SendEmail;
import com.planit.smsrenta.modelos.SmsCalificacion;
import com.planit.smsrenta.modelos.SmsReservacion;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author Desarrollo_Planit
 */
public class CalificacionBean implements Serializable {

    private SmsCalificacion calificacionView;
    private SmsReservacion reservacionView;
    ICalificacionDao calificacionDao;

    public CalificacionBean() {
        calificacionView = new SmsCalificacion();
        reservacionView = new SmsReservacion();
        calificacionDao = new ImpCalificacionDao();
    }

    public SmsCalificacion getCalificacionView() {
        return calificacionView;
    }

    public void setCalificacionView(SmsCalificacion calificacionView) {
        this.calificacionView = calificacionView;
    }

    public SmsReservacion getReservacionView() {
        return reservacionView;
    }

    public void setReservacionView(SmsReservacion reservacionView) {
        this.reservacionView = reservacionView;
    }        

    //metodos
    public String registrar() throws IOException {
        calificacionView.setSmsReservacion(reservacionView);
        calificacionDao.registrarCalificacion(calificacionView);
        
        if(calificacionView.getCalificacionCalidadServicio() < 4){
            SendEmail email = new SendEmail();
            email.sendEmailMalaCalificacion(calificacionView);
        }
        
        calificacionView = new SmsCalificacion();
        return "ClienteReservaciones";
    }
}
