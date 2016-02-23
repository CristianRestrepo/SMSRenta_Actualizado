/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsEstadovehiculo;
import Modelo.SmsVehiculo;
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
    public List<SmsEstadovehiculo> consultarEstadoVehiculo(SmsVehiculo  vehiculo);
}
