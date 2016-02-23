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
public interface IUsuarioDao {

    //Declaracion de metodos CRUD
    public List<SmsUsuario> mostrarUsuario();

    public void registrarUsuario(SmsUsuario usuario);

    public void modificarUsuario(SmsUsuario usuario);

    public void eliminarUsuario(SmsUsuario usuario);

    public List<SmsUsuario> consultarUsuariosAdministradores();

    public List<SmsUsuario> consultarUsuariosClientes();

    public List<SmsUsuario> consultarUsuariosEmpleados();

    public List<SmsUsuario> consultarUsuariosProveedores();

    public List<SmsUsuario> consultarUsuario(SmsUsuario usuario);

    public List<SmsUsuario> consultarDatosSesionUsuario(SmsUsuario usuario);

    public List<SmsUsuario> filtrarUsuariosEmpleados(String valor);

    public List<SmsUsuario> filtrarUsuariosProveedores(String valor);

    public List<SmsUsuario> filtrarUsuariosClientes(String valor);
    
    public List<SmsUsuario> filtrarUsuariosAdministradores(String valor);
    
    public List<SmsUsuario> verificarLoginDisponible(String valor);
}
