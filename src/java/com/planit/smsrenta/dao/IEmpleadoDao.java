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
import java.util.List;

public interface IEmpleadoDao {

    //Definicion metodos CRUD
    public List<SmsEmpleado> mostrarEmpleados();
    
    public List<SmsEmpleado> consultarEmpleadosSegunProveedor(String Proveedor);

    public void registrarEmpleado(SmsEmpleado empleado);

    public void modificarEmpleado(SmsEmpleado empleado);

    public void eliminarEmpleado(SmsEmpleado empleado);
    
    public List<SmsUsuario> consultarUsuariosEmpleados();
    
    public List<SmsEmpleado> filtrarUsuariosEmpleados(String valor);

    public List<SmsEmpleado> consultarEmpleado(SmsUsuario usuario);

    public List<SmsEmpleado> consultarEmpleadosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada , String ciudad, String espacioInicio, String espacioLlegada, String Proveedor);
    
    public List<SmsEmpleado> consultarEmpleadosCiudad(SmsCiudad ciudad);
    
    public List<SmsEmpleado> consultarEmpleadosSegunProveedor(SmsProveedor proveedor);
}
