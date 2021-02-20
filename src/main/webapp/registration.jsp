<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages.messages"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Hospital registration</title>
    <meta charset="UTF-8">

</head>
       <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<style>

    body{
    padding: 20px;
    background: url("https://www.luxinombra.net/wp-content/uploads/2018/06/350829-widescreen-website-background-1920x1080-windows-xp.jpg");
    }

    form {
        width: 300px;
        margin: 0 auto;
        font-family: Arial, sans-serif;
        font-size: 20px;

    }
    form input {
        width: 100%;
        line-height: 40px;
        font-size: 20px;
        padding-left: 15px;
        margin: 9px 0;
    }
    form button {
        width: 85%;
        height: 30px;
        margin-top: 20px;
        text-transform: uppercase;
        font-weight: bold;
    }
</style>
<body>
<h1><fmt:message key="label.registration"/></h1>
     <div class="btn-toolbar position-absolute top-0 end-0" role="toolbar" aria-label="Toolbar with button groups">
        <div class="btn-group me-2" role="group" aria-label="First group">
           <a href="?lang=en" class="btn btn-secondary btn-sm">eng</a>
           <a href="?lang=ru" class="btn btn-secondary btn-sm">ru</a>
        </div>
       </div>

<form action="/registration" method="POST" commandName="patient">
    <fmt:message key="label.login"/> :<br/><input type="text" name="login"><br/>
    <c:if test="${loginInvalid==true}">
        <p><fmt:message key="label.loginInvalid"/></p>
    </c:if>
    <fmt:message key="label.password"/> :<br/><input type="password" name="password"><br/>
    <c:if test="${passwordInvalid==true}">
        <p><fmt:message key="label.passwordInvalid"/></p>
    </c:if>
    <fmt:message key="label.name"/> :<br/><input type="text" name="name"><br/>
    <c:if test="${nameInvalid==true}">
        <p><fmt:message key="label.nameInvalid"/></p>
    </c:if>
    <fmt:message key="label.age"/> :<br/><input required type="number" name="age"><br/>
    <c:if test="${ageInvalid==true}">
        <p><fmt:message key="label.ageInvalid"/></p>
    </c:if>
    <button type="submit"><fmt:message key="label.register"/> </button>
</form>
</body>
</html>