/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsUsuario;
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
public class ImpUsuarioDao implements IUsuarioDao {

    private FacesMessage message;

    @Override
    public List<SmsUsuario> mostrarUsuario() {
        Session session = null;
        List<SmsUsuario> usuarios = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsNacionalidad as nacionalidad left join fetch usuario.smsRol as rol left join fetch usuario.smsCiudad as ciudad");
            usuarios = (List<SmsUsuario>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuarios;

    }

    @Override
    public void registrarUsuario(SmsUsuario usuario) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(usuario);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario registrado", "" + usuario.getUsuarioNombre());
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
    public void modificarUsuario(SmsUsuario usuario) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(usuario);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario modificado", "" + usuario.getUsuarioNombre());
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
    public void eliminarUsuario(SmsUsuario usuario) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(usuario);
            session.getTransaction().commit();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario eliminado", "" + usuario.getUsuarioNombre());
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Imposible realizar la operacion", null);
        } finally {
            if (session != null) {
                session.close();
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    @Override
    public List<SmsUsuario> consultarDatosSesionUsuario(SmsUsuario user) {
        Session session = null;
        List<SmsUsuario> usuario = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario where usuario.usuarioEmail = '" + user.getUsuarioEmail() + "'");
            usuario = (List<SmsUsuario>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuario;
    }

    @Override
    public List<SmsUsuario> consultarUsuario(SmsUsuario usuario) {
        Session session = null;
        List<SmsUsuario> usuarios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsRol as rol left join fetch usuario.smsCiudad as ciudad left join fetch usuario.smsNacionalidad where usuario.idUsuario = '" + usuario.getIdUsuario() + "' or usuario.usuarioNombre = '" + usuario.getUsuarioNombre() + "' or usuario.usuarioEmail = '" + usuario.getUsuarioEmail() + "'");
            usuarios = (List<SmsUsuario>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuarios;
    }

    @Override
    public List<SmsUsuario> verificarLoginDisponible(String valor) {
        Session session = null;
        List<SmsUsuario> usuarios = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario where usuario.usuarioLogin = '" + valor + "'");
            usuarios = (List<SmsUsuario>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuarios;
    }

    @Override
    public List<SmsUsuario> verificarEmailDisponible(String valor) {
        Session session = null;
        List<SmsUsuario> usuarios = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario where usuario.usuarioEmail = '" + valor + "'");
            usuarios = (List<SmsUsuario>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuarios;
    }

    @Override
    public void modificarSesionUsuario(SmsUsuario usuario) {
        Session session = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(usuario);
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
