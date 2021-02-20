package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.TreatmentService;
import javax.servlet.http.HttpServletRequest;

public class AppointmentCommand implements Command{
    private TreatmentService treatmentService = new TreatmentService();

    @Override
    public String execute(HttpServletRequest request) {
        if(null==request.getParameter("category")){
            return "/patient/patient";
        }
        Integer categoryId = Integer.valueOf(request.getParameter("category"));
        Account account = (Account) request.getSession().getAttribute("account");
        try {
            treatmentService.createTreatment(account, categoryId);
        }catch (Exception e){
            System.out.println("Patient already appointment");
            request.setAttribute("message",true);
            return "/patient/patient";
        }
        return "redirect:patient/patient";
    }

}
