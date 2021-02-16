package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;

public class AdminPageCommand implements Command{
    TreatmentService treatmentService = new TreatmentService();

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("treatments",treatmentService.getTreatmentByStatus());
        Account account = (Account) request.getSession().getAttribute("account");
        if(account.getLogin()==null){
            return "redirect:/";
        }
        if(account.getRole().equals("admin")){
            return "/admin/admin.jsp";
        }else return "redirect:/";
    }
}

