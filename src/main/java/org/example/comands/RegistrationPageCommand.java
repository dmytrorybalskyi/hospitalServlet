package org.example.comands;

import org.example.model.entity.Account;
import org.example.model.entity.Patient;
import org.example.service.PatientService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand implements Command{
    private PatientService patientService = new PatientService();

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        if(login==null||login.equals("")||password==null||password.equals("")||name==null||name.equals("")){
            return "/registration.jsp";
        }
        Integer age = Integer.valueOf(request.getParameter("age"));
        Account account = new Account(login,password);
        account.setRole("patient");
        Patient patient = new Patient(name,age);
        try {
            patientService.addPatient(account,patient);
            return "redirect:login";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "/registration.jsp";
        }

    }

}
