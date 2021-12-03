package package1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class validate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            String username = (String) request.getParameter("username");
            String password = (String) request.getParameter("userPassword");
            DatabaseController db = new DatabaseController();
            String userID;
            try {
                db.connect();
                userID = db.searchByUsername(username, password);
                if (!"".equals(userID)) {
                    HttpSession se = request.getSession(true);
                    se.setAttribute("userID", userID);
                    response.sendRedirect("/IA_Assignment2/customerhome.jsp");
                } else {
                    response.sendRedirect("index.html");
                }
            } catch (IOException | ClassNotFoundException | SQLException e) {
                System.out.println(e);
            }
        }
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
}
