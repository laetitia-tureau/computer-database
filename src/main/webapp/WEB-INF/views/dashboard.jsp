<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="txt.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
<link href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.5.0/css/flag-icon.min.css" rel="stylesheet"/>
</head>
<body> 
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/computer/list"> <fmt:message key="txt.home"/> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
             ${pagination.totalItems} <fmt:message key="txt.computersFound"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="${pageContext.request.contextPath}/computer/list" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" value="${criteria.itemName}" class="form-control" placeholder="<fmt:message key="txt.searchName"/>" />
                        <input type="submit" id="searchsubmit" value="<fmt:message key="txt.filterName"/>"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="${pageContext.request.contextPath}/computer/edit"><fmt:message key="txt.addComputer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message key="txt.editComputer"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="${pageContext.request.contextPath}/computer/delete" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/computer/list${not empty criteria.itemName ? '?search='.concat(criteria.itemName).concat('&') : '?'}sort=computer.name&order=${not empty criteria.order && criteria.order == 'ASC' ? 'DESC' : 'ASC'}">Computer name <i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <th>
                            <a href="${pageContext.request.contextPath}/computer/list${not empty criteria.itemName ? '?search='.concat(criteria.itemName).concat('&') : '?'}sort=computer.introduced&order=${not empty criteria.order && criteria.order == 'ASC' ? 'DESC' : 'ASC'}">Introduced date <i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <a href="${pageContext.request.contextPath}/computer/list${not empty criteria.itemName ? '?search='.concat(criteria.itemName).concat('&') : '?'}sort=computer.discontinued&order=${not empty criteria.order && criteria.order == 'ASC' ? 'DESC' : 'ASC'}">Discontinued date <i class="fa fa-fw fa-sort"></i></a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <a href="${pageContext.request.contextPath}/computer/list${not empty criteria.itemName ? '?search='.concat(criteria.itemName).concat('&') : '?'}sort=company.name&order=${not empty criteria.order && criteria.order == 'ASC' ? 'DESC' : 'ASC'}">Company name <i class="fa fa-fw fa-sort"></i></a>
                            
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    <c:forEach var="computer" items="${computerList}">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/computer/edit?id=${computer.id}" onclick="">${computer.name }</a>
                        </td>
                        <td>${computer.introduced}</td>
                        <td>${computer.discontinued}</td>
                        <td>${computer.manufacturer}</td>

                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <div class="btn-group btn-group-sm pull-left" role="group">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/computer/list?lang=en"><span class="flag-icon flag-icon-gb"></a> 
                <a class="dropdown-item" href="${pageContext.request.contextPath}/computer/list?lang=fr"><span class="flag-icon flag-icon-fr"></a>
            </div>
            <ul class="pagination">
                <c:set var="firstPage" value="${1}"/>
                <c:set var="lastPage" value="${5}"/>
                <c:if test="${pagination.currentPage > 1}">
                <c:set var="firstPage" value="${pagination.currentPage - 2}"/>
                <c:set var="lastPage" value="${pagination.currentPage + 2}"/>
                <li class="${pagination.currentPage == indexPage ? 'active' : '' }" >
                    <a href="${not empty url ? url : '?'}currentPage=${pagination.currentPage - 1}&itemsPerPage=${pagination.itemsPerPage}" aria-label="<fmt:message key="txt.previous"/>">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                </c:if>
                
                <c:forEach var="indexPage" begin="${firstPage}" end="${lastPage}">
	                <c:if test="${indexPage > 0 && indexPage <= pagination.totalOfPages}">
	                   <li class="${pagination.currentPage == indexPage ? 'active' : '' }">
	                       <a href="${not empty url ? url : '?'}currentPage=${indexPage}&itemsPerPage=${pagination.itemsPerPage}">${indexPage}</a>
	                   </li>
	                </c:if>
			    </c:forEach>
                <c:if test="${pagination.currentPage < pagination.totalOfPages}">
                <li class="${pagination.currentPage == indexPage ? 'active' : '' }">
                
                    <a href="${not empty url ? url : '?'}currentPage=${pagination.currentPage + 1}&itemsPerPage=${pagination.itemsPerPage}" aria-label="<fmt:message key="txt.next"/>">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                </c:if>
            </ul>
       
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	            <a href="${not empty url ? url : '?'}currentPage=${pagination.currentPage > maxTotalOfPages[0] ? maxTotalOfPages[0] : pagination.currentPage}&itemsPerPage=10" class="btn btn-default ${pagination.itemsPerPage == 10 ? 'active' : '' }">10</a>
	            <a href="${not empty url ? url : '?'}currentPage=${pagination.currentPage > maxTotalOfPages[1] ? maxTotalOfPages[1] : pagination.currentPage}&itemsPerPage=50" class="btn btn-default ${pagination.itemsPerPage == 50 ? 'active' : '' }">50</a>
	            <a href="${not empty url ? url : '?'}currentPage=${pagination.currentPage > maxTotalOfPages[2] ? maxTotalOfPages[2] : pagination.currentPage}&itemsPerPage=100" class="btn btn-default ${pagination.itemsPerPage == 100 ? 'active' : '' }">100</a>
	        </div>
        </div>

    </footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>