/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author XinheHuang
 */
public class query extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String query = request.getParameter("query");
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            Class.forName("org.postgresql.Driver").newInstance();
            String url = "jdbc:postgresql://localhost:5432/CreditCardDB";
            conn = DriverManager.getConnection(url, "postgres", "hxh8526622");
            String s = "";
            out.println("<table>");

            switch (query.charAt(query.length() - 1)) {
                case '1': {
                    s = "select P1.PId,P1.Name,P2.PId,P2.Name\n"
                            + "FROM \"person\" P1, \"person\" P2, \"authorized\" A, \"organization\" O,\"signer\" S,\"card\" C\n"
                            + "WHERE\n"
                            + " (\n"
                            + "  P1.PId=A.PId and A.CId=C.CId and C.OwnerId=O.OId and O.OId=S.OId and S.PId=P2.PId and C.Lim-C.Bal<1000\n"
                            + " )";
                    out.println("<tr><th>User ID</th><th>User Name</th><th>Signer ID</th><th>Signer name</th></tr>");
                    break;
                }
                case '2': {
                    s = "select P.PId, P.Name\n"
                            + "from \"person\" P, \"card\" C, \"authuser\" A\n"
                            + "where\n"
                            + "(\n"
                            + "	P.PId=C.OwnerId and \n"
                            + "	A.PId=P.PId\n"
                            + "	\n"
                            + ")\n"
                            + "group by P.PId\n"
                            + "having (count(C.CId)>=4 and count(A.CId)>=3)\n"
                            + ";";
                    out.println("<tr><th>User ID</th><th>User Name</th></tr>");
                    break;
                }
                case '3': {
                    s = "select distinct C.Cid\n" +
"from \"card\" C, \"signer\" S, \"card\" C1\n" +
"Where\n" +
"(\n" +
"  S.OId=C.OwnerId and\n" +
"  S.PId=C1.OwnerID and\n" +
"  C1.Lim>=25000\n" +
"\n" +
");";
                    out.println("<tr><th>Card ID</th></tr>");
                    break;
                }
                case '4': {
                    s = "select P1.PId,P1.Name,P2.PId,P2.Name\n"
                            + "FROM \"person\" P1, \"person\" P2, \"authorized\" A, \"organization\" O,\"signer\" S,\"card\" C\n"
                            + "WHERE\n"
                            + " (\n"
                            + "  P1.PId=A.PId and A.CId=C.CId and C.OwnerId=O.OId and O.OId=S.OId and S.PId=P2.PId and C.Lim-C.Bal<1000\n"
                            + " )";
                    out.println("<tr><th>User ID</th><th>User Name</th><th>Signer ID</th><th>Signer name</th></tr>");
                    break;
                }
                case '5': {
                    s = "select P1.PId,P1.Name,P2.PId,P2.Name\n"
                            + "FROM \"person\" P1, \"person\" P2, \"authorized\" A, \"organization\" O,\"signer\" S,\"card\" C\n"
                            + "WHERE\n"
                            + " (\n"
                            + "  P1.PId=A.PId and A.CId=C.CId and C.OwnerId=O.OId and O.OId=S.OId and S.PId=P2.PId and C.Lim-C.Bal<1000\n"
                            + " )";
                    out.println("<tr><th>User ID</th><th>User Name</th><th>Signer ID</th><th>Signer name</th></tr>");
                    break;
                }
                default:
                    break;

            }
            ps = conn.prepareStatement(s);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            ResultSetMetaData m = rs.getMetaData();

            while (rs.next()) {
                out.println("<tr>");

                for (int i = 0; i < m.getColumnCount(); i++) {
                    out.println("<th>");
                    out.println(rs.getString(i + 1));
                    out.println("</th>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                out.close();
                ps.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(query.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
