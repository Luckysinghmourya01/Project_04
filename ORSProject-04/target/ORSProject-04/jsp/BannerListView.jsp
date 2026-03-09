<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="in.co.rays.proj4.controller.BannerListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.bannerBean"%>
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
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.bannerBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15px; color: navy;">Banner 
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<form action="<%=ORSView.BANNER_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getpageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<bannerBean> roleList = (List<bannerBean>) request.getAttribute("roleList");
				List<bannerBean> list = (List<bannerBean>) ServletUtility.getList(request);

				Iterator<bannerBean> it = list.iterator();
				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Banner Code :</b></label> <input
						type="text" name="bannerCode" placeholder="Enter First Name"
						value="<%=ServletUtility.getParameter("bannerCode", request)%>">&emsp;

						<label align="center"><b>Banner Title :</b></label><input type="text"
						name="bannerTitle" placeholder="Enter Last Name"
						value="<%=ServletUtility.getParameter("bannerTitle", request)%>">



						
						<input type="submit" name="operation"
						value="<%=BannerListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=BannerListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="13%">Banner Code</th>
					<th width="23%">Banner Title</th>
					<th width="23%">Image Path</th>
					<th width="10%">Banner Status</th>
					<th width="10%">Edit</th>
					
				</tr>

				<%
					while (it.hasNext()) {
						bean =	it.next();
							
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getBannerCode()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getBannerTitle()%></td>
					<td style="text-align: center; text-transform: lowercase;"><%=bean.getImagePath()%></td>
					<td style="text-align: center;"><%=bean.getBannerStatus()%></td>
					<td style="text-align: center;"><a
						href="BannerCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
					}
				%>

				<table style="width: 100%">
					<tr>
						<td style="width: 25%"><input type="submit" name="operation"
							value="<%=BannerListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>></td>

						<td align="center" style="width: 25%"><input type="submit"
							name="operation" value="<%=BannerListCtl.OP_NEW%>"></td>

						<td align="center" style="width: 25%"><input type="submit"
							name="operation" value="<%=BannerListCtl.OP_DELETE%>"></td>

						<td align="right" Style="width: 25%"><input type="submit"
							name="operation" value="<%=BannerListCtl.OP_NEXT%>"
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
							value="<%=BannerListCtl.OP_BACK%>"></td>
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