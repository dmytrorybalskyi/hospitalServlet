package org.example.servlet;

import org.example.comands.*;
import org.example.model.entity.Account;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class MainServlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig) {
        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<Account>());
                commands.put("login", new LoginCommand());
                commands.put("registration", new RegistrationPageCommand());
                commands.put("patient/patient", new PatientPageCommand());
                commands.put("admin/admin", new AdminPageCommand());
                commands.put("patient/appointment", new AppointmentCommand());
                commands.put("admin/addDoctor", new DoctorRegistrationCommand());
                commands.put("admin/treatment",new SetDoctorCommand());
                commands.put("doctor/doctor",new DoctorPageCommand());
                commands.put("diagnosis",new DiagnosisCommand());
                commands.put("doctor/addProcedure",new AddProcedureCommand());
                commands.put("doctor/procedure",new ProcedurePageCommand());
                commands.put("doctor/doProcedures",new DoProceduresCommand());
                commands.put("nurse/doProcedures",new DoProceduresCommand());
                commands.put("discharge",new DischargeCommand());
                commands.put("nurse/nurse",new NursePageCommand());
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashSet<Account> loggedUsers = (HashSet<Account>) req.getSession().getServletContext().getAttribute("loggedUsers");
        System.out.println(Arrays.toString(loggedUsers.toArray()));
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println(path);
        path = path.replaceFirst("/", "").replaceAll("/page=\\d+","")
                .replaceAll("/\\d+","");
        System.out.println(path);
        Command command = commands.getOrDefault(path,
                (r) -> "/index.jsp");
        System.out.println(command.getClass().getName());
        String page = command.execute(request);
        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", "/"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

}
