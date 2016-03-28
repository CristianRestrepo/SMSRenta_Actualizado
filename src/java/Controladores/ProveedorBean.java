/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IMercadoDao;
import Funciones.MD5;
import DAO.IProveedorDao;
import DAO.IUsuarioDao;
import DAO.ImpMercadoDao;
import DAO.ImpProveedorDao;
import DAO.ImpUsuarioDao;
import Funciones.GenerarPassword;
import Funciones.SendEmail;
import static Funciones.Upload.getNameDefaultUsuario;
import static Funciones.Upload.getPathDefaultUsuario;
import Modelo.SmsMercado;
import Modelo.SmsProveedor;
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
    private List<String> mercadosSeleccionados;
    private List<SmsProveedor> proveedorListView;

    //lista de Id de proveedor
    private List<String> nombresProveedoresView;

    //Variables
    private String buscar;
    protected int operacion; //Controla la operacion a realizar
    private String nombreOperacion;

    //Banderas    
    private boolean habilitarCancelar;
    boolean contraseñaModificada;

    //Conexion con el dao
    IProveedorDao proveedorDao;
    IMercadoDao mercadoDao;
    IUsuarioDao usuarioDao;

    public ProveedorBean() {
        super();
        proveedorView = new SmsProveedor();
        mercadosSeleccionados = new ArrayList<>();
        proveedorListView = new ArrayList<>();
        mercadoView = new SmsMercado();

        buscar = null;
        proveedorDao = new ImpProveedorDao();
        mercadoDao = new ImpMercadoDao();
        usuarioDao = new ImpUsuarioDao();

        habilitarCancelar = true;
        contraseñaModificada = false;

        operacion = 0;
        nombreOperacion = "Registrar Proveedor";
    }

    @PostConstruct
    public void init() {
        proveedorListView = proveedorDao.mostrarProveedores();
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
        proveedorListView = proveedorDao.mostrarProveedores();
        for (int i = 0; i < proveedorListView.size(); i++) {
            nombresProveedoresView.add(proveedorListView.get(i).getProveedorRazonSocial());
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

    public List<String> getMercadosSeleccionados() {
        return mercadosSeleccionados;
    }

    public void setMercadosSeleccionados(List<String> mercadosSeleccionados) {
        this.mercadosSeleccionados = mercadosSeleccionados;
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

    public List<SmsProveedor> getProveedorListView() {
        return proveedorListView;
    }

    public void setProveedorListView(List<SmsProveedor> proveedorListView) {
        this.proveedorListView = proveedorListView;
    }

    //Metodos
    public void registrarProveedor() {
        //asignamos un rol al usuario
        proveedorView.getSmsUsuario().getSmsRol().setRolNombre("Proveedor");

        //asignamos al usuario la imagen de perfil default
        proveedorView.getSmsUsuario().setUsuarioFotoRuta(getPathDefaultUsuario());
        proveedorView.getSmsUsuario().setUsuarioFotoNombre(getNameDefaultUsuario());

        //Se genera un login y un pass aleatorio que se le envia al proveedor
        MD5 md = new MD5();
        GenerarPassword pass = new GenerarPassword();
        SendEmail email = new SendEmail();

        password = pass.generarPass(6);//Generamos pass aleatorio

        //Encriptamos las contraseñas
        proveedorView.getSmsUsuario().setUsuarioPassword(md.getMD5(password));//Se encripta la contreseña
        proveedorView.getSmsUsuario().setUsuarioRememberToken(md.getMD5(password));

        //el metodo recibe los atributos, agrega al atributo ciudad del objeto usuario un objeto correspondiente, 
        //de la misma forma comprueba el rol y lo asocia, por ultimo persiste el usuario en la base de datos
        proveedorView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(proveedorView.getSmsUsuario().getSmsCiudad()).get(0));//Asociamos una ciudad a un usuario
        proveedorView.getSmsUsuario().setSmsRol(rolDao.consultarRol(proveedorView.getSmsUsuario().getSmsRol()).get(0));//Asociamos un rol a un usuario
        proveedorView.getSmsUsuario().setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta
        proveedorView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(proveedorView.getSmsUsuario().getSmsNacionalidad()).get(0));
        //registramos el usuario y recargamos la lista de clientes
        usuarioDao.registrarUsuario(proveedorView.getSmsUsuario());

        //Asignamos la informacion de usuario al proveedor a registrar
        proveedorView.setSmsUsuario(usuarioDao.consultarUsuario(proveedorView.getSmsUsuario()).get(0));

        //Registramos al usuario como proveedor
        proveedorDao.registrarProveedor(proveedorView);
        proveedorView = proveedorDao.consultarProveedor(proveedorView).get(0);

        for (int i = 0; i < mercadosSeleccionados.size(); i++) { //Relacionamos la categoria con los mercados seleccionados
            mercadoView.setMercadoNombre(mercadosSeleccionados.get(i));
            mercadoView = mercadoDao.consultarMercadoConProveedores(mercadoView).get(0);
            mercadoView.getSmsProveedors().add(proveedorView);//Se relaciona el proveedor al mercado 
            proveedorView.getSmsMercados().add(mercadoView);//Se relaciona el mercado al proveedor
        }

        proveedorDao.modificarProveedor(proveedorView);//Modificamos el proveedor recien registrado para agregar los mercados a los que pertenece
        email.sendEmailProveedor(proveedorView.getSmsUsuario(), proveedorView, password);//Enviamos correo al proveedor, confirmando su registro al sistema, y enviando datos de sesion
        proveedorListView = proveedorDao.mostrarProveedores();//Recargamos la lista de proveedores

        //Limpiamos objetos      
        proveedorView = new SmsProveedor();
        mercadosSeleccionados = new ArrayList<>();
        password = null;
    }

    public void modificarProveedor() {

        boolean valor = false;

        for (int j = 0; j < mercadosSeleccionados.size(); j++) {

            mercadoView.setMercadoNombre(mercadosSeleccionados.get(j));
            for (SmsMercado mercado : proveedorView.getSmsMercados()) {
                if (mercado.getMercadoNombre().equals(mercadosSeleccionados.get(j))) {
                    valor = true;
                }
            }
            if (!valor) {

                mercadoView = mercadoDao.consultarMercadoConProveedores(mercadoView).get(0);
                mercadoView.getSmsProveedors().add(proveedorView);
                proveedorView.getSmsMercados().add(mercadoView);
            }
            valor = false;
        }

        //el metodo recibe los atributos, agrega al atributo ciudad del objeto usuario un objeto correspondiente, 
        //de la misma forma comprueba el rol y lo asocia        
        proveedorView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(proveedorView.getSmsUsuario().getSmsCiudad()).get(0));//Asociamos una ciudad a un usuario
        proveedorView.getSmsUsuario().setSmsRol(rolDao.consultarRol(proveedorView.getSmsUsuario().getSmsRol()).get(0));//Asociamos un rol a un usuario
        proveedorView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(proveedorView.getSmsUsuario().getSmsNacionalidad()).get(0));

        //Se modifica el usuario y se recarga la lista de proveedores
        usuarioDao.modificarUsuario(proveedorView.getSmsUsuario());
        proveedorDao.modificarProveedor(proveedorView);
        proveedorListView = proveedorDao.mostrarProveedores();

        //Se limpian objetos
        proveedorView = new SmsProveedor();
        mercadosSeleccionados = new ArrayList();

    }

    public void eliminarProveedor() {

        //Se elimina el objeto
        proveedorDao.eliminarProveedor(proveedorView);
        usuarioDao.eliminarUsuario(proveedorView.getSmsUsuario());//Se recarga la lista de proveedores

        proveedorListView = proveedorDao.mostrarProveedores();

        //Limpiamos objetos
        proveedorView = new SmsProveedor();
        mercadosSeleccionados = new ArrayList();

        habilitarCancelar = true;
        operacion = 0;
        nombreOperacion = "Registrar Proveedor";

    }

    public void filtrarProveedores() {
        usuariosListView = new ArrayList<>();
        if (buscar == null) {
            proveedorListView = proveedorDao.mostrarProveedores();
        } else {
            proveedorListView = proveedorDao.filtrarProveedor(buscar);
        }
    }

    //Metodos Propios
    public void metodo() {
        if (operacion == 0) {
            registrarProveedor();
        } else if (operacion == 1) {
            modificarProveedor();
            //Reiniciamos banderas

            habilitarCancelar = true;
            operacion = 0;
            nombreOperacion = "Registrar Proveedor";

        }
    }

    public void seleccionarCRUD(int i) {
        operacion = i;
        mercadosSeleccionados = new ArrayList<>();
        if (operacion == 1) {

            List<SmsMercado> mercadoList = new ArrayList<>();
            mercadoList = mercadoDao.consultarMercados();

            for (SmsMercado mercado : proveedorView.getSmsMercados()) {
                for (int j = 0; j < mercadoList.size(); j++) {
                    if (mercado.getMercadoNombre().equals(mercadoList.get(j).getMercadoNombre())) {
                        mercadosSeleccionados.add(mercado.getMercadoNombre());
                    }
                }
            }

            habilitarCancelar = false;
            nombreOperacion = "Modificar Proveedor";
        }
    }

    public void cancelar() {
        //Limpiamos objetos utilizados
        proveedorView = new SmsProveedor();

        //Reiniciamos los objetos
        mercadosSeleccionados = new ArrayList<>();
        contraseñaModificada = false;
        habilitarCancelar = true;
        nombreOperacion = "Registrar Proveedor";

    }
}
