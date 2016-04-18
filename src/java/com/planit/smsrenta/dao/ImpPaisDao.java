/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsPais;
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
public class ImpPaisDao implements IPaisDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsPais> mostrarPaises() {
        Session session = null;
        List<SmsPais> paises = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsPais");
            paises = (List<SmsPais>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return paises;
    }

    @Override
    public void registrarPais(SmsPais pais) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(pais);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pais registrado", "" + pais.getPaisNombre());
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
    public void modificarPais(SmsPais pais) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(pais);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pais modificado", "" + pais.getPaisNombre());
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
    public void eliminarPais(SmsPais pais) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(pais);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pais eliminado", "" + pais.getPaisNombre());
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
    public SmsPais consultarPais(SmsPais pais) {
        Session session = null;
        SmsPais paises = new SmsPais();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsPais as pais where "
                    + "pais.paisNombre='" + pais.getPaisNombre() + "'");
            paises = (SmsPais) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return paises;
    }

    @Override
    public List<SmsPais> filtrarPais(String valor) {
        Session session = null;
        List<SmsPais> paises = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsPais as pais where pais.paisNombre LIKE '%" + valor + "%'");
            paises = (List<SmsPais>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return paises;
    }

}
