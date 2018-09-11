<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页-登录</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/user/login" method="post">
姓名:<input name="id" type="text"/><hr>
密码:<input name="password" type="password"><hr>
<input type="submit" value="登录">&nbsp;&nbsp;&nbsp;<input type="button" value="注册">
</form>
</body>
</html>

