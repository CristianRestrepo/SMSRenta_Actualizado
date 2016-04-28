/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.dao.IMercadoDao;
import com.planit.smsrenta.metodos.MD5;
import com.planit.smsrenta.dao.IProveedorDao;
import com.planit.smsrenta.dao.IUsuarioDao;
import com.planit.smsrenta.dao.ImpMercadoDao;
import com.planit.smsrenta.dao.ImpProveedorDao;
import com.planit.smsrenta.dao.ImpUsuarioDao;
import com.planit.smsrenta.metodos.GenerarPassword;
import com.planit.smsrenta.metodos.SendEmail;
import static com.planit.smsrenta.metodos.Upload.getNameDefaultUsuario;
import static com.planit.smsrenta.metodos.Upload.getPathDefaultUsuario;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsProveedor;
import java.io.IOException;
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
    public void registrarProveedor() throws IOException {
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
        proveedorView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(proveedorView.getSmsUsuario().getSmsCiudad()));//Asociamos una ciudad a un usuario
        proveedorView.getSmsUsuario().setSmsRol(rolDao.consultarRol(proveedorView.getSmsUsuario().getSmsRol()));//Asociamos un rol a un usuario
        proveedorView.getSmsUsuario().setUsuarioEstadoUsuario(1);//Asignamos un estado de cuenta
        proveedorView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(proveedorView.getSmsUsuario().getSmsNacionalidad()));

        //registramos el usuario y recargamos la lista de clientes
        usuarioDao.registrarUsuario(proveedorView.getSmsUsuario());

        //Asignamos la informacion de usuario al proveedor a registrar
        //proveedorView.setSmsUsuario(usuarioDao.consultarUsuario(proveedorView.getSmsUsuario()).get(0));
        //Registramos al usuario como proveedor
        proveedorDao.registrarProveedor(proveedorView);
        //proveedorView = proveedorDao.consultarProveedor(proveedorView).get(0);

        List<SmsMercado> mercados = new ArrayList<>();
        for (int i = 0; i < mercadosSeleccionados.size(); i++) { //Relacionamos la categoria con los mercados seleccionados
            //Consultamos los mercados seleccionado y guardamos los objetos completos en una lista
            mercadoView.setMercadoNombre(mercadosSeleccionados.get(i));
            mercadoView = mercadoDao.consultarMercadoConProveedores(mercadoView);
            mercados.add(mercadoView);
            mercadoView = new SmsMercado();
        }

        for (int i = 0; i < mercados.size(); i++) {//asociamos los mercados con el proveedor
            mercados.get(i).getSmsProveedors().add(proveedorView);
            proveedorView.getSmsMercados().add(mercados.get(i));
        }

        proveedorDao.modificarProveedor(proveedorView);//Modificamos el proveedor recien registrado para agregar los mercados a los que pertenece
        email.sendEmailProveedor(proveedorView.getSmsUsuario(), proveedorView, password);//Enviamos correo al proveedor, confirmando su registro al sistema, y enviando datos de sesion
        proveedorListView = proveedorDao.mostrarProveedores();//Recargamos la lista de proveedores

        //Limpiamos objetos      
        proveedorView = new SmsProveedor();
        mercadoView = new SmsMercado();
        mercadosSeleccionados = new ArrayList<>();
        password = null;
    }

    public void modificarProveedor() {

        boolean valor = false;
        boolean noexiste = false;
        List<SmsMercado> mercadoList = mercadoDao.consultarMercadosSegunProveedor(proveedorView);
        List<SmsMercado> mercadoEliminadoList = new ArrayList<>();

        //identificamos mercados deseleccionados
        for (SmsMercado mercado : mercadoList) {
            for (int i = 0; i < mercadosSeleccionados.size(); i++) {
                if (mercado.getMercadoNombre().equals(mercadosSeleccionados.get(i))) {
                    noexiste = true;
                }
            }
            if (!noexiste) {
                mercadoEliminadoList.add(mercado);
            }
            noexiste = false;
        }

        //Agregamos nuevos mercados seleccionados
        for (int j = 0; j < mercadosSeleccionados.size(); j++) {
            for (SmsMercado mercado : mercadoList) {
                if (mercado.getMercadoNombre().equals(mercadosSeleccionados.get(j))) {
                    valor = true;
                }
            }
            if (!valor) {
                mercadoView.setMercadoNombre(mercadosSeleccionados.get(j));
                mercadoView = mercadoDao.consultarMercadoConProveedores(mercadoView);
                mercadoView.getSmsProveedors().add(proveedorView);
                proveedorView.getSmsMercados().add(mercadoView);
            }
            valor = false;
            mercadoView = new SmsMercado();
        }

        //Removemos mercados deseleccionados
        for (int i = 0; i < mercadoEliminadoList.size(); i++) {
            SmsProveedor prov = new SmsProveedor();
            
            for (SmsProveedor proveedor : mercadoEliminadoList.get(i).getSmsProveedors()) {
                if(proveedor.getIdProveedor().equals(proveedorView.getIdProveedor())){
                    prov = proveedor;
                }
            }
            
            for (SmsMercado mercado : proveedorView.getSmsMercados()){
                if(mercado.getIdMercado().equals(mercadoEliminadoList.get(i).getIdMercado())){
                mercadoView = mercado;
                }
            }
            
            mercadoEliminadoList.get(i).getSmsProveedors().remove(prov);
            proveedorView.getSmsMercados().remove(mercadoView);

        }

        //el metodo recibe los atributos, agrega al atributo ciudad del objeto usuario un objeto correspondiente, 
        //de la misma forma comprueba el rol y lo asocia        
        proveedorView.getSmsUsuario().setSmsCiudad(ciudadDao.consultarCiudad(proveedorView.getSmsUsuario().getSmsCiudad()));//Asociamos una ciudad a un usuario
        proveedorView.getSmsUsuario().setSmsRol(rolDao.consultarRol(proveedorView.getSmsUsuario().getSmsRol()));//Asociamos un rol a un usuario
        proveedorView.getSmsUsuario().setSmsNacionalidad(nacionalidadDao.consultarNacionalidad(proveedorView.getSmsUsuario().getSmsNacionalidad()));

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
    public void metodo() throws IOException {
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
            mercadoList = mercadoDao.consultarMercadosSegunProveedor(proveedorView);

            for (int j = 0; j < mercadoList.size(); j++) {
                mercadosSeleccionados.add(mercadoList.get(j).getMercadoNombre());
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
        operacion = 0;
        habilitarCancelar = true;
        nombreOperacion = "Registrar Proveedor";

    }
}
