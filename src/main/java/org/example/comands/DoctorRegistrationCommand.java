package org.example.comands;

import org.example.service.CategoryService;
import org.example.service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class DoctorRegistrationCommand implements Command {
    private CategoryService categoryService = new CategoryService();
    private DoctorService doctorService = new DoctorService();
    private static Logger log = Logger.getLogger(DoctorPageCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("categories", categoryService.finAll());
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        if (request.getParameter("category") == null) {
            return "/admin/addDoctor.jsp";
        }
        if (login == null || !login.matches("[a-zA-ZА-Яа-я0-9!<>_-]{1,20}")) {
            request.setAttribute("loginInvalid", true);
            return "/admin/addDoctor.jsp";
        } else if (password == null || !password.matches("[a-zA-ZА-Яа-я0-9!<>_-]{1,20}")) {
            request.setAttribute("passwordInvalid", true);
            return "/admin/addDoctor.jsp";
        } else if (name == null || !name.matches("([А-ЯA-ZЁ][a-zа-яьё'ъ]{2,16}\\s[А-ЯA-ZЁ][a-zа-яьё'ъ]{2,16})")) {
            request.setAttribute("nameInvalid", true);
            return "/admin/addDoctor.jsp";
        }
        Integer categoryId = Integer.valueOf(request.getParameter("category"));
        try {
            doctorService.addDoctor(login, password, name, categoryId);
            return "redirect:admin/admin";
        } catch (Exception e) {
            request.setAttribute("exist",true);
            log.info("Registration denied login: "+login+" -> already exist");
            return "/admin/addDoctor.jsp";
        }

    }
}
