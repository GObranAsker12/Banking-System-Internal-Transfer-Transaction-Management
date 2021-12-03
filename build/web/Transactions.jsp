<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/login.css">
        <title>Transactions</title>
    </head>
    <body>
        <%@page import = "package1.*" %>
        <%
            HttpSession se = request.getSession();
            BankTransaction bt = new BankTransaction();
            bt.setBT_fromAccount(se.getAttribute("BA_ID").toString());
            ArrayList<BankTransaction> all = new ArrayList();
            all = bt.getTransactions();
            if (!all.isEmpty()) {
                for (int i = 0; i < all.size(); i++) {
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(all.get(i).getBT_ID()+"<br>");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(all.get(i).getBT_CreationDate()+"<br>");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(all.get(i).getBT_amount()+"<br>");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(all.get(i).getBT_toAccount()+"<br>");
                    out.println("</td>");
                    Date current = new Date();
                    long diffInMillies = Math.abs(all.get(i).getBT_CreationDate().getTime() - current.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    if (diff <= 1 && all.get(i).getBT_state() == 0) {
                        out.println("<td>");
                        
                        out.println("<form action = ReverseTransaction >");
                        out.println("<input type=\"hidden\" name=\"BT_ID\" value=\"" + all.get(i).getBT_ID() +"\"/>");
                        out.println("<input type=\"submit\" value=\"Reverse Transaction\">");
                        out.println("</form>");
                        
                        out.println("</td>");
                        out.println("<td>");
                        out.println("Successful Transacion"+"<br><br>");
                        out.println("</td>");
                    } else if (all.get(i).getBT_state() == 1) {
                        out.println("<td>");
                        out.println("<button type=\"button\" disabled>Reverse Transaction!</button>"+"<br>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("Reversed Transacion<br><br>");
                        out.println("</td>");
                    } else {
                        out.println("<td>");
                        out.println("<button type=\"button\" disabled>Reverse Transaction!</button><br>");
                        out.println("</td>");
                        out.println("<td>");
                        out.println("Successful Transacion <br><br>");
                        out.println("</td>");
                    }
                    out.println("</tr>");
                }
            }
        %>
        <form action = "/IA_Assignment2/CreateTransaction">
            <p>To Bank Account ID : </p><input type="text" name="To_BA" placeholder="To Bank Account"><br>
            <p>Transfered amount</p><input type="text" name="amount" placeholder="Amount"><br>
            <input type="submit" value="Create Transaction">
        </form>
    </body>
</html>
