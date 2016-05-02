/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsReservacion;
import com.planit.smsrenta.modelos.SmsUsuario;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IReservacionDao {

    //Definicion de metodos CRUD
    public List<SmsReservacion> mostrarReservaciones();
    
    public List<SmsReservacion> mostrarReservacionesSegunMercado(String mercado);
    
    public List<SmsReservacion> consultarReservacionesSegunEstado(int estado);
    
    public List<SmsReservacion> consultarReservacionesSegunEstadoyMercado(int estado, String mercado);

    public void registrarReservacion(SmsReservacion reservacion);

    public void modificarReservacion(SmsReservacion reservacion);

    public void eliminarReservacion(SmsReservacion reservacion);

    public List<SmsReservacion> filtrarReservacionSegunCliente(String valor);
    
    public List<SmsReservacion> filtrarReservacionSegunClienteSegunEstado(String valor, int estado);
    
    public List<SmsReservacion> filtrarReservacionSegunClienteSegunMercado(String valor, String mercado);
    
    public List<SmsReservacion> filtrarReservacionSegunClienteSegunEstadoyMercado(String valor, int estado, String mercado);

    public SmsReservacion consultarReservacionId(SmsReservacion reserva);

    public SmsReservacion consultarReserva(SmsReservacion reserva);

    public SmsReservacion consultarReservacionSinEmpleado(SmsReservacion reserva);

    public List<SmsReservacion> mostrarReservacionCliente(SmsUsuario usuario);

    public List<SmsReservacion> mostrarReservacionClienteSegunEstado(SmsUsuario usuario, int estado);
    
    public List<SmsReservacion> mostrarReservacionClienteSegunMercado(SmsUsuario usuario, String mercado);
    
    public List<SmsReservacion> mostrarReservacionClienteSegunEstadoyMercado(SmsUsuario usuario, int estado, String mercado);

    public List<SmsReservacion> mostrarReservacionConductores(SmsEmpleado conductor);

    public List<SmsReservacion> mostrarReservacionConductoresSegunEstado(SmsEmpleado conductor, int estado);
    
    public List<SmsReservacion> mostrarReservacionConductoresSegunMercado(SmsEmpleado conductor, String mercado);
    
    public List<SmsReservacion> mostrarReservacionConductoresSegunEstadoyMercado(SmsEmpleado conductor, int estado, String mercado);


}
