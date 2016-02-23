/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Funciones.MD5;
import Funciones.Upload;
import Controlador.Proveedor;
import Modelo.SmsCiudad;
import Modelo.SmsProveedor;
import Modelo.SmsRol;
import Modelo.SmsUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Desarrollo_Planit
 */
public class ProveedorBean implements Serializable {

    //Objetos necesarios para vista
    protected SmsUsuario usuarioView;
    protected SmsUsuario DUsuarioView;
    protected SmsUsuario modUsuarioView;
    protected SmsProveedor proveedorView;
    protected SmsCiudad ciudadView;
    protected SmsRol rolView;
    private UploadedFile archivo;

    //Relacion con el controlador
    protected Proveedor proveedorController;
    protected Upload fileController;

    //lista de Id de proveedor
    private List<SmsUsuario> proveedoresListView;
    private List<String> nombresProveedoresView;

    //Variables
    private String buscar;
    private Boolean habilitarEditarSesion;
    private String pass;

    public ProveedorBean() {
        usuarioView = new SmsUsuario();
        modUsuarioView = new SmsUsuario();
        DUsuarioView = new SmsUsuario();
        proveedorView = new SmsProveedor();
        ciudadView = new SmsCiudad();
        rolView = new SmsRol();
        proveedorController = new Proveedor();
        fileController = new Upload();

        buscar = null;
        habilitarEditarSesion = false;
    }

    @PostConstruct
    public void init() {
        proveedoresListView = proveedorController.consultarProveedores();
    }

    //Getters & Setters
    public SmsUsuario getUsuarioView() {
        return usuarioView;
    }

    public void setUsuarioView(SmsUsuario usuarioView) {
        this.usuarioView = usuarioView;
    }

    public SmsProveedor getProveedorView() {
        return proveedorView;
    }

    public void setProveedorView(SmsProveedor proveedorView) {
        this.proveedorView = proveedorView;
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

    public Proveedor getProveedorController() {
        return proveedorController;
    }

    public void setProveedorController(Proveedor proveedorController) {
        this.proveedorController = proveedorController;
    }

    public List<SmsUsuario> getProveedoresListView() {
        return proveedoresListView;
    }

    public void setProveedoresListView(List<SmsUsuario> proveedoresListView) {
        this.proveedoresListView = proveedoresListView;
    }

    public List<String> getNombresProveedoresView() {
        nombresProveedoresView = new ArrayList<>();
        proveedoresListView = proveedorController.consultarProveedores();

        for (int i = 0; i < proveedoresListView.size(); i++) {
            nombresProveedoresView.add(proveedoresListView.get(i).getUsuarioNombre());
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

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public Upload getFileController() {
        return fileController;
    }

    public void setFileController(Upload fileController) {
        this.fileController = fileController;
    }

    public SmsUsuario getModUsuarioView() {
        return modUsuarioView;
    }

    public void setModUsuarioView(SmsUsuario modUsuarioView) {
        this.modUsuarioView = modUsuarioView;
    }

    public Boolean getHabilitarEditarSesion() {
        return habilitarEditarSesion;
    }

    public void setHabilitarEditarSesion(Boolean habilitarEditarSesion) {
        this.habilitarEditarSesion = habilitarEditarSesion;
    }

    public SmsUsuario getDUsuarioView() {
        return DUsuarioView;
    }

    public void setDUsuarioView(SmsUsuario DUsuarioView) {
        this.DUsuarioView = DUsuarioView;
    }

    //Metodos    
    public void registrar() {
        //asignamos un rol al usuario
        rolView.setRolNombre("Proveedor");

        //asignamos al usuario la imagen de perfil default
        usuarioView.setUsuarioFotoRuta(fileController.getPathDefaultUsuario());
        usuarioView.setUsuarioFotoNombre(fileController.getNameDefaultUsuario());

        //registramos el usuario y recargamos la lista de clientes
        proveedorController.registrarUsuario(usuarioView, ciudadView, rolView);
        proveedorController.registrarProveedor(proveedorView, usuarioView);
        proveedoresListView = proveedorController.consultarProveedores();

        //Limpiamos objetos
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        proveedorView = new SmsProveedor();
        rolView = new SmsRol();

    }

    public String modificar() {
        MD5 md = new MD5();

        //se asigna un rol al usuario
        rolView.setRolNombre("Proveedor");

        if (habilitarEditarSesion) { // en caso de modificar las contraseñas estas se encriptan de nuevo
            modUsuarioView.setUsuarioPassword(md.getMD5(modUsuarioView.getUsuarioPassword()));
            modUsuarioView.setUsuarioRememberToken(md.getMD5(modUsuarioView.getUsuarioRememberToken()));
        }

        //Se modifica el usuario y se recarga la lista de proveedores
        proveedorController.modificarUsuario(modUsuarioView, ciudadView, rolView);
        proveedorController.modificarProveedor(proveedorView, modUsuarioView);
        proveedoresListView = proveedorController.consultarProveedores();

        //Se limpian objetos
        proveedorView = new SmsProveedor();
        usuarioView = new SmsUsuario();
        ciudadView = new SmsCiudad();
        modUsuarioView = new SmsUsuario();

        String ruta = "RAdminPProveedores";
        return ruta;

    }

    public void eliminar() {

        proveedorController.eliminarUsuario(DUsuarioView);
        proveedoresListView = proveedorController.consultarProveedores();
        if (usuarioView.equals(DUsuarioView)) {
            usuarioView = new SmsUsuario();
            proveedorView = new SmsProveedor();
        }
        DUsuarioView = new SmsUsuario();

    }

    public void filtrar() {
        proveedoresListView = new ArrayList<>();
        if (buscar == null) {
            proveedoresListView = proveedorController.consultarProveedores();
        } else {
            proveedoresListView = proveedorController.filtrarProveedores(buscar);
        }
    }

    //Metodos propios
    public String irModificarProveedores() {
        proveedorView = proveedorController.consultarProveedor(modUsuarioView).get(0);
        ciudadView = modUsuarioView.getSmsCiudad();
        rolView = modUsuarioView.getSmsRol();

        String ruta = "AdminPEProveedores";
        return ruta;
    }

    public String regresar() {
        modUsuarioView = new SmsUsuario();
        habilitarEditarSesion = false;
        String ruta = "AdminPProveedores";
        return ruta;
    }

    public void habilitarEdicion() {
        habilitarEditarSesion = true;
        pass = modUsuarioView.getUsuarioPassword();
        modUsuarioView.setUsuarioPassword(null);
        modUsuarioView.setUsuarioRememberToken(null);
    }

    public void deshabilitarEdicion() {
        habilitarEditarSesion = false;
        modUsuarioView.setUsuarioPassword(pass);
        modUsuarioView.setUsuarioRememberToken(pass);
    }
}
