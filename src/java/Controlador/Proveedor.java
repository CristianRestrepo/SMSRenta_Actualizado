/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DAO.IProveedorDao;
import DAO.ImpProveedorDao;
import Modelo.SmsProveedor;
import Modelo.SmsUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public class Proveedor extends Usuario {

    SmsProveedor proveedor;
    List<SmsProveedor> proveedores;

    public Proveedor() {
        super();
        proveedor = new SmsProveedor();
    }

    //Metodos de la clase
    public void registrarProveedor(SmsProveedor p, SmsUsuario u) {
        usuario = u;
        proveedor = p;
        usuario = consultarUsuario(usuario).get(0);
        proveedor.setSmsUsuario(usuario);
        IProveedorDao provedorDao = new ImpProveedorDao();
        provedorDao.registrarProveedor(proveedor);
    }

    public void modificarProveedor(SmsProveedor p, SmsUsuario u) {
        usuario = u;
        proveedor = p;
        usuario = consultarUsuario(usuario).get(0);
        proveedor.setSmsUsuario(usuario);
        IProveedorDao provedorDao = new ImpProveedorDao();
        provedorDao.modificarProveedor(proveedor);
    }

    public void eliminarProveedor(SmsProveedor p) {
        proveedor = p;
        IProveedorDao provedorDao = new ImpProveedorDao();
        provedorDao.eliminarProveedor(proveedor);
    }

    public List<SmsProveedor> cargarProveedor() {
        proveedores = new ArrayList<>();
        IProveedorDao provedorDao = new ImpProveedorDao();
        proveedores = provedorDao.mostrarProveedores();
        return proveedores;
    }
    
    public List<SmsProveedor> consultarProveedor(SmsUsuario usuario) {
        proveedores = new ArrayList<>();
        IProveedorDao provedorDao = new ImpProveedorDao();
        proveedores = provedorDao.consultarProveedorUsuario(usuario);
        return proveedores;
    }

    public List<SmsProveedor> filtrarProveedor(String valor) {
        proveedores = new ArrayList<>();
        IProveedorDao provedorDao = new ImpProveedorDao();
        proveedores = provedorDao.filtrarProveedor(valor);
        return proveedores;
    }
}
