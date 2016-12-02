<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="Add Product"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="requestProductAdd"/>
        <jsp:param name="title" value="Add Product"/>
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
            <input type="hidden" name="action" value="productAdd"/>
            <p><label for="id">Product Id</label><input type="text" id="id" name="id"
                                                        required value="<c:out value="${old_id}"/>"></p>
            <p><label for="description">Description</label><input type="text" id="description" name="description"
                                                                  required value="<c:out value="${old_description}"/>">
            </p>
            <p><label for="price">Price</label><input type="number" id="price" name="price"
                                                      required value="<c:out value="${old_price}"/>"></p>
            <p><input type="submit" id="addProduct" value="Add Product"></p>

        </form>
    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value="requestProductAdd"/>
    </jsp:include>

</div>
</body>
</html>


