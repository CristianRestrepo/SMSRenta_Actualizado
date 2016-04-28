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

    public List<SmsReservacion> consultarReservacionesSegunEstado(int estado);

    public void registrarReservacion(SmsReservacion reservacion);

    public void modificarReservacion(SmsReservacion reservacion);

    public void eliminarReservacion(SmsReservacion reservacion);

    public List<SmsReservacion> filtrarReservacionSegunCliente(String valor);
    
    public List<SmsReservacion> filtrarReservacionSegunClienteSegunEstado(String valor, int estado);

    public SmsReservacion consultarReservacionId(SmsReservacion reserva);

    public SmsReservacion consultarReserva(SmsReservacion reserva);

    public SmsReservacion consultarReservacionSinEmpleado(SmsReservacion reserva);

    public List<SmsReservacion> mostrarReservacionCliente(SmsUsuario usuario);

    public List<SmsReservacion> mostrarReservacionClienteSegunEstado(SmsUsuario usuario, int estado);

    public List<SmsReservacion> mostrarReservacionConductores(SmsEmpleado conductor);

    public List<SmsReservacion> mostrarReservacionConductoresSegunEstado(SmsEmpleado conductor, int estado);

}
