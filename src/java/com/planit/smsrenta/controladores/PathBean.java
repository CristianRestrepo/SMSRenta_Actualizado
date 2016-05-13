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

    //Getter & Setters
    public SmsUsuario getSesion() {
        return sesion;
    }

    public void setSesion(SmsUsuario sesion) {
        this.sesion = sesion;
    }

    //METODOS
     /* / ADMIN PRINCIPAL / */
    public String ir_PPrincipal() {
        return "AdminPDashboard";
    }

    public String ir_PPanelAdministracion() {
        return "AdminPPanelAdministracion";
    }

    public String ir_PEdicionPerfil() {
        return "AdminPEditarPerfil";
    }
    
    public String ir_PFacturacion(){
        return "AdminPFacturacion";
    }

    public String ir_PInformacionPersonal() {
        return "AdminPInformacionPersonal";
    }

    public String ir_PInformacionSesion() {
        return "AdminPInformacionSesion";
    }

    public String ir_PVistaReserva() {
        return "AdminPVistaReserva";
    }

    public String ir_PListaReservaciones() {
        return "AdminPIrListaReservaciones";
    }

    public String ir_PAsociarVehiculosConductores() {
        return "AdminPAsociarVehiculosConductores";
    }

    public String ir_PAsociarVehiculosServicios() {
        return "AdminPAsociarVehiculosServicios";
    }

    public String ir_PListaCalificaciones() {
        return "AdminPListaCalificaciones";
    }

    public String ir_PCalificaciones() {
        return "AdminPPanelCalificaciones";
    }

    public String ir_PUsuarios() {
        return "AdminPUsuarios";
    }

    public String ir_PMercados() {
        return "AdminPMercados";
    }

    public String ir_PCategoriasServicios() {
        return "AdminPCategoriasServicios";
    }

    public String ir_PCategorias() {
        return "AdminPCategorias";
    }

    public String ir_PClientes() {
        return "AdminPClientes";
    }

    public String ir_PCiudades() {
        return "AdminPCiudades";
    }

    public String ir_PEmpleados() {
        return "AdminPEmpleados";
    }

    public String ir_PEstadisticas() {
        return "AdminPEstadisticas";
    }

    public String ir_PPaises() {
        return "AdminPPaises";
    }

    public String ir_PDepartamentos() {
        return "AdminPDepartamentos";
    }

    public String ir_PMarcas() {
        return "AdminPMarcas";
    }

    public String ir_PProveedores() {
        return "AdminPProveedores";
    }

    public String ir_PReferencias() {
        return "AdminPReferencias";
    }

    public String ir_PReservaciones() {
        return "AdminPReservaciones";
    }

    public String ir_PRoles() {
        return "AdminPRoles";
    }

    public String ir_PServicios() {
        return "AdminPServicios";
    }

    public String ir_PVehiculos() {
        return "AdminPVehiculos";
    }

    public String ir_PCostosServicios() {
        return "AdminPCostosServicios";
    }

    public String ir_PLugares() {
        return "AdminPLugares";
    }

    public String ir_PTiposServicios() {
        return "AdminPTiposServicios";
    }

    public String ir_PParametrosReservaciones() {
        return "AdminPParametrosReservaciones";
    }

    public String ir_PDocumentos(){
        return "AdminPDocumentos";
    }
    /* / ADMIN SECUNDARIO / */
    public String ir_SDashboard() {
        return "AdminSDashboard";
    }
    
    public String ir_SPanelAdministracion() {
        return "AdminSPanelAdministracion";
    }

    public String ir_SEdicionPerfil() {
        return "AdminSEdicionPerfil";
    }

    public String ir_SInformacionPersonal() {
        return "AdminSInformacionPersonal";
    }

    public String ir_SInformacionSesion() {
        return "AdminSInformacionSesion";
    }
    
    public String ir_SVistaReserva() {
        return "VistaReservaAdminS";
    }

    public String ir_SCategorias() {
        return "AdminSCategorias";
    }

    public String ir_SCiudades() {
        return "AdminSCiudades";
    }

    public String ir_SClientes() {
        return "AdminSClientes";
    }

    public String ir_SEmpleados() {
        return "AdminSEmpleados";
    }

    public String ir_SMarcas() {
        return "AdminSMarcas";
    }

    public String ir_SAsociarVehiculosConductores() {
        return "AdminSAsociarVehiculosConductores";
    }
        
    public String ir_SAsociarVehiculosServicios() {
        return "AdminSAsociarVehiculosServicios";
    }

    public String ir_SPaises() {
        return "AdminSPaises";
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

    public String ir_STiposServicios() {
        return "AdminSTiposServicios";
    }

    public String ir_SCostosServicios() {
        return "AdminSCostosServicios";
    }

    public String ir_SProveedores() {
        return "AdminSProveedores";
    }

    public String ir_SServicios() {
        return "AdminSServicios";
    }

    public String ir_SRoles() {
        return "AdminSRoles";
    }

    public String ir_SSAdministradores() {
        return "AdminSAdministradores";
    }    

    public String ir_SMercados() {
        return "AdminSMercados";
    }

    public String ir_SCategoriasServicios() {
        return "AdminSCategoriaServicios";
    }

    public String ir_SParametrosReservaciones() {
        return "AdminSParametrosReservaciones";
    }

    public String ir_SPanelCalificaciones() {
        return "AdminSPanelCalificaciones";
    }
    
    public String ir_SListaReservaciones(){
        return "AdminSIrListaReservaciones";
    }

    public String ir_SListaCalificaciones() {
        return "AdminSListaCalificaciones";
    }

    public String ir_SReservaciones() {
        return "AdminSReservaciones";
    }

    /* / CONDUCTOR / */
    public String ir_CoDashboard() {
        return "ConductorDashboard";
    }

    public String ir_CoInformacionPersonal() {
        return "ConductorInformacionPersonal";
    }

    public String ir_CoInformacionSesion() {
        return "ConductorInformacionSesion";
    }
    
    public String ir_CoEdicionPerfil() {
        return "ConductorEdicionPerfil";
    }

    public String ir_CoVistaReserva() {
        return "ConductorVistaReserva";
    }

    public String ir_CoClientes() {
        return "ConductorListaClientes";
    }
    
    public String ir_CoListaReservaciones() {
        return "ConductorIrListaReservaciones";
    }


    /* / Proveedor/ */
    public String ir_ProDashboard() {
        return "ProveedorDashboard";
    }
    
    public String ir_ProInformacionPersonal() {
        return "ProveedorInformacionPersonal";
    }

    public String ir_ProInformacionSesion() {
        return "ProveedorInformacionSesion";
    }

    public String ir_ProEdicionPerfil() {
        return "ProveedorEdicionPerfil";
    }

    public String ir_ProEstadoVehiculos() {
        return "ProveedorEstadoVehiculos";
    }

    /* / CLIENTE/ */
    public String ir_CReservaciones() {
        return "ClienteReservaciones";
    }

    public String ir_CInformacionPersonal() {
        return "ClienteInformacionPersonal";
    }

    public String ir_CInformacionSesion() {
        return "ClienteInformacionSesion";
    }
    
    public String ir_CListaReservaciones() {
        return "ClienteListaReservaciones";
    }

    public String ir_CDashboard() {
        return "ClienteDashboard";
    }

    public String ir_CEdicionPerfil() {
        return "ClienteEdicionPerfil";
    }

    public String ir_CVistaReserva() {
        return "VistaReservaCliente";
    }

    public String ir_CCalificarReservaciones() {
        return "ClienteCalificarReservaciones";
    }
    
    public String ir_CTiposServicios() {
        return "ClienteTiposServicios";
    }

    //OTROS
    public String ir_Registrarse() {
        return "Register";
    }

    public String ir_olvideContrasena() {
        return "OlvideContrasena";
    }

    public String ir_Login() {
        return "Login";
    }

    public String regresarTableroPrincipal() {
        String ruta = "";
        switch (sesion.getSmsRol().getRolNombre()) {
            case "Administrador Principal":
                ruta = "AdminPDashboard";
                break;
            case "Administrador Secundario":
                ruta = "AdminSDashboard";
                break;
            case "Cliente":
                ruta = "ClienteDashboard";
                break;
            case "Conductor":
                ruta = "ConductorDashbord";
                break;
            case "Proveedor":
                ruta = "ProveedorDashboard";
                break;
        }
        return ruta;
    }

    public String irRegistroCliente() {
        String ruta = "";
        switch (sesion.getSmsRol().getRolNombre()) {
            case "Administrador Principal":
                ruta = ir_PClientes();
                break;
            case "Administrador Secundario":
                ruta = ir_SClientes();
                break;
        }
        return ruta;
    }

}
