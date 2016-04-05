/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCategoriasServicio;
import com.planit.smsrenta.modelos.SmsMercado;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ICategoriasServicioDao {

    public List<SmsCategoriasServicio> consultarCategoriasServicios();

    public List<SmsCategoriasServicio> consultarCategoriaServicio(SmsCategoriasServicio catServicio);
    
    public List<SmsCategoriasServicio> consultarCategoriasServiciosSegunMercado(SmsMercado mercado);
    
    public void registrarCategoriaServicio(SmsCategoriasServicio categoria);
    
    public void modificarCategoriaServicio(SmsCategoriasServicio categoria);
    
    public void eliminarCategoriaServicio(SmsCategoriasServicio categoria);
    
}
