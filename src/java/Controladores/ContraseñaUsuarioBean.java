/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.IContraseñaUsuarioDao;
import DAO.ImpContraseñaUsuarioDao;
import Modelo.SmsContraseñaUsuario;
import Modelo.SmsUsuario;

/**
 *
 * @author Desarrollo_Planit
 */
public class ContraseñaUsuarioBean {

    private SmsContraseñaUsuario ContraseñaUsuarioView;
    
    //conexion con el dao
    IContraseñaUsuarioDao contraUsuarioDao;
    
    public ContraseñaUsuarioBean() {
        ContraseñaUsuarioView = new SmsContraseñaUsuario();
        contraUsuarioDao = new ImpContraseñaUsuarioDao();
    }

    public SmsContraseñaUsuario getContraseñaUsuarioView() {
        return ContraseñaUsuarioView;
    }

    public void setContraseñaUsuarioView(SmsContraseñaUsuario ContraseñaUsuarioView) {
        this.ContraseñaUsuarioView = ContraseñaUsuarioView;
    }
    
    //metodos    
    public void registrarContraseña(SmsUsuario usuario, String password){
    ContraseñaUsuarioView.setPassword(password);
    ContraseñaUsuarioView.setSmsUsuario(usuario);
    contraUsuarioDao.registrarContraseñaUsuario(ContraseñaUsuarioView);
    ContraseñaUsuarioView = new SmsContraseñaUsuario();
    }
        
    public void modificarContraseña(SmsContraseñaUsuario contraseñaUsuario, String password){
    ContraseñaUsuarioView = contraseñaUsuario;
    ContraseñaUsuarioView.setPassword(password);  
    contraUsuarioDao.modificarContraseñaUsuario(ContraseñaUsuarioView);
    ContraseñaUsuarioView = new SmsContraseñaUsuario();
    }
    
    
}
