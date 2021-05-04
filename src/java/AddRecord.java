import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class AddRecord extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // クライアントに返すコンテンツのタイプ，文字コードを設定する
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // JSPへ転送するための準備
        ServletContext application = getServletContext();

        PrintWriter out = response.getWriter();

        // 接続するデータベースの名前，アカウント情報を設定
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

        HttpSession session = request.getSession(false);

        String username = (String) session.getAttribute("name");
        String day = request.getParameter("day");
        String tion = request.getParameter("tion");
        String period = request.getParameter("period");
        String[] symptom = request.getParameterValues("Symptom");
        double t = Double.parseDouble(tion);
        String symptom_all = String.join(", ", symptom);
        String works = "";

        String sql_message1 = "update 健康調査 set symptom='" + symptom_all + "' where username='" + username + "'";
        String sql_message2 = "update 健康調査 set day='" + day + "' where username='" + username + "'";
        String sql_message3 = "update 健康調査 set tion='" + t + "' where username='" + username + "'";
        String sql_message4 = "update 健康調査 set period='" + period + "' where username='" + username + "'";
        try (
                // DB作成時に登録したユーザー名とパスワードを用いてDBに接続する
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();) {
            
            // SQLの実行結果を受け取る
            int results = statement.executeUpdate(sql_message1);
            results = statement.executeUpdate(sql_message2);
            results = statement.executeUpdate(sql_message3);
            results = statement.executeUpdate(sql_message4);
            
            int con=0;
            for(int i=0;i<symptom.length;i++){
                if(symptom[i].equals("味覚障害")||symptom[i].equals("嘔吐")||symptom[i].equals("倦怠感")||symptom[i].equals("息苦しい")||symptom[i].equals("せき(強)")){
                    con=1;
                    break;
                }
            }
            if (t >= 37.5 ||symptom.length >= 3||con==1) {
                works = "自宅待機";
            }else{
                works ="出勤";
            }
            results=statement.executeUpdate("update 健康調査 set works='" + works + "' where username='" + username + "'");
            
        } catch (Exception e) {
            out.println("Exception" + e.getMessage());
        }
        request.setAttribute("message", works);
        session.setAttribute("name", username);
        //view.jspに飛ぶ（結果表示はJSPに任せる）
        application.getRequestDispatcher("/WEB-INF/result.jsp").forward(request, response);
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
