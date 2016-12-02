<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="fragment/head.jsp">
    <jsp:param name="title" value="Delete Person"/>
</jsp:include>
<body>
<div id="container">

    <jsp:include page="fragment/header.jsp">
        <jsp:param name="page" value="personDelete"/>
        <jsp:param name="title" value="Delete Person"/>
    </jsp:include>

    <main>

        <p>Do you want to delete the following person?</p>

        <br>
        <table>
            <tr>
                <th>Email:</th>
                <th><c:out value="${person.email}"/></th>
            </tr>
            <tr>
                <th>First name:</th>
                <th><c:out value="${person.firstName}"/></th>
            </tr>
            <tr>
                <th>Last name:</th>
                <th><c:out value="${person.lastName}"/></th>
            </tr>
        </table>
        <br>

        <div class="confirmFormContainer">
            <form method="get" action="Controller" novalidate="novalidate">
                <!-- novalidate in order to be able to run tests correctly -->
                <input type="hidden" name="action" value="personDeleteConfirm"/>
                <input type="hidden" name="id" value="<c:out value="${person.userid}"/>"/>
                <input type="submit" id="deletePersonConfirm" value="Confirm"/>
            </form>

            <form method="get" action="Controller" novalidate="novalidate">
                <!-- novalidate in order to be able to run tests correctly -->
                <input type="hidden" name="action" value="personOverview"/>
                <input type="submit" id="deletePersonCancel" value="Cancel"/>
            </form>
        </div>

    </main>

    <jsp:include page="fragment/footer.jsp">
        <jsp:param name="page" value="personOverview"/>
    </jsp:include>

</div>
</body>
</html>


