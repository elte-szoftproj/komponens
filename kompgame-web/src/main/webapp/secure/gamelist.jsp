<%-- 
    Document   : gamelist
    Created on : 2014.05.07., 16:50:07
    Author     : Zsolt
--%>

<%@page import="java.security.Principal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World (GameList)!</h1>
        <%
            Principal p = request.getUserPrincipal();
            out.write("<div>Principal.getName()                 = "+p.getName()+"</div>");
            out.write("<div>request.isUserInRole(users) = "+request.isUserInRole("users"));
            %>
    </body>
</html>
