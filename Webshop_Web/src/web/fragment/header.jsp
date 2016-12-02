<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <h1><span>Web shop</span></h1>
    <nav>
        <ul>
            <li <c:if test="${param.page eq 'index'}">class="actual"</c:if>><a href="Controller">Home</a></li>
            <li <c:if test="${param.page eq 'personOverview'}">class="actual"</c:if>><a href="Controller?action=personOverview">Users</a></li>
            <li <c:if test="${param.page eq 'productOverview'}">class="actual"</c:if>><a href="Controller?action=productOverview">Products</a></li>
            <li <c:if test="${param.page eq 'requestProductAdd'}">class="actual"</c:if>><a href="Controller?action=requestProductAdd">Add Product</a></li>
            <li <c:if test="${param.page eq 'signUp'}">class="actual"</c:if>><a href="Controller?action=requestPersonAdd">Sign up</a></li>
        </ul>
    </nav>
    <h2>${param.title}</h2>
</header>
