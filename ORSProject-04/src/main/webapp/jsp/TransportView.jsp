<%@page import="in.co.rays.proj4.controller.TransportCtl"%>
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

<form action="<%=ORSView.TRANSPORT_CTL%>" , method="post">

		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TransportBean"
			scope="request"></jsp:useBean>

		

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
				Transport
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
				value="<%=bean.getModifiedby()%>"> 
			<table>
				<tr>
					<th align="left">Transport Id<span style="color: red">*</span></th>
					<td><input type="text" name="transportId"
						placeholder="Enter transport Id"
						value="<%=DataUtility.getStringData(bean.getTransportId())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("transportId", request)%></font>
					</td>
				</tr>


				<tr>
					<th align="left">Vehicle No<span style="color: red">*</span></th>
					<td><input type="text" name="vehicleNo"
						placeholder="Enter Vehicle No"
						value="<%=DataUtility.getStringData(bean.getVehicleNo())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("vehicleNo", request)%></font>
					</td>
				</tr>
				<tr>
					<th>Driver Name<span style="color: red">*</span></th>
					<td><input type="text" name="driverName"
						placeholder="Enter Name"
						value="<%=DataUtility.getStringData(bean.getDriverName())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("driverName", request)%></font>
					</td>
				</tr>

				</tr>

				<tr>
					<th align="left">Vehicle Type<span style="color: red">*</span></th>
					<td><input type="text" name="vehicleType"
						placeholder="Enter Password"
						value="<%=DataUtility.getStringData(bean.getVehicleType())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("vehicleType", request)%></font>
					</td>
				</tr>


				

				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Active", "Active");
							map.put("Inactive", "Inactive");

							String htmlList = HtmlUtility.getList("status", bean.getTransportStatus(), map);
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
						value="<%=TransportCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=TransportCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=TransportCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TransportCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>