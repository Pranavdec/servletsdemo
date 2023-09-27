package com.example.week8;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UploadData {

    private static Properties getDbProperties(){
        Properties prop = new Properties();

        ClassLoader classLoader = UploadData.class.getClassLoader();

        try(InputStream inputStream = classLoader.getResourceAsStream("db.properties")){
            prop.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  prop;
    }
    public static String UploadEmployee(String id, String name,String jobTitle,String dateofBirth,String joiningDate,Integer salary,String departmentId) {
        Properties props = getDbProperties();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL database");

            String template_employee = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement insert_employee = con.prepareStatement(template_employee);


            insert_employee.setString(1, id);
            insert_employee.setString(2, name);
            insert_employee.setString(3, jobTitle);
            insert_employee.setString(4, dateofBirth);
            insert_employee.setString(5, joiningDate);
            insert_employee.setInt(6, salary);
            insert_employee.setString(7, departmentId);

            try {
                insert_employee.executeUpdate();
                return "Employee added successfully";
            }
            catch (SQLException e){
                return e.getMessage();
            }

        }
        catch (SQLException | ClassNotFoundException ex) {
            Logger lgr = Logger.getLogger(UploadData.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return "Error";
    }

    public static String UploadDepartment(String id, String name,String location) {
        Properties props = getDbProperties();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");

        System.out.println("db.url: " + url);


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL database");

            String template_department = "INSERT INTO department VALUES(?, ?, ?)";

            PreparedStatement insert_department = con.prepareStatement(template_department);

            insert_department.setString(1, id);
            insert_department.setString(2, name);
            insert_department.setString(3, location);

            try {
                insert_department.executeUpdate();
                return "Department added successfully";
            }
            catch (SQLException e){
                return e.getMessage();
            }

        }
        catch (SQLException | ClassNotFoundException ex) {
            Logger lgr = Logger.getLogger(UploadData.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return "Error";
    }
}


