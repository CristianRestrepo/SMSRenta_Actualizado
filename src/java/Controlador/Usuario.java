/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Funciones.MD5;
import DAO.ICiudadDao;
import DAO.IRolDao;
import DAO.IUsuarioDao;
import DAO.ImpCiudadDao;
import DAO.ImpRolDao;
import DAO.ImpUsuarioDao;
import Modelo.SmsCiudad;
import Modelo.SmsRol;
import Modelo.SmsUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Desarrollo_Planit
 */
public class Usuario {

    //Contexto y sesion
    private HttpSession httpSession;

    //Atributos
    protected SmsUsuario usuario;
    protected List<SmsUsuario> usuarios;

    //Relaciones con otras clases
    protected SmsCiudad ciudad;//asociacion
    protected SmsRol rol;//asociacion

    //Conexion con el DAO
    IUsuarioDao usuarioDao;
    ICiudadDao ciudadDao;
    IRolDao rolDao;

    public Usuario() {

        usuario = new SmsUsuario();
        ciudad = new SmsCiudad();
        rol = new SmsRol();

        usuarios = new ArrayList<>();

        usuarioDao = new ImpUsuarioDao();
        ciudadDao = new ImpCiudadDao();
        rolDao = new ImpRolDao();
    }

    //Metodos CRUD 
    public void registrarUsuario(SmsUsuario u, SmsCiudad c, SmsRol r) {
        usuario = u;
        ciudad = c;
        rol = r;

        MD5 md = new MD5();
        usuario.setUsuarioPassword(md.getMD5(usuario.getUsuarioPassword()));//Se encripta la contreseña
        usuario.setUsuarioRememberToken(md.getMD5(usuario.getUsuarioRememberToken()));

        //el metodo recibe los atributos, agrega al atributo ciudad del objeto usuario un objeto correspondiente, 
        //de la misma forma comprueba el rol y lo asocia, por ultimo persiste el usuario en la base de datos
        ciudadDao = new ImpCiudadDao();
        ciudad = ciudadDao.consultarCiudad(ciudad).get(0);
        usuario.setSmsCiudad(ciudad);//Asociamos una ciudad a un usuario

        rol = rolDao.consultarRol(rol).get(0);
        usuario.setSmsRol(rol);//Asociamos un rol a un usuario

        usuario.setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta
        usuarioDao.registrarUsuario(usuario);//Registramos usuario
    }

    public void modificarUsuario(SmsUsuario u, SmsCiudad c, SmsRol r) {
        usuario = u;
        ciudad = c;
        rol = r;
        //el metodo recibe el objeto usuario, con sus atributos modificados y lo persiste en la BD
        ciudad = ciudadDao.consultarCiudad(ciudad).get(0);
        usuario.setSmsCiudad(ciudad);//Asociamos una ciudad a un usuario

        rol = rolDao.consultarRol(rol).get(0);
        usuario.setSmsRol(rol);//Asociamos un rol a un usuario
        usuarioDao.modificarUsuario(usuario);//Modificamos Usuario
    }

    public void eliminarUsuario(SmsUsuario u) {
        usuario = u;
        //el metodo recibe el objeto usuario, se comunica con el dao y lo elimina de la base de datos
        usuarioDao.eliminarUsuario(usuario);
    }

    public List<SmsUsuario> cargarUsuarios() {
        //el metodo consulta de la base de datos todos los usuarios registrados y los retorna en una lista        
        usuarios = usuarioDao.mostrarUsuario();
        return usuarios;
    }

    public List<SmsUsuario> consultarUsuario(SmsUsuario u) {
        usuario = u;
        //el metodo consulta de la base de datos toda la informacion de usuario registrado y lo retorna en una lista
        usuarios = usuarioDao.consultarUsuario(usuario);
        return usuarios;
    }

    public List<SmsUsuario> consultarUsuarioSesion(SmsUsuario u) {
        usuario = u;
        //el metodo consulta de la base de datos toda la informacion de un usuario mediante su nombre de sesion(Login)
        usuarios = usuarioDao.consultarDatosSesionUsuario(usuario);
        return usuarios;
    }

    public List<SmsUsuario> consultarAdministradores() {
        usuarios = new ArrayList<>();
        //el metodo consulta de la base de datos todos los usuarios registrados con rol administrador y los retorna en una lista
        usuarios = usuarioDao.consultarUsuariosAdministradores();
        return usuarios;
    }

    public List<SmsUsuario> consultarClientes() {
        usuarios = new ArrayList<>();
        //el metodo consulta de la base de datos todos los usuarios registrados con rol cliente y los retorna en una lista
        usuarios = usuarioDao.consultarUsuariosClientes();
        return usuarios;
    }

    public List<SmsUsuario> consultarProveedores() {
        usuarios = new ArrayList<>();
        //el metodo consulta de la base de datos todos los usuarios registrados con rol proveedor y los retorna en una lista
        usuarios = usuarioDao.consultarUsuariosProveedores();
        return usuarios;
    }

    public List<SmsUsuario> consultarEmpleados() {
        usuarios = new ArrayList<>();
        //el metodo consulta de la base de datos todos los usuarios registrados con rol empleado(conductor) y los retorna en una lista
        usuarios = usuarioDao.consultarUsuariosEmpleados();
        return usuarios;
    }

    public List<SmsUsuario> filtrarEmpleados(String valor) {
        usuarios = new ArrayList<>();
        usuarios = usuarioDao.filtrarUsuariosEmpleados(valor);
        return usuarios;
    }

    public List<SmsUsuario> filtrarClientes(String valor) {
        usuarios = new ArrayList<>();
        usuarios = usuarioDao.filtrarUsuariosClientes(valor);
        return usuarios;
    }

    public List<SmsUsuario> filtrarProveedores(String valor) {
        usuarios = new ArrayList<>();
        usuarios = usuarioDao.filtrarUsuariosProveedores(valor);
        return usuarios;
    }

    public List<SmsUsuario> filtrarAdministrador(String valor) {
        usuarios = new ArrayList<>();
        usuarios = usuarioDao.filtrarUsuariosAdministradores(valor);
        return usuarios;
    }

    //Metodos de sesion
    public SmsUsuario obtenerSesion() {
        //Obtiene el usuario subido a la httpSession
        usuario = (SmsUsuario) httpSession.getAttribute("Sesion");
        return usuario;
    }

    //METODO PARA MODIFICAR USUARIO 
    public void modificarPerfilUsuario(SmsUsuario u, SmsCiudad c) {
        usuario = u;
        ciudad = c;
        //Se obtiene el objeto completo de ciudad
        ciudad = ciudadDao.consultarCiudad(ciudad).get(0);
        usuario.setSmsCiudad(ciudad);//Reasignamos la ciudad al usuario
        usuarioDao.modificarUsuario(usuario);//Modificamos Usuario

    }

}
