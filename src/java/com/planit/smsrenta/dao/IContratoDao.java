/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.dao;

import com.planit.smsrenta.modelos.SmsContrato;
import com.planit.smsrenta.modelos.SmsReservacion;
import java.util.List;


public interface IContratoDao {    
    public List<SmsContrato> consultarContratos();
    public List<SmsContrato> consultarContratoSegunReservacion(SmsReservacion reservacion);
    public void registrarContrato(SmsContrato contrato);    
}
