/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsFactura;
import com.planit.smsrenta.modelos.SmsReservacion;
import java.util.List;

/**
 *
 * @author CristianCamilo
 */
public interface IFacturaDao {

    public List<SmsFactura> consultarFacturas();

    public List<SmsFactura> consultarFacturaSegunReservacion(SmsReservacion reservacion);

    public void registrarFactura(SmsFactura factura);

    public void eliminarFactura(SmsFactura factura);
}
