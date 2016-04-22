/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsParametrosReservacion;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IParametrosReservacionDao {
    
    public void registrarParametroReservacion(SmsParametrosReservacion parametro);
    public void modificarParametroReservacion(SmsParametrosReservacion parametro);
    public void eliminarParametroReservacion(SmsParametrosReservacion parametro);
    public SmsParametrosReservacion consultarParametroReservacion(SmsParametrosReservacion parametro);
    public SmsParametrosReservacion consultarParametroSegunMercado(SmsMercado mercado);
    public List<SmsParametrosReservacion> consultarParametrosReservacion();
    public List<SmsParametrosReservacion> filtrarParametrosReservacion(String valor);
    
}
