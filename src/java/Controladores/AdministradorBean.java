/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IAdministradorDao;
import DAO.ImpAdministradorDao;
import Funciones.GenerarPassword;
import Funciones.MD5;
import Funciones.SendEmail;
import static Funciones.Upload.getNameDefaultUsuario;
import static Funciones.Upload.getPathDefaultUsuario;
import Modelo.SmsCiudad;
import Modelo.SmsEmpleado;
import Modelo.SmsProveedor;
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

    private int operacion; //Controla la operacion a realizar
    private String nombreOperacion;

    //Banderas    
    private boolean habilitarCancelar;

    public AdministradorBean() {
        super();
        adminDao = new ImpAdministradorDao();
        buscar = null;

        habilitarCancelar = true;

        operacion = 0;
        nombreOperacion = "Registrar Empleado";

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

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

    public boolean isHabilitarCancelar() {
        return habilitarCancelar;
    }

    public void setHabilitarCancelar(boolean habilitarCancelar) {
        this.habilitarCancelar = habilitarCancelar;
    }

    //Declaracion de metodos
    //Metodos CRUD
    public void registrarAdministrador() {

        //asignamos al usuario la imagen de perfil default
        usuarioView.setUsuarioFotoRuta(getPathDefaultUsuario());
        usuarioView.setUsuarioFotoNombre(getNameDefaultUsuario());

        //Se genera un login y un pass aleatorio que se le envia al proveedor
        MD5 md = new MD5();
        GenerarPassword pass = new GenerarPassword();
        SendEmail email = new SendEmail();

        password = pass.generarPass(6);//Generamos pass aleatorio
        //Asignamos email como nombre de sesion
        usuarioView.setUsuarioLogin(usuarioView.getUsuarioEmail());

        //Encriptamos las contraseñas
        usuarioView.setUsuarioPassword(md.getMD5(password));//Se encripta la contreseña
        usuarioView.setUsuarioRememberToken(md.getMD5(password));

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

        //limpiamos objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        password = "";
    }

    public void modificarAdministrador() {

        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        usuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        rolView = rolDao.consultarRol(rolView).get(0);
        usuarioView.setSmsRol(rolView);//Asociamos un rol a un usuario

        usuarioDao.modificarUsuario(usuarioView);//modificamos la informacion de usuario
        //Modificamos el respaldo de la contraseña si esta fue modificada

        usuariosListView = adminDao.consultarUsuariosAdministradores();

        //limpiamos objetos
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        usuarioView = new SmsUsuario();
    }

    public void eliminarAdministrador() {
        usuarioDao.eliminarUsuario(usuarioView);
        usuariosListView = adminDao.consultarUsuariosAdministradores();
        //limpiamos objetos
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        usuarioView = new SmsUsuario();
    }

    public void filtrar() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            usuariosListView = adminDao.consultarUsuariosAdministradores();
        } else {
            usuariosListView = adminDao.filtrarUsuariosAdministradores(buscar);
        }
    }

    //Metodos Propios
    public void metodo() {
        if (operacion == 0) {
            registrarAdministrador();
        } else if (operacion == 1) {
            modificarAdministrador();
            //Reiniciamos banderas

            habilitarCancelar = true;
            operacion = 0;

            nombreOperacion = "Registrar Administrador";

        }
    }

    public void seleccionarCRUD(int i) {
        operacion = i;
        if (operacion == 1) {

            ciudadView = usuarioView.getSmsCiudad();
            rolView = usuarioView.getSmsRol();

            habilitarCancelar = false;
            nombreOperacion = "Modificar Administrador";

        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados

        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();

        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombreOperacion = "Registrar Proveedor";
    }

}
