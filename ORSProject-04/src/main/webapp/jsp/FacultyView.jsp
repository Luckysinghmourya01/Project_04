<%@page import="in.co.rays.proj4.controller.FacultyCtl"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="in.co.rays.proj4.bean.Subjectbean"%>
<%@page import="in.co.rays.proj4.bean.CollageBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<div align="center">
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.FacultyBean"
			scope="request"></jsp:useBean>

		<h1 align="center"
		 style="margin-bottom: -15; color: navy">Add Faculty</h1>

		<form action="<%=ORSView.FACULTY_CTL%>" method="post">

			<%
				List<CollageBean> collegeList = (List<CollageBean>) request.getAttribute("collegeList");
				List<Subjectbean> subjectList = (List<Subjectbean>) request.getAttribute("subjectList");
				List<CourseBean> courseList = (List<CourseBean>) request.getAttribute("courseList");
			%>

			<table>
			
				<tr>
					<th align="left">First Name<span style="color: red">*</span></th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Last Name<span style="color: red">*</span></th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Email Id<span style="color: red">*</span></th>
					<td><input type="text" name="email"
						placeholder="Enter Email ID"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Date of Birth<span style="color: red">*</span></th>
					<td><input type="text" id="udate" name="dob"
						placeholder="Select Date of Birth"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Gender<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HtmlUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Mobile No<span style="color: red">*</span></th>
					<td><input type="text" name="mobileNo" maxlength="10"
						placeholder="Enter Mobile No."
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th align="left">College<span style="color: red">*</span></th>
					<td><%=HtmlUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), collegeList)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Course<span style="color: red">*</span></th>
					<td><%=HtmlUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Subject<span style="color: red">*</span></th>
					<td><%=HtmlUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), subjectList)%></td>
					
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("subjectId", request)%></font></td>S
				</tr>
				
				<tr>
				<th></th>
				<td><input type="submit" name="operation" value="<%=FacultyCtl.OP_SAVE%>">
				<input type="submit" name="operation" value="<%=FacultyCtl.OP_RESET%>">
				</td>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>