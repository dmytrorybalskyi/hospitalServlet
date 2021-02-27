package org.example.comands;

import org.example.model.entity.Treatment;
import org.example.service.DoctorService;
import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SetDoctorCommand implements Command {
    TreatmentService treatmentService = new TreatmentService();
    DoctorService doctorService = new DoctorService();
    private static Logger log = Logger.getLogger(SetDoctorCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        Treatment treatment = treatmentService.findById(id);
        request.setAttribute("treatment", treatment);
        request.setAttribute("doctors", doctorService.findByCategory(treatment.getCategory()));
        String doctor = request.getParameter("doctor");
        if (doctor == null) {
            return "/admin/treatment.jsp";
        }
        try {
            treatmentService.setDoctor(treatment, Integer.valueOf(doctor));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.info("Treatment #"+treatment.getId()+" --> doctor was already set");
            request.setAttribute("doctorSet", true);
            return "/admin/treatment.jsp";
        }
        return "redirect:admin/admin";
    }
}
