<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <meta name="description" content="">
	    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
	    <meta name="generator" content="Jekyll v3.8.5">
	    <title>Cover Template · Bootstrap</title>
	
	    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/cover/">
	
	    <!-- Bootstrap core CSS -->
		<link href="<spring:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	
		<style>
			.bd-placeholder-img 
			{
				font-size: 1.125rem;
	        	text-anchor: middle;
	        	-webkit-user-select: none;
	        	-moz-user-select: none;
	        	-ms-user-select: none;
	        	user-select: none;
	      	}
	
			@media (min-width: 768px) 
			{
				.bd-placeholder-img-lg 
				{
	          		font-size: 3.5rem;
				}
			}
		</style>
	    <!-- Custom styles for this template -->
	    <link href="<spring:url value='/resources/css/cover.css'/>" rel="stylesheet">
	</head>
	<body class="text-center">
		<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
			<header class="masthead mb-auto">
				<div class="inner">
					<h3 class="masthead-brand">SpringProfileWebSite</h3>
					<nav class="nav nav-masthead justify-content-center">
						<a class="nav-link disable" href="#" style="border-bottom-color: rgba(255, 255, 255, 0);">Welcome <%= request.getAttribute("user_name")==null?request.getAttribute("account_name"):request.getAttribute("user_name")%>!!!</a>
				        <a class="nav-link active" href="profile/edit">Edit</a>
				        <a class="nav-link" href="javascript: document.logoutForm.submit()">Log out</a>
				        
				        <form name="logoutForm" action="<%= request.getContextPath() %>/logout" method="POST" style="display:none;">
				        	<input type="submit" value="logout"/>
				        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				        </form>
					</nav>
			    </div>
			</header>
		
			<main role="main" class="inner cover">
				<div class="container">
				    <div class="row">
				        <div class="col-md-12">
				            <blockquote>
				                <p><%= request.getAttribute("user_surname") %> <%= request.getAttribute("user_name") %> <%= request.getAttribute("user_patronymic") %></p> 
				                <small><cite title="Source Title"><%= request.getAttribute("account_name") %>  <i class="glyphicon glyphicon-map-marker"></i></cite></small>
				            </blockquote>
				            <p> <i class="glyphicon glyphicon-envelope"></i> <%= request.getAttribute("mail") %>
				                <br
				                /> <i class="glyphicon glyphicon-globe"></i> <%= request.getAttribute("role")=="ROLE_ADMIN"?"admin":"HONORARY USER"%></p>
				        </div>
				    </div>
				</div>				
			</main>
		
			<footer class="mastfoot mt-auto">
				<div class="inner">
		      		<p>Cover template for <a href="https://getbootstrap.com/">Bootstrap</a>, by <a href="https://twitter.com/mdo">@mdo</a>.</p>
				</div>
			</footer>
		</div>
	</body>
</html>