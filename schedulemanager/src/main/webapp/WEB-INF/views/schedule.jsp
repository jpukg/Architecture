<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Schedule Manager</title>
<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/app.css" />" rel="stylesheet">
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container">
		<div class="hero-unit">
			<h1>Visualiser un horaire</h1>
			<p>Choisissez l'ann�e � visualiser :</p>
		</div>
		<c:if test="${not empty years}">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Ann�e</th>
						<th></th>
					</tr>
				</thead>
				<c:forEach var="year" items="${years}">
					<tr id="${year}">
						<td><b>${year}</b></td>
						<td class="centered">
							<a class="btn" href="schedule/${year}"><i class="icon-search"></i></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>
