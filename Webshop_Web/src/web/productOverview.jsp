<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="Product Overview"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="productOverview"/>
        <jsp:param name="title" value="Product Overview"/>
    </jsp:include>

    <main>
        <table>
            <tr>
                <th>Id</th>
                <th>Description</th>
                <th>Price</th>
            </tr>
            <c:forEach var="product" items="${productList}">
                <tr>
                    <th><a href="Controller?action=requestProductUpdate&id=<c:out value="${product.id}"/>"><c:out value="${product.id}"/></a></th>
                    <th><c:out value="${product.description}"/></th>
                    <th><c:out value="${product.price}"/></th>
                    <th>
                        <form class="deleteForm overviewForm" method="get" action="Controller">
                            <input type="hidden" name="action" value="requestProductDelete">
                            <input type="hidden" name="id" value="<c:out value="${product.id}"/>">
                            <input type="submit" class="overviewFormSubmit" value="Delete">
                        </form>
                    </th>
                </tr>
            </c:forEach>
            <caption>Product Overview</caption>
        </table>
    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value="productOverview"/>
    </jsp:include>

</div>
</body>
</html>