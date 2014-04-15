
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %> 
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<TITLE>MVC Sample : List of Autos</TITLE>
<link rel="stylesheet" type="text/css" href="styles.css"> 


</style>
</HEAD>
<BODY>
<jsp:useBean id="ItemList" class="ca.ubc.med.mvc.ItemList" scope="session" />
<jsp:useBean id="Info" class="ca.ubc.med.mvc.Info" scope="request" />

<jsp:setProperty name="ItemList" property="*"/>
<h3>MVC Sample Application - Used Autos</h3>
<table border ="1" cellpadding="5">
     <tr>
     <td>ID</td>
     <td>Make</td>
     <td>Model</td>
     <td>Colour</td>
     <td>Cost</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
  </tr>
   
   <c:forEach var="plist" items="${ItemList.list}" >
     <tr>
     <td><a href="controller?action=edit&id=${plist.id}">${plist.id}</td>
	 <td>${plist.make}</td>
     <td>${plist.model}</td>
     <td>${plist.colour}</td>
     <td>${plist.formattedCost}</td>
     <td><a href="controller?action=edit&id=${plist.id}">Edit</td>
     <td><a href="controller?action=delete&id=${plist.id}">Delete</td>
  </tr>
  </c:forEach>
</table>
 <br/><font color="red">${Info.message}</font>
<br/><a href="controller?action=edit&id=-1">New Auto</a>

<br/><a href="login?action=logout">Logout</a>

</BODY>
</HTML>