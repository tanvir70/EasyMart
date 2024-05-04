<%-- login.jsp --%>
<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <h2 class="h2"> Log In </h2>
    <hr class="m-4">
    <form class="form-horizontal" role="form" action="<c:url value="/login"/>" method="post">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label">Username</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="username" name="username" placeholder="Username" required/>
                <c:if test="${errors.username != null}">
                    <small class="text-danger">${errors.username}</small>
                </c:if>

            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password"
                       required>
                <c:if test="${errors.password != null}">
                    <small class="text-danger">${errors.password}</small>
                </c:if>
            </div>
        </div>
        <hr class="m-4">
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary btn-lg">Log in</button>
            </div>
        </div>
        <span>
            Don't have an account?
            <a href="<c:url value="/signup"/>">
            Signup </a>
        </span>
    </form>
</div>

<%@include file="includes/footer.jsp" %>