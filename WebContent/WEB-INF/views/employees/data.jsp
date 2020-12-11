<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
         </c:if>

         <h2>従業員一覧</h2>
         <table id="employee_list">
            <tbody>
                <tr>
                    <th>氏名</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="employee" items="${employees}">
                    <tr class="row${status.count % 2}">
                        <td>
                           <c:out value="${employee.name}" />
                        </td>
                        <td>
                          <c:if test="${employee.id != employee_id}">
                             <c:forEach var="feses" items="${fes}">
                                <c:if test="${feses.followered.id == employee.id}">
                                    <c:out value="フォロー済み"></c:out>
                                </c:if>
                             </c:forEach>
                                 <c:if test="${feses.followered.id != employee.id}">
                                    <a href="<c:url value='/follow?id=${employee.id}' />" class="btn btn--orange btn--radius">フォローする</a>
                                 </c:if>
                          </c:if>
                       </td>
                    </tr>
                </c:forEach>
            </tbody>
          </table>

          <div id="pagination">
            （全 ${employees_count} 人) <br />
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15) + 1}" step="1">
            <c:choose>
                <c:when test="${i == page}">
                    <c:out value="${i}" />&nbsp;
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/employees/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                </c:otherwise>
            </c:choose>
         </c:forEach>
         </div>
          </c:param>
       </c:import>