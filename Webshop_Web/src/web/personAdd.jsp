<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="Sign Up"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="signUp"/>
        <jsp:param name="title" value="Sign Up"/>
    </jsp:include>

    <main>
        <c:if test="${not empty errors}">
            <div class="alert-danger">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form method="post" action="Controller" novalidate="novalidate">
            <!-- novalidate in order to be able to run tests correctly -->
            <input type="hidden" name="action" value="personAdd"/>
            <p><label for="userid">User id</label><input type="text" id="userid" name="userid"
                                                         required value="<c:out value="${old_userid}"/>"></p>
            <p><label for="firstName">First Name</label><input type="text" id="firstName" name="firstName"
                                                               required value="<c:out value="${old_firstName}"/>"></p>
            <p><label for="lastName">Last Name</label><input type="text" id="lastName" name="lastName"
                                                             required value="<c:out value="${old_lastName}"/>"></p>
            <p><label for="email">Email</label><input type="email" id="email" name="email" required
                                                      value="<c:out value="${old_email}"/>"></p>
            <p><label for="password">Password</label><input type="password" id="password" name="password"
                                                            required></p>
            <p><input type="submit" id="signUp" value="Sign Up"></p>

        </form>
    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value="requestPersonAdd"/>
    </jsp:include>

</div>
</body>
</html>


