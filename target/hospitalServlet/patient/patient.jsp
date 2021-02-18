<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages.messages"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
           <meta name="viewport" content="width=device-width, initial-scale=1">

            <!-- Bootstrap CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
      <style>
            body{
             padding: 20px;
             background: url("https://www.luxinombra.net/wp-content/uploads/2018/06/350829-widescreen-website-background-1920x1080-windows-xp.jpg");
            }
     </style>
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <div class="col">
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
          <a class="navbar-brand">Hospital</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
              <a class="nav-link active" aria-current="page" href="/patient">Main</a>
            </div>
            <div class="navbar-nav position-absolute top-0 end-0">
             <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
              <div class="btn-group me-2" role="group" aria-label="First group">
                 <form>
                 <a href="?lang=en" class="btn btn-secondary btn-sm">en</a>
                 </form>
                 <form>
                 <a href="?lang=ru" class="btn btn-secondary btn-sm">ru</a>
                 </form>
              </div>
             <div class="btn-group me-2" role="group" aria-label="Second group">
                 <form action="/login" method="POST">
                       <button type="submit" class="btn btn-secondary btn-sm">sign out</button>
                 </form>
             </div>
           </div>
            </div>
          </div>
        </div>
      </nav>
    </div>
  <div class="row">
    <div class="col-3">
    <div class="container-fluid">
    <div class="row">
    <div class="col-4">
           <form action="/appointment" method="POST" class="form-horizontal">
              <select class="form-select form-select-sm" aria-label=".form-select-sm example" name="category" required>
                       <c:forEach items="${categories}" var="category">
                           <option name="category_id" value="${category.id}">${category.name}</option>
                       </c:forEach>
                   </select>
                    <c:if test="${message==true}">
                        <p>already appointment</p>
                    </c:if>
     </div>
     <div class="col-8">
                   <button type="submit">make appointment</button>

           </form>
          </div>
          </div>
          <div class="row">
          <div class="col">
            <c:if test="${treatment.proceduresList.isEmpty()==false}">
            <h3>Treatment:</h3>
                <p><b>Category:</b> ${treatment.doctor.category.name}</p>
                <p><b>Doctor:</b> ${treatment.doctor.name}</p>
                <p><b>Diagnosis:</b> ${treatment.diagnosis}</p>
            </c:if>
          </div>
          </div>
          </div>
    </div>
    <div class="col-6">
        <h3>Your procedures:</h3>
          <c:forEach items="${treatment.proceduresList}" var="procedures">
                         <table class="table">
                              <thead>
                              <tr>
                                  <td><b>Name</b></td>
                                  <td><b>type</b></td>
                                  <td><b>Status</b></td>
                              </tr>
                              </thead>
                                <form action="doProcedures" method="POST">
                                 <tr>
                                 <td>${procedures.name}</td>
                                 <td>${procedures.type}</td>
                                 <td>${procedures.status}</td>
                                 </tr>
                                 </form>
                         </table>
                </c:forEach>
    </div>
    <div class="col-3">
    </div>
  </div>
</div>


</body>
</html>