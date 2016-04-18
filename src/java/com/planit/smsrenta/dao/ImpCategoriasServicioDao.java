/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCategoriasServicio;
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
public class ImpCategoriasServicioDao implements ICategoriasServicioDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();

    @Override
    public List<SmsCategoriasServicio> consultarCategoriasServicios() {
        Session session = null;
        List<SmsCategoriasServicio> categorias = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCategoriasServicio as catServicio");
            categorias = (List<SmsCategoriasServicio>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return categorias;
    }

    @Override
    public SmsCategoriasServicio consultarCategoriaServicio(SmsCategoriasServicio catServicio) {
        Session session = null;
        SmsCategoriasServicio categorias = new SmsCategoriasServicio();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCategoriasServicio as catServicio"
                    + " where catServicio.catNombre = '" + catServicio.getCatNombre() + "'");
            categorias = (SmsCategoriasServicio) query.list().get(0);
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return categorias;
    }
  
    @Override
    public void registrarCategoriaServicio(SmsCategoriasServicio categoria) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(categoria);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria registrada", "" + categoria.getCatNombre());
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
    public void modificarCategoriaServicio(SmsCategoriasServicio categoria) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(categoria);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria modificada", "" + categoria.getCatNombre());
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
    public void eliminarCategoriaServicio(SmsCategoriasServicio categoria) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(categoria);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria eliminada", "" + categoria.getCatNombre());
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

}
