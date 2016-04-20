/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCiudad;
import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsProveedor;
import com.planit.smsrenta.modelos.SmsUsuario;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ImpEmpleadoDao implements IEmpleadoDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsEmpleado> mostrarEmpleados() {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado "
                    + "left join fetch empleado.smsEstado "
                    + "left join fetch empleado.smsProveedor as proveedor "
                    + "left join fetch empleado.smsUsuario as usuario "
                    + "left join fetch usuario.smsNacionalidad as nacionalidad "
                    + "left join fetch usuario.smsCiudad "
                    + "left join fetch usuario.smsRol "
                    + "left join fetch empleado.smsHojavida as hojaVida");
            empleados = (List<SmsEmpleado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return empleados;
    }

    @Override
    public void registrarEmpleado(SmsEmpleado empleado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(empleado);
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
    public void modificarEmpleado(SmsEmpleado empleado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(empleado);
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
    public void eliminarEmpleado(SmsEmpleado empleado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(empleado);
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
    public SmsEmpleado consultarEmpleado(SmsUsuario usuario) {
        Session session = null;
        SmsEmpleado empleados = new SmsEmpleado();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado "
                    + "left join fetch empleado.smsProveedor as proveedor "
                    + "left join fetch empleado.smsUsuario as usuario "
                    + "left join fetch usuario.smsNacionalidad as nacionalidad "
                    + "left join fetch usuario.smsCiudad "
                    + "left join fetch usuario.smsRol "
                    + "left join fetch empleado.smsHojavida as hojaVida "
                    + "left join fetch empleado.smsVehiculos "
                    + "where usuario.idUsuario = '" + usuario.getIdUsuario() + "' or "
                    + "usuario.usuarioNombre = '" + usuario.getUsuarioNombre() + "'");
            empleados = (SmsEmpleado) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return empleados;
    }
    

    @Override
    public List<SmsEmpleado> consultarEmpleadosCiudad(SmsCiudad ciudad) {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado left join fetch empleado.smsUsuario as usuario where usuario.smsCiudad.ciudadNombre = '" + ciudad.getCiudadNombre() + "'");
            empleados = (List<SmsEmpleado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return empleados;
    }

    @Override
    public List<SmsUsuario> consultarUsuariosEmpleados() {
        Session session = null;
        List<SmsUsuario> usuarios = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsRol as rol left join fetch usuario.smsNacionalidad as nacionalidad left join fetch usuario.smsCiudad as ciudad where rol.rolNombre = 'Empleado'");
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

    @Override
    public List<SmsEmpleado> filtrarUsuariosEmpleados(String valor) {
        Session session = null;
        List<SmsEmpleado> conductores = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario as usuario "
                    + "left join fetch empleado.smsProveedor as proveedor "                    
                    + "left join fetch usuario.smsNacionalidad as nacionalidad "
                    + "left join fetch usuario.smsCiudad as ciudad "
                    + "left join fetch usuario.smsRol as rol "
                    + "left join fetch empleado.smsHojavida as hojaVida "
                    + "left join fetch empleado.smsVehiculos where "
                    + "usuario.usuarioNombre LIKE '%" + valor + "%' or "
                    + "usuario.usuarioCc LIKE '%" + valor + "%' or "
                    + "usuario.usuarioEmail LIKE '%" + valor + "%' or "
                    + "usuario.usuarioTelefono LIKE '%" + valor + "%' or "
                    + "ciudad.ciudadNombre LIKE '%" + valor + "%' or "
                    + "proveedor.proveedorRazonSocial LIKE '%" + valor + "%' or "
                    + "proveedor.proveedorNit LIKE '%" + valor + "%'");
            conductores = (List<SmsEmpleado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return conductores;
    }

    @Override
    public List<SmsEmpleado> consultarEmpleadosSegunProveedor(SmsProveedor proveedor) {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado "
                    + "left join fetch empleado.smsEstado "
                    + "left join fetch empleado.smsProveedor as proveedor "
                    + "left join fetch empleado.smsUsuario as usuario "
                    + "left join fetch usuario.smsNacionalidad as nacionalidad "
                    + "left join fetch usuario.smsCiudad "
                    + "left join fetch usuario.smsRol "
                    + "left join fetch empleado.smsHojavida as hojaVida "
                    + "where "
                    + "proveedor.proveedorRazonSocial = '" + proveedor.getProveedorRazonSocial() + "'");
            empleados = (List<SmsEmpleado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return empleados;
    }

    @Override
    public List<SmsEmpleado> filtrarUsuariosEmpleadosSegunProveedor(String valor, SmsProveedor proveedor) {
        Session session = null;
        List<SmsEmpleado> conductores = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado "
                    + "left join fetch empleado.smsUsuario as usuario "
                    + "left join fetch usuario.smsRol as rol "
                    + "left join fetch usuario.smsCiudad as ciudad "
                    + "left join fetch usuario.smsNacionalidad as nacionalidad "
                    + "left join fetch empleado.smsEstado "
                    + "left join fetch empleado.smsProveedor as proveedor where "
                    + "(usuario.usuarioNombre LIKE '%" + valor + "%' or "
                    + "usuario.usuarioCc LIKE '%" + valor + "%' or "
                    + "usuario.usuarioEmail LIKE '%" + valor + "%' or "
                    + "usuario.usuarioTelefono LIKE '%" + valor + "%' or "
                    + "ciudad.ciudadNombre LIKE '%" + valor + "%' or "
                    + "proveedor.proveedorNit LIKE '%" + valor + "%') and "
                    + "proveedor.proveedorRazonSocial = '" + proveedor.getProveedorRazonSocial() + "'");
            conductores = (List<SmsEmpleado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return conductores;
    }

    @Override
    public List<SmsEmpleado> consultarEmpleadosSegunVehiculo(SmsVehiculo vehiculo) {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado left join fetch empleado.smsEstado "
                    + "left join fetch empleado.smsProveedor as proveedor "
                    + "left join fetch empleado.smsUsuario as usuario "
                    + "left join fetch usuario.smsNacionalidad as nacionalidad "
                    + "left join fetch usuario.smsCiudad "
                    + "left join fetch usuario.smsRol "
                    + "left join fetch empleado.smsEstado as estado "
                    + "left join fetch empleado.smsHojavida as hojaVida "
                    + "where estado.idEstado = '1' and "
                    + "empleado in(select empleado from SmsVehiculo as vehiculo left outer join vehiculo.smsEmpleados as empleado where vehiculo.idVehiculo = '" + vehiculo.getIdVehiculo() + "')");
            empleados = (List<SmsEmpleado>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return empleados;
    }

    @Override
    public SmsEmpleado consultarEmpleadoConVehiculo(SmsEmpleado empleado) {
        Session session = null;
        SmsEmpleado empleados = new SmsEmpleado();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado "
                    + "left join fetch empleado.smsProveedor as proveedor "
                    + "left join fetch empleado.smsUsuario as usuario "
                    + "left join fetch usuario.smsNacionalidad as nacionalidad "
                    + "left join fetch usuario.smsCiudad "
                    + "left join fetch usuario.smsRol "
                    + "left join fetch empleado.smsHojavida as hojaVida "
                    + "left join fetch empleado.smsVehiculos "
                    + "where empleado.idEmpleado = '" + empleado.getIdEmpleado() + "'");
            empleados = (SmsEmpleado) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return empleados;}

}
