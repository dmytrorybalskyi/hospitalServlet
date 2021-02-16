package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class AppointmentCommand implements Command{
    private TreatmentService treatmentService = new TreatmentService();
    @Override
    public String execute(HttpServletRequest request) {
        Integer categoryId = Integer.valueOf(request.getParameter("category"));
        Account account = (Account) request.getSession().getAttribute("account");
        try {
            treatmentService.createTreatment(account.getId(), categoryId);
        }catch (SQLException e){
            System.out.println("Patient already appointment");
            request.setAttribute("message",true);
            return "/patient";
        }
        return "redirect:patient";
    }
}