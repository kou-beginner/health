<%-- 
    Document   : result
    Created on : 2020/07/27, 0:47:54
    Author     : c011809755
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<%
    String str = (String) request.getAttribute("message");
    String a = "自宅待機";
    String b = "出勤";
%>

<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>結果</title>
        <style>
            a{
                position: absolute;
                top:5%;
                right:5%;
                color:aqua;
            }
            .submit{
                margin-top:15px;
                position: absolute;
                left:60px;
                font-size: 30px;
                color: #f9a9ae;
                background: linear-gradient(#fed6e3 0%, #ffaaaa 100%);
                cursor: pointer;
                box-shadow: 0 5px 5px rgba(0, 0, 0, 0.28);
            }
            .submit:hover{
                box-shadow: inset 0 0 5px rgba(128, 128, 128, 0.32);
                background-image: linear-gradient(#f6c7d7 10%, #ffcfcf 90%);
            }
            .p{
                font-size:50px;
                text-align: center;
            }
            .pp{
                font-size:30px;
                text-align: center;
                animation: rollkAnime 4s linear infinite;
                color    : #6666ff;
            }
            @keyframes rollkAnime{
                50% {
                    color    : #66ff66;
                    transform: rotateX(180deg);
                }
                100% {
                    color    : #6666ff;
                    transform: rotateX(360deg);
                }
            }
            
            
        </style>
    </head>
    <body>
        <h1>健康調査完了</h1>
        <form method="post" action="./Show">
            <input type="submit" value="確認" size="40" class="submit">
        </form>
        <%if (str == a) {
        %>
        <style>
            body{
                background-image: url("自宅待機.png");     
                background-repeat:no-repeat; 
                background-size: 90vh;
                background-position: center 150px;
                background-color:ivory;
            }
        </style>
        <p class="p"><%=str%></p>
        <%
        } else if (str == b) {
        %>
        <style>
            body{
                background-image: url("work.png");
                background-repeat:no-repeat; 
                background-size: 50vh;
                background-position: right top;
                background-color:ivory;
            }
        </style>
        <p class="p"><%=str%>しましょう</p>
        <p class="pp">必ずマスクをつけて出勤しましょう！！<br>
        手洗い・うがいも忘れずに</p>
        <%
            }
        %>

        <a href="login.html">ログアウト</a>
       
    </body>
</html>

