
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class LoginUserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        // JSPへ転送するための準備
        ServletContext application = getServletContext();

        String next_view = "";

        String connection_result = "";

        // クライアントに返すコンテンツのタイプ，文字コードを設定する
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

        // login.htmlからフォームに入力された値を取得
        String username = request.getParameter("name");
        String userpass = request.getParameter("pass");
        String md5pass = "";

        String sql_message = "select * from 健康調査 where username='" + username + "'";
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        }
        // DBに接続し，SQL文を実行する
        try (
                // DB作成時に登録したユーザー名とパスワードを用いてDBに接続する
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(sql_message);) {
            // SQLの実行結果を受け取る
            ResultSet results = statement.executeQuery();

            String name = "";

            if (results.next()) {
                // ユーザー名がDBのUSER_TBL上に存在したら次はパスワードチェック
                String dp_pass = results.getString("password");
                md5pass = md5(userpass);

                if (dp_pass.equals(md5pass)) {
                    name = results.getString("username");
                    connection_result = name;
                    connection_result = connection_result.concat("　の健康調査");
                    next_view = "login.jsp";
                } else {
                    connection_result = "パスワードが間違っています";
                    next_view = "error.jsp";
                }

            } else {
                connection_result = "入力されたユーザー存在しません";
                next_view = "error.jsp";
            }
        } catch (Exception e) {
            out.println("Exception" + e.getMessage());
        }

        //messageプロパティ値をconnection_resultの中に入っている値にセット
        request.setAttribute("message", connection_result);
        session.setAttribute("name", username);
        application.getRequestDispatcher("/WEB-INF/" + next_view).forward(request, response);

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

    //　パスワードをMD5に変換
    private static String md5(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());
        String digest = new BigInteger(1, md5.digest()).toString(16);
        char[] buf = new char[32];
        Arrays.fill(buf, '0');
        System.arraycopy(digest.toCharArray(), 0, buf,
                buf.length - digest.length(), digest.length());
        return new String(buf);
    }
}
