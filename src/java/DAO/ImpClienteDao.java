/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpClienteDao implements IClienteDao{
    
     @Override
    public List<SmsUsuario> consultarUsuariosClientes() {
        Session session = null;
        List<SmsUsuario> usuarios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsRol as rol left join fetch usuario.smsCiudad as ciudad where rol.rolNombre = 'Cliente'");
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
    public List<SmsUsuario> filtrarUsuariosClientes(String valor) {
        Session session = null;
        List<SmsUsuario> usuarios = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsRol as rol left join fetch usuario.smsCiudad as ciudad where "
                    + "(usuario.usuarioNombre LIKE '%" + valor + "%' or usuario.usuarioCc LIKE '%" + valor + "%' or usuario.usuarioEmail LIKE '%" + valor + "%' or usuario.usuarioTelefono LIKE '%" + valor + "%' or "
                    + "ciudad.ciudadNombre LIKE '%" + valor + "%') and rol.rolNombre = 'Cliente'");
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
    
}
