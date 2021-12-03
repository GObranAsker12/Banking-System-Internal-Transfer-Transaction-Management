package package1;

import java.sql.*;
public class Customer {
    private String userID;
    private String userPassword;
    private String userName;
    private String customerName;
    private String userAddress;
    private String userMobile;
    private String userGender;
    
    public Customer(){}
    public void setUserID(String ID) {userID=ID;}
    public void setUserPassword(String pass) {userPassword = pass;}
    public void setUserName(String name) {userName = name;}
    public void setCustoemrName(String custname) {customerName = custname;}
    public void setUserAddress(String add) {userAddress = add;}
    public void setUserMobile(String mob) {userMobile = mob;}
    public void setUsergender(String gen) {userGender = gen;}
    public String getUserID() {return userID;}
    public String getUserPassword() {return userPassword;}
    public String getUserName() {return userName;}
    public String getCutomerName() {return customerName;}
    public String getUserAddress() {return userAddress;}
    public String getUserMobile() {return userMobile;}
    public String getUserGender() {return userGender;}
    public void setCustomerDetails() throws SQLException{
        Statement st = DatabaseController.conn.createStatement();
        ResultSet rs = st.executeQuery("select * from Customer where userID = '"+this.userID+"'");
        while (rs.next()){
            this.userPassword=rs.getString(2);
            this.userName=rs.getString(3);
            this.customerName=rs.getString(4);
            this.userAddress=rs.getString(5);
            this.userMobile=rs.getString(6);
            this.userGender=rs.getString(7);
        }
    }
}
