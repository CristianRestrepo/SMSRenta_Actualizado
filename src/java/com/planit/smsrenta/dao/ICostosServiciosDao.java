/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsCostosservicios;
import com.planit.smsrenta.modelos.SmsLugares;
import com.planit.smsrenta.modelos.SmsServicios;
import java.util.List;

/**
 *
 * @author CristianCamilo
 */
public interface ICostosServiciosDao {
    
    public List<SmsCostosservicios> consultarCostos();
    public List<SmsCostosservicios> consultarCostoServicio(SmsServicios servicio, SmsCategoria categoria);
    public List<SmsCostosservicios> consultarCostoServicioTraslado(SmsServicios servicio, SmsCategoria categoria, SmsLugares lugarInicio, SmsLugares lugarDestino);
    public List<SmsCostosservicios> consultarCostoServicio(SmsServicios servicio);
    public void registrarCostoServicio(SmsCostosservicios costo);
    public void modificarCostoServicio(SmsCostosservicios costo);
    public void eliminarCostoServicio(SmsCostosservicios costo);
    public List<SmsCostosservicios> filtrarCostosServicios(String dato);  
    
}
