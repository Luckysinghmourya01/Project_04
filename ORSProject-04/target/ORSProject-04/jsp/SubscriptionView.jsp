<%@page import="in.co.rays.proj4.controller.SubscriptionCtl"%>
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
	<form action="<%=ORSView.SUBSCRIPTION_CTL%>" , method="post">

		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SubscriptionBean"
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
				Subscription
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



			<table>
				<tr>
					<th align="left">Subscriptin Code<span style="color: red">*</span></th>
					<td><input type="text" name="subscriptionCode"
						placeholder="Enter subscription Code "
						value="<%=DataUtility.getStringData(bean.getSubscriptionCode())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subscriptionCode", request)%></font>
					</td>
				</tr>


				<tr>
					<th align="left">Plan Name<span style="color: red">*</span></th>
					<td><input type="text" name="planName"
						placeholder="Enter Plan name"
						value="<%=DataUtility.getStringData(bean.getPlanName())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("planName", request)%></font>
					</td>
				</tr>
				<tr>
					<th>Start Date<span style="color: red">*</span></th>
					<td><input type="text" name="startDate"
						placeholder="Enter start date"
						value="<%=DataUtility.getStringData(bean.getStartDate())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("startDate", request)%></font>
					</td>
				</tr>

				</tr>

				<tr>
					<th align="left">End Date<span style="color: red">*</span></th>
					<td><input type="password" name="endDate"
						placeholder="Enter End Date"
						value="<%=DataUtility.getStringData(bean.getEndDate())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("endDate", request)%></font>
					</td>
				</tr>




				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Active", "Active");
							map.put("Inactive", "Inactive");

							String htmlList = HtmlUtility.getList("status", bean.getSubscriptionStatus(), map);
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
						value="<%=SubscriptionCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=SubscriptionCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=SubscriptionCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=SubscriptionCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>