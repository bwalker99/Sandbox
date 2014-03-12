<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<title>Banner Subject Lookup</title>
<link rel="stylesheet" type="text/css" href="mobile.css">
<script src="mobile.js" type="application/x-javascript"></script>
</head>
<body>

<jsp:useBean id="subject" class="ca.langara.ws.service.Subject" scope="request" />


<h1 id="pageTitle">Langara Course Lookup.</h1>

 


		
<c:if test="${!empty subject.code}">
<div id="backbutton"><a href="subj">< Back to Subject List</a></div>
<div class="subtitle">${subject.code} - ${subject.description}</div>
	<c:forEach var="ch" items="${courseheaders}" >
	<div class="link"><a href="course?subject=${ch.subject}&number=${ch.courseNumber}">${ch.subject}${ch.courseNumber} - ${ch.title}</a></div>
	</c:forEach>        
       
</c:if>


<c:if test="${empty subject.code}">
	<c:forEach var="subj" items="${subjects}" >
 	<div class="link"><a href="subj?subj=${subj.code}">${subj.code} - ${subj.description}</a></div>
	</c:forEach>	
 </c:if>


 
</body>
</html>
