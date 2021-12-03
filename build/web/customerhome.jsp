<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/login.css">
        <title>Customer Home</title>
    </head>
    <body>
        <% HttpSession se = request.getSession();%> 
        <%@page import = "package1.*" %>
        <%Customer cust = new Customer();%>

        <%cust.setUserID(String.valueOf( se.getAttribute("userID")));%>
        <%cust.setCustomerDetails();%>
        
        <%out.println("customer ID: " + cust.getUserID());%>
        <br>
        <%out.println("customer Name: " + cust.getCutomerName());%>
        <br>
        <%out.println("customer Mobile: " + cust.getUserMobile());%>
        <br>
        <%out.println("customer Gender: " + cust.getUserGender()+"<br>");%>
        <% BankAccount ba = new BankAccount();
            boolean baexists=ba.setBankAccount(cust.getUserID());
            if(baexists == false){
                out.println("<form action=\"AddAccount\">"
                        +"<input type=\"submit\" value=\"Create Account\">"
                        +"</form>"
                );
            }
            else {
                se.setAttribute("BA_ID", ba.getBA_ID());
                out.println("Bank Account ID : "+ba.getBA_ID());
                out.println("<br>");
                out.println("bank Account Creation Date : "+ba.getBA_creationDate());
                out.println("<br>");
                out.println("Bank Account Current Balance : "+ba.gettBA_currentBalance());
                out.println("<br>");
                out.println("<form action=\"Transactions.jsp\">"
                        +"<input type=\"submit\" value=\"Transactions\">"
                        +"</form>"
                );
            }
        %>
    </body>
</html>
