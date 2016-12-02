<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>${param.title}</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <c:choose>
        <c:when test="${theme eq 'red'}">
            <link rel="stylesheet" type="text/css" href="css/red.css"/>
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" type="text/css" href="css/yellow.css"/>
        </c:otherwise>
    </c:choose>
</head>