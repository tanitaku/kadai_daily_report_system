package controllers.employees;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Relation;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeFollowDeleteServlet
 */
@WebServlet("/follow/delete")
public class EmployeeFollowDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeFollowDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

        EntityManager em = DBUtil.createEntityManager();

        Employee e = em.find(Employee.class, (Integer)(request.getSession().getAttribute("employee_id")));
        Employee e2 = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        Relation l = null;

        try {
            l = em.createNamedQuery("deleteFollow", Relation.class)
                   .setParameter("follower", e)
                   .setParameter("followered", e2)
                   .getSingleResult();
             } catch(NoResultException ex) {}

        if(l != null) {
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "フォローを解除しました。");
        } else {
            request.getSession().setAttribute("flush", "データが存在しません。");
        }

        }

        response.sendRedirect(request.getContextPath() + "/data");
    }

}
