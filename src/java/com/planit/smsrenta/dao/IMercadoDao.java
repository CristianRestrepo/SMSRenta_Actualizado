/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsMercado;
import com.planit.smsrenta.modelos.SmsProveedor;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IMercadoDao {
    
    
    public List<SmsMercado> consultarMercados();
    public SmsMercado consultarMercado(SmsMercado mercado);
    public SmsMercado consultarMercadoConCategorias(SmsMercado mercado);
    public SmsMercado consultarMercadoConServicios(SmsMercado mercado);
    public SmsMercado consultarMercadoConProveedores(SmsMercado mercado);
    public List<SmsMercado> consultarMercadoSegunCategoria(SmsCategoria categoria);
    public List<SmsMercado> consultarMercadosSegunProveedor(SmsProveedor proveedor); 
    public void registrarMercado(SmsMercado mercado);
    public void modificarMercado(SmsMercado mercado);
    public void eliminarMercado(SmsMercado mercado);
    public List<SmsMercado> filtrarMercados(String valor);
    
    
    
    
}
