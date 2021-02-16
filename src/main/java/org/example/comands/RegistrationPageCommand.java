package org.example.comands;

import org.example.service.PatientService;
import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand implements Command {
    private PatientService patientService = new PatientService();

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        if (login == null || login.equals("") || password == null || password.equals("") || name == null || name.equals("")) {
            return "/registration.jsp";
        }

        Integer age = Integer.valueOf(request.getParameter("age"));
        try {
            patientService.addPatient(login, password, name, age);
            return "redirect:login";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "/registration.jsp";
        }

    }
}
