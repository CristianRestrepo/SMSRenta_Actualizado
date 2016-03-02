/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsTipoLugar;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ITipoLugarDao {
    
    public List<SmsTipoLugar> consultarTiposLugares();
    
    public List<SmsTipoLugar> consultarTipoLugar(SmsTipoLugar tipoLugar);
    
    public List<SmsTipoLugar> filtrarTipoLugar(String valor);
    
    public void registrarTipoLugar(SmsTipoLugar tipoLugar);
    
    public void modificarTipoLugar(SmsTipoLugar tipoLugar);
    
    public void eliminarTipoLugar(SmsTipoLugar tipoLugar);
}
