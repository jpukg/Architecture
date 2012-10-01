<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Schedule Manager</title>
<link href="resources/css/bootstrap.css" rel="stylesheet">
<link href="resources/css/app.css" rel="stylesheet">
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container">
		<div class="row-fluid">
			<div class="span12">
				<h1>Offre de cours ${year}</h1>
				<c:choose>
					<c:when test="${error == true}">
						<div class="alert alert-error">
							<button type="button" class="close" data-dismiss="alert">�</button>
							<strong>Erreur!</strong> Une erreur est survenue, veuillez
							r�essayer.
						</div>
					</c:when>
					<c:when test="${error == false}">
						<div class="alert alert-success">
							<button type="button" class="close" data-dismiss="alert">�</button>
							<strong>Succ�s!</strong>La requ�te s'est effectu�e avec succ�s.
						</div>
					</c:when>
				</c:choose>
				<c:if test="${not empty courses}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Titre</th>
								<th>Credits</th>
								<th>Cycle</th>
								<th>Actions</th>
							</tr>
						</thead>
						<c:forEach var="course" items="${courses}">
							<tr id="${course.acronym}">
								<td><b>${course.acronym} - ${course.title}</b></td>
								<td>${course.credits}</td>
								<td>${course.cycle}</td>
								<td class="centered"><a class="btn btn-danger"
									href="deletecourse?year=${year}&acronym=${course.acronym}"><i
										class="icon-trash icon-white"></i></a></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12 well">
				<h3>Ajouter un cours � cette offre</h3>
				<a href="availablecourses?year=${year}" class="btn btn-success">Ajouter
					un cours &raquo;</a>
			</div>
		</div>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>