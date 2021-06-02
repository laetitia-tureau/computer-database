<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/computer/list"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <c:if test="${not empty success}">
                    <div class="alert alert-success">
					  <strong>Success!</strong> 
					  <c:out value="${success}" />
					</div>
					</c:if>
					<c:if test="${not empty error}">
					<div class="alert alert-danger">
					  <strong>Error!</strong>
					  <c:out value="${error}" />
					</div>
					</c:if>
                    <form action="${pageContext.request.contextPath}/computer/add" method="POST" id="addComputer">
                        <fieldset>
                            <div class="form-group">
                                <label class="control-label" for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name">
                                <span class="help-block hidden">Computer name must not be empty</span>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date">
                                <span class="help-block hidden">Introduced date must not be empty when discontinued date exist !</span>
                            </div>
                            <div class="form-group">
                                <label class="control-label" for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date">
                                <span class="help-block hidden">Discontinued date must be greater than introduced date !</span>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="">--</option>
                                    <c:forEach var="company" items="${companyList}">
                                        <option value="${company.id}">${company.name}</option>
                                    </c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary disabled" id="submitBtn">
                            or
                            <a href="${pageContext.request.contextPath}/computer/list" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/addComputerForm.js"></script>
</html>