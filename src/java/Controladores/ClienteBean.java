/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IClienteDao;
import DAO.ImpClienteDao;
import Funciones.MD5;
import Funciones.SendEmail;
import Modelo.SmsCiudad;
import Modelo.SmsContraseñaUsuario;
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

    public ClienteBean() {
        super();
        nombresClientesListView = new ArrayList<>();
        emailController = new SendEmail();
        clienteDao = new ImpClienteDao();
        
        buscar = null;
    }

    @PostConstruct
    public void init() {
        usuariosListView = clienteDao.consultarUsuariosClientes();
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
        contraseñaController.registrarContraseña(usuarioView, password);
        //Actualizamos la lista de clientes registrador en el sistema
        usuariosListView = clienteDao.consultarUsuariosClientes();
        emailController.sendEmailBienvenida(usuarioView);//enviamos correo de bienvenida
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
        contraseñaController.registrarContraseña(usuarioView, password);

        //Actualizamos la lista de clientes registrador en el sistema
        usuariosListView = clienteDao.consultarUsuariosClientes();

        emailController.sendEmailBienvenida(usuarioView);//enviamos correo de bienvenida
        //limpiamos objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
    }

    public String modificarCliente() {
        MD5 md = new MD5();

        //se asigna un rol al usuario
        rolView.setRolNombre("Cliente");

        // en caso de modificar las contraseñas estas se encriptan de nuevo
        if (!modUsuarioView.getUsuarioPassword().equalsIgnoreCase(md.getMD5(contraUsuarioDao.consultarContraseñaUsuario(modUsuarioView).get(0).getPassword()))) {
            password = modUsuarioView.getUsuarioPassword();//guardamos un relpaldo de la contraseña antes de encriptar
            modUsuarioView.setUsuarioPassword(md.getMD5(modUsuarioView.getUsuarioPassword()));
            modUsuarioView.setUsuarioRememberToken(md.getMD5(modUsuarioView.getUsuarioRememberToken()));
        }

        //el metodo recibe los atributos, agrega al atributo ciudad del objeto usuario un objeto correspondiente, 
        //de la misma forma comprueba el rol y lo asocia, por ultimo persiste el usuario en la base de datos
        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        modUsuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        rolView = rolDao.consultarRol(rolView).get(0);
        modUsuarioView.setSmsRol(rolView);//Asociamos un rol a un usuario

        modUsuarioView.setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta

        //registramos el usuario y recargamos la lista de clientes
        usuarioDao.modificarUsuario(modUsuarioView);
        
        //Modificamos el respaldo de la contraseña si esta fue modificada
        if (!modUsuarioView.getUsuarioPassword().equalsIgnoreCase(md.getMD5(contraUsuarioDao.consultarContraseñaUsuario(modUsuarioView).get(0).getPassword()))) {
            contraseñaUsuarioView = contraUsuarioDao.consultarContraseñaUsuario(modUsuarioView).get(0);
            contraseñaController.modificarContraseña(contraseñaUsuarioView, password);
        }

        //Se modifica el usuario y se recarga la lista de clientes
        usuariosListView = clienteDao.consultarUsuariosClientes();
        //se limpian objetos
        modUsuarioView = new SmsUsuario();
        contraseñaUsuarioView = new SmsContraseñaUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();

        //Nos retorna a la vista de registro de clientes
        String ruta = "RAdminPCliente";
        return ruta;
    }

    public void eliminarCliente() {
        //Ejecutamos la eliminacion del usuario
        usuarioDao.eliminarUsuario(DUsuarioView);
        //Recargamos la lista de clientes
        usuariosListView = clienteDao.consultarUsuariosClientes();

        if (usuarioView.equals(DUsuarioView)) {//Validamos si el cliente a eliminar, esta en proceso de modificacion
            //si es correcto, limpiamos los objetos donde esta guardado este cliente que se acabo de eliminar
            usuarioView = new SmsUsuario();
            ciudadView = new SmsCiudad();
            rolView = new SmsRol();
        }

        //Limpiamos los objetos que contenian el cliente a eliminar
        DUsuarioView = new SmsUsuario();
        modUsuarioView = new SmsUsuario();
    }

    public void filtrarClientes() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            usuariosListView = clienteDao.consultarUsuariosClientes();
        } else {
            usuariosListView = clienteDao.filtrarUsuariosClientes(buscar);
        }
    }

    //Metodos propios
    public String irModificarCliente() {
        //Asigna a ciudad y rol los valores correspondientes segun el cliente elegido a modificar
        ciudadView = modUsuarioView.getSmsCiudad();
        rolView = modUsuarioView.getSmsRol();

        //Nos direcciona a las vista de modificacion
        String ruta = "AdminPECliente";
        return ruta;
    }

    public String regresar() {
        //Resetea los datos que se muestran en la vista, retorna al panel del cliente.
        modUsuarioView = new SmsUsuario();
        String ruta = "AdminPCliente";
        return ruta;
    }
}
