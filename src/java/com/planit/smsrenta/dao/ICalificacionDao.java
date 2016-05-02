/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCalificacion;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ICalificacionDao {
    
    //Definicion de metodos CRUD
    public List<SmsCalificacion> mostrarCalificaciones();
    public List<SmsCalificacion> filtrarCalificaciones(String valor);
    public void registrarCalificacion(SmsCalificacion calificacion);
    public void modificarCalificacion(SmsCalificacion calificacion);
    public void eliminarCalificacion(SmsCalificacion calificacion);
}
