/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsEstadovehiculo;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpEstadoVehiculoDao implements IEstadoVehiculoDao {

    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsEstadovehiculo> mostrarEstadoVehiculo() {
        Session session = null;
        List<SmsEstadovehiculo> EstadoVehiculos = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEstadoVehiculo as estado left join fetch estado.smsVehiculo");
            EstadoVehiculos = (List<SmsEstadovehiculo>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return EstadoVehiculos;
    }

    @Override
    public void registrarEstadoVehiculo(SmsEstadovehiculo estado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(estado);
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
    public void modificarEstadoVehiculo(SmsEstadovehiculo estado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(estado);
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
    public void eliminarEstadoVehiculo(SmsEstadovehiculo estado) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(estado);
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
    public SmsEstadovehiculo consultarEstadoVehiculo(SmsVehiculo vehiculo) {
        Session session = null;
        SmsEstadovehiculo EstadoVehiculos = new SmsEstadovehiculo();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsEstadovehiculo as estado "
                    + "left join fetch estado.smsVehiculo as vehiculo where vehiculo.idVehiculo = '" + vehiculo.getIdVehiculo() + "' and "
                    + "estado.fechaEstadoVehiculo = (select MAX(est.fechaEstadoVehiculo) from SmsEstadovehiculo as est where est.smsVehiculo.idVehiculo = '" + vehiculo.getIdVehiculo() + "')");
            EstadoVehiculos = (SmsEstadovehiculo) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return EstadoVehiculos;
    }
}
