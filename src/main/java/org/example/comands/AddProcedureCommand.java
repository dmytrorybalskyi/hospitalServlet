package org.example.comands;
import org.example.model.entity.Treatment;
import org.example.model.entity.Types;
import org.example.service.DoctorService;
import org.example.service.ProceduresService;
import org.example.service.TreatmentService;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class AddProcedureCommand implements Command {
    DoctorService doctorService = new DoctorService();
    TreatmentService treatmentService = new TreatmentService();
    ProceduresService proceduresService = new ProceduresService();
    static Logger log = Logger.getLogger(DischargeCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) {
        Integer id = (Integer) request.getSession().getAttribute("id");
        Treatment treatment = treatmentService.findById(id);
        request.setAttribute("types", new ArrayList(Arrays.asList(Types.values())));
        request.setAttribute("doctors", doctorService.getAllByCategoryAndNurse(treatment.getCategory().getId()));
        String procedureName = request.getParameter("procedureName");
        if (procedureName == null || procedureName.equals("")) {
            return "/doctor/addProcedure.jsp";
        }
        Integer doctor_id = Integer.valueOf(request.getParameter("doctor"));
        String type = request.getParameter("type");
        try {
            proceduresService.addProcedures(procedureName, doctor_id, id, type);
        } catch (SQLException e) {
            request.setAttribute("sisterError",true);
            log.info("Treatment #"+id+" --> procedure denied , nurse cannot do operation");
            return "/doctor/addProcedure.jsp";
        }
        return "redirect:doctor/doctor";
    }

}
