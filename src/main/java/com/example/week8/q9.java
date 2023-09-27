package com.example.week8;

import java.sql.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class q9 {

    private static Properties getDbProperties(){
        Properties prop = new Properties();

        try(InputStream input = q9.class.getClassLoader().getResourceAsStream("db.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(q9.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  prop;
    }

    public static void main(String[] args) {
        Properties props = getDbProperties();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");

        try (Connection con = DriverManager.getConnection(url, user, password)) {

            con.setAutoCommit(false);

            Statement st = con.createStatement();
            String query = "Insert INTO department VALUES('5','Management','hyderabad')";

            try {
                st.executeUpdate(query);
            }
            catch (SQLException e){
                System.out.println("Error in inserting: " + e.getMessage());
            }

            try {
                query = "SELECT * from employee order by joiningdate limit 3";
                ResultSet rs = st.executeQuery(query);

                query = "UPDATE employee SET DepartmentID = '5' where EmployeeID = ?";
                PreparedStatement st1 = con.prepareStatement(query);

                while (rs.next()){
                    st1.setString(1, rs.getString(1));
                    st1.executeUpdate();
                }
            }
            catch (SQLException ex) {
                Logger lgr = Logger.getLogger(q9.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
                try {
                    con.rollback();
                } catch (SQLException e) {
                    lgr.log(Level.SEVERE, "Failed to rollback transaction", e);
                }
            }

            con.commit();
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(q9.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
    }
}
