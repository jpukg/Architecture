<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Schedule Manager</title>
<link href="<c:url value="/resources/css/bootstrap.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/app.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/addsection.css" />" rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/app.js" />" /></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/bootstrap.js" />" /></script>
</head>
<body>
	<c:import url="header.jsp" />
	<div class="container">
		<div class="row-fluid">
			<div class="span6">
				<h1>Offre de cours ${year}</h1>
				<c:if test="${not empty courses}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Cours</th>
								<th>Actions</th>
							</tr>
						</thead>
						<c:forEach var="course" items="${courses}">
							<tr id="${course.acronym}">
								<td><b>${course.acronym} - ${course.title}</b></td>
								<c:url value="/schedule/addsection/${id}/${year}/${semester}" var="url"/>
								<td class="centered"><a class="btn btn-success"
									href="${url}?acronym=${course.acronym}"><i
										class="icon-plus-sign icon-white"></i></a></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div class="span6">
				<h1>Sections de cours</h1>
        <c:if test="${not empty sections}">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>Sections</th>
                <th class = "pull-right">Actions&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
              </tr>
            </thead>
            <c:forEach var="section" items="${sections}">
              <tr id="${section.nrc}">
                <td><b>${section.acronym} - ${section.nrc} - ${section.group}</b></td>
                <c:url value="/schedule/editsection/${id}/${section.nrc}" var="url"/>
                <td class="centered"><a class="btn btn-danger pull-right actionbutton"
                  href="${url}"><i
                    class="icon-trash icon-white"></i></a><a class="btn btn-warning pull-right actionbutton"
                  href="${url}"><i
                    class="icon-edit"></i></a></td>
              </tr>
            </c:forEach>
          </table>
        </c:if>
			</div>
		</div>
		<a class="btn btn-success pull-right" href="../${year}">G�n�rer</a>
	</div>
	<c:import url="footer.jsp" />
</body>
</html>
