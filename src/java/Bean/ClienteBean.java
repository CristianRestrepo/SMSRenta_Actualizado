/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Funciones.Upload;
import Controlador.Cliente;
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
public class ClienteBean implements Serializable {

    //Objetos necesario para vista
    protected SmsUsuario clienteView;
    protected SmsUsuario DClienteView;
    protected SmsUsuario modClienteView;
    
    protected List<SmsUsuario> clientesListView;
    protected List<String> nombresClientesListView;
    
    protected SmsCiudad ciudadView;
    protected SmsRol rolView;
    //variable para sacar las ciudades de la reservacion
    private SmsAgenda ciudadReservacion;

    //Relacion con el controlador
    protected Cliente clienteController;
    protected Upload fileController;
    protected SendEmail emailController;

    //Variables    
    private String buscar;
    private Boolean habilitarEditarSesion;
    private String pass;

    public ClienteBean() {
        ciudadReservacion = new SmsAgenda();
        DClienteView = new SmsUsuario();
        clienteView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        nombresClientesListView = new ArrayList<>();
        clienteController = new Cliente();
        fileController = new Upload();
        emailController = new SendEmail();
        modClienteView = new SmsUsuario();
        buscar = null;
        habilitarEditarSesion = false;
    }

    @PostConstruct
    public void init() {
        clientesListView = new ArrayList<>();
        clientesListView = clienteController.consultarClientes();
    }

    //Getters & Setters
    public SmsAgenda getCiudadReservacion() {
        return ciudadReservacion;
    }

    public void setCiudadReservacion(SmsAgenda ciudadReservacion) {
        this.ciudadReservacion = ciudadReservacion;
    }

    public SmsUsuario getClienteView() {
        return clienteView;
    }

    public void setClienteView(SmsUsuario clienteView) {
        this.clienteView = clienteView;
    }

    public List<SmsUsuario> getClientesListView() {
        return clientesListView;
    }

    public void setClientesListView(List<SmsUsuario> clientesListView) {
        this.clientesListView = clientesListView;
    }

    public SmsCiudad getCiudadView() {
        return ciudadView;
    }

    public void setCiudadView(SmsCiudad ciudadView) {
        this.ciudadView = ciudadView;
    }

    public SmsRol getRolView() {
        return rolView;
    }

    public void setRolView(SmsRol rolView) {
        this.rolView = rolView;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public SmsUsuario getModClienteView() {
        return modClienteView;
    }

    public void setModClienteView(SmsUsuario modClienteView) {
        this.modClienteView = modClienteView;
    }

    public Boolean getHabilitarEditarSesion() {
        return habilitarEditarSesion;
    }

    public void setHabilitarEditarSesion(Boolean habilitarEditarSesion) {
        this.habilitarEditarSesion = habilitarEditarSesion;
    }

    public SmsUsuario getDClienteView() {
        return DClienteView;
    }

    public void setDClienteView(SmsUsuario DClienteView) {
        this.DClienteView = DClienteView;
    }

    public List<String> getNombresClientesListView() {
        nombresClientesListView = new ArrayList<>();
        clientesListView = new ArrayList<>();
        clientesListView = clienteController.consultarClientes();
        for (int i = 0; i < clientesListView.size(); i++) {
            nombresClientesListView.add(clientesListView.get(i).getUsuarioNombre());
        }
        return nombresClientesListView;
    }

    public void setNombresClientesListView(List<String> nombresClientesListView) {
        this.nombresClientesListView = nombresClientesListView;
    }

    //Metodos     
    public String registrar() {
        //asignamos un rol al usuario
        rolView.setRolNombre("Cliente");

        //asignamos al usuario la imagen de perfil default
        clienteView.setUsuarioFotoRuta(fileController.getPathDefaultUsuario());
        clienteView.setUsuarioFotoNombre(fileController.getNameDefaultUsuario());

        //registramos el usuario y recargamos la lista de clientes
        clienteController.registrarUsuario(clienteView, ciudadView, rolView);
        
        //Actualizamos la lista de clientes registrador en el sistema
        clientesListView = clienteController.consultarClientes();
                
        emailController.sendEmailBienvenida(clienteView);//enviamos correo de bienvenida
        //limpiamos objetos
        clienteView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        
        return "Login";
        
    }
    
    public void registrarClienteAdmin() {
        //asignamos un rol al usuario
        rolView.setRolNombre("Cliente");

        //asignamos al usuario la imagen de perfil default
        clienteView.setUsuarioFotoRuta(fileController.getPathDefaultUsuario());
        clienteView.setUsuarioFotoNombre(fileController.getNameDefaultUsuario());

        //registramos el usuario y recargamos la lista de clientes
        clienteController.registrarUsuario(clienteView, ciudadView, rolView);
        
        //Actualizamos la lista de clientes registrador en el sistema
        clientesListView = clienteController.consultarClientes();
                
        emailController.sendEmailBienvenida(clienteView);//enviamos correo de bienvenida
        //limpiamos objetos
        clienteView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
    }

    public String modificar() {

        MD5 md = new MD5();

        //se asigna un rol al usuario
        rolView.setRolNombre("Cliente");

        if (habilitarEditarSesion) { // en caso de modificar las contraseñas estas se encriptan de nuevo
            modClienteView.setUsuarioPassword(md.getMD5(modClienteView.getUsuarioPassword()));
            modClienteView.setUsuarioRememberToken(md.getMD5(modClienteView.getUsuarioRememberToken()));
        }

        //Se modifica el usuario y se recarga la lista de clientes
        clienteController.modificarUsuario(modClienteView, ciudadView, rolView);
        clientesListView = clienteController.consultarClientes();

        //se limpian objetos
        clienteView = new SmsUsuario();
        modClienteView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        habilitarEditarSesion = false;

        //Nos retorna a la vista de registro de clientes
        String ruta = "RAdminPCliente";
        return ruta;
    }

    public void eliminar() {
        //Ejecutamos la eliminacion del usuario
        clienteController.eliminarUsuario(DClienteView);
        //Recargamos la lista de clientes
        clientesListView = clienteController.consultarClientes();

        if (clienteView.equals(DClienteView)) {//Validamos si el cliente a eliminar, esta en proceso de modificacion
            //si es correcto, limpiamos los objetos donde esta guardado este cliente que se acabo de eliminar
            clienteView = new SmsUsuario();
            ciudadView = new SmsCiudad();
            rolView = new SmsRol();
        }
        
        //Limpiamos los objetos que contenian el cliente a eliminar
        DClienteView = new SmsUsuario();
        modClienteView = new SmsUsuario();
    }

    public void filtrar() {
        clientesListView = new ArrayList<>();
        if (buscar == null) {
            clientesListView = clienteController.consultarClientes();
        } else {
            clientesListView = clienteController.filtrarClientes(buscar);
        }
    }

    //Metodos propios
    public String irModificarCliente() {
        //Asigna a ciudad y rol los valores correspondientes segun el cliente elegido a modificar
        ciudadView = modClienteView.getSmsCiudad();
        rolView = modClienteView.getSmsRol();

        //Nos direcciona a las vista de modificacion
        String ruta = "AdminPECliente";
        return ruta;
    }

    public String regresar() {
        //Resetea los datos que se muestran en la vista, retorna al panel del cliente.
        modClienteView = new SmsUsuario();
        habilitarEditarSesion = false;
        String ruta = "AdminPCliente";
        return ruta;
    }

    public void habilitarEdicion() {
        //En caso de querer modificar los datos de sesion (Password y login)
        //Se guarda la contraseña previa en una variable en caso de que el usuario cancele la edicion
        habilitarEditarSesion = true;
        pass = modClienteView.getUsuarioPassword();
        modClienteView.setUsuarioPassword(null);
        modClienteView.setUsuarioRememberToken(null);
    }

    public void deshabilitarEdicion() {
        //Si se cancela la edicion de los datos de sesion (Password y login)
        //se reasigna la contraseña ya establecida al  usuario que se esta modificando
        habilitarEditarSesion = false;//Se desactiva el panel de edicion
        modClienteView.setUsuarioPassword(pass);
        modClienteView.setUsuarioRememberToken(pass);
    }

}
