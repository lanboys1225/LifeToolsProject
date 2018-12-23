<%--
  Created by IntelliJ IDEA.
  User: 520
  Date: 2018/12/23
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量发送邮件</title>
</head>
<body>


<label>
    <form method="post" action="/api/v1/mail/send">
        收件人userIds <input type="text" name="userId" value="1">
        <br>
        邮件标题 <input type="text" name="title" value="标题">
        <br>
        邮件内容
        <input type="text" name="content" value="内容">
        <br>
        <input type="file">

        <input type="submit" value="发送"/>
    </form>
</label>


</body>
</html>
