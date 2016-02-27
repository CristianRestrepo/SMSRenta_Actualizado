/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IAdministradorDao;
import DAO.ImpAdministradorDao;
import Funciones.MD5;
import Modelo.SmsCiudad;
import Modelo.SmsContraseñaUsuario;
import Modelo.SmsRol;
import Modelo.SmsUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class AdministradorBean extends UsuarioBean implements Serializable {

    IAdministradorDao adminDao;
    private String buscar;

    public AdministradorBean() {
        super();
        adminDao = new ImpAdministradorDao();
        buscar = null;

    }

    @PostConstruct
    public void init() {
        usuariosListView = adminDao.consultarUsuariosAdministradores();
    }

    //Getters $ setter
    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    //Declaracion de metodos
    //Metodos CRUD
    public void registrarAdministrador() {

        //asignamos al usuario la imagen de perfil default
        usuarioView.setUsuarioFotoRuta(fileController.getPathDefaultUsuario());
        usuarioView.setUsuarioFotoNombre(fileController.getNameDefaultUsuario());

        password = usuarioView.getUsuarioPassword();

        MD5 md = new MD5();
        usuarioView.setUsuarioPassword(md.getMD5(usuarioView.getUsuarioPassword()));//Se encripta la contreseña
        usuarioView.setUsuarioRememberToken(md.getMD5(usuarioView.getUsuarioRememberToken()));

        //el metodo recibe los atributos, agrega al atributo ciudad del objeto usuario un objeto correspondiente, 
        //de la misma forma comprueba el rol y lo asocia, por ultimo persiste el usuario en la base de datos
        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        usuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        rolView = rolDao.consultarRol(rolView).get(0);
        usuarioView.setSmsRol(rolView);//Asociamos un rol a un usuario

        usuarioView.setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta

        //registramos el usuario y recargamos la lista de clientes
        usuarioDao.registrarUsuario(usuarioView);
        usuariosListView = adminDao.consultarUsuariosAdministradores();

        //Consultamos la informacion del usuario recien registrado y registramos un respaldo de su contraseña
        usuarioView = usuarioDao.consultarUsuario(usuarioView).get(0);
        contraseñaController.registrarContraseña(usuarioView, password);

        //limpiamos objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        password = "";
    }

    public String modificarAdministrador() {
        MD5 md = new MD5();

        // en caso de modificar las contraseñas estas se encriptan de nuevo
        if (!modUsuarioView.getUsuarioPassword().equalsIgnoreCase(md.getMD5(contraseñaController.contraUsuarioDao.consultarContraseñaUsuario(modUsuarioView).get(0).getPassword()))) {
            password = modUsuarioView.getUsuarioPassword();//guardamos un relpaldo de la contraseña antes de encriptar
            modUsuarioView.setUsuarioPassword(md.getMD5(modUsuarioView.getUsuarioPassword()));
            modUsuarioView.setUsuarioRememberToken(md.getMD5(modUsuarioView.getUsuarioRememberToken()));
        }
        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        modUsuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        rolView = rolDao.consultarRol(rolView).get(0);
        modUsuarioView.setSmsRol(rolView);//Asociamos un rol a un usuario

        usuarioDao.modificarUsuario(modUsuarioView);//modificamos la informacion de usuario
        //Modificamos el respaldo de la contraseña si esta fue modificada
        if (!modUsuarioView.getUsuarioPassword().equalsIgnoreCase(md.getMD5(contraUsuarioDao.consultarContraseñaUsuario(modUsuarioView).get(0).getPassword()))) {
            contraseñaUsuarioView = contraUsuarioDao.consultarContraseñaUsuario(modUsuarioView).get(0);
            contraseñaController.modificarContraseña(contraseñaUsuarioView, password);
        }

        usuariosListView = adminDao.consultarUsuariosAdministradores();

        //limpiamos objetos
        contraseñaUsuarioView = new SmsContraseñaUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        modUsuarioView = new SmsUsuario();

        String ruta = "RAdminPUsuario";
        return ruta;
    }

    public void eliminarAdministrador() {
        usuarioDao.eliminarUsuario(DUsuarioView);
        usuariosListView = adminDao.consultarUsuariosAdministradores();
        if (usuarioView.equals(DUsuarioView)) {
            usuarioView = new SmsUsuario();
        }
        DUsuarioView = new SmsUsuario();
    }

    public void filtrar() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            usuariosListView = adminDao.consultarUsuariosAdministradores();
        } else {
            usuariosListView = adminDao.filtrarUsuariosAdministradores(buscar);
        }
    }

    //Metodos propios
    public String irModificarAdministrador() {
        ciudadView = modUsuarioView.getSmsCiudad();
        rolView = modUsuarioView.getSmsRol();
        String ruta = "AdminPEUsuario";
        return ruta;
    }

    public String regresar() {
        modUsuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();

        String ruta = "AdminPUsuario";
        return ruta;
    }

}
