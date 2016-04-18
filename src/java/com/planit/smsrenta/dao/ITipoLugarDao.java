/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsTipoLugar;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ITipoLugarDao {
    
    public List<SmsTipoLugar> consultarTiposLugares();
    
    public SmsTipoLugar consultarTipoLugar(SmsTipoLugar tipoLugar);
    
    public List<SmsTipoLugar> filtrarTipoLugar(String valor);
   
 }
