<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages.messages"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="${sessionScope.lang}">
<head>
    <title>Add Procedure</title>
    <meta charset="UTF-8">
               <meta name="viewport" content="width=device-width, initial-scale=1">

                <!-- Bootstrap CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<style>
    body{
         padding: 20px;
          background: url("https://www.luxinombra.net/wp-content/uploads/2018/06/350829-widescreen-website-background-1920x1080-windows-xp.jpg");
    }

        form input {
            width: 100%;
            line-height: 40px;
            font-size: 20px;
            padding-left: 15px;
            margin: 9px 0;
        }

        form button {
            width: 70%;
            height: 30px;
            margin-top: 20px;
            text-transform: uppercase;
            font-weight: bold;
        }
</style>
<body>

<div class="container-fluid">
<div class="raw">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand"><fmt:message key="label.hospital"/></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a class="nav-link" aria-current="page" href="/doctor/doctor"><fmt:message key="label.main"/></a>
        <a class="nav-link" href="/doctor/procedure"><fmt:message key="label.procedures"/></a>
      </div>
      <div class="navbar-nav position-absolute top-0 end-0">
       <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
        <div class="btn-group me-2" role="group" aria-label="First group">
           <form>
           <a href="?lang=en" class="btn btn-secondary btn-sm">eng</a>
           </form>
           <form>
           <a href="?lang=ru" class="btn btn-secondary btn-sm">ru</a>
           </form>
        </div>
     </div>
      </div>
    </div>
  </div>
</nav>
</div>

<div class="container-fluid">
<div class="row">
<div class="col-5">
</div>
<div class="col-2">
<form action="/doctor/addProcedure/${id}" method="POST">
    <fmt:message key="label.nameOfProcedure"/>:<br/><input type="text" name="procedureName"><br/>
        <select class="selectpicker form-control form-select-button" name="type" required>
            <c:forEach items="${types}" var="type">
                <option value="${type}">${type}</option>
            </c:forEach>
        </select>
        <select class="selectpicker form-control form-select-button" name="doctor" required>
            <c:forEach items="${doctors}" var="doctor">
                    <option value="${doctor.account.id}">${doctor.name}</option>
            </c:forEach>
        </select>
    <button type="submit"> <fmt:message key="label.add"/></button>
    <c:if test="${sisterError==true}">
        <p><fmt:message key="label.sisterError"/></p>
    </c:if>
</form>
</div>
<div class="col-5">
</div>
</div>
</div>
</body>
</html>