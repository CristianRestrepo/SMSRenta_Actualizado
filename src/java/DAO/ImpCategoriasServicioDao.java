/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsCategoriasServicio;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Desarrollo_Planit
 */
public class ImpCategoriasServicioDao implements ICategoriasServicioDao{

    @Override
    public List<SmsCategoriasServicio> consultarCategoriasServicios() {
        Session session = null;
        List<SmsCategoriasServicio> categorias = new ArrayList<>();
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query =session.createQuery("from SmsCategoriasServicio");
            categorias = (List<SmsCategoriasServicio>) query.list();
        } catch (HibernateException e) {
            e.getMessage();
        }finally{
            if(session != null)
                session.close();
        }
        return categorias;
    }
    
}
