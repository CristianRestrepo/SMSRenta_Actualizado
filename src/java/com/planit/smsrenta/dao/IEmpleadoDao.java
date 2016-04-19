/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsProveedor;
import com.planit.smsrenta.modelos.SmsUsuario;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.util.List;

public interface IEmpleadoDao {

    //Definicion metodos CRUD
    public List<SmsEmpleado> mostrarEmpleados();

    public void registrarEmpleado(SmsEmpleado empleado);

    public void modificarEmpleado(SmsEmpleado empleado);

    public void eliminarEmpleado(SmsEmpleado empleado);

    public List<SmsUsuario> consultarUsuariosEmpleados();

    public List<SmsEmpleado> filtrarUsuariosEmpleados(String valor);
    
    public List<SmsEmpleado> filtrarUsuariosEmpleadosSegunProveedor(String valor, SmsProveedor proveedor);

    public SmsEmpleado consultarEmpleado(SmsUsuario usuario);
    
    public List<SmsEmpleado> consultarEmpleadosCiudad(SmsCiudad ciudad);

    public List<SmsEmpleado> consultarEmpleadosSegunProveedor(SmsProveedor proveedor);
    
    public List<SmsEmpleado> consultarEmpleadosSegunVehiculo(SmsVehiculo vehiculo);
}
