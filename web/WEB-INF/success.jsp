<%-- 
    Document   : success
    Created on : 2020/07/17, 17:19:52
    Author     : c011809755
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // UserManagerServlet.javaから送られたmessageの値を取り出す
    String str = (String)request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DBユーザー登録結果</title>
        <style>
            body{
                background-color:azure; 
                background-image: url("maru.png");     
                background-repeat:no-repeat; 
                background-size:600px;
                background-position: top center;
                text-align: center;
                margin-top:100px;
            }
            a{
                color: aqua;
            }
        </style>
    </head>
    <body>
        <h1>DBユーザー登録結果</h1>
        <h2><%=str%></h2>
        <a href="login.html">ログインページに戻る</a>
    </body>
</html>
