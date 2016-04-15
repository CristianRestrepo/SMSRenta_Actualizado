/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsUsuario;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IUsuarioDao {

    //Declaracion de metodos CRUD
    public List<SmsUsuario> mostrarUsuario();

    public void registrarUsuario(SmsUsuario usuario);

    public void modificarUsuario(SmsUsuario usuario);
    
    public void modificarSesionUsuario(SmsUsuario usuario);

    public void eliminarUsuario(SmsUsuario usuario);

    public List<SmsUsuario> consultarUsuario(SmsUsuario usuario);

    public List<SmsUsuario> consultarDatosSesionUsuario(SmsUsuario user);

    public List<SmsUsuario> verificarLoginDisponible(String valor);
    
    public List<SmsUsuario> verificarEmailDisponible(String valor);
}
