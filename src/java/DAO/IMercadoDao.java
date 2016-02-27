/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsMercado;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IMercadoDao {
    
    
    public List<SmsMercado> consultarMercados();
    public void registrarMercado(SmsMercado mercado);
    public void modificarMercado(SmsMercado mercado);
    public void eliminarMercado(SmsMercado mercado);
    public List<SmsMercado> filtrarMercados(String valor);
    
    
    
    
}
