package Controladores;

import DAO.IEmpleadoDao;
import DAO.IEstadoDao;
import DAO.IHojaVidaDao;
import DAO.IProveedorDao;
import DAO.ImpEmpleadoDao;
import DAO.ImpEstadoDao;
import DAO.ImpHojaVidaDao;
import DAO.ImpProveedorDao;
import Funciones.GenerarPassword;
import Funciones.MD5;
import Funciones.SendEmail;
import static Funciones.Upload.getMapPathFotosUsuario;
import static Funciones.Upload.getMapPathHojasVida;
import static Funciones.Upload.getNameDefaultHojasVida;
import static Funciones.Upload.getNameDefaultUsuario;
import static Funciones.Upload.getPathDefaultHojasVida;
import static Funciones.Upload.getPathDefaultUsuario;
import Modelo.SmsCiudad;
import Modelo.SmsEmpleado;
import Modelo.SmsHojavida;
import Modelo.SmsReservacion;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public void registrarEmpleado() {
        empleadoView.getSmsUsuario().getSmsRol().setRolNombre("Conductor");
        //Asignamos un estado al conductor
        empleadoView.getSmsEstado().setEstadoNombre("Disponible");
        empleadoView.setSmsEstado(estadoDao.consultarEstado(empleadoView.getSmsEstado()).get(0));
        
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

        registrarHojaVida(empleadoView.getSmsHojavida());

        empleadoView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(empleadoView.getSmsUsuario().getSmsCiudad()).get(0));//Asociamos una ciudad a un usuario
        empleadoView.getSmsUsuario().setSmsRol(rolDao.consultarRol(empleadoView.getSmsUsuario().getSmsRol()).get(0));//Asociamos un rol a un usuario
        empleadoView.getSmsUsuario().setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta
        empleadoView.setSmsProveedor(proveedorDao.consultarProveedor(empleadoView.getSmsProveedor()).get(0));
        empleadoView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(empleadoView.getSmsUsuario().getSmsNacionalidad()).get(0));
        
        //registramos el usuario
        usuarioDao.registrarUsuario(empleadoView.getSmsUsuario());

        //Se consulta toda la informacion de usuario y proveedor y se relaciona al empleado
        empleadoView.setSmsProveedor(proveedorDao.consultarProveedor(empleadoView.getSmsProveedor()).get(0));
        empleadoView.setSmsUsuario(usuarioDao.consultarUsuario(empleadoView.getSmsUsuario()).get(0));

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
        empleadoView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(empleadoView.getSmsUsuario().getSmsCiudad()).get(0));
        empleadoView.getSmsUsuario().setSmsRol(rolDao.consultarRol(empleadoView.getSmsUsuario().getSmsRol()).get(0));//Asociamos un rol a un usuario
        empleadoView.setSmsProveedor(proveedorDao.consultarProveedor(empleadoView.getSmsProveedor()).get(0));
        empleadoView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(empleadoView.getSmsUsuario().getSmsNacionalidad()).get(0));
        usuarioDao.modificarUsuario(empleadoView.getSmsUsuario());//Modificamos la informacion de usuario

        empleadoDao.modificarEmpleado(empleadoView);
        estadoArchivo = "Hoja subida:" + empleadoView.getSmsHojavida().getHojaVidaNombre();
    }

    public void modificarEmpleado() {
        
        empleadoView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(empleadoView.getSmsUsuario().getSmsCiudad()).get(0));
        empleadoView.setSmsProveedor(proveedorDao.consultarProveedor(empleadoView.getSmsProveedor()).get(0));
        empleadoView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(empleadoView.getSmsUsuario().getSmsNacionalidad()).get(0));
              
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
    }

    public void filtrarEmpleados() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            usuariosListView = empleadoDao.consultarUsuariosEmpleados();
        } else {
            usuariosListView = empleadoDao.filtrarUsuariosEmpleados(buscar);
        }
    }

    public void registrarHojaVida(SmsHojavida h) {
        hojaDao.registrarHojaVida(h);
    }

    //Metodos Propios
    public void metodo() {
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

        //Reiniciamos los objetos
        habilitarCancelar = true;
        nombreOperacion = "Registrar Proveedor";
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

    public List<SmsEmpleado> consultarEmpleadosDisponibles(SmsReservacion reserva) {

        empleadosListView = new ArrayList<>();

        SimpleDateFormat formatDate;
        SimpleDateFormat formatTime;
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

        String FechaInicio = formatDate.format(reserva.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reserva.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reserva.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reserva.getReservacionHoraLlegada());

        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(reserva.getReservacionHoraInicio());
        calInicio.add(Calendar.HOUR, -1);
        calInicio.add(Calendar.MINUTE, -59);

        Calendar calLlegada = Calendar.getInstance();
        calLlegada.setTime(reserva.getReservacionHoraLlegada());
        calLlegada.add(Calendar.HOUR, 1);
        calLlegada.add(Calendar.MINUTE, 59);

        Date hespacioInicio = calInicio.getTime();
        Date hespacioLlegada = calLlegada.getTime();

        String espacioinicio = formatTime.format(hespacioInicio);
        String espacioLlegada = formatTime.format(hespacioLlegada);

        empleadosListView = empleadoDao.consultarEmpleadosDisponibles(FechaInicio, FechaLlegada, HoraInicio, HoraLlegada, reserva.getSmsCiudadByIdCiudadInicio().getCiudadNombre(), espacioinicio, espacioLlegada, reserva.getSmsVehiculo().getSmsProveedor().getProveedorRazonSocial());
        List<SmsEmpleado> lista = new ArrayList<>();
//        for (int i = 0; i < empleadosListView.size(); i++) {
//            lista.add(empleadoDao.consultarEmpleado(empleadosListView.get(i).getSmsUsuario()).get(0));
//        }
        return empleadosListView;
    }

}
