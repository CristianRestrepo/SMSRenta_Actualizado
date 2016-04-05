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
public interface IClienteDao {

    public List<SmsUsuario> consultarUsuariosClientes();

    public List<SmsUsuario> filtrarUsuariosClientes(String valor);
}
