/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.SmsColor;
import java.util.List;

/**
 *
 * @author Desarrollo_Planit
 */
public interface IColorDao {
    
    public List<SmsColor> consultarColores();
    public List<SmsColor> consultarColor(SmsColor color);
}
