
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>AutoJSP</title>
</head>
<body onload="document.form1.submit()">
<jsp:useBean id="nameVar" class="java.lang.String" scope="request" />


<h3>AutoSubmit jsp</h3>
<% System.out.println("auto.jsp"); %>
We shouldn't see this page. Name=${nameVar}<br/>
<form action="test4" method="post" name="form1">
<input type="hidden" name="name" value="${nameVar}"/><br/> 
</form>

</body>
</html>
