/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsProveedor;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IVehiculoDao {
    
    public List<SmsVehiculo> mostrarVehiculo();
    public void registrarVehiculo(SmsVehiculo vehiculo);
    public void modificarVehiculo(SmsVehiculo vehiculo);
    public void eliminarVehiculo(SmsVehiculo vehiculo);
    public SmsVehiculo consultarVehiculo(SmsVehiculo vehiculo);
    public List<SmsVehiculo> consultarVehiculosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada , String ciudad, String espacioInicio, String espacioLlegada, String mercado);
    public List<SmsVehiculo> filtrarVehiculosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada , String ciudad, String categoria, String espacioInicio, String espacioLlegada, String mercado);
    public List<SmsVehiculo> consultarVehiculosCiudad(SmsCiudad ciudad);
    public List<SmsVehiculo> filtrarVehiculosCiudad(SmsCiudad ciudad, String categoria);
    public List<SmsVehiculo> filtrarVehiculoSegunProveedor(String valor,SmsProveedor proveedor);
    public List<SmsVehiculo> filtrarVehiculos(String valor);
    public List<SmsVehiculo> consultarVehiculosSegunProveedor(SmsProveedor proveedor);
    public SmsVehiculo consultarVehiculoConConductores(SmsVehiculo vehiculo);
    public List<SmsVehiculo> consultarVehiculoDisponibleSegunPlaca(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada , String ciudad, String mercado, String placa);
    }
