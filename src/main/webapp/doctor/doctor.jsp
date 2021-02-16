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
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand">Hospital</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-link active" aria-current="page" href="/">Main</a>
                    <a class="nav-link " aria-current="page" href="/procedure">Procedure</a>
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
                        <div class="btn-group me-2" role="group" aria-label="Second group">
                            <form action="/logout" method="POST">
                                <button type="submit" class="btn btn-secondary btn-sm">Sign out</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <div class="container-fluid">
        <div class="row">
            <div class="col-1">
            </div>
            <div class="col-6">
                <h3>Your patients</h3>
                <c:forEach items="${treatments}" var="treatment">
                <table class="table">
                    <thead>
                    <tr>
                        <td><b>Patient</b></td>
                        <td><b>Diagnosis</b></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </thead>
                    <tr>
                        <td>${treatment.patient.name}</td>
                        <form action="/diagnosis/${treatment.id}" method="POST">
                            <td><input type="text" name="diagnosis" value=${treatment.diagnosis}></td>
                            <td><button type="submit" class="btn btn-primary">edit</button></td>
                        </form>
                        <td><a href="/addProcedure/${treatment.id}" class="btn btn-primary">add procedure</a></td>
                        <form action="/discharge/${treatment.id}" method="POST">
                            <td><button type="submit" class="btn btn-primary">discharge</button></td>
                        </form>
                    </tr>
                    <tr>
                        <td><b>Procedure name</b></td>
                        <td><b>Type</b></td>
                        <td><b>Status</b></td>
                    </tr>
                    <c:forEach items="${treatment.proceduresList}" var="procedures">
                        <tr>
                            <td>${procedures.procedureName}</td>
                            <td>${procedures.type}</td>
                            <td>${procedures.status}</td>
                        </tr>
                    </c:forEach>
                    </br>
                    </c:forEach>
                </table>
            </div>
            <div class="col-5">
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>