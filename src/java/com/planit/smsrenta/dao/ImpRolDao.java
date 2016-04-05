/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsRol;
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
public class ImpRolDao implements IRolDao{
    
    private FacesMessage message;

    @Override
    public List<SmsRol> mostrarRoles() {
       
        Session session = null;
        List<SmsRol> roles = new ArrayList<>();
        
        try{
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsRol as rol left join fetch rol.smsPermisoses as permisos group by rol.idRol");
            roles = (List<SmsRol>) query.list();
            
        }catch(HibernateException e){
            e.getMessage();
        }finally{
            if(session != null){
                session.close();
            }
        }return roles;
    }
        

    @Override
    public void registrarRol(SmsRol Rol) {
        Session session = null;
        try{
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(Rol);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol registrado", "" + Rol.getRolNombre() );
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
    public void modificarRol(SmsRol Rol) {
       Session session = null;
        try{
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(Rol);
            session.getTransaction().commit();
             message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol modificado", "" + Rol.getRolNombre() );
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
    public void eliminarRol(SmsRol Rol) {
        Session session = null;
        try{
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(Rol);
            session.getTransaction().commit();
             message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol eliminado", "" + Rol.getRolNombre() );
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
    public List<SmsRol> consultarRol(SmsRol rol) {
        Session session = null;
        List<SmsRol> roles = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsRol as rol left join fetch rol.smsPermisoses as permisos where rol.rolNombre = '" + rol.getRolNombre() + "'");
            roles = (List<SmsRol>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return roles;
    }

    @Override
    public List<SmsRol> filtrarRol(String valor) {
        Session session = null;
        List<SmsRol> roles = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsRol as rol left join fetch rol.smsPermisoses as permisos where rol.rolNombre Like '%" + valor + "%'  group by rol.idRol");
            roles = (List<SmsRol>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return roles;
    }
        
}
