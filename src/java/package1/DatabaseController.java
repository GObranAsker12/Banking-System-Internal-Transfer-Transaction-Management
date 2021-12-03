package package1;

import java.sql.*;


public class DatabaseController {
    
    public static Connection conn;

    public static void connect() throws SQLException, ClassNotFoundException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/BankDB?characterEncoding=latin1&useConfigs=maxPerformance";
            conn = DriverManager.getConnection(dbURL,"root","root");
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public String searchByUsername(String username,String password) throws SQLException, ClassNotFoundException{
        String userID="";
        try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select userID from Customer where username = '"+username+"' "
                        + "and userpassword = '"+password+"'");
                while (rs.next()) {
                    userID=rs.getString(1);
                }
            }
        catch(Exception e){
            connect();
            userID=searchByUsername(username, password);
        }
    
        return userID;
    }
}
