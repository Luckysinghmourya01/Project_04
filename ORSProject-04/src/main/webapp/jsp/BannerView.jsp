<%@page import="in.co.rays.proj4.controller.BannerCtl"%>
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

	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.bannerBean"
		scope="request"></jsp:useBean>

	<div align="center">



		<h1 align="center" style="margin-bottom: -15; color: navy">Add
			Banner</h1>
			
			<div style="height: 15px; margin-bottom: 12px">
			<h3 align="center">
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3 align="center">
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.BANNER_CTL%>" method="post">
			<table>
				<tr>
					<th align="left">Banner Code<span style="color: red">*</span></th>
					<td><input type="text" name="bannerCode"
						placeholder="Enter banner Code"
						value="<%=DataUtility.getStringData(bean.getBannerCode())%>"></td>

					<td style="position: fixed;"><font style="color: red"><%=ServletUtility.getErrorMessage("bannerCode", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Banner Tittle<span style="color: red">*</span></th>
					<td><input type="text" name="bannerTitle"
						placeholder="Enter banner title"
						value="<%=DataUtility.getStringData(bean.getBannerTitle())%>"></td>

					<td style="position: fixed;"><font style="color: red"><%=ServletUtility.getErrorMessage("bannerTitle", request)%></font>
					</td>


				</tr>

				<tr>
					<th align="left">Banner Path<span style="color: red">*</span></th>
					<td><input type="text" name="imagePath"
						placeholder="Enter banner title"
						value="<%=DataUtility.getStringData(bean.getImagePath())%>"></td>

					<td style="position: fixed;"><font style="color: red"><%=ServletUtility.getErrorMessage("imagePath", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("active", "active");
							map.put("Inactive", "Inactive");

							String html = HtmlUtility.getList("status", bean.getImagePath(), map);
						%> <%=html%>


					</td>
					<td style="position: fixed;"><font style="color: red"><%=ServletUtility.getErrorMessage("status", request)%></font>
					</td>
				</tr>

				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=BannerCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=BannerCtl.OP_RESET%>"></td>
				</tr>
			</table>

		</form>
	</div>
</body>
</html>