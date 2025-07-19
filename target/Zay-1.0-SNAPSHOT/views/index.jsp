<%-- 

    Document   : index
    Created on : 15 juill. 2025, 12 h 20 min 46 s
    Author     : Aurel Noe Kenfack
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
    <body>
        <%@include file="/WEB-INF/jspf/header.jspf" %>
        <jsp:include page="/WEB-INF/sections/hero.jsp" />
        <jsp:include page="/WEB-INF/sections/categories.jsp" />
        <jsp:include page="/WEB-INF/sections/featured.jsp" />

        <%@include file="/WEB-INF/jspf/footer.jspf" %>
        <%@include file="/WEB-INF/jspf/scripts.jspf" %>
    </body>
</html>
