<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.SubjectCtl"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="in.co.rays.proj4.bean.Subjectbean"%>
<%@page import="java.util.List"%>
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

<form action="<%=ORSView.SUBJECT_CTL%>" method="post">
		<%
			List<Subjectbean> courseList = (List<Subjectbean>) request.getAttribute("courseList");
		%>
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.Subjectbean"
			scope="request"></jsp:useBean>

		<h1 align="center" style="margin-bottom: -15; color: navy">Add
			Subject</h1>

		<table>
			<tr>
				<th align="left">Name<span style="color: red">*</span></th>
				<td><input type="text" name="name"
					placeholder="Enter Subject Name"
					value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					
					<td style="position: fixed;">
						<font color="red"><%=ServletUtility.getErrorMessage("name", request) %></font>
						</td>
			</tr>

			<tr>
				<th align="left">Course<span style="color: red">*</span></th>
				<td><%=HtmlUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%>
				</td>
				
				<td style="position: fixed;">
						<font color="red"><%=ServletUtility.getErrorMessage("courseId", request) %></font>
						</td>
			</tr>

			<tr>
				<th align="left">Description<span style="color: red">*</span></th>
				<td><textarea style="width: 170px; resize: none;"
						name="description" row="3" placeholder="Enter Short Description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></textarea></td>
						
						<td style="position: fixed;">
						<font color="red"><%=ServletUtility.getErrorMessage("description", request) %></font>
						</td>
			</tr>
			<th></th>
			 <td align="left" colspan="2">
                            <input type="submit" name="operation" value="<%=SubjectCtl.OP_SAVE%>">
                            <input type="submit" name="operation" value="<%=SubjectCtl.OP_RESET%>">
                        </td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>