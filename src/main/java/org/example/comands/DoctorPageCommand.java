package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;

public class DoctorPageCommand implements Command{
     private TreatmentService treatmentService = new TreatmentService();

    @Override
    public String execute(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        if(account==null||account.getLogin()==null){
            return "redirect:/";
        }
        if(account.getRole().name().equals("doctor")){
           request.setAttribute("treatments",treatmentService.getAllByDoctorAndStatus(account));
            return "/doctor/doctor.jsp";
        }else return "redirect:/";
    }

}
