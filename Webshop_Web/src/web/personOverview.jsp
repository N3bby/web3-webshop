<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="User Overview"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="personOverview"/>
        <jsp:param name="title" value="User Overview"/>
    </jsp:include>

    <main>
        <table>
            <tr>
                <th>E-mail</th>
                <th>First Name</th>
                <th>Last Name</th>
            </tr>
            <c:forEach var="person" items="${personList}">
                <tr>
                    <th><c:out value="${person.email}"/></th>
                    <th><c:out value="${person.firstName}"/></th>
                    <th><c:out value="${person.lastName}"/></th>
                    <th>
                        <form class="deleteForm overviewForm" method="get" action="Controller">
                            <input type="hidden" name="action" value="requestPersonDelete">
                            <input type="hidden" name="id" value="<c:out value="${person.userid}"/>">
                            <input type="submit" class="overviewFormSubmit" value="Delete">
                        </form>
                    </th>
                    <th>
                        <form class="checkForm overviewForm" method="get" action="Controller">
                            <input type="hidden" name="action" value="requestCheckPassword">
                            <input type="hidden" name="id" value="<c:out value="${person.userid}"/>">
                            <input type="submit" class="overviewFormSubmit" value="Check">
                        </form>
                    </th>
                </tr>
            </c:forEach>
            <caption>Users Overview</caption>
        </table>
    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value="personOverview"/>
    </jsp:include>

</div>
</body>
</html>