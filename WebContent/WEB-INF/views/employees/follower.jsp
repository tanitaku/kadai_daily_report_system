<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:import url="../layout/app.jsp">
<c:param name="content">
<c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
         </c:if>
<h2>フォロワー一覧</h2>
     <table id="employee_list">
            <tbody>
                <tr>
                    <th>氏名</th>
                </tr>
                <c:forEach var="feses" items="${fes}">
                    <tr>
                        <td><c:out value="${feses.followered.name}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
          </table>

<a href="<c:url value='/' />">一覧に戻る</a>
</c:param>
</c:import>