/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.planit.smsrenta.metodos;

import com.planit.smsrenta.dao.IReservacionDao;
import com.planit.smsrenta.dao.IUsuarioDao;
import com.planit.smsrenta.dao.ImpReservacionDao;
import com.planit.smsrenta.dao.ImpUsuarioDao;
import com.planit.smsrenta.modelos.SmsCalificacion;
import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsProveedor;
import com.planit.smsrenta.modelos.SmsReservacion;
import com.planit.smsrenta.modelos.SmsUsuario;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    // Leer la plantilla  
    InputStream inputStream = getClass().getResourceAsStream("plantillaCorreo");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    // StringBuffer para almacenar el contenido de la plantilla  
    String strLine;
    StringBuffer styleHTML = new StringBuffer();

    String inicio = "<body><table class=\"body\" data-made-with-foundation=\"\"><tr><td class=\"float-center\" align=\"center\" valign=\"top\"><center data-parsed=\"\">"
            + "<table class=\"wrapper header float-center\" align=\"center\"><tr><td class=\"wrapper-inner\"><table class=\"container\"><tbody><tr><td><table class=\"row collapse\">"
            + "<tbody><tr><th class=\"small-6 large-6 columns first\"><table><tr><th> <img src=\"http://smsrenta.com/images/ai/logo.png\"> </th></tr></table></th><th class=\"small-6 large-6 columns last\">"
            + "<table><tr><th><p class=\"text-right\"><a href=\"http://smsrenta.com\" style=\" color: #FC5B35;\">Página Web SMS Renta</a></p></th></tr></table></th></tr></tbody>"
            + "</table></td></tr></tbody></table></td></tr></table><table class=\"container float-center\"><tbody><tr><td><table class=\"spacer\"><tbody><tr><td height=\"16px\" style=\"font-size:16px;line-height:16px;\">&#xA0;</td>"
            + "</tr></tbody></table><table class=\"row\"><tbody><tr><th class=\"small-12 large-12 columns first last\"><table><tr><th>";

    String fin = "</th><th class=\"expander\"></th></tr></table></th></tr></tbody></table><table class=\"wrapper secondary\" align=\"center\"><tr>"
            + "<td class=\"wrapper-inner\"><table class=\"spacer\"><tbody><tr><td height=\"16px\" style=\"font-size:16px;line-height:16px;\">&#xA0;</td>"
            + "</tr></tbody></table><table class=\"row\"><tbody><tr><th class=\"small-12 large-6 columns first\"><table><tr><th><h5>Conectate con Nosotros:</h5>"
            + "<table class=\"button twitter expand\"><tr><td><!--<table><tr><td><center data-parsed=\"\"><a href=\"http://zurb.com\" align=\"center\" class=\"float-center\">Twitter</a>"
            + "</center></td></tr></table>--><a href=\"https://play.google.com/store/apps/details?id=co.com.planit.sms.renta.app.android.smsrenta&hl=es-419\">"
            + "<img class=\"img-center\" src=\"http://smsrenta.com/images/disponible_google.png\" alt=\"\" /></a></td><td class=\"expander\"></td></tr></table>"
            + "<table class=\"button google expand\"><tr><td><!--<table><tr><td><center data-parsed=\"\"><a href=\"http://zurb.com\" align=\"center\" class=\"float-center\">Twitter</a>"
            + "</center></td></tr></table>--><a href=\"http://www.app.smsrenta.com:8080/faces/login.xhtml\"><img class=\"img-center\" src=\"http://smsrenta.com/images/app-web.png\" alt=\"\" />"
            + "</a></td><td class=\"expander\"></td></tr></table><table class=\"button facebook expand\"><tr><td><table><tr><td><center data-parsed=\"\"><a href=\"http://zurb.com\" align=\"center\" class=\"float-center\">Facebook</a></center>"
            + "</td></tr></table></td><td class=\"expander\"></td></tr></table></th></tr></table></th><th class=\"small-12 large-6 columns last\"><table><tr><th>"
            + "<h5>Información de Contacto:</h5><p>Teléfono: (57-1) 406 55 20</p><p>Email: <a href=\"mailto:edwinrobles@smsrenta.com\">edwinrobles@smsrenta.com</a></p>"
            + "</th></tr></table></th></tr></tbody></table></td></tr></table></td></tr></tbody></table></center></td></tr></table></body></html>";

    private final Properties properties = new Properties();
    private Session session;

    IReservacionDao resDao = new ImpReservacionDao();
    IUsuarioDao usuDao = new ImpUsuarioDao();

    SimpleDateFormat formatDate;
    SimpleDateFormat formatTime;

    public SendEmail() {
        formatDate = new SimpleDateFormat("yyyy-MM-dd");
        formatTime = new SimpleDateFormat("HH:mm:ss");

    }

    private void init() {

        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.put("mail.smtp.user", "smsrenta@gmail.com");
        properties.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties);
    }

    public void sendEmailCliente(SmsEmpleado empleado, SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) throws IOException {

        init();
        IUsuarioDao usuDao = new ImpUsuarioDao();
        SmsUsuario cliente = usuDao.consultarUsuario(Cliente);

        //Se formatean las fechas y horas
        String FechaInicio = formatDate.format(reservacion.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reservacion.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reservacion.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reservacion.getReservacionHoraLlegada());

        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + cliente.getUsuarioEmail()));
            message.setSubject("SMSRenta informe de su reservacion");

            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }

            String mensajehtml = styleHTML.toString() + inicio + "<h1>Hola, " + cliente.getUsuarioNombre() + "</h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Le confirmamos su reserva para el vehículo " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + ". "
                    + "Allí lo atenderá el asesor " + empleado.getSmsUsuario().getUsuarioNombre() + ", quien estará disponible y a sus órdenes para su traslado y apoyo en su estadía. "
                    + "El Valor de su servicio es de COP $" + reservacion.getReservacionCosto() + ", la factura será enviada a su correo electrónico en dos días.\n"
                    + "Esperamos que nuestro servicio sea de su total satisfacción y no olvide calificarlo."
                    + "Atentamente,\n"
                    + "SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }
    }

    public void sendEmailClienteWithout(SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) throws IOException {

        init();
        IUsuarioDao usuDao = new ImpUsuarioDao();
        SmsUsuario cliente = usuDao.consultarUsuario(Cliente);

        //Se formatean las fechas y horas
        String FechaInicio = formatDate.format(reservacion.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reservacion.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reservacion.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reservacion.getReservacionHoraLlegada());

        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + cliente.getUsuarioEmail()));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("SMSRenta informe de su reservacion");
            String mensajehtml = styleHTML.toString() + inicio + "<h1>Hola, " + cliente.getUsuarioNombre() + "<h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Le confirmamos su reserva para el vehículo " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + ". "
                    + "Allí lo atenderá un asesor de SMSRenta, quien le entregara su vehiculo y le indicara todo lo relacionado al dia y hora de entrega.\n"
                    + "El Valor de su servicio es de COP $" + reservacion.getReservacionCosto() + ", la factura será enviada a su correo electrónico en dos días.\n"
                    + "Esperamos que nuestro servicio sea de su total satisfacción y no olvide calificarlo."
                    + "Atentamente,\n"
                    + "SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }
    }

    public void sendEmailAdministrador(SmsEmpleado empleado, SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) throws IOException {

        init();
        //Se formatean las fechas y horas
        String FechaInicio = formatDate.format(reservacion.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reservacion.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reservacion.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reservacion.getReservacionHoraLlegada());

        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("operaciones@smsrenta.com"));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("Nueva reservacion en el sistema. Reservacion " + reservacion.getIdReservacion() + ", cliente " + Cliente.getUsuarioNombre() + ", fecha de inicio " + FechaInicio + "");
            String mensajehtml = styleHTML.toString() + inicio + "<h1>Hola, Administrador Principal</h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Se confirmo una reservación para el cliente '" + Cliente.getUsuarioNombre() + "', el vehiculo elegido es " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + ". "
                    + "El asesor elegido es el señor " + empleado.getSmsUsuario().getUsuarioNombre() + " y el Valor de la servicio es de COP $" + reservacion.getReservacionCosto() + "."
                    + "Atentamente,\n"
                    + "SMS Renta</p>" + fin;

            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }
    }

    public void sendEmailAdministradorWithout(SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) throws IOException {

        init();
        //Se formatean las fechas y horas
        String FechaInicio = formatDate.format(reservacion.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reservacion.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reservacion.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reservacion.getReservacionHoraLlegada());

        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("operaciones@smsrenta.com"));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("Nueva reservacion en el sistema. Reservacion " + reservacion.getIdReservacion() + ", cliente " + Cliente.getUsuarioNombre() + ", fecha de inicio " + FechaInicio + "");
            String mensajehtml = styleHTML + inicio + "<h1>Hola, Administrador Principal</h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Se confirmo una reservación para el cliente '" + Cliente.getUsuarioNombre() + "', el vehiculo elegido es " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + ". "
                    + "No hay asesor elegido y el Valor de la servicio es de COP $" + reservacion.getReservacionCosto() + "."
                    + "Atentamente,\n"
                    + "SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }
    }

    public void sendEmailConductor(SmsEmpleado empleado, SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) throws IOException {

        init();
        //Se formatean las fechas y horas
        String FechaInicio = formatDate.format(reservacion.getReservacionFechaInicio());
        String FechaLlegada = formatDate.format(reservacion.getReservacionFechaLlegada());
        String HoraInicio = formatTime.format(reservacion.getReservacionHoraInicio());
        String HoraLlegada = formatTime.format(reservacion.getReservacionHoraLlegada());

        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + empleado.getSmsUsuario().getUsuarioEmail()));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("Nueva Reservacion fecha inicio: " + FechaInicio + ", hora de inicio " + HoraInicio + "");
            String mensajehtml = styleHTML + inicio + "<h1>Hola " + empleado.getSmsUsuario().getUsuarioNombre() + "<h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Se confirmo una nueva reservacion para la cual usted fue escogido como asesor, el vehiculo escogido es un(a) " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + "."
                    + "Allí lo espera el cliente " + Cliente.getUsuarioNombre() + ", quien requiere del mejor servicio y atencion prestada\n"
                    + "Esperamos que su servicio sea prestado de la mejor forma para garantizar la total satisfaccion del cliente"
                    + "Atentamente,\n"
                    + "SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }

    }

    public void sendEmailCliente(SmsUsuario Cliente, String password) throws IOException {

        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + Cliente.getUsuarioEmail()));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("Bienvenido a SMS Renta");
            String mensajehtml = styleHTML + inicio + "<h1>Hola, " + Cliente.getUsuarioNombre() + "</h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Le confirmamos que su registro al sistema SMS Renta fue exitoso, y le damos la bienvenida a nuestra familia. \n"
                    + "Esperamos que nuestro servicio sea de su total agrado.\n"
                    + "Los datos de sesion para acceder al sistema son:.\n"
                    + "Nombre de sesion o email: " + Cliente.getUsuarioEmail() + "\n"
                    + "Contraseña: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear un nombre de sesion y contraseña personalizados.\n"
                    + "Atentamente, SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }

    }

    public void sendEmailAdministradorBienvenida(SmsUsuario Admin, String password) throws IOException {

        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + Admin.getUsuarioEmail()));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("Bienvenido a SMS Renta");
            String mensajehtml = styleHTML + inicio + "<h1>Hola, " + Admin.getUsuarioNombre() + "</h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Le confirmamos que su registro al sistema SMS Renta fue exitoso, y le damos la bienvenida a nuestra familia. \n"
                    + "Los datos de sesion para acceder al sistema son:.\n"
                    + "Nombre de sesion o email: " + Admin.getUsuarioEmail() + "\n"
                    + "Contraseña: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear un nombre de sesion y contraseña personalizados.\n"
                    + "Atentamente, SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }

    }

    public void sendEmailProveedor(SmsUsuario usuario, SmsProveedor proveedor, String password) throws IOException {

        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + usuario.getUsuarioEmail()));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("Bienvenido a SMS Renta");
            String mensajehtml = styleHTML + inicio + "<h1>Señores " + proveedor.getProveedorRazonSocial() + "</h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Le confirmamos que su registro al sistema SMS Renta fue exitoso, y le damos la bienvenida a nuestra familia. \n"
                    + "Los datos de sesion para acceder al sistema y administrar sus vehiculos y conductores son:\n"
                    + "Nombre de sesion o email: " + usuario.getUsuarioEmail() + "\n"
                    + "Contraseña: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear un nombre de sesion y contraseña personalizados.\n"
                    + "Atentamente, SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }

    }

    public void sendEmailConductor(SmsUsuario conductor, String password) throws IOException {

        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + conductor.getUsuarioEmail()));
            message.setSubject("Bienvenido a SMS Renta");
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }

            String mensajehtml = styleHTML.toString() + inicio + "<h1>Hola, " + conductor.getUsuarioNombre() + "</h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Le confirmamos que su registro al sistema SMS Renta fue exitoso, y le damos la bienvenida a nuestra familia. \n"
                    + "Los datos de sesion para acceder al sistema y ver los servicios que tiene agendados son:\n"
                    + "Nombre de sesion o email: " + conductor.getUsuarioEmail() + "\n"
                    + "Contraseña: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear un nombre de sesion y contraseña personalizados.\n"
                    + "Atentamente, SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }

    }

    public void sendEmailNuevaContrasena(SmsUsuario usuario, String password) throws IOException {
        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + usuario.getUsuarioEmail()));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("Nueva contraseña para SMS Renta");
            String mensajehtml = styleHTML.toString() + inicio + "<h1>Hola, " + usuario.getUsuarioNombre() + "</h1>"
                    + "\n"
                    + "<p class=\"content-txt\">Mediante este mensaje enviamos una contraseña provisional para acceder al sistema SMS Renta."
                    + "Los datos de sesion para acceder al sistema son:\n"
                    + "Nombre de sesion o email: " + usuario.getUsuarioEmail() + "\n"
                    + "La nueva contraseña es: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear una contraseña personalizada.\n"
                    + "Atentamente, SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }
    }

    public void sendEmailMalaCalificacion(SmsCalificacion calificacion) throws IOException {
        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("operaciones@smsrenta.com.co"));
            
            // Almacenar el contenido de la plantilla en un StringBuffer  
            while ((strLine = bufferedReader.readLine()) != null) {
                styleHTML.append(strLine);
            }
            
            message.setSubject("Calificacion baja, reservacion " + calificacion.getSmsReservacion().getIdReservacion());
            String mensajehtml = styleHTML.toString() + inicio + "<h1>Hola, el cliente " + calificacion.getSmsReservacion().getSmsUsuario().getUsuarioNombre() + "</h1>\n"
                    + "<p class=\"content-txt\">califico la reservacion " + calificacion.getSmsReservacion().getIdReservacion() + " con un puntaje de " + calificacion.getCalificacionCalidadServicio() + " estrellas."
                    + "El conductor asignado a este servicio era el señor(a) " + calificacion.getSmsReservacion().getSmsEmpleado().getSmsUsuario().getUsuarioNombre() + " y "
                    + "el vehiculo elegido era el identificado con placa " + calificacion.getSmsReservacion().getSmsVehiculo().getVehPlaca() + "./n"
                    + "Atentamente, SMS Renta</p>" + fin;
            message.setContent(mensajehtml, "text/html");
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "Smsrenta2016");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            me.getMessage();
            //Aqui se deberia o mostrar un mensaje de error o en lugar
            //de no hacer nada con la excepcion, lanzarla para que el modulo
            //superior la capture y avise al usuario con un popup, por ejemplo.
            return;
        }
    }

}
