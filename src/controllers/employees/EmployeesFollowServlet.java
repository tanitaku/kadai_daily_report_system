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
 * Servlet implementation class EmployeesFollowServlet
 */
@WebServlet("/follow")
public class EmployeesFollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesFollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        EntityManager em = DBUtil.createEntityManager();

        //　LIKEをインスタンス化し、外部キーでIDを入れる,セット完了
           Relation f = new Relation();

           f.setFollower((Employee)request.getSession().getAttribute("login_employee"));
           Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
           f.setFollowered(e);

           f.setFollow_flag(1);


            Employee e1 = f.getFollower();
            Employee r21 = f.getFollowered();


            Relation l = null;

               // 社員番号とパスワードが正しいかチェックする
               try {
                  l = em.createNamedQuery("checkFollow", Relation.class)
                         .setParameter("follower", e1)
                         .setParameter("followered", r21)
                         .getSingleResult();
                   } catch(NoResultException ex) {}


                 if(l != null) {
                     request.getSession().setAttribute("flush", "フォロー済みです。");
             }else {

                     em.getTransaction().begin();
                     em.persist(f);
                     em.getTransaction().commit();
                     request.getSession().setAttribute("flush", "フォローしました");
             }

                 em.close();

                 response.sendRedirect(request.getContextPath() + "/data");
    }


}
