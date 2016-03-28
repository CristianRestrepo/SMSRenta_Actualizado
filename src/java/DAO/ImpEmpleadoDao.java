/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCiudad;
import Modelo.SmsEmpleado;
import Modelo.SmsUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ImpEmpleadoDao implements IEmpleadoDao {

    @Override
    public List<SmsEmpleado> mostrarEmpleados() {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado left join fetch empleado.smsProveedor as proveedor left join fetch empleado.smsUsuario as usuario left join fetch usuario.smsNacionalidad as nacionalidad left join fetch usuario.smsCiudad left join fetch usuario.smsRol left join fetch empleado.smsHojavida as hojaVida");
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
            session = NewHibernateUtil.getSessionFactory().openSession();
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
            session = NewHibernateUtil.getSessionFactory().openSession();
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
            session = NewHibernateUtil.getSessionFactory().openSession();
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
    public List<SmsEmpleado> consultarEmpleado(SmsUsuario usuario) {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado left join fetch empleado.smsProveedor as proveedor left join fetch empleado.smsUsuario as usuario left join fetch usuario.smsNacionalidad as nacionalidad left join fetch usuario.smsCiudad left join fetch usuario.smsRol left join fetch empleado.smsHojavida as hojaVida where usuario.idUsuario = '" + usuario.getIdUsuario() + "'");
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
    public List<SmsEmpleado> consultarEmpleadosDisponibles(String fechaInicio, String fechaLlegada, String horaInicio, String horaLlegada, String ciudad, String espacioInicio, String espacioLlegada, String Proveedor) {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado left join fetch empleado.smsProveedor as proveedor left join fetch empleado.smsUsuario as usuario left join fetch usuario.smsNacionalidad as nacionalidad left join fetch usuario.smsCiudad left join fetch usuario.smsRol left join fetch empleado.smsHojavida as hojaVida "
                    + "where empleado.smsUsuario.smsCiudad.ciudadNombre = '" + ciudad + "' and "
                    + "empleado.smsProveedor.proveedorRazonSocial = '" + Proveedor + "' and "
                    + "not exists(from SmsReservacion as reservacion where "
                    + "reservacion.reservacionFechaInicio = '" + fechaInicio + "' and "
                    + "reservacion.reservacionFechaLlegada = '" + fechaLlegada + "' and "
                    + "reservacion.reservacionHoraLlegada = '" + horaLlegada + "' and "
                    + "reservacion.reservacionHoraInicio = '" + horaInicio + "' and "
                    + "reservacion.smsEmpleado.idEmpleado = empleado.idEmpleado) "
                    + "and "
                    + "(('" + fechaInicio + "' <> '" + fechaLlegada + "' and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsEmpleado.idEmpleado = empleado.idEmpleado and "
                    + "(reservacion.reservacionFechaInicio >= '" + fechaInicio + "' and "
                    + "reservacion.reservacionFechaLlegada <= '" + fechaLlegada + "')) "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsEmpleado.idEmpleado = empleado.idEmpleado and reservacion.reservacionFechaLlegada = '" + fechaInicio + "' and reservacion.reservacionHoraLlegada > '" + horaInicio + "') "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsEmpleado.idEmpleado = empleado.idEmpleado and reservacion.reservacionFechaInicio = '" + fechaLlegada + "' and reservacion.reservacionHoraInicio < '" + horaLlegada + "') "
                    + "and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsVehiculo.idEmpleado = empleado.idEmpleado and reservacion.reservacionFechaInicio = '" + fechaInicio + "' and (reservacion.reservacionHoraInicio >= '" + horaInicio + "' or reservacion.reservacionHoraLlegada >= '" + horaInicio + "'))"
                    + ")"
                    + "or "
                     + "('" + fechaInicio + "' = '" + fechaLlegada + "' and "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsEmpleado.idEmpleado = empleado.idEmpleado and "
                    + "(reservacion.reservacionHoraInicio >= '" + horaInicio + "' and "
                    + "reservacion.reservacionHoraLlegada <= '" + horaLlegada + "') and reservacion.reservacionFechaInicio = '" + fechaInicio + "')"
                    + "or "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsEmpleado.idEmpleado = empleado.idEmpleado and reservacion.reservacionFechaLlegada = '" + fechaInicio + "' and reservacion.reservacionHoraLlegada > '" + horaInicio + "') "
                    + "or "
                    + "not exists(from SmsReservacion as reservacion where reservacion.smsEmpleado.idEmpleado = empleado.idEmpleado and reservacion.reservacionFechaInicio = '" + fechaLlegada + "' and reservacion.reservacionHoraInicio < '" + horaLlegada + "') "
                    + "))");

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
    public List<SmsEmpleado> consultarEmpleadosCiudad(SmsCiudad ciudad) {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
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
            session = NewHibernateUtil.getSessionFactory().openSession();
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
    public List<SmsUsuario> filtrarUsuariosEmpleados(String valor) {
        Session session = null;
        List<SmsUsuario> usuarios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsRol as rol left join fetch usuario.smsCiudad as ciudad left join fetch usuario.smsNacionalidad as nacionalidad where "
                    + "(usuario.usuarioNombre LIKE '%" + valor + "%' or usuario.usuarioCc LIKE '%" + valor + "%' or usuario.usuarioEmail LIKE '%" + valor + "%' or usuario.usuarioTelefono LIKE '%" + valor + "%' or "
                    + "ciudad.ciudadNombre LIKE '%" + valor + "%') and rol.rolNombre = 'Empleado'");
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
    public List<SmsEmpleado> consultarEmpleadosSegunProveedor(String Proveedor) {
        Session session = null;
        List<SmsEmpleado> empleados = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsEmpleado as empleado left join fetch empleado.smsProveedor as proveedor left join fetch empleado.smsUsuario as usuario left join fetch usuario.smsNacionalidad as nacionalidad left join fetch usuario.smsCiudad left join fetch usuario.smsRol left join fetch empleado.smsHojavida as hojaVida where proveedor.proveedorRazonSocial = '" + Proveedor + "'");
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

}
