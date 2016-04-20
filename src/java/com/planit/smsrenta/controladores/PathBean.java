/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.controladores;

import com.planit.smsrenta.metodos.Sesion;
import com.planit.smsrenta.modelos.SmsUsuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;

/**
 *
 * @author Desarrollo_Planit
 */
public class PathBean implements Serializable {

    private SmsUsuario sesion;
    private final Sesion sesionController;

    public PathBean() {
        sesionController = new Sesion();
    }

    @PostConstruct
    public void init() {
        sesion = sesionController.obtenerSesion();
    }
    /* / ADMIN PRINCIPAL / */

    public SmsUsuario getSesion() {
        return sesion;
    }

    public void setSesion(SmsUsuario sesion) {
        this.sesion = sesion;
    }

    public String ir_PUsuario() {
        return "AdminPUsuario";
    }

    public String ir_AdminEditarPerfil() {
        return "AdminEditarPerfil";
    }

    public String ir_PMercado() {
        return "AdminPMercado";
    }

    public String ir_PCategoriasServicios() {
        return "AdminPCategoriasServicios";
    }

    public String ir_PCategoria() {
        return "AdminPCategoria";
    }

    public String ir_PCliente() {
        return "AdminPCliente";
    }

    public String ir_PCiudad() {
        return "AdminPCiudad";
    }

    public String ir_PReservaPrueba() {
        return "ReservaPrueba";
    }

    public String ir_PEmpleado() {
        return "AdminPEmpleado";
    }

    public String ir_PEstadisticas() {
        return "AdminPEstadisticas";
    }

    public String ir_PPrincipal() {
        return "AdminPPrincipal";
    }

    public String ir_PPais() {
        return "AdminPPais";
    }
    
    public String ir_PDepartamentos() {
        return "AdminPDepartamentos";
    }

    public String ir_PMarca() {
        return "AdminPMarca";
    }

    public String ir_PProveedor() {
        return "AdminPProveedores";
    }

    public String ir_PReferencias() {
        return "AdminPReferencias";
    }

    public String ir_PReserva() {
        return "AdminPReserva";
    }

    public String ir_PRol() {
        return "AdminPRol";
    }

    public String ir_PServicios() {
        return "AdminPServicios";
    }

    public String ir_PVehiculos() {
        return "AdminPVehiculos";
    }

    public String ir_PEdicionPerfil() {
        return "AdminPEdicionPerfil";
    }

    public String ir_PCostosServicios() {
        return "AdminPCostosServicios";
    }

    public String ir_PLugares() {
        return "AdminPLugares";
    }

    public String ir_AdminPVistaReserva() {
        return "VistaReservaAdminP";
    }

    public String ir_AdminReporte() {
        return "AdminPReporte";
    }

    public String ir_AdminPTipoReporte() {
        return "AdminPTipoServicio";
    }

    public String ir_AdminPReservaTurismo() {
        return "AdminPReservaTurismo";
    }

    public String ir_AdminPReserva() {
        return "AdminPTipoServicio";
    }

    public String ir_AdminPCondicionUso() {
        return "AdminPCondicionesUso";
    }

    /* / ADMIN PRINCIPAL / */
    /* / ADMIN SECUNDARIO / */
    public String ir_SCategoria() {
        return "AdminSCategoria";
    }

    public String ir_SCiudad() {
        return "AdminSCiudad";
    }

    public String ir_SClientes() {
        return "AdminSClientes";
    }

    public String ir_SEmpleados() {
        return "AdminSEmpleados";
    }

    public String ir_SGeneral() {
        return "AdminSGeneral";
    }

    public String ir_SMarcas() {
        return "AdminSMarcas";
    }
    
    public String ir_SAdminVehiculos() {
        return "SAdminVehiculos";
    }

    public String ir_SPais() {
        return "AdminSPais";
    }

    public String ir_SLugares() {
        return "AdminSLugares";
    }

    public String ir_SDepartamentos() {
    return "AdminSDepartamentos";
    }
    
    public String ir_SReferencias() {
        return "AdminSReferencias";
    }

    public String ir_SVehiculos() {
        return "AdminSVehiculos";
    }

    public String ir_SReservacion() {
        return "AdminSTipoReserva";
    }

    public String ir_SCostoServicio() {
        return "AdminSCostosServicio";
    }

    public String ir_SProveedor() {
        return "AdminSProveedor";
    }

    public String ir_SEdicionPerfil() {
        return "AdminSEdicionPerfil";
    }

    public String ir_SServicios() {
        return "AdminSServicios";
    }

    public String ir_SRoll() {
        return "AdminSRoll";
    }

    public String ir_SSAdministradores() {
        return "AdminSAdministradores";
    }

    public String ir_AdminSVistaReserva() {
        return "VistaReservaAdminS";
    }

    public String ir_AdminSMercado() {
        return "AdminSMercado";
    }

    public String ir_SCategoServicio() {
        return "AdminSCategoriaServicio";
    }

    /* / ADMIN SECUNDARIO / */
    /* / CONDUCTOR / */
    public String ir_ConductorDash() {
        return "ConductorDash";
    }

    public String ir_ConductorCliente() {
        return "ConductorCliente";
    }

    public String ir_ConductorEdicionPerfil() {
        return "CondEdicionPerfil";
    }

    public String ir_ConductorVistaReserva() {
        return "VistaReservaConductor";
    }

    /* / CONDUCTOR/ */
    /* / PRoveedor/ */
    public String ir_ProDashboard() {
        return "ProveedorDash";
    }

    public String ir_ProEstVehi() {
        return "ProveedorEstadoVehiculo";
    }

    public String ir_ProEdicionPerfil() {
        return "ProveedorEdicionPerfil";
    }
    /* / PROVEEDOR/ */
    /* / CLIENTE/ */

    public String ir_ClienteReservacion() {
        return "ClienteReservacion";
    }

    public String ir_ClienteReservaciones() {
        return "ClienteReservaciones";
    }

    public String ir_ClienteDash() {
        return "ClienteDash";
    }

    public String ir_ClienteEdicionPerfil() {
        return "ClienteEdicionPerfil";
    }

    public String ir_ClienteVistaReserva() {
        return "VistaReservaCliente";
    }
    /* / CLIENTE/ */

    public String ir_Registrarse() {
        return "Register";
    }
    
    public String ir_olvideContrasena(){
        return "OlvideContrasena";
    }

    public String ir_Login() {
        return "Login";
    }

    public String regresarTableroPrincipal() {
        String ruta = "";
        switch (sesion.getSmsRol().getRolNombre()) {
            case "Administrador Principal":
                ruta = "AdminPPrincipal";
                break;
            case "Administrador Secundario":
                ruta = "AdminSGeneral";
                break;
            case "Cliente":
                ruta = "ClienteDash";
                break;
            case "Conductor":
                ruta = "ConductorDash";
                break;
            case "Proveedor":
                ruta = "ProveedorDash";
                break;
        }
        return ruta;
    }

    public String irRegistroCliente() {
        String ruta = "";
        switch (sesion.getSmsRol().getRolNombre()) {
            case "Administrador Principal":
                ruta = "AdminPCliente";
                break;
            case "Administrador Secundario":
                ruta = "AdminSClientes";
                break;
        }
        return ruta;
    }

}
