
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

public class AddUserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JSPへ転送するための準備
        ServletContext application = getServletContext();
        
        String next_view = "";
        String connection_result = "";

        // クライアントに返すコンテンツのタイプ，文字コードを設定する
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
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

        // login2.htmlからフォームに入力された値を取得
        String username = request.getParameter("uname");
        String userpass = request.getParameter("pass");
        String userpass2 = request.getParameter("pass2");
        String md5pass = "";

        // ユーザーから入力されたパスワードをMD5でハッシュ化
        try {
            md5pass = md5(userpass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // DBのUSER_TBLにユーザ名とパスワード(ハッシュ値）を新規登録するためのSQL文     
        String sql_message1 = "select * from 健康調査 where username='" + username + "'";
        String sql_message2 = "select * from 健康調査 where password='" + md5pass + "'";
        String sql_message3 = "insert into app.健康調査 (username, password) "
                + "values ('" + username + "','" + md5pass + "')";
        // DBに接続し、SQL文を実行する
        try (
                // DB作成時に登録したユーザー名とパスワードを用いてDBに接続する
                Connection connection = DriverManager.getConnection(url, user, password);
                // SQL文の実行準備
                PreparedStatement statement1 = connection.prepareStatement(sql_message1);
                PreparedStatement statement2 = connection.prepareStatement(sql_message2);
                PreparedStatement statement3 = connection.prepareStatement(sql_message3);) {

            // SQL文を実行した結果を受け取る
            ResultSet results1 = statement1.executeQuery();
            ResultSet results2 = statement2.executeQuery();
            int updated_lines = 0;

            //if文でDB上に追加されない条件を指定
            if (results1.next() || results2.next()) {
                connection_result = "ユーザー名またはパスワードがすでに使われています";
                next_view = "error.jsp";
            } else if (username.isEmpty() || userpass.isEmpty()) {
                connection_result = "ユーザー名またはパスワードが空白です";
                next_view = "error.jsp";
            } else if (!userpass.equals(userpass2)) {
                connection_result = "パスワードと確認用パスワードが一致していません";
                next_view = "error.jsp";
            } else {
                updated_lines = statement3.executeUpdate();

                // テーブルの更新行数が0ではない＝更新があった
                if (updated_lines != 0) {
                    connection_result = username + "　をユーザーとしてDBに登録しました";
                    next_view = "success.jsp";
                }
            }

        } catch (Exception e) {
            out.println("例外エラー：" + e.getMessage());
        }

        // view.jspのためにmessageプロパティ値をconnection_resultの中に入っている値にセット
        request.setAttribute("message", connection_result);

        // view.jspに飛ぶ（結果表示はJSPに任せる）
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
