<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.RoleCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body><%@include file="Header.jsp"%>
	<div align="center">
		<form action="<%=ORSView.ROLE_CTL%>" method="post">
			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.RoleBean"
				scope="request"></jsp:useBean>

			<h1 align="center" style="margin-bottom: -15; color: navy">
			
			<%
			if(bean != null && bean.getId() > 0){
			%>Update<%
			} else {
			%>
			Add<%
			}
			%>
			Role
			</h1>


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
					<th align="left">Name<span style="color: red">*</span></th>
					<td align="center"><input type="text" name="name"
						placeholder="Enter Role Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font>
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
					<th></th>
					<%
					if(bean != null && bean.getId() > 0){
					%>
					
					<td><input type="submit" name="operation" value="<%=RoleCtl.OP_UPDATE%>">
					<input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL%>">
					
					</td>
					<%
					}else{
					%>
					
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=RoleCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=RoleCtl.OP_RESET%>"></td>
						
						<%} %>
				</tr>
			</table>
		</form>
	</div>
</body>

</html>