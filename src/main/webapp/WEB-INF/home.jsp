<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://tanvir.com/functions" %>

<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <div class="jumbotron">
        <c:if test="${sec:isAuthenticated(pageContext.request)}">
        <h1>
            Hello, <c:out value="${sec:getCurrentUser(pageContext.request).firstName}"/> Welcome to EasyMart!
        </h1>
        </c:if>
        <img src="<c:url value="/image/cart.png"/>" style="height: 50px" alt=""/>

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
                                Price: $ <c:out value="${product.price}"/>
                            </p>
                            <a href="#"
                               class="card-link btn btn-outline-info"
                               onclick="addToCart(${product.id})">
                                Add to Cart
                            </a>
                            <form style="visibility: hidden"
                                  id="addToCart_${product.id}"
                                  method="post"
                                  action="<c:url
                                  value="/add-to-cart?productId=${product.id}"/>">
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <script>
            function addToCart(productId) {
                let form = document.getElementById('addToCart_' + productId);
                form.submit();
            }
        </script>

        <%@include file="includes/footer.jsp" %>
