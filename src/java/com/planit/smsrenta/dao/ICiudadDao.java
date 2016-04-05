/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCiudad;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ICiudadDao {
    
    //Declaracion de metodos CRUD
    public List<SmsCiudad> mostrarCiudades();
    public void registrarCiudad(SmsCiudad ciudad);
    public void modificarCiudad(SmsCiudad ciudad);
    public void eliminarCiudad(SmsCiudad ciudad);
    public List<SmsCiudad> consultarCiudad(SmsCiudad ciudad);
    public List<SmsCiudad> filtrarCiudad(String dato);
    
}
