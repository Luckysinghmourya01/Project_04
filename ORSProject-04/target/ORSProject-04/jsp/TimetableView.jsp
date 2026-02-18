<%@page import="in.co.rays.proj4.controller.TimetableCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.bean.Subjectbean"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
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

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TimetableBean"
			scope="request"></jsp:useBean>

		<h1 align="center" style="margin-bottom: -15; color: navy">Add
			Timetable</h1>
		<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">

			<%
				List<CourseBean> courseList = (List<CourseBean>) request.getAttribute("courseList");
				List<Subjectbean> subjectList = (List<Subjectbean>) request.getAttribute("subjectList");
			%>
			
			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>
				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
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
					<th align="left">Course<span style="color: red">*</span></th>
					<td><%=HtmlUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%>
					</td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Subject<span style="color: red">*</span></th>
					<td><%=HtmlUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), subjectList)%>
					</td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Semester<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("1", "1");
							map.put("2", "2");
							map.put("3", "3");
							map.put("4", "4");
							map.put("5", "5");
							map.put("6", "6");
							map.put("7", "7");
							map.put("8", "8");

							String htmlList = HtmlUtility.getList("semester", bean.getSemester(), map);
						%><%=htmlList%>
					</td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("semester", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Exam Date<span style="color: red"> *</span></th>
					<td><input type="text" name="examDate" id="udatee"
						placeholder="Enter Exam Date"
						value="<%=DataUtility.getStringData(bean.getExamDate())%>"></td>
						
						<td style="position: fixed;">
					<font color="red"><%=ServletUtility.getErrorMessage("examDate", request) %></font>
					</td>
				</tr>


				<tr>
					<th align="left">Exam Time<span style="color: red">*</span></th>
					<td>
						<%
							LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
							map1.put("08:00 AM to 11:00 AM", "08:00 AM to 11:00 AM");
							map1.put("12:00 PM to 03:00 PM", "12:00 PM to 03:00 PM");
							map1.put("04:00 PM to 07:00 PM", "04:00 PM to 07:00 PM");

							String htmlList1 = HtmlUtility.getList("examTime", bean.getExamTime(), map1);
						%> <%=htmlList1%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("examTime", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td><textarea style="width: 170px; resize: none;" rows="3"
							name="description" placeholder="Enter Short Description"
							value="<%=DataUtility.getStringData(bean.getDescription())%>"></textarea></td>
							
							<td style="position: fixed;">
					<font color="red"><%=ServletUtility.getErrorMessage("description", request) %></font>
					</td>
				</tr>

				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=TimetableCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TimetableCtl.OP_RESET%>"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>