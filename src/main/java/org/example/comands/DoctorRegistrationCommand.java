package org.example.comands;

import org.example.service.CategoryService;
import org.example.service.DoctorService;
import javax.servlet.http.HttpServletRequest;

public class DoctorRegistrationCommand implements Command{
    private CategoryService categoryService = new CategoryService();
    private DoctorService doctorService = new DoctorService();

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("categories",categoryService.finAll());
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        if(login==null||login.equals("")||password==null||password.equals("")||name==null||name.equals("")){
            return "/admin/addDoctor.jsp";
        }
        Integer categoryId = Integer.valueOf(request.getParameter("category"));
        try {
            doctorService.addDoctor(login,password,name,categoryId);
            return "/patient";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "/admin/addDoctor.jsp";
        }

    }
}
