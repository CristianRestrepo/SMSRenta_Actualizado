/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsProveedor;
import Modelo.SmsUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpProveedorDao implements IProveedorDao {

    @Override
    public List<SmsProveedor> mostrarProveedores() {
        Session session = null;
        List<SmsProveedor> Proveedores = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsProveedor as proveedor left join fetch proveedor.smsMercados as mercados left join fetch proveedor.smsUsuario as usuario"
                    + " left join fetch usuario.smsCiudad as ciudad left join fetch usuario.smsRol as rol");
            Proveedores = (List<SmsProveedor>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Proveedores;
    }

    @Override
    public void registrarProveedor(SmsProveedor proveedor) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(proveedor);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void modificarProveedor(SmsProveedor proveedor) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(proveedor);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void eliminarProveedor(SmsProveedor proveedor) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(proveedor);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public List<SmsProveedor> filtrarProveedor(String dato) {
        Session session = null;
        List<SmsProveedor> Proveedores = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsProveedor as proveedor left join fetch proveedor.smsUsuario as usuario left join fetch usuario.smsCiudad as ciudad "
                    + "where proveedor.proveedorValorGanancia LIKE '%" + dato + "%' OR usuario.usuarioNombre LIKE '%" + dato + "%' OR usuario.usuarioEmail LIKE '" + dato + "' OR proveedor.proveedorNit LIKE '" + dato + "' OR proveedor.proveedorRazonSocial LIKE '" + dato + "' OR "
                    + "ciudad.ciudadNombre LIKE '" + dato + "'");
            Proveedores = (List<SmsProveedor>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Proveedores;
    }

    @Override
    public List<SmsProveedor> consultarProveedor(SmsProveedor proveedor) {
        Session session = null;
        List<SmsProveedor> Proveedores = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsProveedor as proveedor left join fetch proveedor.smsUsuario as usuario left join fetch proveedor.smsMercados where proveedor.idProveedor = '" + proveedor.getIdProveedor() + "' or proveedor.proveedorRazonSocial = '" + proveedor.getProveedorRazonSocial() + "'");
            Proveedores = (List<SmsProveedor>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Proveedores;
    }
    
    @Override
    public List<SmsUsuario> consultarUsuariosProveedores() {
        Session session = null;
        List<SmsUsuario> usuarios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsRol as rol left join fetch usuario.smsCiudad as ciudad where rol.rolNombre = 'Proveedor'");
            usuarios = (List<SmsUsuario>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuarios;
    }

}
