/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsMercado;
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
public class ImpMercadoDao implements IMercadoDao {

    private FacesMessage message;

    @Override
    public List<SmsMercado> consultarMercados() {
        Session session = null;
        List<SmsMercado> mercados = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsMercado as mercado");
            mercados = (List<SmsMercado>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;
    }

    @Override
    public void registrarMercado(SmsMercado mercado) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(mercado);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mercado registrado", "" + mercado.getMercadoNombre());
        } catch (Exception e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Imposible realizar operacion", "");
        
        } finally {
            if(session != null){
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    
    }

    @Override
    public void modificarMercado(SmsMercado mercado) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(mercado);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mercado actualizado", "" + mercado.getMercadoNombre());
        } catch (Exception e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Imposible realizar operacion", "");
        
        } finally {
            if(session != null){
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    
    }

    @Override
    public void eliminarMercado(SmsMercado mercado) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(mercado);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminar registrado", "" + mercado.getMercadoNombre());
        } catch (Exception e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Imposible realizar operacion", "");
        
        } finally {
            if(session != null){
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    
    }

    @Override
    public List<SmsMercado> filtrarMercados(String valor) {
        Session session = null;
        List<SmsMercado> mercados = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsMercado as mercado where mercado.mercadoNombre LIKE '%"+ valor +"%' or mercado.mercadoDescripcion LIKE '%"+ valor +"%'");
            mercados = (List<SmsMercado>) query.list();

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return mercados;}

}
