/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.validadores;

import com.planit.smsrenta.dao.IVehiculoDao;
import com.planit.smsrenta.dao.ImpVehiculoDao;
import com.planit.smsrenta.modelos.SmsVehiculo;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Desarrollo_Planit
 */
@FacesValidator("placaExistenteValidator")
public class placaExistenteValidator implements Validator {

    String placa;
    int operacion;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        placa = (String) value;
        operacion = (int) component.getAttributes().get("operacion");
        IVehiculoDao vehDao = new ImpVehiculoDao();

        if (operacion == 0) {
            if (vehDao.verificarExistenciaPlaca(placa)) {
                FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Placa ya registrada", "El vehiculo con esta placa ya se encuentra registrado");
                throw new ValidatorException(fmsg);
            }

        } else if (operacion == 1) {
            SmsVehiculo vehiculo = (SmsVehiculo) component.getAttributes().get("vehiculo");
            SmsVehiculo nuevoVehiculo = new SmsVehiculo();

            if (!vehiculo.getVehPlaca().equalsIgnoreCase(placa)) {
                SmsVehiculo veh = new SmsVehiculo();
                veh.setVehPlaca(placa);
                nuevoVehiculo = vehDao.consultarVehiculo(veh);
                if (nuevoVehiculo.getIdVehiculo() != null) {
                    FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Placa ya registrada", "El vehiculo con esta placa ya se encuentra registrado");
                    throw new ValidatorException(fmsg);
                }
            }
        }
    }

}
