package org.example.comands;

import org.example.model.entity.Account;
import org.example.model.entity.Roles;
import org.example.service.ProceduresService;

import javax.servlet.http.HttpServletRequest;

public class DoProceduresCommand implements Command {
    private ProceduresService proceduresService = new ProceduresService();

    @Override
    public String execute(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        Integer id = Integer.valueOf(request.getParameter("procedures_id"));
        proceduresService.doProcedures(id);
        if (account.getRole().equals(Roles.nurse)) {
            return "redirect:nurse/nurse";
        }
        return "redirect:doctor/procedure";
    }

}
