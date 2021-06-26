<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="txt.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body> 
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <!-- a class="navbar-brand" href="${pageContext.request.contextPath}/computer/list"> <fmt:message key="txt.home"/> </a-->
            <div class="navbar-header">
            	<a class="navbar-brand" href="${pageContext.request.contextPath}/computer/list"> <fmt:message key="txt.home"/> </a>
            </div>
            <div class="collapse navbar-collapse">
            	<ul class="nav navbar-nav navbar-right">
            		<li class="active"><a href="#">Welcome [[${username}]]</a></li>
            		<li class="active"><a href="${pageContext.request.contextPath}/logout"><fmt:message key="txt.logout" /></a></li>
            	</ul>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                   
                    <c:choose>
					  <c:when test="${not empty computer.id }">
					    <div class="label label-default pull-right">
	                        id: ${computer.id}
	                    </div>
	                    <h1><fmt:message key="txt.editComputer"/></h1>
					  </c:when>
					  <c:otherwise>
					    <h1><fmt:message key="txt.addComputer"/></h1>
					  </c:otherwise>
                    </c:choose>
                    <c:if test="${not empty success}">
                    <div class="alert alert-success">
					  <strong>Success!</strong> 
					  ${success}
					</div>
					</c:if>
					<c:if test="${not empty error}">
					<div class="alert alert-danger">
					  <strong>Error!</strong>
					  ${error}
					</div>
					</c:if>
                    <form:form action="${pageContext.request.contextPath}/computer/edit${not empty computer.id ? '?id='.concat(computer.id) : ''}" method="POST" id="addComputer" modelAttribute="computer">
                        <fieldset>
                            <div class="form-group">
                                <form:label class="control-label" path="name" for="computerName"><fmt:message key="label.computerName"/></form:label>
                                <form:input type="text" class="form-control" id="computerName" path="name" name="name" placeholder="Computer name" value="${not empty computer ? computer.name : ''}" />
                                <span class="help-block hidden"><fmt:message key="error.name.required"/></span>
                            </div>
                            <div class="form-group">
                                <form:label class="control-label" path="introduced" for="introduced"><fmt:message key="label.introduced"/></form:label>
                                <form:input type="date" class="form-control" id="introduced" path="introduced" name="introduced" placeholder="Introduced date" value="${not empty computer.introduced ? computer.introduced : ''}" />
                                <span class="help-block hidden"><fmt:message key="error.introduced.invalid"/></span>
                            </div>
                            <div class="form-group">
                                <form:label class="control-label" path="discontinued" for="discontinued"><fmt:message key="label.discontinued"/></form:label>
                                <form:input type="date" class="form-control" id="discontinued" path="discontinued" name="discontinued" placeholder="Discontinued date" value="${not empty computer.discontinued ? computer.discontinued : ''}" />
                                <span class="help-block hidden"><fmt:message key="error.discontinued.invalid"/></span>
                            </div>
                            <div class="form-group">
                                <form:label path="manufacturer" for="companyId"><fmt:message key="label.company"/></form:label>
                                <form:select class="form-control" id="companyId" path="manufacturer" name="companyId">
	                                <c:choose>
				                      <c:when test="${not empty computer && not empty computer.manufacturer}">
				                        <option value="${computer.manufacturer}">${computer.companyName}</option>
				                      </c:when>
				                      <c:otherwise>
				                        <option value="">--</option>
				                      </c:otherwise>
				                    </c:choose>
                                    <c:forEach var="company" items="${companyList}">
                                        <option value="${company.id}">${company.name}</option>
                                    </c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <c:if test="${ empty computer.id }">
                                <input type="submit" value="<fmt:message key="txt.add"/>" class="btn btn-primary disabled" id="submitBtn">
                            </c:if>
                            <c:if test="${ not empty computer.id }">
                                <input type="submit" value="<fmt:message key="txt.edit"/>" class="btn btn-primary disabled" id="submitBtn">
                            </c:if>
                            
                            <fmt:message key="txt.or"/>
                            <a href="${pageContext.request.contextPath}/computer/list" class="btn btn-default"><fmt:message key="txt.cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/editComputerForm.js"></script>
</html>