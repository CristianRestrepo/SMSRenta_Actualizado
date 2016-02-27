/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Funciones.MD5;
import DAO.IProveedorDao;
import DAO.ImpProveedorDao;
import Modelo.SmsCiudad;
import Modelo.SmsMercado;
import Modelo.SmsProveedor;
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
public class ProveedorBean extends UsuarioBean implements Serializable {

    //Objetos necesarios para vista  
    private SmsProveedor proveedorView;   
    private SmsMercado mercadoView;
    
    //lista de Id de proveedor
    private List<String> nombresProveedoresView;

    //Variables
    private String buscar;
   
    //Conexion con el dao
    IProveedorDao proveedorDao;
    

    public ProveedorBean() {  
        super();
        proveedorView = new SmsProveedor();
       
        buscar = null;       
        proveedorDao = new ImpProveedorDao();
    }

    @PostConstruct 
    public void init() {
        usuariosListView = proveedorDao.consultarUsuariosProveedores();
    }

    //Getters & Setters
    public SmsProveedor getProveedorView() {
        return proveedorView;
    }

    public void setProveedorView(SmsProveedor proveedorView) {
        this.proveedorView = proveedorView;
    }
   

    public List<String> getNombresProveedoresView() {
        nombresProveedoresView = new ArrayList<>();
        usuariosListView = proveedorDao.consultarUsuariosProveedores();
        for (int i = 0; i < usuariosListView.size(); i++) {
            nombresProveedoresView.add(usuariosListView.get(i).getUsuarioNombre());
        }
        return nombresProveedoresView;
    }

    public void setNombresProveedoresView(List<String> nombresProveedoresView) {
        this.nombresProveedoresView = nombresProveedoresView;
    }
    

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }
    
    

    //Metodos    
    public void registrarProveedor() {
        //asignamos un rol al usuario
        rolView.setRolNombre("Proveedor");

        //asignamos al usuario la imagen de perfil default
        usuarioView.setUsuarioFotoRuta(fileController.getPathDefaultUsuario());
        usuarioView.setUsuarioFotoNombre(fileController.getNameDefaultUsuario());

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
        
        usuarioView = usuarioDao.consultarUsuario(usuarioView).get(0);
        proveedorView.setSmsUsuario(usuarioView);
       
        //Registramos al usuario como proveedor
        proveedorDao.registrarProveedor(proveedorView);
        usuariosListView = proveedorDao.consultarUsuariosProveedores();//Recargamos la lista de proveedores

        //Limpiamos objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        proveedorView = new SmsProveedor();
        rolView = new SmsRol();

    }

    public String modificarProveedor() {
        MD5 md = new MD5();

        //se asigna un rol al usuario
        rolView.setRolNombre("Proveedor");

        // en caso de modificar las contraseñas estas se encriptan de nuevo
            modUsuarioView.setUsuarioPassword(md.getMD5(modUsuarioView.getUsuarioPassword()));
            modUsuarioView.setUsuarioRememberToken(md.getMD5(modUsuarioView.getUsuarioRememberToken()));
       

        //el metodo recibe los atributos, agrega al atributo ciudad del objeto usuario un objeto correspondiente, 
        //de la misma forma comprueba el rol y lo asocia, por ultimo persiste el usuario en la base de datos
        ciudadView = ciudadDao.consultarCiudad(ciudadView).get(0);
        modUsuarioView.setSmsCiudad(ciudadView);//Asociamos una ciudad a un usuario

        rolView = rolDao.consultarRol(rolView).get(0);
        modUsuarioView.setSmsRol(rolView);//Asociamos un rol a un usuario
        
        //Se modifica el usuario y se recarga la lista de proveedores
        usuarioDao.modificarUsuario(modUsuarioView);
        
        proveedorView.setSmsUsuario(modUsuarioView);
        proveedorDao.modificarProveedor(proveedorView);
        usuariosListView = proveedorDao.consultarUsuariosProveedores();

        //Se limpian objetos
        proveedorView = new SmsProveedor();
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        modUsuarioView = new SmsUsuario();

        String ruta = "RAdminPProveedores";
        return ruta;

    }

    public void eliminarProveedor() {

        usuarioDao.eliminarUsuario(DUsuarioView);
        usuariosListView = proveedorDao.consultarUsuariosProveedores();
        if (usuarioView.equals(DUsuarioView)) {
            usuarioView = new SmsUsuario();
            proveedorView = new SmsProveedor();
        }
        DUsuarioView = new SmsUsuario();
    }

    public void filtrarProveedores() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            usuariosListView = proveedorDao.consultarUsuariosProveedores();
        } else {
            usuariosListView = proveedorDao.filtrarUsuariosProveedores(buscar);
        }
    }

    //Metodos propios
    public String irModificarProveedores() {
        proveedorView = proveedorDao.consultarProveedorUsuario(modUsuarioView).get(0);
        ciudadView = modUsuarioView.getSmsCiudad();
        rolView = modUsuarioView.getSmsRol();

        String ruta = "AdminPEProveedores";
        return ruta;
    }
  
    public String regresar() {
        modUsuarioView = new SmsUsuario();       
        String ruta = "AdminPProveedores";
        return ruta;
    }  
}
