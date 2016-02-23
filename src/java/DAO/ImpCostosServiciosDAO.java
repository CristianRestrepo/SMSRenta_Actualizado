/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCategoria;
import Modelo.SmsCostosServicio;
import Modelo.SmsServicios;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author CristianCamilo
 */
public class ImpCostosServiciosDAO implements ICostosServiciosDAO {

    private FacesMessage message;

    @Override
    public List<SmsCostosServicio> mostrarCostosServicios() {
        Session session = null;
        List<SmsCostosServicio> Costos = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsCostosServicio as costo left join fetch costo.smsCategoria left join fetch costo.smsServicios as servicios");
            Costos = (List<SmsCostosServicio>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Costos;
    }

    @Override
    public List<SmsCostosServicio> consultarCostoServicio(SmsServicios servicio, SmsCategoria categoria) {
        Session session = null;
        List<SmsCostosServicio> Costos = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsCostosServicio as costo left join fetch costo.smsCategoria as categoria left join fetch costo.smsServicios as servicios where categoria = '" + categoria.getIdCategoria() + "' and servicios = '" + servicio.getIdServicios()+ "' ");
            Costos = (List<SmsCostosServicio>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Costos;
    }

    @Override
    public void registrarCostoServicio(SmsCostosServicio costo) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(costo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Costo registrado", "");
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
    public void modificarCostoServicio(SmsCostosServicio costo) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(costo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Costo modificado", "");
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
    public void eliminarCostoServicio(SmsCostosServicio costo) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(costo);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Costo eliminado", "");
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
    public List<SmsCostosServicio> filtrarCostosServicios(String dato) {
        Session session = null;
        List<SmsCostosServicio> Costos = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsCostosServicio as costo left join fetch costo.smsCategoria as categoria left join fetch costo.smsServicios as servicios where categoria.categoriaNombre LIKE '%" + dato + "%' or servicios.serviciosNombre LIKE '%" + dato + "%'");
            Costos = (List<SmsCostosServicio>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Costos;
    }

}
