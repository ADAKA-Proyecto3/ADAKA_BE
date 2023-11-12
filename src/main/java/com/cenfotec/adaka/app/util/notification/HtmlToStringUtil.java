package com.cenfotec.adaka.app.util.notification;

import com.cenfotec.adaka.app.domain.Status;
import com.cenfotec.adaka.app.domain.User;
import org.springframework.stereotype.Component;


@Component
public class HtmlToStringUtil {
    public final String subject = "Bienvenido a ZhenAir";

    public String createEmailBodyForUser(User user,String password) {
        Status s =user.getStatus();
        if (s.equals(Status.ACTIVE) || s.equals(Status.FREEZE)) {
            if (s.equals(Status.ACTIVE)) {
                String html = createHtmlForNewUser(user.getName(), user.getEmail(),password);
                return html;
            } else {
                String html = createHtmlForForgotPassword(user.getName(), user.getEmail(),password);
                return html;
            }
        }else throw new IllegalArgumentException("The provided Status: " + s + "  is nos supported ");

    }


    private String createHtmlForNewUser(String name, String email, String password) {
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<!DOCTYPE html>\n");
        htmlStringBuilder.append("<html lang=\"es\">\n");
        htmlStringBuilder.append("<head>\n");
        htmlStringBuilder.append("    <meta charset=\"UTF-8\">\n");
        htmlStringBuilder.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        htmlStringBuilder.append("    <title>Bienvenido a ZhenAir</title>\n");
        htmlStringBuilder.append("</head>\n");
        htmlStringBuilder.append("<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n");
        htmlStringBuilder.append("\n");
        htmlStringBuilder.append(" <div style=\"max-width: 600px; margin: 0 auto; background-color: #049DD9; color: white; padding: 20px;  box-shadow: 0 4px 8px rgba(0,0,0,0.1);\">\n");
        htmlStringBuilder.append("        <h1 style=\"color: #Fffff;\">¡Bienvenido a ZhenAir!</h1>\n");
        htmlStringBuilder.append("       </div> \n");
        htmlStringBuilder.append("    <div style=\"max-width: 600px; margin: 0 auto;  color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);\">\n");
        htmlStringBuilder.append("       \n");
        htmlStringBuilder.append("        <p style=\"color: #555; font-size: 16px; line-height: 1.6;\">Hola " + name + ",</p>\n");
        htmlStringBuilder.append("        <p style=\"color: #555; font-size: 16px; line-height: 1.6;\">Estamos emocionados de tenerte con nosotros. Tu correo de acceso es :  " + email + "  la contraseña temporal es:   <strong>" + password + "</strong></p>\n");
        htmlStringBuilder.append("        <p style=\"color: #555; font-size: 16px; line-height: 1.6;\">Por favor, utiliza esta contraseña para iniciar sesión en tu cuenta. No olvides cambiarla después de tu primer inicio de sesión por motivos de seguridad.</p>\n");
        htmlStringBuilder.append("\n");
        htmlStringBuilder.append("        <p style=\"color: #555; font-size: 16px; line-height: 1.6;\">Saludos cordiales,<br>ZhenAir <span style=\"color: red;\">❤</span>  </p>\n");
        htmlStringBuilder.append("    </div>\n");
        htmlStringBuilder.append("\n");
        htmlStringBuilder.append("</body>\n");
        htmlStringBuilder.append("</html>");
        String htmlContent = htmlStringBuilder.toString();
        return htmlContent;
    }

    private String createHtmlForForgotPassword(String name, String email, String password) {
        StringBuilder htmlStringBuilder = new StringBuilder();
        htmlStringBuilder.append("<!DOCTYPE html>\n");
        htmlStringBuilder.append("<html lang=\"es\">\n");
        htmlStringBuilder.append("<head>\n");
        htmlStringBuilder.append("    <meta charset=\"UTF-8\">\n");
        htmlStringBuilder.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        htmlStringBuilder.append("    <title>Solicitud de recuperación de contraseña</title>\n");
        htmlStringBuilder.append("</head>\n");
        htmlStringBuilder.append("<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n");
        htmlStringBuilder.append("\n");
        htmlStringBuilder.append(" <div style=\"max-width: 600px; margin: 0 auto; background-color: #049DD9; color: white; padding: 20px;  box-shadow: 0 4px 8px rgba(0,0,0,0.1);\">\n");
        htmlStringBuilder.append("        <h1 style=\"color: #Fffff;\">Solicitud de recuperación de contraseña</h1>\n");
        htmlStringBuilder.append("       </div> \n");
        htmlStringBuilder.append("    <div style=\"max-width: 600px; margin: 0 auto;  color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);\">\n");
        htmlStringBuilder.append("       \n");
        htmlStringBuilder.append("        <p style=\"color: #555; font-size: 16px; line-height: 1.6;\">Hola " + name + ",</p>\n");
        htmlStringBuilder.append("        <p style=\"color: #555; font-size: 16px; line-height: 1.6;\">La a contraseña temporal es:   <strong>" + password + "</strong></p>\n");
        htmlStringBuilder.append("        <p style=\"color: #555; font-size: 16px; line-height: 1.6;\">Por favor, utiliza esta contraseña para iniciar sesión en tu cuenta,  cambiarla después de  inicio de sesión.</p>\n");
        htmlStringBuilder.append("\n");
        htmlStringBuilder.append("        <p style=\"color: #555; font-size: 16px; line-height: 1.6;\">Saludos cordiales,<br>ZhenAir <span style=\"color: red;\">❤</span>  </p>\n");
        htmlStringBuilder.append("    </div>\n");
        htmlStringBuilder.append("\n");
        htmlStringBuilder.append("</body>\n");
        htmlStringBuilder.append("</html>");
        String htmlContent = htmlStringBuilder.toString();
        return htmlContent;
    }

}
