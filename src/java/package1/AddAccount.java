package package1;

import java.io.IOException;
import java.util.Random;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AddAccount extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        BankAccount newba = new BankAccount();
        String id = "";
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numset="0123456789";
        Random rand = new Random();
        for (int i=0;i<2;i++){
            
            id+=charset.charAt(rand.nextInt(53));
        }
        for(int i=2;i<9;i++){
            id+=numset.charAt(rand.nextInt(10));
        }
        newba.setBA_ID(id);
        newba.setBA_creationDate(new Date());
        newba.setBA_currentBalance(1000);
        HttpSession se = request.getSession(false);
        newba.setCustomer_ID(se.getAttribute("userID").toString());
        newba.createBankAccount();
        response.sendRedirect("/IA_Assignment2/customerhome.jsp");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
