<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages.messages"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Add Doctor</title>
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
    <a class="navbar-brand">Hospital</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a class="nav-link" aria-current="page" href="/">Main</a>
        <a class="nav-link active" href="/addDoctor">Add Doctor</a>
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


<div class="row">
<div class="col-5">
</div>
<div class="col-2">
<form action="/addDoctor" method="POST">
    <fmt:message key="label.login"/> :<br/><input type="text" name="login"><br/>
             <c:if test="${loginNotBlank==true}">
                  <p></p>
             </c:if>
             <c:if test="${loginLength==true}">
                  <p></p>
             </c:if>
    <fmt:message key="label.password"/> :<br/><input type="password" name="password"><br/>
             <c:if test="${passwordNotBlank==true}">
                  <p></p>
             </c:if>
    <fmt:message key="label.name"/> :<br/><input type="text" name="name"><br/>
             <c:if test="${nameNotBlank==true}">
                  <p></p>
             </c:if>
             <c:if test="${nameLength==true}">
                  <p></p>
             </c:if>
        <select class="selectpicker form-control form-select-button" name="category" required>
            <c:forEach items="${categories}" var="category">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>
    <button type="submit"><fmt:message key="label.register"/></button>
           <c:if test="${message==true}">
                 <p></p>
           </c:if>
</form>
</div>
<div class="col-5">
</div>
</div>
</div>
</body>
</html>