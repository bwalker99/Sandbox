<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<title>Test WS Person Lookup</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>

<jsp:useBean id="personList" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="person" class="ca.ubc.med.ws.demo.service.Person" scope="request" />

<h2>TestWS Person Lookup</h2>
<h3>Search by Identifier</h3>
<p>Please provide identifier for lookup using the Test Web Service.</p>
<form method="GET" action="person">
<table>
<tr><td align="right">cwl:</td>
<td align="left"><input name="cwl" type="text" value="" /></td>
<td align="left"><i>e.g., medinst1, medstu2</i></td></tr>
<tr><td colspan="3"> <input id="submit" type="submit" value="Submit" /></td></tr>
</table>
</form>

<p> Results: Single Person information display here (if requested):</p>

  <table class="box">
    <tr><th>CWL</th><th>Firstname</th><th>Lastname</th></tr>
    <tr><td>${person.cwl}</td><td>${person.firstname}</td><td>${person.lastname}</td></tr>
 
    </table>

<hr />
<h3>Search by last name</h3>

<form method="GET" action="person">
<table>
<tr><td align="right">Lastname:</td><td align="left"><input name="lastname" type="text" value="" /></td></tr>
<tr><td colspan="2"> <input id="submit" type="submit" value="Submit" /></td></tr>
</table>
</form>

<p> Results: List Person information display here (if requested):</p>
  <table class="box">
  <tr><th>CWL</th><th>Firstname</th><th>Lastname</th></tr>
<c:forEach var="per" items="${personList}" >
  <tr><td>${per.cwl}</td><td>${per.firstname}</td><td>${per.lastname}</td></tr>
</c:forEach>
  </table>

<hr/>
</body>
</html>
