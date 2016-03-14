/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCiudad;
import Modelo.SmsEmpleado;
import Modelo.SmsUsuario;
import java.util.List;

public interface IEmpleadoDao {

    //Definicion metodos CRUD
    public List<SmsEmpleado> mostrarEmpleados();

    public void registrarEmpleado(SmsEmpleado empleado);

    public void modificarEmpleado(SmsEmpleado empleado);

    public void eliminarEmpleado(SmsEmpleado empleado);
    
    public List<SmsUsuario> consultarUsuariosEmpleados();
    
    public List<SmsUsuario> filtrarUsuariosEmpleados(String valor);

    public List<SmsEmpleado> consultarEmpleado(SmsUsuario usuario);

    public List<SmsEmpleado> consultarEmpleadosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada , String ciudad, String espacioInicio, String espacioLlegada);
    
    public List<SmsEmpleado> consultarEmpleadosCiudad(SmsCiudad ciudad);
}
