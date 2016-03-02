/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCategoria;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpCategoriaDao implements ICategoriaDao {

    private FacesMessage message;

    @Override
    public List<SmsCategoria> mostrarCategorias() {
        Session session = null;
        List<SmsCategoria> categorias = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsCategoria");
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
            session = NewHibernateUtil.getSessionFactory().openSession();
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
            session = NewHibernateUtil.getSessionFactory().openSession();
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
            session = NewHibernateUtil.getSessionFactory().openSession();
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
    public List<SmsCategoria> consultarCategoria(SmsCategoria categoria) {
        Session session = null;
        List<SmsCategoria> categorias = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsCategoria as categoria left join fetch categoria.smsMercados as mercados where categoria.categoriaNombre='" + categoria.getCategoriaNombre() + "'");
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
    public List<SmsCategoria> filtrarCategorias(String dato) {
        Session session = null;
        List<SmsCategoria> categorias = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
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
            session = NewHibernateUtil.getSessionFactory().openSession();
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

 
}