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

        if (null==request.getParameter("age")) {
            return "/registration.jsp";
        }
        Integer age = Integer.valueOf(request.getParameter("age"));
        if(login==null||!login.matches("[a-zA-ZА-Яа-я0-9!<>_-]{1,20}")){
            request.setAttribute("loginInvalid",true);
            return "/registration.jsp";
        }else if(password==null||!password.matches("[a-zA-ZА-Яа-я0-9!<>_-]{1,20}")){
            request.setAttribute("passwordInvalid",true);
            return "/registration.jsp";
        }else if(name==null||!name.matches("([А-ЯA-ZЁ][a-zа-яьё'ъ]{2,16}\\s[А-ЯA-ZЁ][a-zа-яьё'ъ]{2,16})")){
            request.setAttribute("nameInvalid",true);
            return "/registration.jsp";
        }else if(age>130||age<1){
            request.setAttribute("ageInvalid",true);
            return "/registration.jsp";
        }
            try {
                patientService.addPatient(login, password, name, age);
                return "redirect:login";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "/registration.jsp";
            }
    }
}
