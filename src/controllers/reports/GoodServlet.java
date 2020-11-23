package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class GoodServlet
 */
@WebServlet("/reports/good")
public class GoodServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, (Integer)(request.getSession().getAttribute("reports_id")));


        String goods = request.getParameter("good");
        if(goods != null) {
            Integer count = r.getGoods();
            count++;
            r.setGoods(count);
        }
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "いいねを投稿しました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");
}
}
