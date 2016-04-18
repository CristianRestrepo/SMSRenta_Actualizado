/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsLugares;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface ILugarDao {
    
    public List<SmsLugares> consultarLugares();
    public void registrarLugar(SmsLugares lugar);
    public void modificarLugar(SmsLugares lugar);
    public void eliminarLugar(SmsLugares lugar);
    public List<SmsLugares> filtrarLugar(String dato);
    public List<SmsLugares> consultarLugarCiudad(String dato);
    public SmsLugares consultarLugar(SmsLugares lugar);
}
