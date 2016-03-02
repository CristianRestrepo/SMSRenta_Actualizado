/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCiudad;
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
public class ImpCiudadDao implements ICiudadDao {

    private FacesMessage message;

    @Override
    public List<SmsCiudad> mostrarCiudades() {
        Session session = null;
        List<SmsCiudad> ciudades = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsCiudad as ciudad left join fetch ciudad.smsDepartamento left join fetch ciudad.smsTipoLugar");
            ciudades = (List<SmsCiudad>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ciudades;
    }

    @Override
    public void registrarCiudad(SmsCiudad ciudad) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(ciudad);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ciudad registrado", "" + ciudad.getCiudadNombre());
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
    public void modificarCiudad(SmsCiudad ciudad) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(ciudad);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ciudad modificada", "" + ciudad.getCiudadNombre());
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
    public void eliminarCiudad(SmsCiudad ciudad) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(ciudad);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ciudad eliminada", "" + ciudad.getCiudadNombre());
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
    public List<SmsCiudad> consultarCiudad(SmsCiudad ciudad) {
        Session session = null;
        List<SmsCiudad> ciudades = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsCiudad as ciudad left join fetch ciudad.smsDepartamento left join fetch ciudad.smsTipoLugar where ciudad.ciudadNombre='" + ciudad.getCiudadNombre() + "'");
            ciudades = (List<SmsCiudad>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ciudades;
    }

    @Override
    public List<SmsCiudad> filtrarCiudad(String dato) {
        Session session = null;
        List<SmsCiudad> ciudades = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsCiudad as ciudad left join fetch ciudad.smsDepartamento left join fetch ciudad.smsTipoLugar where ciudad.ciudadNombre LIKE '%" + dato + "%' or ciudad.smsDepartamento.departamentoNombre LIKE '%" + dato + "%'");
            ciudades = (List<SmsCiudad>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ciudades;
    }

}
