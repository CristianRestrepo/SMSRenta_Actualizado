package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IEmpleadoDao;
import com.planit.smsrenta.dao.IEstadoDao;
import com.planit.smsrenta.dao.IHojaVidaDao;
import com.planit.smsrenta.dao.IProveedorDao;
import com.planit.smsrenta.dao.IUsuarioDao;
import com.planit.smsrenta.dao.ImpEmpleadoDao;
import com.planit.smsrenta.dao.ImpEstadoDao;
import com.planit.smsrenta.dao.ImpHojaVidaDao;
import com.planit.smsrenta.dao.ImpProveedorDao;
import com.planit.smsrenta.dao.ImpUsuarioDao;
import com.planit.smsrenta.metodos.GenerarPassword;
import com.planit.smsrenta.metodos.MD5;
import com.planit.smsrenta.metodos.SendEmail;
import static com.planit.smsrenta.metodos.Upload.getMapPathFotosUsuario;
import static com.planit.smsrenta.metodos.Upload.getMapPathHojasVida;
import static com.planit.smsrenta.metodos.Upload.getNameDefaultHojasVida;
import static com.planit.smsrenta.metodos.Upload.getNameDefaultUsuario;
import static com.planit.smsrenta.metodos.Upload.getPathDefaultHojasVida;
import static com.planit.smsrenta.metodos.Upload.getPathDefaultUsuario;
import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsHojavida;
import com.planit.smsrenta.modelos.SmsProveedor;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Desarrollo_Planit
 */
public class EmpleadoBean extends UsuarioBean implements Serializable {

    //objetos necesarios en vista
    private SmsEmpleado empleadoView;
    private List<SmsEmpleado> empleadosListView;

    //Variables   
    private String buscar;
    private StreamedContent file;//Variable que permite descargar la hoja de vida del empleado
    private UploadedFile archivo;
    private UploadedFile foto;

    private String estadoArchivo;

    protected int operacion; //Controla la operacion a realizar
    private String nombreOperacion;

    //Banderas    
    private boolean habilitarCancelar;
    private boolean hojavidaActualizada;

    //Conexion con el dao
    IEmpleadoDao empleadoDao;
    IHojaVidaDao hojaDao;
    IProveedorDao proveedorDao;
    IEstadoDao estadoDao;
    IUsuarioDao usuarioDao;

    public EmpleadoBean() {
        super();
        empleadoView = new SmsEmpleado();
        empleadosListView = new ArrayList<>();

        buscar = null;
        habilitarCancelar = true;
        hojavidaActualizada = false;

        operacion = 0;
        nombreOperacion = "Registrar Empleado";

        estadoFoto = "Foto sin subir";
        estadoArchivo = "Hoja de vida sin subir";

        empleadoDao = new ImpEmpleadoDao();
        hojaDao = new ImpHojaVidaDao();
        proveedorDao = new ImpProveedorDao();
        estadoDao = new ImpEstadoDao();
        usuarioDao = new ImpUsuarioDao();
    }

    @PostConstruct
    public void init() {
        empleadosListView = empleadoDao.mostrarEmpleados();
    }

    //Getters & Setters   
    public SmsEmpleado getEmpleadoView() {
        return empleadoView;
    }

    public void setEmpleadoView(SmsEmpleado empleadoView) {
        this.empleadoView = empleadoView;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public UploadedFile getFoto() {
        return foto;
    }

    public void setFoto(UploadedFile foto) {
        this.foto = foto;
    }

    public List<SmsEmpleado> getEmpleadosListView() {
        return empleadosListView;
    }

    public void setEmpleadosListView(List<SmsEmpleado> empleadosListView) {
        this.empleadosListView = empleadosListView;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getEstadoArchivo() {
        return estadoArchivo;
    }

    public void setEstadoArchivo(String estadoArchivo) {
        this.estadoArchivo = estadoArchivo;
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

    //Metodos que se comunican con el controlador    
    public void registrarEmpleado() throws IOException {
        empleadoView.getSmsUsuario().getSmsRol().setRolNombre("Conductor");
        //Asignamos un estado al conductor
        empleadoView.getSmsEstado().setEstadoNombre("No disponible");
        empleadoView.setSmsEstado(estadoDao.consultarEstado(empleadoView.getSmsEstado()));

        //Si el usuario no registra foto y hoja de vida se asignas unas default
        if (empleadoView.getSmsUsuario().getUsuarioFotoRuta() == null && empleadoView.getSmsHojavida().getHojaVidaRuta() == null) {
            //asignamos al usuario la imagen de perfil default
            empleadoView.getSmsUsuario().setUsuarioFotoRuta(getPathDefaultUsuario());
            empleadoView.getSmsUsuario().setUsuarioFotoNombre(getNameDefaultUsuario());
            empleadoView.getSmsHojavida().setHojaVidaNombre(getNameDefaultHojasVida());
            empleadoView.getSmsHojavida().setHojaVidaRuta(getPathDefaultHojasVida());
        }

        //Se genera un login y un pass aleatorio que se le envia al proveedor
        MD5 md = new MD5();
        GenerarPassword pass = new GenerarPassword();
        SendEmail email = new SendEmail();

        password = pass.generarPass(6);//Generamos pass aleatorio

        //Encriptamos las contraseñas
        empleadoView.getSmsUsuario().setUsuarioPassword(md.getMD5(password));//Se encripta la contreseña
        empleadoView.getSmsUsuario().setUsuarioRememberToken(md.getMD5(password));

        registrarHojaVida(empleadoView.getSmsHojavida());//Se registra la hoja de vida

        empleadoView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(empleadoView.getSmsUsuario().getSmsCiudad()));//Asociamos una ciudad a un usuario
        empleadoView.getSmsUsuario().setSmsRol(rolDao.consultarRol(empleadoView.getSmsUsuario().getSmsRol()));//Asociamos un rol a un usuario
        empleadoView.getSmsUsuario().setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta
        empleadoView.setSmsProveedor(proveedorDao.consultarProveedor(empleadoView.getSmsProveedor()));
        empleadoView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(empleadoView.getSmsUsuario().getSmsNacionalidad()));

        //registramos el usuario
        usuarioDao.registrarUsuario(empleadoView.getSmsUsuario());

        //Se consulta toda la informacion de usuario y proveedor y se relaciona al empleado
        empleadoView.setSmsProveedor(proveedorDao.consultarProveedor(empleadoView.getSmsProveedor()));
        empleadoView.setSmsUsuario(usuarioDao.consultarUsuario(empleadoView.getSmsUsuario()));

        empleadoDao.registrarEmpleado(empleadoView);//Registramos al usuario como empleado
        email.sendEmailConductor(empleadoView.getSmsUsuario(), password);//Se envia correo con datos de sesion a conductor

        //Se limpian objetos y se reinician banderas
        estadoFoto = "Foto sin subir";
        estadoArchivo = "Hoja de vida sin subir";

        empleadoView = new SmsEmpleado();
        empleadosListView = empleadoDao.mostrarEmpleados();
    }

    public void modificarPerfilEmpleado() {
        MD5 md = new MD5();

        empleadoView.getSmsUsuario().setUsuarioPassword(md.getMD5(empleadoView.getSmsUsuario().getUsuarioPassword()));
        empleadoView.getSmsUsuario().setUsuarioRememberToken(md.getMD5(empleadoView.getSmsUsuario().getUsuarioRememberToken()));

        //Asociamos una ciudad a un usuario
        empleadoView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(empleadoView.getSmsUsuario().getSmsCiudad()));
        empleadoView.getSmsUsuario().setSmsRol(rolDao.consultarRol(empleadoView.getSmsUsuario().getSmsRol()));//Asociamos un rol a un usuario
        empleadoView.setSmsProveedor(proveedorDao.consultarProveedor(empleadoView.getSmsProveedor()));
        empleadoView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(empleadoView.getSmsUsuario().getSmsNacionalidad()));
        usuarioDao.modificarUsuario(empleadoView.getSmsUsuario());//Modificamos la informacion de usuario

        empleadoDao.modificarEmpleado(empleadoView);
        estadoArchivo = "Hoja subida:" + empleadoView.getSmsHojavida().getHojaVidaNombre();
    }

    public void modificarEmpleado() {

        empleadoView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(empleadoView.getSmsUsuario().getSmsCiudad()));
        empleadoView.setSmsProveedor(proveedorDao.consultarProveedor(empleadoView.getSmsProveedor()));
        empleadoView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(empleadoView.getSmsUsuario().getSmsNacionalidad()));

        usuarioDao.modificarUsuario(empleadoView.getSmsUsuario());
        empleadoDao.modificarEmpleado(empleadoView);//Se modifica el empleado
        estadoFoto = "Foto sin subir";
        estadoArchivo = "Hoja de vida sin subir";

        //Limpiamos objetos
        empleadoView = new SmsEmpleado();
        //Recargamos lista de conductores
        empleadosListView = empleadoDao.mostrarEmpleados();
    }

    public void eliminarEmpleado() {

        usuarioDao.eliminarUsuario(empleadoView.getSmsUsuario());
        hojaDao.eliminarHojaVida(empleadoView.getSmsHojavida());

        empleadoView = new SmsEmpleado();
        empleadosListView = empleadoDao.mostrarEmpleados();
        habilitarCancelar = true;
        operacion = 0;
        hojavidaActualizada = false;
        nombreOperacion = "Registrar Empleado";
    }

    public void filtrarEmpleados() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            empleadosListView = empleadoDao.mostrarEmpleados();
        } else {
            empleadosListView = empleadoDao.filtrarUsuariosEmpleados(buscar);
        }
    }

    public void registrarHojaVida(SmsHojavida h) {
        hojaDao.registrarHojaVida(h);
    }

    //Metodos Propios
    public void metodo() throws IOException {
        if (operacion == 0) {
            registrarEmpleado();
        } else if (operacion == 1) {
            modificarEmpleado();
            //Reiniciamos banderas

            habilitarCancelar = true;
            operacion = 0;
            hojavidaActualizada = false;
            nombreOperacion = "Registrar Empleado";

        }
    }

    public void seleccionarCRUD(int i) {
        operacion = i;
        if (operacion == 1) {

            if (empleadoView.getSmsUsuario().getUsuarioFotoNombre() != null || empleadoView.getSmsUsuario().getUsuarioFotoRuta() != null) {
                estadoFoto = "Foto subida:" + empleadoView.getSmsUsuario().getUsuarioFotoNombre();
            }

            if (empleadoView.getSmsHojavida().getHojaVidaNombre() != null && empleadoView.getSmsHojavida().getHojaVidaRuta() != null) {
                estadoArchivo = "Hoja subida:" + empleadoView.getSmsHojavida().getHojaVidaNombre();
            }

            habilitarCancelar = false;
            nombreOperacion = "Modificar Empleado";

        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados
        empleadoView = new SmsEmpleado();
        operacion = 0;

        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombreOperacion = "Registrar Empleado";
        estadoFoto = "Foto sin subir";
        estadoArchivo = "Hoja de vida sin subir";
    }

    //Subida de archivos
    public void uploadDoc(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedDoc = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathHojasVida();
            destination = map.get("path");
            if (null != uploadedDoc) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedDoc.getInputstream()), uploadedDoc.getFileName(), destination);
                empleadoView.getSmsHojavida().setHojaVidaNombre(uploadedDoc.getFileName());
                empleadoView.getSmsHojavida().setHojaVidaRuta(map.get("url") + uploadedDoc.getFileName());
                if (operacion == 0) {
                    estadoArchivo = "Hoja de vida subida con exito";
                } else if (operacion == 1) {
                    estadoArchivo = "Hoja de vida actualizada con exito";
                    hojavidaActualizada = true;
                }

                if (hojavidaActualizada) {
                    registrarHojaVida(empleadoView.getSmsHojavida());
                }

            }

            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su Hoja de vida (" + uploadedDoc.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    //Subida de archivos
    public void uploadPhoto(FileUploadEvent e) throws IOException {
        try {
            UploadedFile uploadedPhoto = e.getFile();
            String destination;

            HashMap<String, String> map = getMapPathFotosUsuario();
            destination = map.get("path");
            if (null != uploadedPhoto) {
                fileController.uploadFile(IOUtils.toByteArray(uploadedPhoto.getInputstream()), uploadedPhoto.getFileName(), destination);
                empleadoView.getSmsUsuario().setUsuarioFotoNombre(uploadedPhoto.getFileName());
                empleadoView.getSmsUsuario().setUsuarioFotoRuta(map.get("url") + uploadedPhoto.getFileName());
                estadoFoto = "Foto actualizada con exito";
            }
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su foto (" + uploadedPhoto.getFileName() + ")  se ha guardado con exito.", ""));
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    
    public List<String> consultarEmpleadosSegunProveedor(SmsProveedor proveedor) {
        empleadosListView = new ArrayList<>();
        nombresUsuarios = new ArrayList<>();
        empleadosListView = empleadoDao.consultarEmpleadosSegunProveedor(proveedor);
        for (int i = 0; i < empleadosListView.size(); i++) {
            nombresUsuarios.add(empleadosListView.get(i).getSmsUsuario().getUsuarioNombre());
        }
        return nombresUsuarios;
    }

    

}
