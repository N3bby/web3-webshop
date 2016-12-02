<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="Update Product"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="productUpdate"/>
        <jsp:param name="title" value="Update Product"/>
    </jsp:include>

    <main>

        <p>Update properties for product id: ${product.id}</p>

        <c:if test="${not empty errors}">
            <br>
            <div class="alert-danger">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <form method="get" action="Controller" novalidate="novalidate">
            <!-- novalidate in order to be able to run tests correctly -->
            <input type="hidden" name="action" value="productUpdate"/>
            <input type="hidden" name="id" value="<c:out value="${id}"/>"/>
            <p><label for="description">Description</label><input type="text" id="description" name="description"
                                                         required value="<c:out value="${description}"/>"></p>
            <p><label for="price">Price</label><input type="number" id="price" name="price"
                                                               required value="<c:out value="${price}"/>"></p>
            <p><input type="submit" id="updateProduct" value="Update Product"></p>

        </form>
    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value="productOverview"/>
    </jsp:include>

</div>
</body>
</html>


