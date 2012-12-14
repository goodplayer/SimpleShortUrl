<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@page import="mx.meido.simpleshorturl.listener.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=SimpleShortUrlContextListener.getProps().get("admin-url")%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>完成</title>
</head>
<body>
${actionBean.msg}<br />
<a href="${actionBean.shortUrl}" target="_blank">${actionBean.shortUrl}</a><br />
<a href="submiturl.jsp">填写另一个要缩写url</a>
</body>
</html>