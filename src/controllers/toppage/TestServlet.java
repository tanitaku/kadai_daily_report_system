package controllers.toppage;

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
import utils.DBUtil;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        EntityManager em = DBUtil.createEntityManager();

        Relation f = new Relation();

        f.setFollower((Employee)request.getSession().getAttribute("login_employee"));

        Employee e1 = f.getFollower();

        List<Relation> fe = em.createNamedQuery("getMyAllFollowers", Relation.class)
                .setParameter("follower", e1)
                .getResultList();

        em.close();


        request.setAttribute("fes", fe);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/follower.jsp");
        rd.forward(request, response);
    }

}
