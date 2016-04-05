/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsEstado;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IEstadoDao {
    
    public List<SmsEstado> consultarEstados();
    public List<SmsEstado> consultarEstado(SmsEstado estado);
    
}
