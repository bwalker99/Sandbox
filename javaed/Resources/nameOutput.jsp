
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %> 
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<TITLE>Name Output</TITLE>
</HEAD>
<BODY style="font-family: Verdana, Arial, Sans Serif;">
<jsp:useBean id="nameVar" class="java.lang.String" scope="request" />

<%@ include file="_header.jspf" %>

<h3>Hello Servlet 4</h3>
<i>Nice output from a jsp page...</i><br/>
<i>Hello ${nameVar}</i><br/>

</BODY>
</HTML>