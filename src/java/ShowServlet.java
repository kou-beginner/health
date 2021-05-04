import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;//DBを利用するために必要なパッケージをインポート
import javax.servlet.http.HttpSession;

public class ShowServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //クライアントに返すコンテンツのタイプ、文字コードを設定する
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>サーブレットDB連携</title>");
        out.println("</head>");
        out.println("<body background-color=\"ivory;\">");

        // 接続するデータベースの名前、アカウント情報を設定
        String db_name = "Webapp";
        String user = "APP";
        String password = "app";

        // DBに接続するためのドライバを読み込む
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // DBへの接続方法を設定
        String url = "jdbc:derby://localhost/" + db_name;
        // 実行するSQL文を設定
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("name");
        String symptom = "";
        String[] symptoms;
        String tion = "";
        String period = "";
        String works="";
        String sql_message = "select * from 健康調査 where username='"+username+"'";
        // DBに接続し、SQL文を実行する
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();) {

            ResultSet results = statement.executeQuery(sql_message);
            // SQL文を実行した結果を受け取る
            while (results.next()) {
                symptom = results.getString("symptom");
                tion = results.getString("tion");
                period = results.getString("period");
                works=results.getString("works");
            }
            //カンマ区切りをそれぞれ配列に代入
            symptoms = symptom.split(",", 0);           
            
            out.println("<h1 align=\"center\">"+"登録情報" +"</h1>");
            out.println("<table border=\"1\" padding=\"0\"  align=\"center\"  border=\"1\" cellspacing=\"0\">");
            out.println("<tr>");
            out.println("<th colspan=\"2\">今日の体調</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th width=\"70\">体温</th>");
            out.println("<th width=\"100\">" + tion + "度" + "</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th>症状</th>");
            out.println("<th>");
            for (int i = 0; i < symptoms.length; i++) {
                out.println(symptoms[i]);
                out.println("<br>");
            }

            out.println("</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th>期間</th>");
            out.println("<th>" + period + "</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th>出勤状況</th>");
            out.println("<th>" + works + "</th>");
            out.println("</tr>");
            out.println("</table>");

        } catch (Exception e) {
            out.println("Exception" + e.getMessage());
        }

        out.println("<a href=\"login.html\">ログインページ戻る</a>");

        out.println("</body>");
        out.println("</html>");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
