/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsServicios;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IServicioDao {
    
    //Declaracion de metodos
    public List<SmsServicios> mostrarServicios();
    public List<SmsServicios> ConsultarServicio(SmsServicios servicio);
    public void registrarServicio(SmsServicios servicio);
    public void modificarServicio(SmsServicios servicio);
    public void eliminarServicio(SmsServicios servicio);
    public List<SmsServicios> filtrarServicios(String dato);
    
}
