/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsPermisos;
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
public class ImpPermisosDao implements IPermisosDao {
    
     private FacesMessage message;
    
    @Override
    public List<SmsPermisos> mostrarPermisos() {
        Session session = null;
        List<SmsPermisos> permisos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsPermisos");
            permisos = (List<SmsPermisos>) query.list();           
        } catch (HibernateException e) {
            e.getMessage();           
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return permisos;

    }

    @Override
    public void registrarPermiso(SmsPermisos permiso) {
        Session session = null;
        try{
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(permiso);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Permiso registrado", ""+ permiso.getPermisosNombre());
        }catch(HibernateException e){
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        }finally{
            if(session != null){
                session.close();
            }        
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void modificarPermiso(SmsPermisos permiso) {
        Session session = null;
        try{
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(permiso);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Permiso modificado", ""+ permiso.getPermisosNombre());
        }catch(HibernateException e){
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        }finally{
            if(session != null){
                session.close();
            }
        } 
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public void eliminarPermiso(SmsPermisos permiso) {
        Session session = null;
        try{
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(permiso);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Permiso eliminado", ""+ permiso.getPermisosNombre());
        }catch(HibernateException e){
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        }finally{
            if(session != null){
                session.close();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    @Override
    public List<SmsPermisos> consultarPermiso(String permiso) {
        Session session = null;
        List<SmsPermisos> permisos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsPermisos as permiso where permiso.permisosNombre = '" + permiso + "'");
            permisos = (List<SmsPermisos>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return permisos;

    }

    @Override
    public List<SmsPermisos> filtrarPermiso(String valor) {
        Session session = null;
        List<SmsPermisos> permisos = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsPermisos as permiso where permiso.permisosNombre LIKE '%" + valor + "%' or permiso.permisosDescripcion LIKE '%" + valor + "%'");
            permisos = (List<SmsPermisos>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return permisos;
    }

}
