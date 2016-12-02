<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="Home"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="index"/>
        <jsp:param name="title" value="Home"/>
    </jsp:include>

    <main>

        <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem
        aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo
        enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos
        qui ratione voluptatem sequi nesciunt.</p>

        <br>

        <c:if test="${not empty errors}">
            <div class="alert-danger">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${not empty user}"> <!-- Logged in -->
                <strong><p id="welcomeMessage">Welcome, <c:out value="${user.firstName}"/>!</p></strong>
                <form id="logoutForm" method="post" action="Controller" novalidate="novalidate">
                    <input type="hidden" name="action" value="logout">
                    <input type="submit" id="logout" value="Logout">
                </form>
            </c:when>
            <c:otherwise> <!-- Not logged in -->
                <form method="post" action="Controller" novalidate="novalidate">
                    <input type="hidden" name="action" value="login">
                    <p><label for="userid">User Id</label><input type="text" id="userid" name="userid"></p>
                    <p><label for="password">Password</label><input type="password" id="password" name="password"></p>
                    <p><input type="submit" id="login" value="Login"></p>
                </form>
            </c:otherwise>
        </c:choose>

    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value=""/>
    </jsp:include>

</div>
</body>
</html>