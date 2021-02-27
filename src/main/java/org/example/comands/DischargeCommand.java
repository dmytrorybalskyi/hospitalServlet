package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DischargeCommand implements Command {
    private TreatmentService treatmentService = new TreatmentService();
    private static Logger log = Logger.getLogger(DischargeCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        Integer treatmentId = Integer.valueOf(request.getParameter("treatment_id"));
        Integer patientId = Integer.valueOf(request.getParameter("patient_id"));
        try {
            treatmentService.discharge(account.getId(), patientId, treatmentId);
        } catch (SQLException e) {
           log.info("Patient #"+patientId+" cannot be discharge");
        }
        return "redirect:doctor/doctor";
    }
}
