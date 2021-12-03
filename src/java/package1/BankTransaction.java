/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class BankTransaction {
    private String BT_ID;
    private Date BT_CreationDate;
    private double BT_amount;
    private String BT_fromAccount;
    private String BT_toAccount;
    private int BT_state;

    public BankTransaction(){};
    public void setBT_ID(String BT_ID) {this.BT_ID = BT_ID;}
    public void setBT_CreationDate(Date BT_CreationDate) {this.BT_CreationDate = BT_CreationDate;}
    public void setBT_amount(double BT_amount) {this.BT_amount = BT_amount;}
    public void setBT_fromAccount(String BT_fromAccount) {this.BT_fromAccount = BT_fromAccount;}
    public void setBT_toAccount(String BT_toAccount) {this.BT_toAccount = BT_toAccount;}
    public void setBT_state(int st){this.BT_state=st;}
    public String getBT_ID() {return BT_ID;}
    public Date getBT_CreationDate() {return BT_CreationDate;}
    public double getBT_amount() {return BT_amount;}
    public String getBT_fromAccount() {return BT_fromAccount;}
    public String getBT_toAccount() {return BT_toAccount;}
    public int getBT_state(){return BT_state;}
    
    public ArrayList<BankTransaction> getTransactions() throws SQLException, ClassNotFoundException{
        ArrayList <BankTransaction> allTrans = new ArrayList();
        Statement st = DatabaseController.conn.createStatement();
        while(st==null){
            DatabaseController.connect();
            st=DatabaseController.conn.createStatement();
        }
        
        
        ResultSet rs = st.executeQuery("select * from BankTransaction where BT_fromAccount = '"
        +this.BT_fromAccount+"' order by BT_creationDate DESC");
        while (rs.next()){
            BankTransaction newBT = new BankTransaction();
            newBT.BT_ID=rs.getString(1);
            newBT.BT_CreationDate=rs.getTimestamp(2);
            newBT.BT_amount=rs.getDouble(3);
            newBT.BT_fromAccount=rs.getString(4);
            newBT.BT_toAccount=rs.getString(5);
            newBT.BT_state=rs.getInt(6);
            allTrans.add(newBT);
        }
        return allTrans;
    }
    
    public boolean ValidateTransaction() throws SQLException{
        Statement st = DatabaseController.conn.createStatement();
        ResultSet rs = st.executeQuery("select * from BankAccount where BA_ID = '"+this.BT_toAccount+"'");
        double total=0;
        if (rs.next()){
            st=DatabaseController.conn.createStatement();
            rs=st.executeQuery("select BA_CurrentBalance from BankAccount where BA_ID = '"+this.BT_fromAccount+"'");
            
            while (rs.next()){
                total = rs.getDouble(1);
                if (total<this.BT_amount){
                    System.out.println("not enough");
                    return false;
                }
            }
            createTransaction(total);
            return true;
        }
        System.out.println("account not found");
        return false;
    }
    public void createTransaction(double total) throws SQLException{
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
        this.BT_ID=id;
        Statement st = DatabaseController.conn.createStatement();
        int state = st.executeUpdate("insert into BankTransaction values ('"+this.BT_ID + "', CURRENT_TIMESTAMP(),'"
                +this.BT_amount+"','"+this.BT_fromAccount+"','"+ this.BT_toAccount+"','0')");
        
        st=DatabaseController.conn.createStatement();
        double newbalance = total-this.BT_amount;
        st.executeUpdate("update BankAccount set BA_currentBalance = '"+newbalance+"' where BA_ID = '"
        +this.BT_fromAccount+"'");
        System.out.println(this.BT_toAccount);
        st = DatabaseController.conn.createStatement();
        st.executeUpdate("update BankAccount set BA_currentBalance = BA_currentbalance + "+this.BT_amount+" where BA_ID = '"+this.BT_toAccount+"';");        
    }
    
    public int ReverseTransaction() throws SQLException{
        int state=0;
        Statement st = DatabaseController.conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from BankTransaction where BT_ID =  '"+this.BT_ID+"'");
        while (rs.next()){
            this.BT_fromAccount=rs.getString(4);
            this.BT_toAccount=rs.getString(5);
            this.BT_amount=rs.getDouble(3);
        }
        st=DatabaseController.conn.createStatement();
        state += st.executeUpdate("update BankTransaction set state = 1 where BT_ID = '"+this.BT_ID+"'");
        st=DatabaseController.conn.createStatement();
        state += st.executeUpdate("update BankAccount set BA_currentBalance = BA_currentBalance + "
                +this.BT_amount +" where BA_ID = '"+this.BT_fromAccount+"'");
        state += st.executeUpdate("update BankAccount set BA_currentBalance = BA_currentBalance - "
                +this.BT_amount +" where BA_ID = '"+this.BT_toAccount+"'");
        return state;
    }
}
