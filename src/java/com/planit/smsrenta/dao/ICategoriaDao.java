 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsCategoria;
import com.planit.smsrenta.modelos.SmsMercado;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ICategoriaDao {
    
    public List<SmsCategoria> mostrarCategorias();
    public List<SmsCategoria> consultarCategoria(SmsCategoria categoria);   
    public List<SmsCategoria> filtrarCategorias(String dato);
    public List<SmsCategoria> consultarCategoriasSegunMercado(SmsMercado mercado);
    public void registrarCategoria(SmsCategoria categoria);
    public void modificarCategoria(SmsCategoria categoria);
    public void agregarMercadosCategoria(SmsCategoria categoria);
    public void eliminarCategoria(SmsCategoria categoria);    
    
}
