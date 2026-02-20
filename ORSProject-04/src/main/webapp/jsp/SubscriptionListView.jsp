<%@page import="in.co.rays.proj4.controller.SubscriptionCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.SubscriptionBean"%>
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
	<%@ include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SubscriptionBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15px; color: navy;">Subscription
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<form action="<%=ORSView.SUBSCRIPTION_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getpageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<SubscriptionBean> roleList = (List<SubscriptionBean>) request.getAttribute("roleList");
				List<SubscriptionBean> list = (List<SubscriptionBean>) ServletUtility.getList(request);

				Iterator<SubscriptionBean> it = list.iterator();
				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Subscription Code :</b></label> <input
						type="text" name="subscriptionCode"
						placeholder="Enter subscription code"
						value="<%=ServletUtility.getParameter("subscriptionCode", request)%>">&emsp;

						<label align="center"><b>Plan Name :</b></label><input type="text"
						name="planName" placeholder="Enter Last Name"
						value="<%=ServletUtility.getParameter("planName", request)%>">




						<input type="submit" name="operation"
						value="<%=SubscriptionCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=SubscriptionCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="13%">Subscription Code</th>
					<th width="23%">Plan Name</th>
					<th width="23%">Start Date</th>
					<th width="10%">End Date</th>
					<th width="15%">Status</th>
					<th width="10%">Edit</th>

				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getSubscriptionCode()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getPlanName()%></td>
					<td style="text-align: center; text-transform: lowercase;"><%=bean.getStartDate()%></td>
					<td style="text-align: center;"><%=bean.getEndDate()%></td>
					<td style="text-align: center;"><%=bean.getSubscriptionStatus() %></td>
					<td style="text-align: center;"><a
						href="SubscriptionCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
					}
				%>

				<table style="width: 100%">
					<tr>
						<td style="width: 25%"><input type="submit" name="operation"
							value="<%=SubscriptionCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>></td>

						<td align="center" style="width: 25%"><input type="submit"
							name="operation" value="<%=SubscriptionCtl.OP_NEW%>"></td>

						<td align="center" style="width: 25%"><input type="submit"
							name="operation" value="<%=SubscriptionCtl.OP_DELETE%>"></td>

						<td align="right" Style="width: 25%"><input type="submit"
							name="operation" value="<%=SubscriptionCtl.OP_NEXT%>"
							<%=nextListSize != 0 ? "" : "disabled"%>></td>
					</tr>

				</table>

				<%
					}
					if (list.size() == 0) {
				%>
				<table>
					<tr>
						<td align="right"><input type="submit" name="operation"
							value="<%=SubscriptionCtl.OP_BACK%>"></td>
					</tr>
				</table>

				<%
					}
				%>
			</table>
		</form>

	</div>
</body>
</html>