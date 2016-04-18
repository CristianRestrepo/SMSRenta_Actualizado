/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsRol;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IRolDao {

//Declaracion de funciones CRUD
    public List<SmsRol> mostrarRoles();

    public void registrarRol(SmsRol Rol);

    public void modificarRol(SmsRol Rol);

    public void eliminarRol(SmsRol Rol);

    public SmsRol consultarRol(SmsRol rol); 
    
    public List<SmsRol> filtrarRol(String valor);
   
}
