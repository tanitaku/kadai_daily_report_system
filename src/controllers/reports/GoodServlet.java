package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
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
     * @param r2
     * @param e
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        EntityManager em = DBUtil.createEntityManager();

     //　LIKEをインスタンス化し、外部キーでIDを入れる,セット完了
        Like l2 = new Like();

        l2.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        l2.setReport(em.find(Report.class, (Integer)(request.getSession().getAttribute("reports_id"))));


         Employee e1 = l2.getEmployee();
         Report r21 = l2.getReport();

         Like l = null;

            // 社員番号とパスワードが正しいかチェックする
            try {
               l = em.createNamedQuery("checkId", Like.class)
                      .setParameter("employee", e1)
                      .setParameter("report", r21)
                      .getSingleResult();
                } catch(NoResultException ex) {}


              if(l != null) {
                  request.getSession().setAttribute("flush", "いいね済みです。");
          }else {

                  em.getTransaction().begin();
                  em.persist(l2);
                  em.getTransaction().commit();
                  em.close();
                  request.getSession().setAttribute("flush", "いいねを投稿しました。");
   }
               response.sendRedirect(request.getContextPath() + "/reports/index");

     }




}




