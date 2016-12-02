<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="fragment/head.jsp">
        <jsp:param name="title" value="Check Password"/>
    </jsp:include>
</head>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="checkPassword"/>
        <jsp:param name="title" value="Check Password"/>
    </jsp:include>

    <main>

        <p>Check the password that matches the following email address</p>
        <br>
        <strong><p id="personEmail"><c:out value="${person.email}"/></p></strong>

        <form method="post" action="Controller" novalidate="novalidate">
            <!-- novalidate in order to be able to run tests correctly -->
            <input type="hidden" name="action" value="checkPassword"/>
            <input type="hidden" name="id" value="<c:out value="${person.userid}"/>"/>
            <label for="password">Password</label><input type="password" id="password" name="password" required>
            <br>
            <input type="submit" id="submitCheck" value="Check"/>
        </form>

        <c:if test="${not empty resultMessage}">
            <strong>
                <p>Result</p>
                <p id="resultMessage">${resultMessage}</p>
            </strong>
        </c:if>

    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value="personOverview"/>
    </jsp:include>

</div>
</body>
</html>


