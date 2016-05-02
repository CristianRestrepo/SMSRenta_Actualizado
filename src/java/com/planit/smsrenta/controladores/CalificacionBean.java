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
import com.planit.smsrenta.modelos.SmsCorreo;
import com.planit.smsrenta.modelos.SmsReservacion;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Desarrollo_Planit
 */
public class CalificacionBean implements Serializable {

    private SmsCalificacion calificacionView;
    private SmsReservacion reservacionView;
    private List<SmsCalificacion> calificacionesListView;
    ICalificacionDao calificacionDao;

    //objeto para enviar mensaje
    private SmsCorreo correo;

    //bandera para manejar objetos en la vista
    private boolean habilitar;

    //Variables
    private String buscar;

    public CalificacionBean() {
        calificacionView = new SmsCalificacion();
        reservacionView = new SmsReservacion();
        calificacionDao = new ImpCalificacionDao();
        calificacionesListView = new ArrayList<>();

        habilitar = false;
        correo = new SmsCorreo();

        buscar = null;

    }

    @PostConstruct
    public void init() {
        calificacionesListView = calificacionDao.mostrarCalificaciones();
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

    public List<SmsCalificacion> getCalificacionesListView() {
        return calificacionesListView;
    }

    public void setCalificacionesListView(List<SmsCalificacion> calificacionesListView) {
        this.calificacionesListView = calificacionesListView;
    }

    public void setReservacionView(SmsReservacion reservacionView) {
        this.reservacionView = reservacionView;
    }

    public boolean isHabilitar() {
        return habilitar;
    }

    public void setHabilitar(boolean habilitar) {
        this.habilitar = habilitar;
    }

    public SmsCorreo getCorreo() {
        return correo;
    }

    public void setCorreo(SmsCorreo correo) {
        this.correo = correo;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    //metodos
    public String registrar() throws IOException {
        calificacionView.setSmsReservacion(reservacionView);
        calificacionDao.registrarCalificacion(calificacionView);

        if (calificacionView.getCalificacionCalidadServicio() < 4) {
            SendEmail email = new SendEmail();
            email.sendEmailMalaCalificacion(calificacionView);
        }

        calificacionView = new SmsCalificacion();
        return "ClienteReservaciones";
    }

    public void filtrar() {
        calificacionesListView = new ArrayList<>();
        if (buscar == null) {
            calificacionesListView = calificacionDao.mostrarCalificaciones();
        } else {
            calificacionesListView = calificacionDao.filtrarCalificaciones(buscar);
        }
    }

    public void habilitarCorreo() {
        habilitar = true;
        correo.setCorreoDestinatario(calificacionView.getSmsReservacion().getSmsUsuario().getUsuarioEmail());
    }

    public void cancelarCorreo() {
        correo = new SmsCorreo();
        habilitar = false;
    }

    public void enviarCorreo() {
        SendEmail mail = new SendEmail();
        mail.sendEmailCorreoPersonalizado(correo);
        correo = new SmsCorreo();
        cancelarCorreo();
        FacesMessage message;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje enviado con exito", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
