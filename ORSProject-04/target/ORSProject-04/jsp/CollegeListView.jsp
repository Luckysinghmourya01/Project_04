<%@page import="in.co.rays.proj4.controller.CollegeListCtl"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.bean.CollageBean"%>
<%@page import="java.util.List"%>
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
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CollageBean"
		scope="request"></jsp:useBean>

	<div>

		<h1 align="center" style="margin-bottom: -15; color: navy">College
			List</h1>

		<div align="center" height: 15px; margin-bottom: 12px">
			<h3 align="center">
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3 align="center">
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post">

			<%
		  int pageNo =    ServletUtility.getpageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = 	DataUtility.getInt(request.getAttribute("nextListSize").toString());
	          List<CollageBean> collegeList = (List<CollageBean>)  request.getAttribute("collegeList");
	     List<CollageBean> list =    (List<CollageBean>)ServletUtility.getList(request);
	     
	   Iterator<CollageBean> it =   list.iterator();
	   
	   if(list.size()!= 0){
		
		%>

			 <table style="width: 100%">
                <tr>
                    <td align="center">
                        <label><b>College Name : </b></label>
                        <%=HtmlUtility.getList("collegeId", String.valueOf(bean.getId()), collegeList)%>
                        <label><b>City :</b></label>
                        <input type="text" name="city" placeholder="Enter College City" value="<%=ServletUtility.getParameter("city", request)%>">&emsp;
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_SEARCH%>">&nbsp;
                       <input type="submit" name="operation" value="<%=CollegeListCtl.OP_RESET%>"> 
                    </td>
                </tr>
            </table>
            
            <table border="1" style="width: 100%; border: groove;">
                <tr style="background-color: #e1e6f1e3;">
                    <th width="5%"><input type="checkbox" id="selectall" /></th>
                    <th width="5%">S.No</th>
                    <th width="25%">College Name</th>
                    <th width="25%">Address</th>
                    <th width="15%">State</th>
                    <th width="10%">City</th>
                    <th width="10%">Phone No</th>
                    <th width="5%">Edit</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        bean = it.next();
                %>
                <tr>
                    <td style="text-align: center;">
                        <input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>">
                    </td>
                     <td style="text-align: center;"><%=index++%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getName()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getAddress()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getState()%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getCity()%></td>
                    <td style="text-align: center;"><%=bean.getPhoneNo()%></td>
                    <td style="text-align: center;"><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>

            <table style="width: 100%">
                <tr>
                    <td style="width: 25%">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEW%>">
                    </td>
                    <td align="center" style="width: 25%">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_DELETE%>">
                    </td>
                    <td style="width: 25%" align="right">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled" %>>
                    </td>
                </tr>
            </table>

            <%
                }
                if (list.size() == 0) {
            %>
            <table>
                <tr>
                    <td align="right">
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_BACK%>">
                    </td>
                </tr>
            </table>
            <%
                }
            %>
		</form>
	</div>
</body>
</html>