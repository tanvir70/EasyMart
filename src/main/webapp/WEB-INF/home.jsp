<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EasyMart </title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap/4.4.1/css/bootstrap.min.css"/>"/>
</head>
<body style="padding-top: 70px">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="<c:url value="/"/>">EasyMart</a>
        <button class="navbar-toggler" type="button"
                data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive"
                aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse"
             id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link"
                       href="<c:url value="/"/>">Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="jumbotron">
        <h1>Welcome to EasyMart! </h1>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <c:out value="${product.name}"/>
                </td>
                <td>
                    <c:out value="${product.description}"/>
                </td>
                <td>
                    <c:out value="${product.price}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<footer class="footer mt-auto py-3 fixed-bottom">
    <div class="container">
 <span class="text-muted">
 Copyright &copy; EasyMart.com 2024
 </span>
    </div>
</footer>
</body>
</html>