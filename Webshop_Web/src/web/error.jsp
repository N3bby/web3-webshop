<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="Error"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="title" value="Oh dear!"/>
    </jsp:include>

    <main>

        <p><b>It seems the server is experiencing issues.</b></p>
        <br>
        ${pageContext.exception} ${pageContext.exception.message}
        <br>
        <c:forEach var="line" items="${pageContext.exception.stackTrace}">
            &emsp;&emsp;<c:out value="${line}"/><br>
        </c:forEach>

    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value=""/>
    </jsp:include>

</div>
</body>
</html>