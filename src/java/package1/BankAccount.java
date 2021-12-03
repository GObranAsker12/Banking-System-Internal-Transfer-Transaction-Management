package package1;

import java.sql.*;
import java.util.Date;

public class BankAccount {
    private String BA_ID;
    private Date BA_creationDate;
    private double BA_currentBalance;
    private String customer_ID;
    
    public BankAccount(){}
    public void setBA_ID ( String id ){ BA_ID = id; }
    public void setBA_creationDate( Date date ){ BA_creationDate = date; }
    public void setBA_currentBalance ( float balance){ BA_currentBalance = balance; } 
    public void setCustomer_ID ( String cus ){ customer_ID = cus; }
    public String getBA_ID (){ return BA_ID; }
    public Date getBA_creationDate(){ return BA_creationDate; }
    public double gettBA_currentBalance (){ return BA_currentBalance; } 
    public String getCustomer_ID (){ return customer_ID; }
    
    public Boolean setBankAccount(String custID) throws SQLException{
        Statement st = DatabaseController.conn.createStatement();
        ResultSet rs=st.executeQuery("select * from BankAccount where customerID = '"+custID+"'");
        while (rs.next()){
            this.BA_ID=rs.getString(1);
            this.BA_creationDate=rs.getTimestamp(2);
            this.BA_currentBalance=rs.getDouble(3);
            return true;
        }
        return false;
    }
    
    public void createBankAccount() throws SQLException{
        Statement st = DatabaseController.conn.createStatement();
        int state = st.executeUpdate("insert into BankAccount values ('"+this.BA_ID + "', CURRENT_TIMESTAMP(),'"
                +BA_currentBalance+"','"+customer_ID+"')");
    }
}
