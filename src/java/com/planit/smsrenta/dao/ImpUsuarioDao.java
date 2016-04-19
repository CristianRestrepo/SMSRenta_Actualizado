/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import static com.planit.smsrenta.dao.NewHibernateUtil.getSessionFactory;
import com.planit.smsrenta.modelos.SmsUsuario;
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
public class ImpUsuarioDao implements IUsuarioDao {

    private FacesMessage message;
    SessionFactory sessions = getSessionFactory();

    @Override
    public List<SmsUsuario> mostrarUsuario() {
        Session session = null;
        List<SmsUsuario> usuarios = null;
        try {
            session = sessions.openSession();
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
            session = sessions.openSession();
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
            session = sessions.openSession();
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
            session = sessions.openSession();
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
    public SmsUsuario consultarDatosSesionUsuario(SmsUsuario user) {
        Session session = null;
        SmsUsuario usuario = new SmsUsuario();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsUsuario as usuario where"
                    + " usuario.usuarioEmail = '" + user.getUsuarioEmail() + "'");
            usuario = (SmsUsuario) query.list().get(0);
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
    public SmsUsuario consultarUsuario(SmsUsuario usuario) {
        Session session = null;
        SmsUsuario usuarios = new SmsUsuario();
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsUsuario as usuario"
                    + " left join fetch usuario.smsRol as rol"
                    + " left join fetch usuario.smsCiudad as ciudad"
                    + " left join fetch usuario.smsNacionalidad where"
                    + " usuario.idUsuario = '" + usuario.getIdUsuario() + "' or"
                    + " usuario.usuarioNombre = '" + usuario.getUsuarioNombre() + "' or"
                    + " usuario.usuarioEmail = '" + usuario.getUsuarioEmail() + "'");
            usuarios = (SmsUsuario) query.list().get(0);
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
            session = sessions.openSession();
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
            session = sessions.openSession();
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
            session = sessions.openSession();
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

    @Override
    public boolean consultarExistenciaUsuario(SmsUsuario usuario) {
        Session session = null;
        boolean existe = false;
        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsUsuario as usuario"
                    + " left join fetch usuario.smsRol as rol"
                    + " left join fetch usuario.smsCiudad as ciudad"
                    + " left join fetch usuario.smsNacionalidad where"
                    + " usuario.usuarioEmail = '" + usuario.getUsuarioEmail() + "'");
            if (!query.list().isEmpty()) {
                existe = true;
            }

        } catch (HibernateException e) {
            e.getMessage();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return existe;
    }
}
