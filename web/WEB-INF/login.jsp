<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String str = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html　lang="ja">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ログイン</title>
        <style>
            body{
                background-color: azure
            }
            h1{
                color:black;
            }
            a{
                position: absolute;
                top:5%;
                right:5%;
                color:aqua;
            }
            .submit{
                margin-top:15px;
                text-align: center;
            }
            .day{
                text-align: center;
            }
            input.text{
                width: 100px;
            }
            h2{
                text-align: center;
            }

        </style>
    </head>
    <body>
        <h1><%=str%></h1>
        <%
            Date dNow = new Date();
            SimpleDateFormat ft
                    = new SimpleDateFormat("yyyy/MM/dd");
            out.print("<h2 align=\"center\">" + ft.format(dNow) + "</h2>");
        %>
        <form method="post"  action="./AddRecord">
            <div class="day">
                曜日を記入(例 2000/1/1)<input type="text" class="text" name="day" required>
            </div>
            <table padding="0"  align="center"  border="1" cellspacing="0" >
                <tr>
                    <th colspan="7">今日の体調</th>
                </tr>
                <tr>
                    <th width="50">体温</th>
                    <th colspan="6">(例　36.0　※ピリオド(.)を使用)
                        <input type="text" name="tion" class="text" required="required">度
                    </th>
                </tr>
                <tr>
                    <th rowspan="3">症状</th>
                    <th><label> <input type="checkbox" name="Symptom" value="頭痛">頭痛</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="鼻づまり">鼻づまり</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="さむけ">さむけ</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="のどの痛み">のどが痛い</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="関節痛">関節痛</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="せき(弱)">せき(弱)</th></label>
                </tr>
                <tr>
                    <th><label> <input type="checkbox" name="Symptom" value="下痢">下痢</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="息苦しい">息苦しい</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="味覚障害">味覚障害</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="嘔吐">嘔吐</th></label>  
                    <th><label> <input type="checkbox" name="Symptom" value="倦怠感">倦怠感</th></label>
                    <th><label> <input type="checkbox" name="Symptom" value="せき(強)">せき(強)</th></label>
                </tr>
                <tr>
                    <th colspan="6"><label> <input type="checkbox" name="Symptom" value="なし">なし</th></label>
                </tr>
                <tr>
                    <th>期間</th>
                    <th colspan="6">(例　体調が悪いと感じてから１週間未満の場合→0週間)
                        <select name="period">
                            <option value="0週間">0週間</option>
                            <option value="1週間">1週間</option>
                            <option value="2週間">2週間</option>
                        </select>
                    </th>
                </tr>

            </table>
            <div class="submit">
                <input type="submit" value="診断">
            </div>
        </form>
        <a href="login.html">ログアウト</a>
    </body>
</html>
