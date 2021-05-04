<%-- 
    Document   : error
    Created on : 2020/07/17, 17:19:18
    Author     : c011809755
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String str = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DBユーザー登録結果</title>
        <style>
            body{
                background-color:lightpink; 
                background-image: url("batsu.png");     
                background-repeat:no-repeat; 
                background-size:600px;
                background-position: top center;
                text-align: center;
                margin-top:100px;
            }
        </style>
    </head>
    <body>
        <h1>ユーザー登録エラー</h1>
        <h2><%=str%></h2>        
        <p><a href="login2.html">新規登録ページに戻る</a>
        　　　　<a href="login.html">ログインページに戻る</a></p>
    </body>
</html>
