/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsLugares;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpLugarDao implements ILugarDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();

    @Override
    public List<SmsLugares> consultarLugares() {
        Session session = null;
        List<SmsLugares> lugares = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsLugares as lugar "
                    + "left join fetch lugar.smsCiudad as ciudad "
                    + "left join fetch lugar.smsLocalidad");
            lugares = (List<SmsLugares>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lugares;
    }

    @Override
    public void registrarLugar(SmsLugares lugar) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(lugar);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lugar registrado", "");
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void modificarLugar(SmsLugares lugar) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(lugar);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lugar modificado", "");
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void eliminarLugar(SmsLugares lugar) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(lugar);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Lugar eliminado", "");
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public List<SmsLugares> filtrarLugar(String dato) {
        Session session = null;
        List<SmsLugares> lugares = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsLugares as lugar "
                    + "left join fetch lugar.smsCiudad as ciudad "
                    + "left join fetch lugar.smsLocalidad "
                    + "where lugar.lugarNombre LIKE '%" + dato + "%' or  lugar.lugarDireccion LIKE '%" + dato + "%' or ciudad.ciudadNombre LIKE '%" + dato + "%'");
            lugares = (List<SmsLugares>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lugares;
    }

    @Override
    public List<SmsLugares> consultarLugarCiudad(String dato) {
        Session session = null;
        List<SmsLugares> lugares = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsLugares as lugar left join fetch lugar.smsCiudad as ciudad where ciudad.ciudadNombre = '" + dato + "'");
            lugares = (List<SmsLugares>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lugares;
    }

     @Override
    public SmsLugares consultarLugar(SmsLugares lugar) {
        Session session = null;
        SmsLugares lugares = new SmsLugares();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsLugares as lugar where lugar.lugarNombre = '" + lugar.getLugarNombre()+ "'");
            lugares = (SmsLugares) query.list().get(0);

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lugares;
    }
}
