package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.CategoryService;

import javax.servlet.http.HttpServletRequest;

public class PatientPageCommand implements Command {
    private CategoryService categoryService = new CategoryService();

    @Override
    public String execute(HttpServletRequest request) {
      Account account = (Account) request.getSession().getAttribute("account");
      if(account==null||account.getLogin()==null){
          return "redirect:/";
      }
      if(account.getRole().name().equals("patient")){
            request.setAttribute("categories",categoryService.finAll());
          return "/patient/patient.jsp";
      }else return "redirect:/";
    }
}
