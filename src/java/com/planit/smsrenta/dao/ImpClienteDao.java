/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsUsuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;



/**
 *
 * @author Desarrollo_Planit
 */
public class ImpClienteDao implements IClienteDao{
    
    SessionFactory sessions = NewHibernateUtil.getSessionFactory();
    
     @Override
    public List<SmsUsuario> consultarUsuariosClientes() {
        Session session = null;
        List<SmsUsuario> usuarios = new ArrayList<>();

        try {
            session = sessions.openSession();
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsNacionalidad as nacionalidad"
                    + " left join fetch usuario.smsRol as rol"
                    + " left join fetch usuario.smsCiudad as ciudad where rol.rolNombre = 'Cliente'");
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
            session = sessions.openSession();                   
            Query query = session.createQuery("from SmsUsuario as usuario left join fetch usuario.smsNacionalidad as nacionalidad left join fetch usuario.smsRol as rol left join fetch usuario.smsCiudad as ciudad where "
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
