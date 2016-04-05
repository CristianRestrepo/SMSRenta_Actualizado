/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsMarca;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IMarcaDao {
    
    public List<SmsMarca> mostrarMarcas();
    public void registrarMarca(SmsMarca marca);
    public void modificarMarca(SmsMarca marca);
    public void eliminarMarca(SmsMarca marca);
    public List<SmsMarca> consultarMarca(SmsMarca marca);
    public List<SmsMarca> filtrarMarca(String dato);
    
    
}
