/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IClienteDao;
import DAO.ImpClienteDao;
import Funciones.GenerarPassword;
import Funciones.MD5;
import Funciones.SendEmail;
import Modelo.SmsCiudad;
import Modelo.SmsRol;
import Modelo.SmsUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class ClienteBean extends UsuarioBean implements Serializable {

    //Objetos necesario para vista
    private List<String> nombresClientesListView;

    //Conexion con el dao
    IClienteDao clienteDao;

    //Relacion con el controlador    
    protected SendEmail emailController;

    //Variables   
    private String buscar;
    private int operacion; //Controla la operacion a realizar
    private String nombreOperacion;

    //Banderas    
    private boolean habilitarCancelar;

    public ClienteBean() {
        super();
        nombresClientesListView = new ArrayList<>();
        emailController = new SendEmail();
        clienteDao = new ImpClienteDao();

        buscar = null;
        habilitarCancelar = true;       

        operacion = 0;
        nombreOperacion = "Registrar Cliente";
    }

    @PostConstruct
    public void init() {
        usuariosListView = clienteDao.consultarUsuariosClientes();
//                clienteDao.consultarUsuariosClientes();
    }

    //Getters & Setters
    public List<String> getNombresClientesListView() {
        nombresClientesListView = new ArrayList<>();
        usuariosListView = new ArrayList<>();
        usuariosListView = clienteDao.consultarUsuariosClientes();
        for (int i = 0; i < usuariosListView.size(); i++) {
            nombresClientesListView.add(usuariosListView.get(i).getUsuarioNombre());
        }
        return nombresClientesListView;
    }

    public void setNombresClientesListView(List<String> nombresClientesListView) {
        this.nombresClientesListView = nombresClientesListView;
    }

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
    
    

    //Metodos     
    public String registrarCliente() {
        //asignamos un rol al usuario
        rolView.setRolNombre("Cliente");

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
        //Consultamos informacion usuario recien registrado y guardamos respaldo de su contraseña
        usuarioView = usuarioDao.consultarUsuario(usuarioView).get(0);
        //Actualizamos la lista de clientes registrador en el sistema
        usuariosListView = clienteDao.consultarUsuariosClientes();
        emailController.sendEmailCliente(usuarioView, password);//enviamos correo de bienvenida
        //limpiamos objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();

        return "Login";

    }

    public void registrarClienteAdmin() {
        //asignamos un rol al usuario
        rolView.setRolNombre("Cliente");

        //asignamos al usuario la imagen de perfil default
        usuarioView.setUsuarioFotoRuta(fileController.getPathDefaultUsuario());
        usuarioView.setUsuarioFotoNombre(fileController.getNameDefaultUsuario());

        //Se genera un login y un pass aleatorio que se le envia al proveedor
        MD5 md = new MD5();
        GenerarPassword pass = new GenerarPassword();//Generamos un password aleatorio

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

        //Consultamos informacion usuario recien registrado y guardamos respaldo de su contraseña
        usuarioView = usuarioDao.consultarUsuario(usuarioView).get(0);

        //Actualizamos la lista de clientes registrador en el sistema
        usuariosListView = clienteDao.consultarUsuariosClientes();

        emailController.sendEmailCliente(usuarioView, password);//enviamos correo de bienvenida
        //limpiamos objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
    }

    public void modificarCliente() {
        MD5 md = new MD5();

        //el metodo recibe los atributos, agrega al atributo ciudad del objeto usuario un objeto correspondiente, 
        //de la misma forma comprueba el rol y lo asocia, por ultimo persiste el usuario en la base de datos
        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        usuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        rolView = rolDao.consultarRol(rolView).get(0);
        usuarioView.setSmsRol(rolView);//Asociamos un rol a un usuario

        usuarioView.setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta

        //modificamos el usuario y recargamos la lista de clientes
        usuarioDao.modificarUsuario(usuarioView);
        usuariosListView = clienteDao.consultarUsuariosClientes();
        //se limpian objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
    }

    public void eliminarCliente() {
        usuarioDao.eliminarUsuario(usuarioView);//Se elimina el objeto
        //Se recarga la lista de clientes
        usuariosListView = clienteDao.consultarUsuariosClientes();

        //Limpiamos objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();

        habilitarCancelar = true;
        operacion = 0;
        nombreOperacion = "Registrar Proveedor";
    }

    public void filtrarClientes() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            usuariosListView = clienteDao.consultarUsuariosClientes();
        } else {
            usuariosListView = clienteDao.filtrarUsuariosClientes(buscar);
        }
    }

    //Metodos Propios
    public void metodo() {
        if (operacion == 0) {
            registrarClienteAdmin();
        } else if (operacion == 1) {
            modificarCliente();
            //Reiniciamos banderas

            habilitarCancelar = true;
            operacion = 0;
            nombreOperacion = "Registrar Cliente";

        }
    }

    public void seleccionarCRUD(int i) {
        operacion = i;
        if (operacion == 1) {
            ciudadView = usuarioView.getSmsCiudad();
            rolView = usuarioView.getSmsRol();
            habilitarCancelar = false;
            nombreOperacion = "Modificar Cliente";
        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();

        //Reiniciamos los objetos       
        habilitarCancelar = true;
        nombreOperacion = "Registrar Cliente";

    }
}
