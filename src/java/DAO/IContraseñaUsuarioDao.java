/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsContraseñaUsuario;
import Modelo.SmsUsuario;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IContraseñaUsuarioDao {
    
    public void registrarContraseñaUsuario(SmsContraseñaUsuario contraUsuario);
    public void modificarContraseñaUsuario(SmsContraseñaUsuario contraUsuario);
    public List<SmsContraseñaUsuario> consultarContraseñaUsuario(SmsUsuario usuario);
}
