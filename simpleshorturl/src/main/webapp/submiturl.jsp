<%@page import="mx.meido.simpleshorturl.listener.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<base href="<%=SimpleShortUrlContextListener.getProps().get("admin-url")%>">
</head>
<body>
    <form action="ShortUrl.action" method="post">
        <table>
            <tr>
                <td>需要缩短的URL:</td>
                <td><input name="fullUrl" type="text" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input name="submit" value="确定" type="submit" />                 
                </td>
            </tr>
        </table>
    </form>

</body>
</html>
