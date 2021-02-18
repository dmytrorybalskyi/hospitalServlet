package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.ProceduresService;
import javax.servlet.http.HttpServletRequest;

public class NursePageCommand implements Command {
    private ProceduresService proceduresService = new ProceduresService();

    @Override
    public String execute(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null || account.getLogin() == null) {
            return "redirect:login";
        }
        if (account.getRole().name().equals("nurse")) {
            request.setAttribute("proceduresList", proceduresService.findAllByDoctor(account.getId()));
            return "/nurse/nurse.jsp";
        } else return "redirect:/";
    }

}
