package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.ProceduresService;

import javax.servlet.http.HttpServletRequest;

public class ProcedurePageCommand implements Command{
    private ProceduresService proceduresService = new ProceduresService();

    @Override
    public String execute(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        request.setAttribute("proceduresList",proceduresService.findAllByDoctor(account.getId()));
        return "/doctor/procedure.jsp";
    }
}
