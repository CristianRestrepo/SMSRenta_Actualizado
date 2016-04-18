/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsMercado;
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
public class ImpCategoriaDao implements ICategoriaDao {

    private FacesMessage message;
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
    @Override
    public List<SmsCategoria> mostrarCategorias() {
        Session session = null;
        List<SmsCategoria> categorias = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCategoria as categoria");
            categorias = (List<SmsCategoria>) query.list();
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
    public void registrarCategoria(SmsCategoria categoria) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.save(categoria);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria registrada", "" + categoria.getCategoriaNombre());
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
    public void modificarCategoria(SmsCategoria categoria) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(categoria);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria modificada", "" + categoria.getCategoriaNombre());
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
    public void eliminarCategoria(SmsCategoria categoria) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.delete(categoria);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Categoria eliminada", "" + categoria.getCategoriaNombre());
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
    public SmsCategoria consultarCategoria(SmsCategoria categoria) {
        Session session = null;
        SmsCategoria categorias = new SmsCategoria();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCategoria as categoria "
                    + "left join fetch categoria.smsMercados as mercados "
                    + "where categoria.categoriaNombre='" + categoria.getCategoriaNombre() + "'");
            categorias = (SmsCategoria) query.list().get(0);
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
    public List<SmsCategoria> filtrarCategorias(String dato) {
        Session session = null;
        List<SmsCategoria> categorias = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsCategoria as categoria where categoria.categoriaNombre LIKE '%" + dato + "%' or categoria.categoriaDescripcion LIKE '%" + dato + "%'");
            categorias = (List<SmsCategoria>) query.list();
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
    public void agregarMercadosCategoria(SmsCategoria categoria) {
        Session session = null;
        try {
            session = sessions.openSession();
            session.beginTransaction();
            session.update(categoria);
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
    public List<SmsCategoria> consultarCategoriasSegunMercado(SmsMercado mercado) {
        Session session = null;
        List<SmsCategoria> categorias = new ArrayList<>();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("select categoria from SmsMercado as mercado left outer join mercado.smsCategorias as categoria where mercado.idMercado = '" + mercado.getIdMercado() + "'");
            categorias = (List<SmsCategoria>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return categorias;
    }

}
