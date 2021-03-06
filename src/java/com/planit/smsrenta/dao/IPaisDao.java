/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsPais;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IPaisDao {
    
    //Metodos
    public List<SmsPais> mostrarPaises();
    public void registrarPais(SmsPais pais);
    public void modificarPais(SmsPais pais);
    public void eliminarPais(SmsPais pais);
    public SmsPais consultarPais(SmsPais pais);
    public List<SmsPais> filtrarPais(String valor);
}
