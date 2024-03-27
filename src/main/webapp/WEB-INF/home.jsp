<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap/4.4.1/css/bootstrap.min.css"/>"/>
</head>
<body>

<%@include file="includes/header.jsp"%>

<div class="container">
    <div class="jumbotron">
        <img src="<c:url value="/image/cart.png"/>" style="height: 50px" alt=""/>
        <h1>Welcome to EasyMart! You need shop easy. </h1>
    </div>
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-sm-4">
                <div class="card h-100 mb-4">
                    <div class="card-body">
                        <h5 class="card-title">
                            <c:out value="${product.name}"/>
                        </h5>
                        <p class="card-text">
                            <c:out value="${product.description}"/>
                        </p>
                        <p class="card-text">
                            Price: $
                            <c:out value="${product.price}"/>
                        </p>
                        <a href="#" class="card-link btn btn-outline-info">
                            Add toCart
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
</body>
</html>
