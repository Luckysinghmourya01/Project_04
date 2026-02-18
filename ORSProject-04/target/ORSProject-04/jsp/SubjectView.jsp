<%@page import="in.co.rays.proj4.controller.CollegeCtl"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
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
				List<CourseBean> courseList = (List<CourseBean>) request.getAttribute("courseList");
			%>
			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.Subjectbean"
				scope="request"></jsp:useBean>

			<h1 align="center" style="margin-bottom: -15; color: navy">
			
			<%
			if(bean != null && bean.getId()> 0){
			%>Update
			<%} else { %>
			Add
			<%} %>
			Subject
			</h1>
				

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
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
					<th align="left">Subject<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Subject Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Course<span style="color: red">*</span></th>
					<td><%=HtmlUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%>
					</font></td>
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
					<%
					if(bean != null && bean.getId() > 0){
					%>
					<td><input type="submit" name="operation" value="<%=SubjectCtl.OP_UPDATE%>">
					<input type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL%>">
					</td>
					<%} else{ %>
					
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=SubjectCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=SubjectCtl.OP_RESET%>"></td>
						<%} %>
					</th>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>