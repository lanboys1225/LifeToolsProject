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

<fieldset>
    <legend>批量发送邮件</legend>
    <form method="post" action="/api/v1/mail/send" enctype="multipart/form-data">
        收件人userIdsExcel文件 <input type="file" name="userIdsExcelFile">
        <br>
        邮件标题 <input type="text" name="title" value="titleName">
        <br>
        邮件内容
        <input type="text" name="content" value="contentsssss">
        <br>
        收件邮箱 <input type="text" name="mail" value="lanbing@baogongyoucai.com">
        <br>
        <input type="submit" value="发送"/>
    </form>
</fieldset>

<fieldset>
    <legend>模板</legend>
    <form method="post" action="/api/v1/mail/downloadTemp">
        <input type="submit" value="下载模板"/>
    </form>
</fieldset>


</body>
</html>
