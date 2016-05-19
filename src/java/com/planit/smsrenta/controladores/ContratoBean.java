/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IContratoDao;
import com.planit.smsrenta.dao.ImpContratoDao;
import com.planit.smsrenta.metodos.GenerarReportes;
import com.planit.smsrenta.modelos.SmsContrato;
import com.planit.smsrenta.modelos.SmsReservacion;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Desarrollo_Planit
 */
public class ContratoBean {

    private SmsContrato contratoView;
    IContratoDao contratoDao;

    public ContratoBean() {
        contratoView = new SmsContrato();
        contratoDao = new ImpContratoDao();
    }

    public SmsContrato getContratoView() {
        return contratoView;
    }

    public void setContratoView(SmsContrato contratoView) {
        this.contratoView = contratoView;
    }

    public IContratoDao getContratoDao() {
        return contratoDao;
    }

    public void setContratoDao(IContratoDao contratoDao) {
        this.contratoDao = contratoDao;
    }

    public void generarFuec(SmsReservacion reservacion) throws JRException, IOException {
        FacesMessage message;
        if (!reservacion.getSmsServicios().getSmsMercado().getMercadoNombre().equalsIgnoreCase("Renta")) {
            SmsContrato contrato = contratoDao.consultarContratoSegunReservacion(reservacion);
            if (contrato.getIdContrato() == null) {
                contratoView.setSmsReservacion(reservacion);
                contratoView.setContratoObjeto("Proveedor servicio de transporte");
                int numerocontrato = 266;
                contratoView.setContratoNumeroContrato(numerocontrato);
                contratoView.setContratoVigencia(2016);
                int indicativo = 0;
                if (contratoDao.consultarMaxIndicativo() == 0) {
                    indicativo = 700;
                } else {
                    indicativo = contratoDao.consultarMaxIndicativo() + 1;
                }
                contratoView.setContratoIndicativo(indicativo);
                contratoDao.registrarContrato(contratoView);
            }
            GenerarReportes reporte = new GenerarReportes();
            if (contratoView.getIdContrato() == null) {
                contratoView = contratoDao.consultarContratoSegunReservacion(reservacion);
            }
            reporte.generarFUEC(contratoView);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Este tipo de servicio no necesita de documento FUEC", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
