package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Relation;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        EntityManager em = DBUtil.createEntityManager();

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        Long reports_count = (long)em.createNamedQuery("getReportsCount", Long.class)
                                     .getSingleResult();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        Relation r = new Relation();
        r.setFollower(login_employee);

        Employee e = r.getFollower();


        List<Employee> fe = em.createNamedQuery("getFollow", Employee.class)
                .setParameter("follower", e)
                .getResultList();


        List<Report> r2 = em.createNamedQuery("getAllReports", Report.class)
                            .setFirstResult(15 * (page - 1))
                            .setMaxResults(15)
                            .getResultList();

        em.close();

        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("login_employee", login_employee.getId());
        request.setAttribute("reports", r2);
        request.setAttribute("fes", fe);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
    }

}
