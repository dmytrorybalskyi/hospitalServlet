package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.CategoryService;
import org.example.service.ProceduresService;
import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;

public class PatientPageCommand implements Command {
    private CategoryService categoryService = new CategoryService();
    private TreatmentService treatmentService = new TreatmentService();

    @Override
    public String execute(HttpServletRequest request) {
      Account account = (Account) request.getSession().getAttribute("account");
      if(account==null||account.getLogin()==null){
          return "redirect:/";
      }
      if(account.getRole().name().equals("patient")){
            request.setAttribute("categories",categoryService.finAll());
            request.setAttribute("treatment",treatmentService.getTreatmentByPatient(account));
          return "/patient/patient.jsp";
      }else return "redirect:/";
    }
}
