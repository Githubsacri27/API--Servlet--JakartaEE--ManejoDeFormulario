package org.rsacri.apiservlet.webapp.form;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/registro")
public class FormServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String pais = req.getParameter("pais");
        String[] lenguajes = req.getParameterValues("lenguajes");
        String[] roles = req.getParameterValues("roles");

        String idioma = req.getParameter("idioma");
        boolean habilitar = req.getParameter("habilitar") != null &&
                req.getParameter("habilitar").equals("on");
        String secreto = req.getParameter("secreto");

        Map <String, String> errores = new HashMap<>();
        if (username == null || username.isBlank()) {
            errores.put("username","El username es requerido!");

        }
        if (password == null || password.isBlank()) {
            errores.put("password","El password no puede estar vacío.");
        }
        if (email == null || !email.contains("@")) {
            errores.put("email","el email es requerido y debe contener un formato correo");
        }
        if (pais == null || pais.equals("") || pais.equals(" ")) {
            errores.put("pais"," Debe seleccionar un país!");
        }
        if (lenguajes == null || lenguajes.length == 0) {
            errores.put("lenguajes","Debe seleccionar una tecnología!");
        }
        if (roles == null || roles.length == 00) {
            errores.put("roles","Debe selecciona al menos un rol");
        }
        if (idioma == null) {
            errores.put("idioma","Debe seleccionar un idioma");
        }

        if (errores.isEmpty()) {
            try (PrintWriter out = resp.getWriter()) {

                out.println("<!DOCTTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Resultado form</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("    <h1>Resultado form!</h1>");

                out.println("               <ul>");
                out.println("               <li> Username: " + username + "</li>");
                out.println("               <li> Pasword: " + password + "</li>");
                out.println("               <li> Email: " + email + "</li>");
                out.println("               <li> País: " + pais + "</li>");
                out.println("               <li> Lenguajes: <ul>");
                Arrays.asList(lenguajes).forEach(lenguaje -> {
                    out.println("                   <li>" + lenguaje + "</li>");
                });
                out.println("               <li>Roles: <ul>");
                Arrays.asList(roles).forEach(role -> {
                    out.println("                   <li>" + role + "</li>");
                });
                out.println("               </ul></li>");
                out.println("               <li>Idioma: " + idioma + "</li>");
                out.println("               <li>Habilitar: " + habilitar + "</li>");
                out.println("               <li>Secreto: " + secreto + "</li>");
                out.println("               </ul>");
                out.println("    </body>");
                out.println("</html>");
            }
            } else {
                /*errores.forEach(error -> {
                    out.println("<li>" + error + "</li>");
                });
                out.println("<p><a href=\"/webapp-form/index.jsp\">volver</a></p>");*/
                //se pasan los mensajes de error al contexto del request
                req.setAttribute("errores", errores);
                //cargar la vista
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }

        }
    }

