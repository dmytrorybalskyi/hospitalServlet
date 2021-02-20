package org.example.comands;

import org.example.model.entity.Treatment;
import org.example.service.DoctorService;
import org.example.service.TreatmentService;
import javax.servlet.http.HttpServletRequest;

public class SetDoctorCommand implements Command{
    TreatmentService treatmentService = new TreatmentService();
    DoctorService doctorService = new DoctorService();

    @Override
    public String execute(HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        Treatment treatment = treatmentService.findById(id);
        request.setAttribute("treatment",treatment);
        request.setAttribute("doctors",doctorService.findByCategory(treatment.getCategory()));
        String doctor = request.getParameter("doctor");
        if(doctor==null){
            return "/admin/treatment.jsp";
        }
        treatmentService.setDoctor(treatment,Integer.valueOf(doctor));
        return "redirect:admin/admin";
    }
}
