/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funciones;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Desarrollo_Planit
 */
public class Upload {

    UploadedFile archivo;

    //Getters & Setters
    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    //Metodos
    public static String getPath() {
        try {
            ExternalContext tmpEC;

            tmpEC = FacesContext.getCurrentInstance().getExternalContext();
            String realPath = tmpEC.getRealPath("/");
            return realPath;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static HashMap<String, String> getMapPathFotosVehiculos() {
        try {
            HashMap<String, String> map = new HashMap<>();
            String path = getPathFotosVehiculos();
            map.put("path", path);
            map.put("url", "/resources/images/upload/Vehiculos/");
            return map;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static HashMap<String, String> getMapPathFotosUsuario() {
        try {
            HashMap<String, String> map = new HashMap<>();
            String path = getPathFotosUsuarios();
            map.put("path", path);
            map.put("url", "/resources/images/upload/Usuario/");
            return map;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
    public static HashMap<String, String> getMapPathFotosMercado() {
        try {
            HashMap<String, String> map = new HashMap<>();
            String path = getPathFotosMercados();
            map.put("path", path);
            map.put("url", "/resources/images/upload/Mercados/");
            return map;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
     public static HashMap<String, String> getMapPathFotosMarcas() {
        try {
            HashMap<String, String> map = new HashMap<>();
            String path = getPathFotosMarcas();
            map.put("path", path);
            map.put("url", "/resources/images/upload/Marcas/");
            return map;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static HashMap<String, String> getMapPathHojasVida() {
        try {
            HashMap<String, String> map = new HashMap<>();
            String path = getPathHojasVida();
            map.put("path", path);
            map.put("url", "/resources/images/upload/HojasVida/");
            return map;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
    

    

    public static String getNameDefaultUsuario() {

        try {
            String path = "Default.png";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static String getNameDefaultVehiculo() {

        try {
            String path = "Default.png";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static String getNameDefaultHojasVida() {

        try {
            String path = "Default.pdf";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
    public static String getNameDefaultMarca() {

        try {
            String path = "Default.pdf";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
    public static String getPathDefaultUsuario() {

        try {
            String path = "/resources/images/upload/Usuario/Default.png";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static String getPathDefaultMarca() {

        try {
            String path = "/resources/images/upload/Marcas/Default.png";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
    public static String getPathDefaultHojasVida() {

        try {
            String path = "/resources/images/upload/HojasVida/Default.pdf";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }

    public static String getPathDefaultVehiculo() {

        try {
            String path = "/resources/images/upload/Vehiculos/Default.png";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }

    public static String getPathFotosVehiculos() {
        try {
            String path = getPath() + "/resources/images/upload/Vehiculos/";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static String getPathFotosUsuarios() {
        try {
            String path = getPath() + "/resources/images/upload/Usuario/";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
    public static String getPathFotosMercados() {
        try {
            String path = getPath() + "/resources/images/upload/Mercados/";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
      public static String getPathFotosMarcas() {
        try {
            String path = getPath() + "/resources/images/upload/Marcas/";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public static String getPathHojasVida() {
        try {
            String path = getPath() + "/resources/images/upload/HojasVida/";
            return path;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    
    


    public void uploadFile(byte[] b, String nombre, String destino) throws IOException {

        String FilePath = destino;
        if (null != b) {
            byte[] bytes = b;
            String filename = nombre;

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(FilePath + filename)));
            stream.write(bytes);
            stream.close();
        }
    }

    public void DownloadFile() {
    }
}
