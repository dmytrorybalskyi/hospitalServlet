package org.example.comands;

import org.example.model.entity.Account;
import org.example.service.TreatmentService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class DischargeCommand implements Command{
    private TreatmentService treatmentService = new TreatmentService();

    @Override
    public String execute(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        Integer treatmentId = Integer.valueOf(request.getParameter("treatment_id"));
        Integer patientId = Integer.valueOf(request.getParameter("patient_id"));

        try{
            treatmentService.discharge(account.getId(),patientId,treatmentId);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return "redirect:doctor";
    }
}
