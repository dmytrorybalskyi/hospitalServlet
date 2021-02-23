package org.example.comands;

import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;

public class DiagnosisCommand implements Command {
    private TreatmentService treatmentService = new TreatmentService();

    @Override
    public String execute(HttpServletRequest request) {
        String diagnosis = request.getParameter("diagnosis");
        Integer id = Integer.valueOf(request.getParameter("treatment_id"));
        treatmentService.setDiagnosis(diagnosis, id);
        return "/doctor/doctor";
    }
}
