<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
		UserBean user = (UserBean) session.getAttribute("user");
	%>

	<%
		if (user != null) {
	%>

	<h3>
		hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>
		)

	</h3>
	<a href="ChangePasswordCtl"><b>Change Password</b></a>
	<b>|</b>
	<a href="MarksheetMeritListCtl">MarksheetMeritList</a>
	<b>|</b>
	<a href="RoleCtl"><b>Add Role</b></a>
	<b>|</b>
	<a href="RoleListCtl"><b>Role List</b></a>
	<b>|</b>
	<a href="UserCtl">Add User</a>
	<b>|</b>
	<a href="UserListCtl">User List</a>
	<b>|</b>
	<a href="CollegeCtl">Add College</a>
	<b>|</b>
	<a href="CollegeListCtl">College List</a>
	<b>|</b>
	<a href="StudentCtl">Add Student</a>
	<b>|</b>
	<a href="StudentListCtl">StudentList</a>
	<b>|</b>
	<a href="MarksheetCtl">Add Marksheet</a>
	<b>|</b>
	<a href="MarksheetListCtl">Marksheet List</a>
	<b>|</b>
	<a href="CourseCtl">Add Course</a>
	<b>|</b>
	<a href="CourseListCtl">Course List</a>
	<b>|</b>
	<a href="SubjectCtl">Add Subject</a>
	<b>|</b>
	<a href="SubjectListCtl">Subject List</a>
	<b>|</b>
	<a href="TimetableCtl">Add Timetable</a>
	<b>|</b>
	<a href="FacultyCtl">Add Faculty</a>
	<b>|</b>
	<a href="FacultyListCtl">Faculty List</a>
	<b>|</b>
	<a href="BannerCtl">Add Banner</a>
	<b>|</b>
	<a href="BannerListCtl">Banner List</a>
	<b>|</b>
	<a href="LoginCtl?operation=<%=LoginCtl.OP_LOG_OUT%>"><b>Logout</b></a>
	<%
		} else {
	%>
	<h3>hi,Guest</h3>
	<a href="WelcomeCtl"><b>Welcome</b></a>
	<b>|</b>
	<a href="LoginCtl"><b>Login</b></a>
	<%
		}
	%>
	<hr>


</body>
</html>