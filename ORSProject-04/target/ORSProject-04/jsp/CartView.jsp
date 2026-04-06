<%@page import="in.co.rays.proj4.controller.CartCtl"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="<%=ORSView.CART_CTL%>" , method="post">

		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CartBean"
			scope="request"></jsp:useBean>

		<%-- <%
			List<> roleList = (List<>) request.getAttribute("roleList");
		%> --%>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>Update<%
					} else {
				%>
				Add<%
					}
				%>
				Cart
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
					<th align="left">User Name<span style="color: red">*</span></th>
					<td><input type="text" name="userName"
						placeholder="Enter User name"
						value="<%=DataUtility.getStringData(bean.getUserName())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font>
					</td>
				</tr>


				<tr>
					<th align="left">Cart Code<span style="color: red">*</span></th>
					<td><input type="text" name="cartCode"
						placeholder="Enter Code"
						value="<%=DataUtility.getStringData(bean.getCartCode())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("cartCode", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">Total item<span style="color: red">*</span></th>
					<td><input type="text" name="totalItem"
						placeholder="Enter Item"
						value="<%=DataUtility.getStringData(bean.getTotalItem())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("totalItem", request)%></font>
					</td>
				</tr>

				</tr>
				
				
				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Active", "Active");
							map.put("Inactive", "Inactive");

							String htmlList = HtmlUtility.getList("status", bean.getStatus(), map);
						%> <%=htmlList%>

					</td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("status", request)%></font>
					</td>
				</tr>

				
				<tr>
					<th>
						<%
							if (bean != null && bean.getId() > 0) {
						%>
					
					<td><input type="submit" name="operation"
						value="<%=CartCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=CartCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=CartCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=CartCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>

				

			</table>
		</div>
		<%@ include file="Footer.jsp"%>
	</form>
	
</body>
</html>