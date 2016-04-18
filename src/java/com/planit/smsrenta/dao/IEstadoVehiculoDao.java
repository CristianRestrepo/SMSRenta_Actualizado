/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsEstadovehiculo;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IEstadoVehiculoDao {
    
    //Definicion metodos CRUD
    public List<SmsEstadovehiculo> mostrarEstadoVehiculo();
    public void registrarEstadoVehiculo(SmsEstadovehiculo estado);
    public void modificarEstadoVehiculo(SmsEstadovehiculo estado);
    public void eliminarEstadoVehiculo(SmsEstadovehiculo estado);
    public SmsEstadovehiculo consultarEstadoVehiculo(SmsVehiculo  vehiculo);
}
