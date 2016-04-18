/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsPermisos;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IPermisosDao {

//Declaracion de funciones CRUD
    public List<SmsPermisos> mostrarPermisos();

    public void registrarPermiso(SmsPermisos permiso);

    public void modificarPermiso(SmsPermisos permiso);

    public void eliminarPermiso(SmsPermisos permiso);

    public SmsPermisos consultarPermiso(String permiso);
    
    public List<SmsPermisos> filtrarPermiso(String valor);

}
