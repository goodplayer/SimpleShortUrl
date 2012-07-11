<%@page import="mx.meido.simpleshorturl.servlet.UrlRedirectServlet"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<base href="<%=UrlRedirectServlet.props.get("admin-url")%>">
</head>
<body>
<h1>欢迎使用ShortUrl服务</h1>
</body>
</html>
