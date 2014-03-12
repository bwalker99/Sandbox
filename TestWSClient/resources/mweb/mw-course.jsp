<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<title>Course Description</title>
<link rel="stylesheet" type="text/css" href="mobile.css">
</head>
<body>

<h1 id="pageTitle">Langara Course Lookup</h1>
<div id="backbutton"><a href="subj?subj=${course.subject}">< Back to ${course.subject}</a></div>


<div class="subtitle">${course.subject}${course.courseNumber} - ${course.title}</div>
<div class="xtext"><span class="xlabel" >Lecture Hours : </span><span class="xvalue">${course.lectureHours}</span></div>
<div class="xtext"><span class="xlabel" >Credits : </span><span class="xvalue">${course.credits}</span></div>
<div class="xtext"><span class="xlabel" >Prerequisites : </span><span class="xvalue">${course.prerequisites}</div>
<div class="xtext"><span class="xlabel" >co-requisites : </span><span class="xvalue">${course.corequisites}</span></div>
<div class="xtext"><span class="xlabel" >Notes : </span><span class="xvalue">${course.notes}</span></div>






</body>
</html>
