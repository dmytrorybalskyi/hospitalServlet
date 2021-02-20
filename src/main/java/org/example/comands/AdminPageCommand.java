package org.example.comands;

import org.example.model.entity.Account;
import org.example.model.entity.Page;
import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;

public class AdminPageCommand implements Command{
    TreatmentService treatmentService = new TreatmentService();

    @Override
    public String execute(HttpServletRequest request) {
        Page page = treatmentService.getPageByStatus(new Page((Integer) request.getSession().getAttribute("page")));
        request.setAttribute("treatments",page.getList());
        request.setAttribute("pages",page.getTotalPages());
        return "/admin/admin.jsp";

    }
}

