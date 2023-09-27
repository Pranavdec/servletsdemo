package com.example.week8;

import java.sql.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class q8 {

    private static Properties getDbProperties(){
        Properties prop = new Properties();

        try(InputStream input = q8.class.getClassLoader().getResourceAsStream("db.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(q8.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  prop;
    }

    public static void main(String[] args) {
        Properties props = getDbProperties();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");

        try (Connection con = DriverManager.getConnection(url, user, password);
             Scanner sc = new Scanner(System.in)) {
            System.out.println("Connected to MySQL database");

            System.out.println("Enter joining Date for getting details of employees who joined after the given date");
            String date = sc.next();

            System.out.println("Enter n year for getting details of employees are more than n years old");
            int n = sc.nextInt();

            String query = "select * from Employee where JoiningDate > ? and year(curdate()) - year(DateofBirth) > ?";

            try (PreparedStatement st = con.prepareStatement(query)){
                st.setString(1, date);
                st.setInt(2, n);

                ResultSet rs = st.executeQuery();

                while (rs.next()){
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " +
                            rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " +
                            rs.getString(7));
                }
            }
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(q8.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
