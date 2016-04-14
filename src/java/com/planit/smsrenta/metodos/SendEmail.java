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
import com.planit.smsrenta.modelos.SmsEmpleado;
import com.planit.smsrenta.modelos.SmsProveedor;
import com.planit.smsrenta.modelos.SmsReservacion;
import com.planit.smsrenta.modelos.SmsUsuario;
import com.planit.smsrenta.modelos.SmsVehiculo;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

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

    public void sendEmailCliente(SmsEmpleado empleado, SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) {

        init();
        IUsuarioDao usuDao = new ImpUsuarioDao();
        SmsUsuario cliente = usuDao.consultarUsuario(Cliente).get(0);

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
            message.setText("Señor(a) " + cliente.getUsuarioNombre() + ","
                    + "\n"
                    + "Le confirmamos su reserva para el vehículo " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + ". "
                    + "Allí lo atenderá el asesor " + empleado.getSmsUsuario().getUsuarioNombre() + ", quien estará disponible y a sus órdenes para su traslado y apoyo en su estadía. "
                    + "El Valor de su servicio es de COP $" + reservacion.getReservacionCosto() + ", la factura será enviada a su correo electrónico en dos días.\n"
                    + "Esperamos que nuestro servicio sea de su total satisfacción y no olvide calificarlo."
                    + "Atentamente,\n"
                    + "SMS Renta");

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

    public void sendEmailClienteWithout(SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) {

        init();
        IUsuarioDao usuDao = new ImpUsuarioDao();
        SmsUsuario cliente = usuDao.consultarUsuario(Cliente).get(0);

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
            message.setText("Señor(a) " + cliente.getUsuarioNombre() + ","
                    + "\n"
                    + "Le confirmamos su reserva para el vehículo " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + ". "
                    + "Allí lo atenderá un asesor de SMSRenta, quien le entregara su vehiculo y le indicara todo lo relacionado al dia y hora de entrega.\n"
                    + "El Valor de su servicio es de COP $" + reservacion.getReservacionCosto() + ", la factura será enviada a su correo electrónico en dos días.\n"
                    + "Esperamos que nuestro servicio sea de su total satisfacción y no olvide calificarlo."
                    + "Atentamente,\n"
                    + "SMS Renta");

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

    public void sendEmailAdministrador(SmsEmpleado empleado, SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) {

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
            message.setSubject("Nueva reservacion en el sistema. Reservacion " + reservacion.getIdReservacion() + ", cliente " + Cliente.getUsuarioNombre() + ", fecha de inicio " + FechaInicio + "");
            message.setText("Señor(a) Administrador Principal,"
                    + "\n"
                    + "Se confirmo una reservación para el cliente '" + Cliente.getUsuarioNombre() + "', el vehiculo elegido es " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + ". "
                    + "El asesor elegido es el señor " + empleado.getSmsUsuario().getUsuarioNombre() + " y el Valor de la servicio es de COP $" + reservacion.getReservacionCosto() + "."
                    + "Atentamente,\n"
                    + "SMS Renta");

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

    public void sendEmailAdministradorWithout(SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) {

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
            message.setSubject("Nueva reservacion en el sistema. Reservacion " + reservacion.getIdReservacion() + ", cliente " + Cliente.getUsuarioNombre() + ", fecha de inicio " + FechaInicio + "");
            message.setText("Señor(a) Administrador Principal,"
                    + "\n"
                    + "Se confirmo una reservación para el cliente '" + Cliente.getUsuarioNombre() + "', el vehiculo elegido es " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + ". "
                    + "No hay asesor elegido y el Valor de la servicio es de COP $" + reservacion.getReservacionCosto() + "."
                    + "Atentamente,\n"
                    + "SMS Renta");

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

    public void sendEmailConductor(SmsEmpleado empleado, SmsVehiculo vehiculo, SmsReservacion reservacion, SmsUsuario Cliente) {

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
            message.setSubject("Nueva Reservacion fecha inicio: " + FechaInicio + ", hora de inicio " + HoraInicio + "");
            message.setText("Señor(a) " + empleado.getSmsUsuario().getUsuarioNombre() + ","
                    + "\n"
                    + "Se confirmo una nueva reservacion para la cual usted fue escogido como asesor, el vehiculo escogido es un(a) " + vehiculo.getSmsReferencia().getSmsMarca().getMarcaNombre() + " " + vehiculo.getSmsReferencia().getReferenciaNombre() + " programada para el día " + FechaInicio + " a las " + HoraInicio + " en " + reservacion.getReservacionLugarLlegada() + " en la ciudad de " + reservacion.getSmsCiudadByIdCiudadInicio().getCiudadNombre() + " hasta el día " + FechaLlegada + "."
                    + "Allí lo espera el cliente " + Cliente.getUsuarioNombre() + ", quien requiere del mejor servicio y atencion prestada\n"
                    + "Esperamos que su servicio sea prestado de la mejor forma para garantizar la total satisfaccion del cliente"
                    + "Atentamente,\n"
                    + "SMS Renta");

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

    public void sendEmailCliente(SmsUsuario Cliente, String password) {

        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + Cliente.getUsuarioEmail()));
            message.setSubject("Bienvenido a SMS Renta");
            message.setText("Señor(a) " + Cliente.getUsuarioNombre() + ","
                    + "\n"
                    + "Le confirmamos que su registro al sistema SMS Renta fue exitoso, y le damos la bienvenida a nuestra familia. \n"
                    + "Esperamos que nuestro servicio sea de su total agrado.\n"
                    + "Los datos de sesion para acceder al sistema son:.\n"
                    + "Nombre de sesion o email: " + Cliente.getUsuarioEmail() + "\n"
                    + "Contraseña: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear un nombre de sesion y contraseña personalizados.\n"
                    + "Atentamente, SMS Renta");

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

    public void sendEmailAdministradorBienvenida(SmsUsuario Admin, String password) {

        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + Admin.getUsuarioEmail()));
            message.setSubject("Bienvenido a SMS Renta");
            message.setText("Administrador(a) " + Admin.getUsuarioNombre() + ","
                    + "\n"
                    + "Le confirmamos que su registro al sistema SMS Renta fue exitoso, y le damos la bienvenida a nuestra familia. \n"
                    + "Los datos de sesion para acceder al sistema son:.\n"
                    + "Nombre de sesion o email: " + Admin.getUsuarioEmail() + "\n"
                    + "Contraseña: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear un nombre de sesion y contraseña personalizados.\n"
                    + "Atentamente, SMS Renta");

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

    public void sendEmailProveedor(SmsUsuario usuario, SmsProveedor proveedor, String password) {

        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + usuario.getUsuarioEmail()));
            message.setSubject("Bienvenido a SMS Renta");
            message.setText("Señores " + proveedor.getProveedorRazonSocial() + ","
                    + "\n"
                    + "Le confirmamos que su registro al sistema SMS Renta fue exitoso, y le damos la bienvenida a nuestra familia. \n"
                    + "Los datos de sesion para acceder al sistema y administrar sus vehiculos y conductores son:\n"
                    + "Nombre de sesion o email: " + usuario.getUsuarioEmail() + "\n"
                    + "Contraseña: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear un nombre de sesion y contraseña personalizados.\n"
                    + "Atentamente, SMS Renta");

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

    public void sendEmailConductor(SmsUsuario conductor, String password) {

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
            message.setText("Conductor " + conductor.getUsuarioNombre() + ","
                    + "\n"
                    + "Le confirmamos que su registro al sistema SMS Renta fue exitoso, y le damos la bienvenida a nuestra familia. \n"
                    + "Los datos de sesion para acceder al sistema y ver los servicios que tiene agendados son:\n"
                    + "Nombre de sesion o email: " + conductor.getUsuarioEmail() + "\n"
                    + "Contraseña: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear un nombre de sesion y contraseña personalizados.\n"
                    + "Atentamente, SMS Renta");

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

    
    public void sendEmailNuevaContrasena(SmsUsuario usuario, String password) {
        init();
        try {
            MimeMessage message = new MimeMessage(session);

            //quien envia
            message.setFrom(new InternetAddress("smsrenta@gmail.com"));

            // a donde se envia
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("" + usuario.getUsuarioEmail()));
            message.setSubject("Nueva contraseña para SMS Renta");
            message.setText("Señor(a) " + usuario.getUsuarioNombre() + ","
                    + "\n"
                    + "Mediante este mensaje enviamos una contraseña provisional para acceder al sistema SMS Renta."
                    + "Los datos de sesion para acceder al sistema son:\n"
                    + "Nombre de sesion o email: " + usuario.getUsuarioEmail() + "\n"
                    + "La nueva contraseña es: " + password + "\n"
                    + "Por favor no olvide editar su perfil para crear una contraseña personalizada.\n"
                    + "Atentamente, SMS Renta");

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
