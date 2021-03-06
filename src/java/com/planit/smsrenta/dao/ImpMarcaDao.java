/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsMarca;
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
public class ImpMarcaDao implements IMarcaDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsMarca> mostrarMarcas() {
        Session session = null;
        List<SmsMarca> marcas = null;

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMarca");
            marcas = (List<SmsMarca>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return marcas;
    }

    @Override
    public void registrarMarca(SmsMarca marca) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(marca);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Marca registrada", "" + marca.getMarcaNombre());
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
    public void modificarMarca(SmsMarca marca) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(marca);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Marca modificada", "" + marca.getMarcaNombre());
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
    public void eliminarMarca(SmsMarca marca) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(marca);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Marca eliminada", "" + marca.getMarcaNombre());
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
    public SmsMarca consultarMarca(SmsMarca marca) {
        Session session = null;
        SmsMarca marcas = new SmsMarca();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMarca as marca "
                    + "where marca.marcaNombre = '" + marca.getMarcaNombre() + "'");
            marcas = (SmsMarca) query.list().get(0);

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return marcas;
    }

    @Override
    public List<SmsMarca> filtrarMarca(String dato) {
        Session session = null;
        List<SmsMarca> marcas = null;

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsMarca as marca where marca.marcaNombre LIKE '%" + dato + "%'");
            marcas = (List<SmsMarca>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return marcas;
    }
}
