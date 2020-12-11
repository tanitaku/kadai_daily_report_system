package controllers.employees;

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
 * Servlet implementation class EmployeesDataServlet
 */
@WebServlet("/data")
public class EmployeesDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        EntityManager em = DBUtil.createEntityManager();

        // 開くページ数を取得（デフォルトが1ページ目）
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}

     // 最大件数と開始位置を指定してメッセージを取得
        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                                     .setFirstResult(15 * (page - 1)) // ページ１の場合、０から15件表示するということ
                                     .setMaxResults(15)
                                     .getResultList();

     // 全件数を取得
        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                                        .getSingleResult();

        Relation test2 = new Relation();
        test2.setFollower((Employee)request.getSession().getAttribute("login_employee"));
        Employee e = test2.getFollower();


        test2.setFollower((Employee)request.getSession().getAttribute("login_employee"));

        Employee e1 = test2.getFollower();

        List<Relation> fe = em.createNamedQuery("getMyAllFollowers", Relation.class)
                .setParameter("follower", e1)
                .getResultList();



        em.close();


        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }


        // リクエストスコープに保存する
        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);
        request.setAttribute("employee_id", e.getId());
        request.setAttribute("fes", fe);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/data.jsp");
        rd.forward(request, response);

    }

}
