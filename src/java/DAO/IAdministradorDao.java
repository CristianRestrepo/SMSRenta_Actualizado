/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsUsuario;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IAdministradorDao {
    
     public List<SmsUsuario> consultarUsuariosAdministradores();

     public List<SmsUsuario> filtrarUsuariosAdministradores(String valor);
}
