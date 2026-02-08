
<%@page import="in.co.rays.proj4.controller.CourseCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="java.util.HashMap"%>
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

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CourseBean"
			scope="request"></jsp:useBean>
		<h1 style="margin-bottom: -15; color: navy">Add Course</h1>
		<form action="<%=ORSView.COURSE_CTL%>" method="post">
		
		
		<div style="height: 15px; margin-bottom: 12px">
			<h3 align="center">
			<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
			</h3>
			
			<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div>
		
		
		<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedby()%>"> <input type="hidden"
				name="createddatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">

			<table>
				<tr>
					<th align="left">Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Course Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
						
						<td style="position: fixed;">
						<font color="red"><%=ServletUtility.getErrorMessage("name", request) %></font>
						</td>
				</tr>
				<tr>
					<th align="left">Duration<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("1 Year", "1 Year");
							map.put("2 Year", "2 Year");
							map.put("3 Year", "3 Year");
							map.put("4 Year", "4 Year");
							map.put("5 Year", "5 Year");
							map.put("6 Year", "6 Year");
							map.put("7 Year", "7 Year");

							String htmlList = HtmlUtility.getList("duration", bean.getDuration(), map);
						%> <%=htmlList%>
						
					</td>
					<td style="position: fixed;">
						<font color="red"><%=ServletUtility.getErrorMessage("duration", request) %></font>
						</td>
				</tr>

				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td align="center"><textarea
							style="width: 170px; resize: none;" name="description" rows="3"
							placeholder="Enter Short description"><%=DataUtility.getStringData(bean.getDescription()).trim()%></textarea>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%>
					</font></td>
				</tr>

				<tr>
					<th>
					<td align="left" colspan="2"><input type="submit" name="operation"
						value="<%=CourseCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=CourseCtl.OP_RESET%>"></td>
					</th>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>