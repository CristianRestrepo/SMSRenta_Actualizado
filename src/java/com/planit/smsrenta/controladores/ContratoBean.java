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
        if (contratoDao.consultarContrañoSegunReservacion(reservacion).isEmpty()) {
            contratoView.setSmsReservacion(reservacion);
            contratoView.setContratoObjeto("Servicio de transporte");
            contratoDao.registrarContrato(contratoView);
        }
        GenerarReportes reporte = new GenerarReportes();
        contratoView = contratoDao.consultarContrañoSegunReservacion(reservacion).get(0);
        reporte.generarFUEC(contratoView);
    }

}
