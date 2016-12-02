<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="Delete Product"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="productDelete"/>
        <jsp:param name="title" value="Delete Product"/>
    </jsp:include>

    <main>

        <p>Do you want to delete the following product?</p>

        <br>
        <table>
            <tr>
                <th>Id:</th>
                <th><c:out value="${product.id}"/></th>
            </tr>
            <tr>
                <th>Description:</th>
                <th><c:out value="${product.description}"/></th>
            </tr>
            <tr>
                <th>Price:</th>
                <th><c:out value="${product.price }"/></th>
            </tr>
        </table>
        <br>

        <div class="confirmFormContainer">
            <form method="get" action="Controller" novalidate="novalidate">
                <!-- novalidate in order to be able to run tests correctly -->
                <input type="hidden" name="action" value="productDeleteConfirm"/>
                <input type="hidden" name="id" value="<c:out value="${product.id}"/>"/>
                <input type="submit" id="deleteProductConfirm" value="Confirm"/>
            </form>

            <form method="get" action="Controller" novalidate="novalidate">
                <!-- novalidate in order to be able to run tests correctly -->
                <input type="hidden" name="action" value="productOverview"/>
                <input type="submit" id="deleteProductCancel" value="Cancel"/>
            </form>
        </div>

    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value="productOverview"/>
    </jsp:include>

</div>
</body>
</html>


