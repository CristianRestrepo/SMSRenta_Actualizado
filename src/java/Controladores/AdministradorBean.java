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

    protected int operacion; //Controla la operacion a realizar
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

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
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
        usuarioView.setSmsCiudad(ciudadDao.consultarCiudad(usuarioView.getSmsCiudad()).get(0));//Asociamos una ciudad a un usuario
        usuarioView.setSmsRol(rolDao.consultarRol(usuarioView.getSmsRol()).get(0));//Asociamos un rol a un usuario
        usuarioView.setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta
        usuarioView.setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(usuarioView.getSmsNacionalidad()).get(0));
       
        
        //registramos el usuario y recargamos la lista de clientes
        usuarioDao.registrarUsuario(usuarioView);
        usuariosListView = adminDao.consultarUsuariosAdministradores();

        //limpiamos objetos
        usuarioView = new SmsUsuario();
        password = "";
    }

    public void modificarAdministrador() {

        usuarioView.setSmsCiudad(ciudadDao.consultarCiudad(usuarioView.getSmsCiudad()).get(0));//Asociamos una ciudad a un usuario
        usuarioView.setSmsRol(rolDao.consultarRol(usuarioView.getSmsRol()).get(0));//Asociamos un rol a un usuario
        usuarioView.setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(usuarioView.getSmsNacionalidad()).get(0));
       
        usuarioDao.modificarUsuario(usuarioView);//modificamos la informacion de usuario
        //Modificamos el respaldo de la contraseña si esta fue modificada

        usuariosListView = adminDao.consultarUsuariosAdministradores();

        //limpiamos objetos        
        usuarioView = new SmsUsuario();
    }

    public void eliminarAdministrador() {
        usuarioDao.eliminarUsuario(usuarioView);
        usuariosListView = adminDao.consultarUsuariosAdministradores();
        //limpiamos objetos
      
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
            habilitarCancelar = false;
            nombreOperacion = "Modificar Administrador";

        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados
        usuarioView = new SmsUsuario();
        
        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombreOperacion = "Registrar Proveedor";
    }

}
